# Goi Kotlin

## Test framework notes

I have two gradlew plugins installed:
- Traditional JUnit with a plugin to give pretty output.
- Kotest specific plugin.

The Kotest one is invoked with:
```shell
./gradlew jvmKotest
```
While the Junit one is invoked wih the traditional:
```shell
./gradlew test
```

## Antlr Notes

Antlr is a little weird to work with:
1. The IntelliJ plugin likes to gen files into both `src/main/antlr/gen` and `src/main/gen`. The output path
   can be configured in the project settings, and it is relative to `src/main`.
2. The Gradle Plugin doesn't have the right dependencies builtin for kotlin, so you have to manually add them.

⚠️ The generated files don't always rebuild appropriately using gradle; be sure to delete them when making changes.