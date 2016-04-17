package com.sel2in.an.learn.animate.animate1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class DoggyAnimActivity extends Activity {

    Timer tmr = null;
    ImageView i1, i2;
    public void anim(View view){
        //i1.setAlpha(1f);
        //i1.setImageResource(R.drawable.beagle2);//beagle2
       if(i1.getAlpha() == 0){//use another var for state
           i1.animate().alpha(1f).setDuration(2300);
           i2.animate().alpha(0f).setDuration(2000);
       }else{
           i2.animate().alpha(1f).setDuration(2300);
           i1.animate().alpha(0f).setDuration(2000);
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_puppy);
        tmr = new Timer("anim_dog", true);
        i1 = (ImageView) findViewById(R.id.aniImg);
        i2 = (ImageView) findViewById(R.id.aniImg2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puppy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}