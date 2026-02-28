# ScreenLight4 - Updated Features & Usage

## Changes Made (v2.0)

### ✅ Brightness Control Logic
**New Percentage System**: 5%, 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%

**Right Swipe (Increase Brightness):**
- From 100% → 5% (wraps around)
- From 5% → 10% (+5%)
- From 10%-90% → +10% each swipe
- Example: 20% → 30% → 40% → ... → 90% → 100% → 5%

**Left Swipe (Decrease Brightness):**
- From 5% → 100% (wraps around)
- From 10% → 5% (-5%)
- From 20%-100% → -10% each swipe
- Example: 80% → 70% → 60% → ... → 20% → 10% → 5% → 100%

**Display**: Shows percentage (e.g., "50%", "100%", "5%")

### ✅ Menu - Flat Structure
All menu items now in single level (no nested submenus):
1. **Flash init** - Reinitialize flashlight
2. **Screen init** - Reinitialize screen brightness  
3. **Flash off** - Turn off flashlight
4. **Screen dim** - Set brightness to 20%
5. **About** - App version info
6. **Website** - Package info

### ✅ Keep Screen On Checkbox
- **Text**: "Keep Screen On"
- **Position**: Centered horizontally
- **Synced**: Checkbox state syncs with screen wake lock
- **Default**: Checked (ON) - screen stays on
- **Behavior**: When unchecked, screen can dim/sleep normally

### ✅ Flash Button with Icon
- **Icon**: ⚡ Lightning bolt icon displayed on button
- **Text**: "F" label
- **Function**: Toggle flashlight on/off

### ✅ App Icon
- **New icon**: ⚡ Lightning bolt (flash.png) used as launcher icon
- **Applied to**: All density sizes (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)

## Usage Guide

### Screen Brightness Control
1. **Swipe RIGHT** anywhere on screen → Brightness increases by 10% (or +5% from 5%)
2. **Swipe LEFT** anywhere on screen → Brightness decreases by 10% (or -5% from 10%)
3. Current brightness shown at bottom (e.g., "75%")

### Flashlight Control
1. Tap the **⚡ F** button to toggle flashlight
2. Grant camera permission when prompted (first time)

### Keep Screen On
1. **Checkbox checked** = Screen stays on (doesn't dim or sleep)
2. **Checkbox unchecked** = Normal screen timeout behavior
3. Menu option "Screen init" will also turn this ON if OFF

### Menu Options
Tap the **⋮** (three dots) menu:
- **Flash init**: Reset and reinitialize flashlight
- **Screen init**: Reset screen wake lock (turns Keep Screen On)
- **Flash off**: Force flashlight off
- **Screen dim**: Quick set to 20% brightness (and enables Keep Screen On)
- **About**: Version and feature info
- **Website**: Package name info

## Brightness Level Reference
```
5% → 10% → 20% → 30% → 40% → 50% → 60% → 70% → 80% → 90% → 100% → 5% (wraps)
←─────────────── Left Swipe (Decrease) ───────────────
                 Right Swipe (Increase) ──────────────→
```

## Testing Scenarios

### Test 1: Right Swipe Sequence
- Start: 100%
- Swipe right: 5%
- Swipe right: 10%
- Swipe right: 20%
- Continue: 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%, 5%...

### Test 2: Left Swipe Sequence  
- Start: 100%
- Swipe left: 90%
- Swipe left: 80%
- Continue: 70%, 60%, 50%, 40%, 30%, 20%, 10%, 5%, 100%...

### Test 3: Boundary Conditions
- At 100%, right swipe → 5% ✓
- At 5%, left swipe → 100% ✓
- At 5%, right swipe → 10% ✓
- At 10%, left swipe → 5% ✓

## Build & Install
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Known Issues Fixed
- ✅ Fixed confusing "1 0.1" and "10 0.1" display → now shows "5%", "100%"
- ✅ Menu no longer nested (was hard to see options)
- ✅ Checkbox now has descriptive text
- ✅ Flash button has icon
- ✅ Screen wake lock synced with checkbox
- ✅ "Screen dim" menu enables Keep Screen On automatically
