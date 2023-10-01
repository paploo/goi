# Goi Kotlin

## Antlr Notes

Antlr is a little weird to work with:
1. The IntelliJ plugin likes to gen files into both `src/main/antlr/gen` and `src/main/gen`. The output path
   can be configured in the project settings, and it is relative to `src/main`.
2. The Gradle Plugin doesn't have the right dependencies builtin for kotlin, so you have to manually add them.

⚠️ The generated files don't always rebuild appropriately using gradle; be sure to delete them when making changes.