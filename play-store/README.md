# Google Play launch kit

Everything needed to publish CircuitQuEEst on Google Play: listing text, graphics, form answers, and
the console steps. Text fields are within Play's limits (checked by `play-store/check-listing.py`).

## Assets in this folder

| File | Play Console field | Spec |
|---|---|---|
| `graphics/icon-512.png` | App icon | 512×512 PNG |
| `graphics/feature-graphic-1024x500.png` | Feature graphic | 1024×500, no alpha |
| `screenshots/*.png` | Phone screenshots | 1080×1920 (≤ 2:1), 2–8 required; regenerate with the *Store Screenshots* workflow |
| `listing/*.txt` | Store listing text | see below |
| Privacy policy URL | App content → Privacy policy | https://highviewone.github.io/CircuitQueest/privacy.html |

Build the upload bundle with `scripts/build-play-bundle.sh` → `CircuitQueest-vX.Y.aab`.

---

## Step by step

### 1. Developer account (you)
1. Go to https://play.google.com/console/signup, choose **Personal** (just you; no D-U-N-S number needed).
2. Pay the one-time **$25** fee, verify your identity (government ID) and phone number. Google can take
   a few days to verify.
3. Personal accounts must also verify they have a real Android device — the Play Console mobile app
   walks you through it on your phone.

### 2. Create the app
**Play Console → Create app**
- App name: `CircuitQuEEst: Electronics` (`listing/title.txt`)
- Default language: English (United States)
- App or game: **App** · Free or paid: **Free**
- Accept the declarations.

### 3. App signing — choose "Use your own key" *(important, one-time)*
When you first upload a bundle (step 6), Play asks how to sign the app it delivers:
- **Recommended: "Use existing app signing key from Java keystore"**, uploading
  `~/.config/circuitqueest/release.jks`. Then Play's downloads and the GitHub APKs are signed with
  the same key, so players can move between them without uninstalling (on Android 13+).
  Play shows a `pepk` command to run; send me its exact text and I'll run it with the right paths.
- Alternative: let Google generate the key. Simpler, but a GitHub install and a Play install can
  never update each other — switching source means uninstalling and losing progress.

The bundle itself is signed with `release.jks` as the **upload key** either way.

### 4. Store listing
**Grow → Store presence → Main store listing**
- Short description: `listing/short-description.txt`
- Full description: `listing/full-description.txt`
- App icon, feature graphic, phone screenshots: `graphics/`, `screenshots/`
- Category: **Education** · Tags: Education, Science
- Contact email: an address you're happy to show publicly · Website:
  https://highviewone.github.io/CircuitQueest/

### 5. App content (Policy → App content) — answers
| Section | Answer |
|---|---|
| Privacy policy | https://highviewone.github.io/CircuitQueest/privacy.html |
| Ads | **No**, the app does not contain ads |
| App access | **All functionality is available without special access** (no login) |
| Content rating | Questionnaire category **Reference, News, or Educational**. Answer **No** to violence, sexuality, language, controlled substances, gambling, user interaction/communication, sharing location, digital purchases. Expected result: Everyone / PEGI 3 |
| Target audience | **13–15, 16–17, 18+**. Leave the under-13 boxes unticked: selecting them enrols the app in the Families programme, which adds requirements this app doesn't need |
| Data safety | **Does your app collect or share any of the required user data types? → No.** (The app has no internet permission; progress is stored only on-device. Android system backup is handled by Google, not the app, and isn't "collection" for this form.) Encryption in transit: not applicable. Deletion: users clear app data in Android Settings |
| Government app | No |
| Financial features | None |
| Health | None |
| News app | No |
| Advertising ID | **No** — the app doesn't use it (no ads/analytics SDKs; target API 36 declares no AD_ID permission) |

### 6. Closed test (required before production for new personal accounts)
Google requires **at least 12 testers opted in continuously for 14 days** before you can apply for
production. Testers who leave early don't count, so invite a few extra (15–20).
1. **Test and release → Testing → Closed testing → Create track** (or use "Alpha").
2. **Testers:** create an email list with your testers' Google account emails.
3. **Create release:** upload `CircuitQueest-v2.5.aab`, release notes from `listing/release-notes.txt`,
   review and roll out.
4. Copy the **opt-in link** and send it to testers. Each must open it, tap *Become a tester*, then
   install from the Play Store link — and stay opted in for 14 days.
5. Ask testers to actually use it: Google's production application asks about their feedback.

### 7. Apply for production (after 14 days)
**Dashboard → Apply for production**: answer questions about the test (how you recruited testers,
feedback you got, what you changed). Review usually takes a few days. Then create a production
release with the same bundle (or a newer one).

---

## Updating later
1. Bump `versionCode`/`versionName`, build: `scripts/build-play-bundle.sh` (Play) and
   `scripts/sign-release.sh` (GitHub APK) — see `docs/DEPLOYMENT.md`.
2. Play Console → the track → Create new release → upload → notes → roll out.
