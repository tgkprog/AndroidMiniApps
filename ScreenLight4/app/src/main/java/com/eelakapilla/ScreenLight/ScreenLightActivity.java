package com.eelakapilla.ScreenLight;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ScreenLightActivity extends AppCompatActivity {

    private static final String TAG = "ScreenLight4";
    private static final int CAMERA_PERMISSION_REQUEST = 101;
    private static final int WRITE_SETTINGS_PERMISSION_REQUEST = 102;
    
    private static boolean firstTime = true;
    private CameraManager cameraManager;
    private String cameraId;
    private Context context;
    private boolean isFlashOn = false;
    private boolean hasFlash;
    float bright = 1;
    String dimScreen = "On ";
    CheckBox letBacklightDim;
    float prevDragX;
    PowerManager.WakeLock wl;

    long cnt = -1;
    int brightnessPercent = 100; // Brightness as percentage: 5, 10, 20, 30...100
    TextView txtStatus;
    TextView txtDirection;
    float xStartDrag;

    private boolean isFlashWasOn;
    private boolean keepScreenOn = true;
    private int lastDirection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtDirection = (TextView) findViewById(R.id.txtDirection);
        letBacklightDim = (CheckBox) findViewById(R.id.letLightDim);
        
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            // Ignore
        }
        
        checkPermissions();
        crt();
        setBrightnessPercent(100);
        updateDirectionIndicator();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Toast.makeText(this, "Grant permission to modify system settings for better brightness control", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraFlashSetup(context);
            } else {
                showDialog("Permission Denied", "Camera permission is required for flashlight");
            }
        }
    }

    private void crt() {
        try {
            screenBackLightOnInit();
            Log.i(TAG, "onCreate 1 ");

            cameraFlashSetup(context);
            Log.e(TAG, "onCreate 2 ");
            
            View v = findViewById(R.id.topLayout);            
            
            v.setOnTouchListener((view, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xStartDrag = event.getX();
                        return true;

                    case MotionEvent.ACTION_UP:
                        float xEnd = event.getX();
                        if (xEnd < xStartDrag) {
                            adjustBrightness(-1);
                        } else {
                            adjustBrightness(1);
                        }
                        return true;
                }
                return false;
            });

        } catch (Throwable e) {
            Log.e(TAG, "onCreate " + e, e);
        }
    }

    public void flashReInit() {
        try {
            if(firstTime){
                showDialog("Screen light", "1st Click " + new java.util.Date());
                firstTime = false;
            }
            cameraFlashSetup(context);
            turnOffFlash(true);
            turnOnFlash(true);
        } catch (Throwable e2) {
            Log.e("btn", "err " + e2, e2);

        }
    }

    public void flashTogggle(View v) {
        cnt++;
        try {

            if (!isFlashOn) {
                turnOnFlash(false);
            } else {
                turnOffFlash(false);
            }
        } catch (Throwable e2) {
            Log.e("btn", "err " + e2, e2);

        }
    }


    private void cameraFlashSetup(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            hasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if (!hasFlash) {
                showDialog("No Flash", "Your device does not have a flash light, that I can get hold of.");
                return;
            }
            
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            if (cameraManager != null) {
                try {
                    cameraId = cameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    Log.e(TAG, "Cannot access camera", e);
                }
            }
        }catch(Throwable e){
            Log.e(TAG + " fls", "cameraFlashSetup " + e, e);
        }
    }

    private void showDialog(String ttle, String msg) {
        Toast.makeText(getApplicationContext(), ttle + " " + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFlashWasOn = false;
        if (isFlashOn) {
            isFlashWasOn = true;
            turnOffFlash(true);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFlashWasOn) {
            isFlashWasOn = false;
            turnOffFlash(true);
            turnOnFlash(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart ");
    }


    private void turnOnFlash(boolean force) {
        Log.i(TAG, "fl on ");
        if (force || !isFlashOn) {
            if (cameraManager == null || cameraId == null) {
                return;
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true);
                    isFlashOn = true;
                }
            } catch (CameraAccessException e) {
                Log.e(TAG, "fl on er " + e, e);
            }
        }
    }

    private void turnOffFlash(boolean force) {
        Log.i(TAG, "fl off ");
        if (isFlashOn || force) {
            if (cameraManager == null || cameraId == null) {
                return;
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, false);
                    isFlashOn = false;
                }
            } catch (CameraAccessException e) {
                Log.e(TAG, "fl off er " + e, e);
            }
        }
    }

    void adjustBrightness(int direction) {
        lastDirection = direction;
        // direction: 1 = increase (right swipe), -1 = decrease (left swipe)
        if (direction > 0) {
            // Swipe LEFT to RIGHT - increase brightness
            if (brightnessPercent >= 100) {
                brightnessPercent = 5;
            } else if (brightnessPercent == 5) {
                brightnessPercent = 10; // +5%
            } else {
                brightnessPercent += 10; // +10%
                if (brightnessPercent > 100) brightnessPercent = 100;
            }
        } else {
            // Swipe RIGHT to LEFT - decrease brightness
            if (brightnessPercent <= 5) {
                brightnessPercent = 100; // wrap to 100
            } else if (brightnessPercent == 10) {
                brightnessPercent = 5; // -5%
            } else {
                brightnessPercent -= 10; // -10%
                if (brightnessPercent < 5) brightnessPercent = 5;
            }
        }
        setBrightnessPercent(brightnessPercent);
        updateDirectionIndicator();
    }
    
    void setBrightnessPercent(int percent) {
        try {
            brightnessPercent = percent;
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            
            // Convert percentage to brightness value (0.05 to 1.0)
            layout.screenBrightness = brightnessPercent / 100.0f;
            
            bright = layout.screenBrightness;
            txtStatus.setText(brightnessPercent + "%");
            getWindow().setAttributes(layout);
            
            Log.i(TAG, "Brightness set to " + brightnessPercent + "% (" + bright + ")");
        } catch (Throwable e) {
            Log.i("setBrightness", "err " + e, e);
        }
    }
    
    void updateDirectionIndicator() {
        if (txtDirection != null) {
            String arrow;
            if (lastDirection == 0) {
                arrow = "^";
            } else if (lastDirection == 1) {
                arrow = ">";
            } else if (lastDirection == -1) {
                arrow = "<";
            } else {
                arrow = "-";
            }
            txtDirection.setText(arrow);
        }
    }

    public void dimToggle(View v) {
        letBacklightDim = (CheckBox) v;
        keepScreenOn = letBacklightDim.isChecked();
        Log.i("dimToggle", " ch " + keepScreenOn);
        if (keepScreenOn) {
            screenBackLightOnInit();
        } else {
            screenBackLightNormal();
        }
    }


    private void screenBackLightNormal() {
        try {
            if (wl != null) {
                wl.release();
                Log.i(TAG, " wl re ");
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.i(TAG, "screen backlight ini ");
        } catch (Throwable e) {
            Log.e(TAG, "screenBackLightNormal " + e, e);
        }
        wl = null;
    }

    private void screenBackLightOnInit() {
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.e(TAG, "screen backlight ini ");
            if (wl == null) {
                PowerManager pwr = (PowerManager) getSystemService(Context.POWER_SERVICE);
                wl = pwr.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG + ":BrightScreenTorch");
                wl.setReferenceCounted(false);
                wl.acquire();
                Log.e(TAG, " wl aq ");
            }
        } catch (Throwable e) {
            Log.e(TAG, "screenBackLightOnInit " + e, e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_screen_light, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionReInitFlash) {
            cameraFlashSetup(context);
            flashReInit();
            return true;
        } else if (id == R.id.actionReInitScreen) {
            if (!keepScreenOn) {
                keepScreenOn = true;
                if (letBacklightDim != null) {
                    letBacklightDim.setChecked(true);
                }
            }
            screenBackLightNormal();
            screenBackLightOnInit();
            setBrightnessPercent(brightnessPercent);
            return true;
        } else if (id == R.id.actionFlashOff) {
            turnOffFlash(true);
            return true;
        } else if (id == R.id.actionScreenDim) {
            if (!keepScreenOn) {
                keepScreenOn = true;
                if (letBacklightDim != null) {
                    letBacklightDim.setChecked(true);
                }
                screenBackLightOnInit();
            }
            setBrightnessPercent(20);
            return true;
        } else if (id == R.id.actionAbout) {
            showDialog("Screen Light 4", 
                "Version 3.0\n\n" +
                "⚡ Tap flash icon to turn on torch (flash)\n\n" +
                "☐ Checkbox keeps screen on\n\n" +
                "↔ Swipe left/right to change screen brightness\n" +
                "   Levels: 5%, 10%, 20%, 30%, 40%, 50%,\n" +
                "           60%, 70%, 80%, 90%, 100%\n\n" +
                "© 2016-2026 sel2in.com Tushar Kapila tgkprog");
            return true;
        } else if (id == R.id.actionWebsite) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sel2in.com"));
                startActivity(browserIntent);
            } catch (Exception e) {
                showDialog("Website", "https://sel2in.com\n\nPackage: com.eelakapilla.ScreenLight");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "stop ");
        try {
            screenBackLightNormal();
            Log.e(TAG, "stop 2. ");
            turnOffFlash(true);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Throwable e) {
            Log.e(TAG, "onStop error", e);
        }
    }
}
