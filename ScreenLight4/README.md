# ScreenLight4

Modern Android app for screen brightness control via swipe gestures and flashlight toggle.

## Description
ScreenLight4 is a migrated and modernized version of ScreenLight3, updated to work with Android 10+ through Android 18 (API levels 29-35).

## Features
- **Swipe to control screen brightness**: Swipe left/right to adjust brightness in 10 levels
- **Flashlight toggle**: Button to turn flashlight on/off
- **Keep screen awake**: Option to prevent screen from dimming
- **Menu options**: Reinitialize flash/screen, turn off flash, set dim mode

## Migration Changes from ScreenLight3

### Build System
- Updated from Gradle 2.1.0 to 8.2 with Android Gradle Plugin 8.2.2
- Migrated from old `compile` dependencies to `implementation`
- Updated from API 23 to API 35 (compileSdk)
- Changed minSdk from 16 to 29 (Android 10+)

### Code Modernization
- **Camera API**: Replaced deprecated `android.hardware.Camera` with `android.hardware.camera2.CameraManager` for flashlight control
- **Drag API**: Updated from deprecated `startDrag()` to `startDragAndDrop()`
- **WakeLock**: Fixed tag format to comply with modern Android lint requirements (TAG:suffix format)
- **AndroidX**: Migrated from support library to AndroidX
- **Permissions**: Added runtime permission handling for Camera access
- **Namespace**: Added namespace declaration in build.gradle (modern requirement)

### Permissions & Manifest
- Added `android:exported="true"` for launcher activity (required for Android 12+)
- Added `WRITE_SETTINGS` permission for better brightness control
- Made camera features optional (`android:required="false"`) for devices without flash
- Fixed deprecated `uses-sdk` declaration (now in build.gradle)
- Set portrait orientation for better UX

## Build & Run

### Build Debug APK
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release-unsigned.apk`

### Install on Device
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Compatibility
- **Minimum SDK**: Android 10 (API 29)
- **Target SDK**: Android 15 (API 35)
- **Tested on**: Android 10, 14, 15, 16 (as per requirements)

## Usage
1. Launch the app
2. Grant camera permission when prompted (for flashlight)
3. **Swipe left/right** anywhere on screen to adjust brightness (10 levels)
4. Tap **F button** to toggle flashlight
5. Use **checkbox** to enable/disable screen wake lock
6. Use **menu (⋮)** for additional options:
   - Flash init: Reinitialize flashlight
   - Screen init: Reinitialize screen brightness
   - Flash off: Turn off flashlight
   - Screen dim: Set to dim level

## Technical Details
- Package: `com.sel2in.an.screenlight`
- Min SDK: 29 (Android 10)
- Target SDK: 35 (Android 15)
- Build Tools: Gradle 8.2, AGP 8.2.2
- Language: Java 11
- Dependencies: AndroidX AppCompat, Material Components

## Known Behaviors
- Screen brightness changes are per-window and don't persist after app closes
- Flashlight requires camera permission
- Some devices may have limitations on minimum brightness
- WakeLock keeps screen on even when device would normally sleep
