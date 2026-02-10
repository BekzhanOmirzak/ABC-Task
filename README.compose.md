# ABC-Task (Compose)

This document describes the Jetpack Compose implementation.

## Branch

- `compose` (default branch on GitHub)

## Tech Stack

- Kotlin
- Jetpack Compose + Material 3
- Android Jetpack (Lifecycle, ViewModel)
- Coroutines + StateFlow
- Gradle (Kotlin DSL + Version Catalog)
- No third-party libraries

## Run Compose Version

```bash
git checkout compose
./gradlew assembleDebug
```

Open the project in Android Studio and run the app on an emulator or device.

## Need XML Version?

Switch to the `xml` branch and use [`README.xml.md`](README.xml.md).
