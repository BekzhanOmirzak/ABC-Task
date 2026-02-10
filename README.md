# ABC-Task

Android test task project with two UI implementations in separate branches.

## Branches

| Branch | Implementation | Notes |
| --- | --- | --- |
| `compose` | Jetpack Compose | Default branch on GitHub |
| `xml` | XML + Views | Alternative UI implementation |

GitHub opens `compose` by default.  
If you need the XML version, switch to the `xml` branch explicitly.

## Tech Stack

- Kotlin
- Android SDK (minSdk 24, targetSdk 36)
- Android Jetpack (Lifecycle, ViewModel)
- Coroutines + StateFlow
- UI: Jetpack Compose (`compose`) / XML + Views (`xml`)
- Gradle (Kotlin DSL + Version Catalog)
- No third-party libraries

## Quick Start

### Prerequisites

- Android Studio (latest stable version)
- JDK 11+
- Android Emulator or physical device

### Clone and Build

```bash
git clone git@github.com:BekzhanOmirzak/ABC-Task.git
cd ABC-Task
git checkout compose # or: git checkout xml
./gradlew assembleDebug
```

## Branch-Specific README Files

- [`README.compose.md`](README.compose.md) - detailed notes for the Compose implementation
- [`README.xml.md`](README.xml.md) - detailed notes for the XML implementation
