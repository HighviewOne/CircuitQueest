#!/usr/bin/env bash
# Build and sign the Android App Bundle (.aab) for Google Play.
#
# Play re-signs the app for delivery (Play App Signing); this signs the bundle with the
# upload key, which is the CircuitQueest release key. See play-store/README.md.
#
# Usage:  scripts/build-play-bundle.sh [output.aab]     (default: CircuitQueest-<version>.aab)
# Needs ~/.config/circuitqueest/keystore.properties (see scripts/sign-release.sh).
set -euo pipefail
cd "$(dirname "$0")/.."

props=${CQ_SIGNING_DIR:-$HOME/.config/circuitqueest}/keystore.properties
[[ -f $props ]] || { echo "missing: $props" >&2; exit 1; }
prop() { grep -E "^$1=" "$props" | head -1 | cut -d= -f2-; }

version=$(grep -oE 'versionName = "[^"]+"' app/build.gradle.kts | cut -d'"' -f2)
out=${1:-CircuitQueest-v$version.aab}

./gradlew --console=plain -q bundleRelease
src=app/build/outputs/bundle/release/app-release.aab
cp "$src" "$out"

export CQ_STORE_PW CQ_KEY_PW
CQ_STORE_PW=$(prop storePassword)
CQ_KEY_PW=$(prop keyPassword)
jarsigner -sigalg SHA256withRSA -digestalg SHA-256 \
    -keystore "$(prop storeFile)" -storepass:env CQ_STORE_PW -keypass:env CQ_KEY_PW \
    "$out" "$(prop keyAlias)" >/dev/null

jarsigner -verify "$out" | grep -m1 "jar verified"
keytool -printcert -jarfile "$out" | grep -m1 "SHA256:"
echo "bundle: $out"
