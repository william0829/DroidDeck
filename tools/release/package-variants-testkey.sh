#!/usr/bin/env bash
# Repackage the single testkey-signed release APK into the four disguise
# variants, signed only with the committed AOSP testkey. This mirrors
# sign-apk.sh's manifest/authority rewrite but skips the release-key
# rotation/lineage, which needs a private keystore secret not present in
# this fork. Outputs: <outdir>/DroidDeckWOW-<label><suffix>.apk
set -euo pipefail

die() { echo "::error::$*" >&2; exit 1; }
[ $# -ge 1 ] || die "usage: $0 <built.apk> [outdir] [label]"

in="$1"
outdir="${2:-variants}"
label="${3:-0.7.1}"
here=$(cd "$(dirname "$0")" && pwd)
repo=$(cd "$here/../.." && pwd)

[ -f "$in" ] || die "input apk not found: $in"
TOOLS=$(ls -d "$ANDROID_HOME"/build-tools/* 2>/dev/null | sort -V | tail -n1)
[ -n "$TOOLS" ] || die "no Android build-tools under ANDROID_HOME"
AAPT="$TOOLS/aapt"
APKSIGNER="$TOOLS/apksigner"
ZIPALIGN="$TOOLS/zipalign"
TESTKEY="$repo/keystore/testkey.p12"
[ -f "$TESTKEY" ] || die "testkey missing: $TESTKEY"

P=com.droiddeck.launcher
mkdir -p "$outdir"

while read -r name pkg suffix; do
  [ -n "$name" ] || continue
  case "$name" in \#*) continue;; esac
  [ "$suffix" = "-" ] && suffix=""

  work=$(mktemp -d)
  trap 'rm -rf "$work"' EXIT

  if [ "$pkg" = "$P" ]; then
    python3 "$here/axml_rename.py" "$in" "$work/renamed.apk"
  else
    python3 "$here/axml_rename.py" "$in" "$work/renamed.apk" \
      "$P=$pkg" "$P.androidx-startup=$pkg.androidx-startup" "$P.logs=$pkg.logs" \
      "$P.pairingguide=$pkg.pairingguide" "$P.devicecode=$pkg.devicecode" "$P.pin=$pkg.pin" \
      "$P.documents=$pkg.documents" "$P.home=$pkg.home" "$P.agent=$pkg.agent" \
      "$P.vr_recovery=$pkg.vr_recovery" "$P.dualscreen=$pkg.dualscreen" "$P.typing=$pkg.typing" \
      "$P.keeptop=$pkg.keeptop" \
      "$P.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION=$pkg.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
  fi

  got=$("$AAPT" dump badging "$work/renamed.apk" | sed -n "s/^package: name='\([^']*\)'.*/\1/p")
  [ "$got" = "$pkg" ] || die "$name: apk says package '$got', not '$pkg'"
  if [ "$pkg" != "$P" ]; then
    tree=$("$AAPT" dump xmltree "$work/renamed.apk" AndroidManifest.xml)
    grep -E 'android:(authorities|taskAffinity)' <<<"$tree" | grep -F "\"$P." \
      && die "$name: manifest still names $P in an authority or task"
  fi

  # Companion system-keys helper, if bundled: re-sign it with the testkey too.
  companion=assets/shortcuts.apk
  if unzip -l "$work/renamed.apk" "$companion" >/dev/null 2>&1; then
    mkdir -p "$work/companion/assets"
    unzip -p "$work/renamed.apk" "$companion" > "$work/companion-in.apk"
    "$APKSIGNER" sign --ks "$TESTKEY" --ks-type PKCS12 --ks-pass pass:android \
      --ks-key-alias testkey --key-pass pass:android \
      --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true \
      --out "$work/companion/$companion" "$work/companion-in.apk"
    (cd "$work/companion" && zip -q -0 "$work/renamed.apk" "$companion")
  fi

  "$ZIPALIGN" -p -f 4 "$work/renamed.apk" "$work/aligned.apk"
  out="$outdir/DroidDeckWOW-$label$suffix.apk"
  "$APKSIGNER" sign --ks "$TESTKEY" --ks-type PKCS12 --ks-pass pass:android \
    --ks-key-alias testkey --key-pass pass:android \
    --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true \
    --out "$out" "$work/aligned.apk"
  rm -f "$out.idsig"

  "$APKSIGNER" verify --min-sdk-version 26 "$out" >/dev/null || die "$name: does not verify"
  echo "$name ($pkg) -> $(basename "$out")"
  rm -rf "$work"
  trap - EXIT
done < "$here/variants.txt"
