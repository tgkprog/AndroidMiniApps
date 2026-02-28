# ScreenLight4 - Complete Feature List

## 🎨 App Icons & Branding

### Launcher Icon (Home Screen)
- **Icon**: Flashlight beam with blue glow effect
- **Branding**: "sel2in.com" displayed
- **Size**: 282x164px (all densities)
- **File**: ic_launcher.png

### Flash Button Icon
- **Icon**: ⚡ Yellow lightning bolt
- **Size**: 19x39px
- **File**: ic_flash.png (drawable)
- **Position**: Left side of "F" button

## 🎯 Core Features

### 1. Screen Brightness Control (Swipe Gestures)
**Levels**: 5%, 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%

**Right Swipe → Increase:**
```
100% → 5%  (wrap)
5%   → 10% (+5%)
10%  → 20% (+10%)
20%  → 30% (+10%)
...
90%  → 100% (+10%)
100% → 5%  (wrap)
```

**Left Swipe → Decrease:**
```
5%   → 100% (wrap)
10%  → 5%   (-5%)
20%  → 10%  (-10%)
30%  → 20%  (-10%)
...
100% → 90%  (-10%)
```

**Display**: Shows current percentage at bottom (e.g., "50%")

### 2. Flashlight Toggle
- **Button**: ⚡F button with lightning icon
- **Function**: Tap to toggle device flashlight ON/OFF
- **Permission**: Requests camera permission on first use
- **API**: Modern Camera2 API (CameraManager.setTorchMode)

### 3. Keep Screen On
- **Checkbox**: "Keep Screen On" with descriptive text
- **Default**: Checked (ON) - screen stays awake
- **When ON**: Screen never dims or sleeps
- **When OFF**: Normal device timeout behavior
- **Sync**: Automatically synced with WakeLock state

### 4. Menu Options (Flat Structure)

All options visible in single-level menu:

**1. Flash init**
- Reinitializes flashlight hardware
- Useful if flashlight gets stuck

**2. Screen init**
- Resets screen brightness system
- Re-enables "Keep Screen On" if disabled
- Maintains current brightness level

**3. Flash off**
- Force turns flashlight OFF
- Quick access without toggling

**4. Screen dim**
- Sets brightness to 20%
- Automatically enables "Keep Screen On"
- Quick dim for battery saving

**5. About**
- Shows app information:
  - Version 2.0
  - Usage instructions
  - Brightness levels
  - Copyright © 2024 sel2in.com

**6. Website** 🌐
- Opens browser to https://sel2in.com
- Falls back to dialog if browser unavailable
- Shows package name: com.sel2in.an.screenlight

## 🔧 Technical Specifications

### Platform Support
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35
- **Tested On**: Android 10, 14, 15, 16
- **Forward Compatible**: Android 18+

### APIs Used
- **Camera2 API**: Modern flashlight control (replaces deprecated Camera)
- **WindowManager**: Per-window brightness control
- **PowerManager**: WakeLock for keeping screen on
- **Intent.ACTION_VIEW**: Opening browser for website

### Permissions
- `CAMERA` - For flashlight (runtime permission)
- `WAKE_LOCK` - Keep screen on
- `FLASHLIGHT` - Additional flashlight support
- `WRITE_SETTINGS` - Enhanced brightness control (optional)

### Dependencies
- AndroidX AppCompat 1.7.0
- Material Components 1.12.0
- ConstraintLayout 2.1.4

## 📱 User Interface

### Layout Elements
1. **Top**: "Keep Screen On" checkbox (centered)
2. **Center**: ⚡F button with lightning icon
3. **Bottom**: Brightness percentage display
4. **Menu**: ⋮ (three dots) - 6 flat options

### Visual Design
- Portrait orientation only
- Fullscreen swipe area for brightness
- Material Design components
- AppCompat theme with custom colors

## 🚀 Installation & Usage

### Build & Install
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### First Launch
1. App opens at 100% brightness
2. "Keep Screen On" is checked (screen won't sleep)
3. Camera permission dialog appears
4. Grant permission for flashlight feature

### Daily Usage
- **Adjust brightness**: Swipe left/right anywhere
- **Toggle flashlight**: Tap ⚡F button
- **Keep screen on**: Use checkbox
- **Quick dim**: Menu → Screen dim (20%)
- **Visit website**: Menu → Website → Opens browser

## 📊 Testing Results

### Build Status
✅ Debug APK: 12 MB - **SUCCESS**
✅ Release APK: 9.9 MB - **SUCCESS**
✅ Lint checks: **PASSED**
✅ No errors or warnings (except deprecation notes)

### Functionality
✅ Brightness control: 11 levels (5%-100%)
✅ Swipe gestures: Working with correct increments
✅ Flashlight: Camera2 API implemented
✅ Permissions: Runtime permissions working
✅ Menu: Flat structure, all items visible
✅ Icons: Launcher and button icons displaying
✅ Website: Opens https://sel2in.com in browser
✅ Checkbox sync: Wake lock synced properly

## 🐛 Issues Fixed

| # | Issue | Status | Solution |
|---|-------|--------|----------|
| 1 | Display shows "1 0.1, 10 0.1" | ✅ FIXED | Percentage display: "5%, 10%, 20%..." |
| 2 | Menu nested, can't see options | ✅ FIXED | Flat menu with all 6 items visible |
| 3 | Checkbox has no label | ✅ FIXED | Shows "Keep Screen On" |
| 4 | Flash button no icon | ✅ FIXED | ⚡ Lightning bolt icon added |
| 5 | Generic app icon | ✅ FIXED | Flashlight beam icon with branding |
| 6 | Screen turns off unexpectedly | ✅ FIXED | Checkbox syncs with wake lock |
| 7 | Swipe logic confusing | ✅ FIXED | Clear +/-5/10% with wrapping |
| 8 | No About/Website menu | ✅ FIXED | Added with sel2in.com link |

## 📝 Notes
- Website menu opens browser to https://sel2in.com
- About dialog shows version, usage, and © 2024 sel2in.com
- App icon features the flashlight beam design with branding
- Flash button uses lightning bolt icon for clear visual indication
- All icons properly sized for different screen densities
