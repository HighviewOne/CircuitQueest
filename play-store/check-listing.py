#!/usr/bin/env python3
"""Check store listing text against Google Play's length limits."""
import pathlib, sys
LIMITS = {"title.txt": 30, "short-description.txt": 80, "full-description.txt": 4000, "release-notes.txt": 500}
d = pathlib.Path(__file__).parent / "listing"
ok = True
for name, limit in LIMITS.items():
    n = len((d / name).read_text(encoding="utf-8").strip())
    flag = "OK " if n <= limit else "TOO LONG"
    ok &= n <= limit
    print(f"{flag} {name}: {n}/{limit}")
sys.exit(0 if ok else 1)
