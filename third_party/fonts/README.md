# Bundled fonts

Shipped in `app/src/main/res/font/`. Both are under the SIL Open Font License 1.1 (texts alongside).

| Font | Source | Files |
|------|--------|-------|
| Space Grotesk | https://github.com/floriankarsten/space-grotesk | Regular, Medium, Bold from `fonts/ttf/static/`. SemiBold is a static instance of `fonts/ttf/SpaceGrotesk[wght].ttf` at wght=600 (fontTools `varLib.instancer`), since upstream ships no static SemiBold. |
| JetBrains Mono | https://github.com/JetBrains/JetBrainsMono | Regular, Medium, Bold |

When replacing a font, download the raw file (not the GitHub page) and check it with `file *.ttf`:
every entry must say "TrueType Font data". The Space Grotesk files were once committed as saved
HTML pages, which crashed every build on launch.
