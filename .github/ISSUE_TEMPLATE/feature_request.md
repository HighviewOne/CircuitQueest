name: Feature Request
description: Suggest a new feature for CircuitQuEEst
title: "[FEATURE] "
labels: ["enhancement"]
body:
  - type: markdown
    attributes:
      value: |
        Thanks for suggesting a feature! Help us understand what you'd like to see.

  - type: textarea
    id: description
    attributes:
      label: Feature Description
      description: Clear description of the feature you'd like
      placeholder: |
        Add ability to create custom quizzes on topics I'm struggling with
    validations:
      required: true

  - type: textarea
    id: motivation
    attributes:
      label: Motivation
      description: Why would this feature be useful?
      placeholder: |
        As a learner struggling with certain topics, I'd like to focus my practice on specific areas
    validations:
      required: true

  - type: textarea
    id: alternative
    attributes:
      label: Alternatives Considered
      description: Any alternative approaches?
      placeholder: |
        Could also allow toggling which topics appear in the quiz selection

  - type: textarea
    id: additional
    attributes:
      label: Additional Context
      description: Screenshots, mockups, or other details
      placeholder: |
        This would help me prepare for exams more efficiently
