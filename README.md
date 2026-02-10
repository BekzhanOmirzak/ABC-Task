# ABC Demo Android App

## Overview

ABC Demo is a sample Android application created as a technical test
project. The goal of this project is to demonstrate Android development
skills using Kotlin, modern architecture components, and best practices.

## Getting Started

### Prerequisites

-   Android Studio (latest stable version)
-   JDK 11 or higher
-   Android Emulator or physical device

### Installation

``` bash
git clone https://github.com/BekzhanOmirzak/abc-demo.git
cd abc-demo
./gradlew clean build
```

### Run

Open the project in Android Studio and run it on an emulator or a
connected device.

## Features

-   Kotlin-based Android application
-   Modern Android architecture
-   Clean project structure
-   Support for scalable UI development

## Architecture

The project follows a modern Android architecture approach:

-   MVVM (Model-View-ViewModel)
-   Jetpack components (ViewModel, LiveData/StateFlow, Navigation)
-   Coroutines for asynchronous operations

## Testing

Automated tests were not implemented in this project in order to save
development time.

However, the following tests could be added to improve reliability and
maintainability:

-   Unit tests for ViewModels and utility classes
-   Repository layer tests with mocked data sources
-   UI tests using Espresso or Jetpack Compose testing framework
-   Edge case and error handling tests

## Tools and Technologies

-   Kotlin
-   Android SDK
-   Gradle
-   Jetpack Libraries
-   Coroutines

## Build

To build the project manually:

``` bash
./gradlew assembleDebug
```

## Future Improvements

-   Add automated unit and UI tests
-   Improve error handling
-   Add CI/CD pipeline (GitHub Actions)
-   Enhance documentation
-   Add dependency injection (Hilt/Koin)

## License

This project is provided for evaluation and educational purposes.
