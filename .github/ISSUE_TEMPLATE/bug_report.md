name: Bug Report
description: Report a bug in CircuitQuEEst
title: "[BUG] "
labels: ["bug"]
body:
  - type: markdown
    attributes:
      value: |
        Thanks for reporting a bug! Please provide as much detail as possible.

  - type: textarea
    id: description
    attributes:
      label: Description
      description: Clear description of the bug
      placeholder: |
        The app crashes when I tap the retry button on the results screen
    validations:
      required: true

  - type: textarea
    id: steps
    attributes:
      label: Steps to Reproduce
      description: Step-by-step instructions to reproduce the bug
      placeholder: |
        1. Launch the app
        2. Complete a quiz
        3. Tap "Retry" button on results screen
        4. App crashes
    validations:
      required: true

  - type: textarea
    id: expected
    attributes:
      label: Expected Behavior
      description: What should happen instead?
      placeholder: |
        App should restart the quiz without crashing
    validations:
      required: true

  - type: textarea
    id: actual
    attributes:
      label: Actual Behavior
      description: What actually happens?
      placeholder: |
        App force closes with error
    validations:
      required: true

  - type: textarea
    id: logs
    attributes:
      label: Logs & Error Messages
      description: Paste relevant error messages or logcat output
      placeholder: |
        ```
        E/AndroidRuntime: java.lang.NullPointerException: Attempt to read from field 'mQuizState' on a null object reference
        ```

  - type: dropdown
    id: android-version
    attributes:
      label: Android Version
      description: What version of Android are you using?
      options:
        - Android 15 (SDK 35)
        - Android 14 (SDK 34)
        - Android 13 (SDK 33)
        - Android 12 (SDK 32)
        - Android 11 (SDK 30)
        - Android 10 (SDK 29)
        - Android 9 (SDK 28)
        - Android 8 (SDK 26)
        - Other (please specify below)
    validations:
      required: true

  - type: dropdown
    id: device
    attributes:
      label: Device Type
      description: What device are you using?
      options:
        - Phone
        - Tablet
        - Emulator
        - Other
    validations:
      required: true

  - type: textarea
    id: device-info
    attributes:
      label: Device Information
      description: Device model, screen size, RAM, storage
      placeholder: |
        Pixel 8, 6GB RAM, 128GB storage

  - type: textarea
    id: additional
    attributes:
      label: Additional Context
      description: Any other relevant information?
      placeholder: |
        This started happening after update to version 1.1.0
