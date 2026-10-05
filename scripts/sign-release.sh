#!/usr/bin/env bash
# Sign a release APK with key rotation (APK Signature Scheme v3.1).
#
# Releases up to v2.4 were signed with the Android debug key. From v2.5 on, the
# release key takes over; the rotation lineage (old key vouching for the new one)
# lets the new APK install as an update over old ones, keeping players' progress.
#
# Usage:  scripts/sign-release.sh <unsigned.apk> <signed-output.apk>
#   e.g.  ./gradlew assembleRelease
#         scripts/sign-release.sh app/build/outputs/apk/release/app-release-unsigned.apk CircuitQueest-v2.5.apk
#
# Needs (never committed; backed up on hamptonserver:~/backups/circuitqueest-signing/):
#   ~/.config/circuitqueest/keystore.properties  storeFile, storePassword, keyAlias, keyPassword
#   ~/.config/circuitqueest/rotation.lineage     debug key -> release key
#   ~/.android/debug.keystore                    the original signer (still required)
# Override the directory with CQ_SIGNING_DIR, the SDK with ANDROID_HOME.
set -euo pipefail

in_apk=${1:?usage: $0 <unsigned.apk> <signed-output.apk>}
out_apk=${2:?usage: $0 <unsigned.apk> <signed-output.apk>}

signing_dir=${CQ_SIGNING_DIR:-$HOME/.config/circuitqueest}
props="$signing_dir/keystore.properties"
lineage="$signing_dir/rotation.lineage"
old_ks="$HOME/.android/debug.keystore"
sdk=${ANDROID_HOME:-$HOME/.local/android-sdk}
apksigner=$(ls -d "$sdk"/build-tools/*/apksigner | sort -V | tail -1)

for f in "$in_apk" "$props" "$lineage" "$old_ks" "$apksigner"; do
    [[ -e $f ]] || { echo "missing: $f" >&2; exit 1; }
done

prop() { grep -E "^$1=" "$props" | head -1 | cut -d= -f2-; }
new_ks=$(prop storeFile)
new_alias=$(prop keyAlias)
export CQ_NEW_STORE_PW CQ_NEW_KEY_PW
CQ_NEW_STORE_PW=$(prop storePassword)
CQ_NEW_KEY_PW=$(prop keyPassword)

"$apksigner" sign \
    --ks "$old_ks" --ks-key-alias androiddebugkey --ks-pass pass:android \
    --next-signer \
    --ks "$new_ks" --ks-key-alias "$new_alias" \
    --ks-pass env:CQ_NEW_STORE_PW --key-pass env:CQ_NEW_KEY_PW \
    --lineage "$lineage" \
    --v4-signing-enabled false \
    --out "$out_apk" "$in_apk"

"$apksigner" verify --verbose --print-certs "$out_apk" |
    grep -E "^Verified using|^Signer|rotation|lineage" || true
echo "signed: $out_apk"
