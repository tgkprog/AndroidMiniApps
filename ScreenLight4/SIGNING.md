# Signing Configuration for ScreenLight4

## Automatic Signing (Gradle)

### Configuration in `app/build.gradle`
```gradle
signingConfigs {
    release {
        storeFile file("/data/code/gt/tgk/AndroidMiniApps/tk2.jks")
        storePassword System.getenv("S2n_Jks")
        keyAlias "s2n"
        keyPassword System.getenv("S2n_Jks")
    }
}

buildTypes {
    release {
        signingConfig signingConfigs.release
    }
}
```

### Build Signed Release
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk (SIGNED)
```

---

## Manual Signing (If Gradle signing fails)

### Keystore Information (from android_sign.txt)
- **Keystore**: `/data/rme1/private/s2n/tk2.jks` or `/data/code/gt/tgk/AndroidMiniApps/tk2.jks`
- **Password**: Environment variable `$S2n_Jks`
- **Key Alias**: `s2n` (try also: `androiddebugkey`, `key0`, or list with keytool)
- **Signer**: CN=Tushar Kapila, OU=IT, O=sel2in, L=Utah, ST=Tx, C=US

### Fingerprints
- **SHA-256**: `8e:9b:00:41:7c:c2:51:1d:3e:6c:3c:55:88:7f:ab:3a:c6:68:b0:57:f9:e5:a0:61:e7:13:e0:90:39:05:c7:f6`
- **SHA-1**: `1b:9b:22:d6:1f:cd:51:ca:2c:89:b7:3e:d2:f9:3d:c2:da:86:05:8f`
- **MD5**: `a9:39:73:50:71:38:e8:74:87:55:c9:05:4f:b3:d4:5b`

### Manual Signing Steps

#### 1. Build Unsigned Release
```bash
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

#### 2. List Keystore Aliases (if needed)
```bash
keytool -list -v -keystore /data/rme1/private/s2n/tk2.jks -storepass "$S2n_Jks"
```

#### 3. Sign with jarsigner
```bash
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore /data/rme1/private/s2n/tk2.jks \
  -storepass "$S2n_Jks" \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  s2n
```

#### 4. Verify Signature
```bash
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release-unsigned.apk
```

#### 5. Align APK (using apksigner - recommended)
```bash
# Method 1: Using apksigner (modern, recommended)
apksigner sign --ks /data/rme1/private/s2n/tk2.jks \
  --ks-key-alias s2n \
  --ks-pass env:S2n_Jks \
  --key-pass env:S2n_Jks \
  --out app/build/outputs/apk/release/app-release-signed.apk \
  app/build/outputs/apk/release/app-release-unsigned.apk

# Verify
apksigner verify app/build/outputs/apk/release/app-release-signed.apk
```

#### 6. Align with zipalign (if using jarsigner)
```bash
zipalign -v -p 4 \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/app-release-signed-aligned.apk
```

---

## Alternative: Create Debug Keystore Signing

If you just need a consistently signed debug build:

```gradle
// In app/build.gradle
signingConfigs {
    debug {
        storeFile file("debug.keystore")
        storePassword "android"
        keyAlias "androiddebugkey"
        keyPassword "android"
    }
}
```

---

## Troubleshooting

### If "Keystore was tampered with, or password was incorrect"

**Check password:**
```bash
echo $S2n_Jks  # Should show the password
```

**Try different alias names:**
```bash
# Common aliases to try: s2n, key0, androiddebugkey, mykey
jarsigner -keystore /data/rme1/private/s2n/tk2.jks -list
```

**Test keystore integrity:**
```bash
keytool -list -keystore /data/rme1/private/s2n/tk2.jks
# Enter password manually when prompted
```

### If you need to create a new keystore:
```bash
keytool -genkeypair -v \
  -keystore /data/code/gt/tgk/AndroidMiniApps/ScreenLight4/release.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias s2n \
  -dname "CN=Tushar Kapila, OU=IT, O=sel2in, L=Utah, ST=TX, C=US"
```

---

## Quick Manual Signing

If automatic signing doesn't work, use this one-liner:

```bash
# Using apksigner (recommended for Android)
cd /data/code/gt/tgk/AndroidMiniApps/ScreenLight4
./gradlew assembleRelease && \
apksigner sign --ks /data/rme1/private/s2n/tk2.jks \
  --ks-key-alias s2n \
  --out app/build/outputs/apk/release/app-release-signed.apk \
  app/build/outputs/apk/release/app-release-unsigned.apk
```

---

## Current Setup

**Build configuration includes signing setup but commented out to allow unsigned builds.**

To enable automatic signing:
1. Verify keystore password and alias
2. Uncomment the line in `app/build.gradle`:
   ```gradle
   signingConfig signingConfigs.release
   ```
3. Run: `./gradlew assembleRelease`

**Current output**: `app-release-unsigned.apk` (needs manual signing)
