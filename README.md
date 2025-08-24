# Internship Onboarding App

A comprehensive Android application designed to guide new interns through their onboarding journey, from company orientation to hands-on Android development projects.

## Features

### 🎯 **Company Orientation**
- Company culture and values overview
- Internship goals and expectations
- Team member and mentor introductions

### ⚙️ **Environment Setup**
- Android Studio installation and configuration
- SDK and build tools setup
- Java/Kotlin development environment
- Git and GitHub account setup

### 📱 **Android Development Basics**
- Android fundamentals (activities, fragments, layouts)
- Comprehensive tutorials and guides
- Interactive learning materials

### 🛠️ **Hands-on Projects**
- Hello World project
- Calculator application
- Todo list application
- Progressive difficulty levels

## Key Features

- **Progress Tracking**: Visual progress indicators and completion tracking
- **Interactive Resources**: Links to tutorials, documentation, and tools
- **Step-by-Step Guides**: Detailed instructions for each onboarding step
- **Modern UI**: Built with Jetpack Compose and Material Design 3
- **Offline Support**: Core functionality works without internet connection
- **Persistent Progress**: Your progress is saved automatically

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Dependency Injection**: Dagger Hilt
- **Data Storage**: DataStore Preferences
- **Navigation**: Navigation Compose
- **Design System**: Material Design 3

## Project Structure

```
app/
├── src/main/java/com/internship/onboarding/
│   ├── data/
│   │   ├── model/           # Data models and enums
│   │   └── repository/      # Data repository and business logic
│   ├── di/                  # Dependency injection modules
│   ├── ui/
│   │   ├── navigation/      # Navigation setup
│   │   ├── screens/         # Compose screens
│   │   ├── theme/           # Material Design theme
│   │   └── viewmodel/       # ViewModels
│   ├── MainActivity.kt      # Main activity
│   └── OnboardingApplication.kt  # Application class
└── src/main/res/            # Resources (strings, colors, themes)
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 8 or later
- Android SDK with API level 24 or higher

### Installation

1. Clone or download this project
2. Open Android Studio
3. Select "Open an Existing Project"
4. Navigate to the project directory and open it
5. Wait for Gradle sync to complete
6. Run the app on an emulator or physical device

### Building the Project

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## Onboarding Steps

### Phase 1: Company Orientation (3 steps)
1. **Company Culture Overview** - Learn values and mission
2. **Internship Goals & Expectations** - Understand objectives
3. **Meet Your Team** - Team introductions and mentor assignment

### Phase 2: Environment Setup (7 steps)
4. **Install Android Studio** - Download and install IDE
5. **Configure Android Studio** - Setup plugins and preferences
6. **Install Android SDKs** - Required SDK versions and build tools
7. **Java/Kotlin Development Setup** - Development environment
8. **Gradle Configuration** - Build system understanding
9. **Git Installation & Configuration** - Version control setup
10. **Create GitHub Account** - Repository hosting and collaboration

### Phase 3: Android Development Basics (4 steps)
11. **Android Development Fundamentals** - Core concepts
12. **Activities and Fragments** - App building blocks
13. **Layouts and Views** - UI components and design
14. **Complete Introductory Tutorials** - Hands-on practice

### Phase 4: Hands-on Projects (3 steps)
15. **Hello World Project** - First Android application
16. **Simple Calculator App** - Basic UI and event handling
17. **Todo List App** - Data persistence and complex UI

## App Screenshots

The app features a modern, intuitive interface with:
- Welcome screen with progress overview
- Category-based step organization
- Detailed step instructions
- Interactive checklists
- Resource links and tutorials

## Customization

### Adding New Steps

1. Update `OnboardingRepository.getAllSteps()` in the repository
2. Add new step data with appropriate category and resources
3. Create specific guides in `StepDetailScreen.kt` if needed

### Modifying Categories

1. Update `OnboardingCategory` enum in the data models
2. Add corresponding icons in `HomeScreen.getCategoryIcon()`
3. Update color schemes as needed

## Contributing

This is a template project for internship onboarding. Feel free to customize it for your organization's specific needs:

- Update company-specific information
- Add your own resources and links
- Modify the step progression
- Customize the UI theme and branding

## Architecture

The app follows clean architecture principles:

- **Data Layer**: Repository pattern with local data storage
- **Domain Layer**: Use cases and business logic
- **Presentation Layer**: ViewModels and Compose UI

### Key Components

- **OnboardingRepository**: Manages step data and progress tracking
- **OnboardingViewModel**: Handles UI state and user interactions
- **HomeScreen**: Main interface with progress overview
- **StepDetailScreen**: Detailed view for each onboarding step

## License

This project is intended for educational and organizational use. Customize as needed for your internship program.

---

## Support

For questions or issues with this onboarding app:
1. Check the setup instructions in each step
2. Consult your assigned mentor
3. Review the Android development resources provided
4. Ask questions during team meetings or standups

Happy learning! 🚀
