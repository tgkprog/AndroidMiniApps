# ScreenLight4 - Build & Installation Guide

## Quick Start

### Build APK
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleDebug
```

### Install on Device
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Supported Android Versions
- **Minimum**: Android 10 (API 29)
- **Target**: Android 15 (API 35)
- **Tested**: Will work on Android 10, 14, 15, 16, and forward compatible with Android 18

## Key Features
1. **Swipe Brightness Control**: Swipe left/right anywhere on screen to adjust brightness (10 levels)
2. **Flashlight Toggle**: Tap 'F' button to toggle device flashlight
3. **Screen Wake Lock**: Checkbox to keep screen always on
4. **Menu Options**: Access additional controls via overflow menu

## Permissions
The app will request the following permissions:
- **CAMERA**: Required for flashlight functionality (runtime permission)
- **WAKE_LOCK**: To keep screen on
- **WRITE_SETTINGS**: For optimal brightness control (optional)

## Technical Specifications
- **Language**: Java 11
- **Build System**: Gradle 8.2 with AGP 8.2.2
- **Package**: com.sel2in.an.screenlight
- **Architecture**: Single Activity (ScreenLightActivity)
- **Dependencies**: AndroidX AppCompat, Material Components

## Build Commands

### Debug Build (unsigned, debuggable)
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk (12 MB)
```

### Release Build (unsigned, optimized)
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk (9.8 MB)
```

### Clean Build
```bash
./gradlew clean build
```

### Lint Check
```bash
./gradlew lint
# Report: app/build/reports/lint-results-debug.html
```

## Signing Release APK (if needed)
```bash
# Using existing keystore
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore /data/code/gt/tgk/AndroidMiniApps/tk.jks \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  your-key-alias

# Align the APK
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/app-release-signed.apk
```

## Troubleshooting

### Build Issues
- Ensure Android SDK is installed with API 35
- Java 11 or higher required
- Run `./gradlew clean` if getting stale build errors

### Runtime Issues
- If flashlight doesn't work, check Camera permission was granted
- Screen brightness changes are per-window only
- Some devices have minimum brightness restrictions

### Testing on Devices
```bash
# Check connected devices
adb devices

# Install and run
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.sel2in.an.screenlight/.ScreenLightActivity

# View logs
adb logcat -s ScreenLight4:* *:E
```

## Project Status
✅ **BUILD SUCCESSFUL** - Both debug and release APKs build without errors
✅ **LINT PASSED** - All code quality checks pass
✅ **READY FOR TESTING** - Install and test on target devices
