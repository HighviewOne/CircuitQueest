name: Documentation Improvement
description: Suggest improvements to documentation
title: "[DOCS] "
labels: ["documentation"]
body:
  - type: textarea
    id: current
    attributes:
      label: Current Documentation
      description: Link to or quote the current documentation
      placeholder: |
        https://github.com/project/CircuitQueest/blob/main/docs/TESTING.md
      validations:
        required: true

  - type: textarea
    id: issue
    attributes:
      label: Issue with Documentation
      description: What's unclear, missing, or incorrect?
      placeholder: |
        The TESTING.md guide doesn't explain how to run tests in watch mode
      validations:
        required: true

  - type: textarea
    id: suggestion
    attributes:
      label: Suggested Improvement
      description: How should this be improved?
      placeholder: |
        Add a section explaining `./gradlew test -t` for watch mode testing
