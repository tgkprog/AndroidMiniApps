# ScreenLight4 - Swipe Direction Guide

## 📱 Corrected Swipe Logic

### Visual Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    SCREEN                                │
│                                                          │
│         Swipe LEFT ←→ RIGHT (Increase)                  │
│         Swipe RIGHT ←→ LEFT (Decrease)                  │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

---

## Swipe LEFT → RIGHT (Increase Brightness)

**Direction**: Finger moves from LEFT edge to RIGHT edge

```
Start    →    5%    →    10%   →    20%   →    30%   →    40%
  ↓
 5%            +5%        +10%       +10%       +10%       +10%

40%   →    50%   →    60%   →    70%   →    80%   →    90%
        +10%       +10%       +10%       +10%       +10%

90%   →    100%  →    5%
        +10%       wrap
```

**Steps:**
- 5% → 10% (special: +5%)
- 10% → 20% → 30% → 40% → 50% → 60% → 70% → 80% → 90% → 100% (normal: +10%)
- 100% → 5% (wrap around)

---

## Swipe RIGHT → LEFT (Decrease Brightness)

**Direction**: Finger moves from RIGHT edge to LEFT edge

```
Start   ←    100%  ←    90%   ←    80%   ←    70%   ←    60%
  ↓
100%          -10%       -10%       -10%       -10%       -10%

60%   ←    50%   ←    40%   ←    30%   ←    20%   ←    10%
        -10%       -10%       -10%       -10%       -10%

10%   ←    5%    ←    100%
        -5%        wrap
```

**Steps:**
- 100% → 90% → 80% → 70% → 60% → 50% → 40% → 30% → 20% → 10% (normal: -10%)
- 10% → 5% (special: -5%)
- 5% → 100% (wrap around)

---

## Quick Reference Table

| Current | Swipe L→R | Result | Swipe R→L | Result |
|---------|-----------|--------|-----------|--------|
| 5% | → | 10% (+5%) | ← | 100% (wrap) |
| 10% | → | 20% (+10%) | ← | 5% (-5%) |
| 20% | → | 30% (+10%) | ← | 10% (-10%) |
| 30% | → | 40% (+10%) | ← | 20% (-10%) |
| 40% | → | 50% (+10%) | ← | 30% (-10%) |
| 50% | → | 60% (+10%) | ← | 40% (-10%) |
| 60% | → | 70% (+10%) | ← | 50% (-10%) |
| 70% | → | 80% (+10%) | ← | 60% (-10%) |
| 80% | → | 90% (+10%) | ← | 70% (-10%) |
| 90% | → | 100% (+10%) | ← | 80% (-10%) |
| 100% | → | 5% (wrap) | ← | 90% (-10%) |

---

## Testing Instructions

### Test Swipe RIGHT → LEFT (Decrease)
1. Start app (shows 100%)
2. Swipe finger **RIGHT → LEFT**: 100% → 90% ✓
3. Swipe **RIGHT → LEFT** again: 90% → 80% ✓
4. Continue until: 20% → 10% → 5% → 100% (wraps) ✓

### Test Swipe LEFT → RIGHT (Increase)
1. Start at any brightness
2. Swipe finger **LEFT → RIGHT**: increases by 10%
3. At 5%: swipe **LEFT → RIGHT**: 5% → 10% ✓
4. At 100%: swipe **LEFT → RIGHT**: 100% → 5% (wraps) ✓

---

## Display Format

**Old (BROKEN)**: "On 1 0.1", "On 10 0.1", "N 1 1.0"
**New (FIXED)**: "5%", "10%", "20%", "30%"..."100%"

---

## Complete Feature List

✅ App icon: Flashlight beam with sel2in.com
✅ Flash button icon: ⚡ Lightning bolt
✅ Swipe L→R: Increase (5%, 10%, 20%...100%, wrap to 5%)
✅ Swipe R→L: Decrease (100%, 90%...10%, 5%, wrap to 100%)
✅ Display: Percentage format
✅ Checkbox: "Keep Screen On" with sync
✅ Menu: Flat 6 items
✅ About: Version & copyright
✅ Website: Opens https://sel2in.com
✅ Signing: Configuration ready

---

**Ready to install and test!** 🚀
