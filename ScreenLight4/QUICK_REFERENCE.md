# ScreenLight4 - Quick Reference Card

## 📱 What You Get

### Icons
- **App Icon**: 🎨 Flashlight beam with "sel2in.com" branding (ic_launcher.png)
- **Flash Button**: ⚡ Lightning bolt icon (flash.png)

### Display
Shows current brightness as percentage: `5%`, `10%`, `20%`, `30%`, `40%`, `50%`, `60%`, `70%`, `80%`, `90%`, `100%`

## 🎮 Controls

### Swipe Gestures
| Gesture | Action | From 5% | From 10% | From 20-90% | From 100% |
|---------|--------|---------|----------|-------------|-----------|
| **Swipe →** | Increase | +5% (→10%) | +10% | +10% | Wrap to 5% |
| **Swipe ←** | Decrease | Wrap to 100% | -5% (→5%) | -10% | -10% (→90%) |

### Button & Checkbox
- **⚡F Button**: Toggle flashlight ON/OFF
- **Keep Screen On**: Checkbox to prevent screen sleep (checked by default)

### Menu (⋮)
1. **Flash init** - Reset flashlight
2. **Screen init** - Reset brightness system
3. **Flash off** - Turn off flashlight  
4. **Screen dim** - Quick set to 20%
5. **About** - Version & info
6. **Website** - Open https://sel2in.com

## 🔍 Testing Checklist

### Brightness
- [ ] Launches at 100%
- [ ] Right swipe from 100% → goes to 5%
- [ ] Right swipe from 5% → goes to 10%
- [ ] Right swipe from 10% → goes to 20%
- [ ] Left swipe from 5% → goes to 100%
- [ ] Left swipe from 10% → goes to 5%
- [ ] Display shows percentage (not decimals)

### Flashlight
- [ ] Button shows ⚡ icon
- [ ] Camera permission requested
- [ ] Toggles flashlight ON/OFF
- [ ] Flashlight turns off when app paused

### Menu
- [ ] All 6 items visible (no nesting)
- [ ] About shows version & sel2in.com
- [ ] Website opens browser to https://sel2in.com
- [ ] Screen dim sets to 20% and checks box

### Checkbox
- [ ] Shows "Keep Screen On" text
- [ ] Checked by default
- [ ] Screen stays on when checked
- [ ] Screen dims normally when unchecked
- [ ] Menu "Screen init" checks the box
- [ ] Menu "Screen dim" checks the box

### Icons
- [ ] App launcher shows flashlight beam icon
- [ ] Icon has "sel2in.com" branding visible
- [ ] Flash button has lightning bolt

## 📥 Installation

```bash
# Install debug version
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.sel2in.an.screenlight/.ScreenLightActivity

# View logs for debugging
adb logcat -s ScreenLight4:* | grep -E "Brightness|flash"
```

## 🎯 Quick Test Scenario

1. **Install & Launch**
   - See flashlight icon in app drawer
   - App opens showing "100%"
   - "Keep Screen On" checked

2. **Test Brightness**
   - Swipe right → "5%"
   - Swipe right → "10%"
   - Swipe right → "20%"
   - Swipe left → "10%"
   - Swipe left → "5%"
   - Swipe left → "100%"

3. **Test Flashlight**
   - Tap ⚡F button
   - Grant camera permission
   - Flashlight turns ON
   - Tap again → OFF

4. **Test Menu**
   - Tap ⋮ menu
   - See all 6 options
   - Tap "About" → Dialog appears
   - Tap "Website" → Browser opens to sel2in.com

5. **Test Checkbox**
   - Uncheck "Keep Screen On"
   - Wait → Screen should dim/sleep
   - Tap menu "Screen dim"
   - Checkbox should be checked again

## ✅ Success Criteria

All features working:
- ✅ Brightness: Clear percentage display with proper +/-5/10% logic
- ✅ Swipe: Left/right gestures working correctly
- ✅ Flashlight: Modern Camera2 API with permission handling
- ✅ Icons: Branded launcher icon and lightning bolt button
- ✅ Menu: Flat structure with About & Website (https://sel2in.com)
- ✅ Checkbox: Labeled and synced with wake lock
- ✅ Compatible: Android 10 through 18

## 🔗 Website
**https://sel2in.com** - Opens from app menu

---
**Project**: ScreenLight4 v2.0  
**Package**: com.sel2in.an.screenlight  
**© 2024 sel2in.com**
