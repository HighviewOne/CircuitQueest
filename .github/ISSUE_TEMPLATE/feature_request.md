name: Feature Request
description: Suggest a new feature or topic for CircuitQueest
title: "[FEATURE] "
labels: ["enhancement"]
body:
  - type: dropdown
    id: type
    attributes:
      label: Request type
      options:
        - New EE topic
        - UI / UX improvement
        - Learning feature (e.g. review mode, hints)
        - Accessibility
        - Other
    validations:
      required: true

  - type: textarea
    id: description
    attributes:
      label: Feature description
      description: What would you like to see? Be as specific as possible.
      placeholder: |
        A spaced-repetition review mode that resurfaces questions I got wrong, weighted by how long ago I answered them.
    validations:
      required: true

  - type: textarea
    id: motivation
    attributes:
      label: Why is this useful?
      placeholder: |
        I finish topics but forget the formulas a week later. A review queue would help retention without re-doing full quizzes.
    validations:
      required: true

  - type: textarea
    id: alternative
    attributes:
      label: Alternatives you've considered
      placeholder: |
        Could also be a "weak spots" tab on the home screen listing topics where my quiz score was < 70%.

  - type: textarea
    id: additional
    attributes:
      label: Additional context
      description: Screenshots, mockups, or references to similar apps
