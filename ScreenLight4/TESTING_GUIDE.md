# ScreenLight4 Testing Guide

## Installation
```bash
adb install -r /data/code/gt/tgk/AndroidMiniApps/ScreenLight4/app/build/outputs/apk/debug/app-debug.apk
```

## Test Cases

### Test 1: Brightness Percentage Display
**Expected**: Display shows percentage format (5%, 10%, 20%...100%)
- Launch app
- Check display at bottom shows "100%"
- ✅ PASS: No more "1 0.1" or "10 0.1" display

### Test 2: Right Swipe (Increase Brightness)
**Starting at 100%:**
1. Swipe right → Should show **5%** (wrap around)
2. Swipe right → Should show **10%** (+5% increment)
3. Swipe right → Should show **20%** (+10% increment)
4. Continue swiping right → 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%, 5%...

**Expected behavior:**
- ✅ At 100% → wraps to 5%
- ✅ At 5% → increases by +5% to 10%
- ✅ At 10%-90% → increases by +10%

### Test 3: Left Swipe (Decrease Brightness)
**Starting at 100%:**
1. Swipe left → Should show **90%** (-10% decrement)
2. Continue → 80%, 70%, 60%, 50%, 40%, 30%, 20%
3. At 20% swipe left → **10%** (-10%)
4. At 10% swipe left → **5%** (-5% increment)
5. At 5% swipe left → **100%** (wrap around)

**Expected behavior:**
- ✅ At 5% → wraps to 100%
- ✅ At 10% → decreases by -5% to 5%
- ✅ At 20%-100% → decreases by -10%

### Test 4: Boundary Testing
| Current | Swipe Direction | Expected Result |
|---------|----------------|-----------------|
| 100% | Right | 5% |
| 100% | Left | 90% |
| 5% | Right | 10% |
| 5% | Left | 100% |
| 10% | Right | 20% |
| 10% | Left | 5% |
| 50% | Right | 60% |
| 50% | Left | 40% |

### Test 5: Keep Screen On Checkbox
1. Launch app → Checkbox should be **checked** by default
2. Wait 30 seconds → Screen should **NOT** dim or sleep
3. Uncheck the checkbox
4. Wait for device timeout → Screen should dim/sleep normally
5. Check the checkbox again → Screen should stay on
6. Tap menu → Screen init → Checkbox should be checked

**Sync test:**
- Uncheck box → Screen can dim
- Use menu "Screen dim" → Checkbox should become checked
- Use menu "Screen init" → Checkbox should become checked

### Test 6: Flash Button with Icon
1. Check button shows ⚡ lightning bolt icon on the left side
2. Button shows "F" text
3. Tap button → Camera permission requested (first time)
4. Grant permission
5. Tap again → Flashlight turns ON
6. Tap again → Flashlight turns OFF

### Test 7: Menu - Flat Structure
1. Tap **⋮** (overflow menu)
2. Should see **all 6 items** in one list:
   - Flash init
   - Screen init
   - Flash off
   - Screen dim
   - About
   - Website
3. NO nested "→" submenu

**Test each menu item:**
- **Flash init**: Reinitializes flashlight (off then on if was on)
- **Screen init**: Re-enables screen wake, checkbox becomes checked
- **Flash off**: Turns flashlight off
- **Screen dim**: Sets brightness to 20%, checkbox becomes checked
- **About**: Shows dialog "Screen Light 4, Version 2.0..."
- **Website**: Shows dialog with package name

### Test 8: App Icon
1. Go to app drawer/home screen
2. App icon should be ⚡ **lightning bolt** (yellow/gold color)
3. ✅ PASS: No longer generic Android icon

## Android Version Testing Matrix

| Android Version | API Level | Test Status |
|----------------|-----------|-------------|
| Android 10 | 29 | Min SDK - Should work |
| Android 11 | 30 | Should work |
| Android 12 | 31 | Should work |
| Android 13 | 33 | Should work |
| Android 14 | 34 | Should work |
| Android 15 | 35 | Target SDK - Fully compatible |
| Android 16+ | 36+ | Forward compatible |

## Permission Testing

### Camera Permission (Flashlight)
1. First launch → Permission dialog should appear
2. **Allow** → Flashlight button works
3. **Deny** → Toast message shown, flashlight doesn't work
4. Can still use brightness control without this permission

### Screen Brightness
- No special permission needed
- Works immediately on launch
- Per-window brightness (doesn't affect system setting)

## Lifecycle Testing

### Background/Foreground
1. Launch app with flashlight ON and brightness at 50%
2. Press Home button → Flashlight should turn OFF
3. Return to app → Flashlight remains OFF
4. Brightness should still be 50%

### Screen Rotation
- App locked to **portrait** mode (no rotation)

### App Stop
1. Launch app
2. Enable Keep Screen On
3. Press Back or stop app
4. Screen should return to normal timeout behavior

## Bug Fixes Verified

| Issue | Status | Fix |
|-------|--------|-----|
| Shows "1 0.1, 10 0.1" | ✅ FIXED | Now shows "5%, 10%, 20%..." |
| Menu hard to see (nested) | ✅ FIXED | Flat menu structure |
| Checkbox has no text | ✅ FIXED | Shows "Keep Screen On" |
| Flash button no icon | ✅ FIXED | Has ⚡ icon |
| Generic app icon | ✅ FIXED | Lightning bolt icon |
| Screen turns off | ✅ FIXED | Synced with checkbox |
| Brightness logic confusing | ✅ FIXED | Clear +/-5/10% logic |

## Quick Test Commands
```bash
# Install fresh
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch
adb shell am start -n com.sel2in.an.screenlight/.ScreenLightActivity

# View logs
adb logcat -s ScreenLight4:* -c && adb logcat -s ScreenLight4:*

# Check brightness changes in logs
# Should see: "Brightness set to XX% (0.XX)"
```

## Expected Log Output
```
ScreenLight4: Brightness set to 100% (1.0)
ScreenLight4: Brightness set to 5% (0.05)
ScreenLight4: Brightness set to 10% (0.1)
ScreenLight4: Brightness set to 20% (0.2)
```
