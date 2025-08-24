package com.internship.onboarding.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.internship.onboarding.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    
    companion object {
        private val COMPLETED_STEPS_KEY = stringSetPreferencesKey("completed_steps")
        private val CURRENT_STEP_KEY = stringPreferencesKey("current_step")
    }
    
    val progress: Flow<OnboardingProgress> = dataStore.data.map { preferences ->
        OnboardingProgress(
            completedSteps = preferences[COMPLETED_STEPS_KEY] ?: emptySet(),
            currentStep = preferences[CURRENT_STEP_KEY]
        )
    }
    
    suspend fun markStepCompleted(stepId: String) {
        dataStore.edit { preferences ->
            val currentCompleted = preferences[COMPLETED_STEPS_KEY] ?: emptySet()
            preferences[COMPLETED_STEPS_KEY] = currentCompleted + stepId
        }
    }
    
    suspend fun setCurrentStep(stepId: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_STEP_KEY] = stepId
        }
    }
    
    fun getAllSteps(): List<OnboardingStep> = listOf(
        // Company Orientation Steps
        OnboardingStep(
            id = "orientation_culture",
            title = "Company Culture Overview",
            description = "Learn about our company values, mission, and work culture",
            category = OnboardingCategory.ORIENTATION,
            order = 1,
            estimatedTime = "30 minutes",
            resources = listOf(
                Resource("Company Values Presentation", "#", ResourceType.VIDEO),
                Resource("Employee Handbook", "#", ResourceType.DOCUMENTATION)
            )
        ),
        OnboardingStep(
            id = "orientation_goals",
            title = "Internship Goals & Expectations",
            description = "Understand your internship objectives and success metrics",
            category = OnboardingCategory.ORIENTATION,
            order = 2,
            estimatedTime = "20 minutes",
            resources = listOf(
                Resource("Internship Program Guide", "#", ResourceType.DOCUMENTATION)
            )
        ),
        OnboardingStep(
            id = "team_introduction",
            title = "Meet Your Team",
            description = "Get introduced to team members, mentors, and key stakeholders",
            category = OnboardingCategory.ORIENTATION,
            order = 3,
            estimatedTime = "45 minutes",
            resources = listOf(
                Resource("Team Directory", "#", ResourceType.DOCUMENTATION),
                Resource("Mentor Assignment", "#", ResourceType.DOCUMENTATION)
            )
        ),
        
        // Environment Setup Steps
        OnboardingStep(
            id = "android_studio_install",
            title = "Install Android Studio",
            description = "Download and install Android Studio IDE on your development machine",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 4,
            estimatedTime = "30 minutes",
            resources = listOf(
                Resource("Download Android Studio", "https://developer.android.com/studio", ResourceType.DOWNLOAD_LINK),
                Resource("Installation Guide", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "android_studio_config",
            title = "Configure Android Studio",
            description = "Set up Android Studio with necessary plugins and preferences",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 5,
            estimatedTime = "20 minutes",
            resources = listOf(
                Resource("Configuration Tutorial", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "sdk_install",
            title = "Install Android SDKs",
            description = "Install required Android SDK versions and build tools",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 6,
            estimatedTime = "25 minutes",
            resources = listOf(
                Resource("SDK Manager Guide", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "java_kotlin_setup",
            title = "Java/Kotlin Development Setup",
            description = "Ensure proper Java and Kotlin development environment",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 7,
            estimatedTime = "15 minutes",
            resources = listOf(
                Resource("Kotlin Setup Guide", "#", ResourceType.TUTORIAL),
                Resource("Java Development Guide", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "gradle_setup",
            title = "Gradle Configuration",
            description = "Learn about Gradle build system and project configuration",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 8,
            estimatedTime = "20 minutes",
            resources = listOf(
                Resource("Gradle Basics", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "git_setup",
            title = "Git Installation & Configuration",
            description = "Install Git and configure your development environment",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 9,
            estimatedTime = "15 minutes",
            resources = listOf(
                Resource("Download Git", "https://git-scm.com/downloads", ResourceType.DOWNLOAD_LINK),
                Resource("Git Configuration Guide", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "github_account",
            title = "Create GitHub Account",
            description = "Set up your GitHub account and configure SSH keys",
            category = OnboardingCategory.ENVIRONMENT_SETUP,
            order = 10,
            estimatedTime = "15 minutes",
            resources = listOf(
                Resource("GitHub Signup", "https://github.com/join", ResourceType.DOWNLOAD_LINK),
                Resource("SSH Key Setup", "#", ResourceType.TUTORIAL)
            )
        ),
        
        // Android Development Basics
        OnboardingStep(
            id = "android_fundamentals",
            title = "Android Development Fundamentals",
            description = "Learn the core concepts of Android app development",
            category = OnboardingCategory.ANDROID_BASICS,
            order = 11,
            estimatedTime = "60 minutes",
            resources = listOf(
                Resource("Android Developer Guide", "https://developer.android.com/guide", ResourceType.DOCUMENTATION),
                Resource("Fundamentals Tutorial", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "activities_fragments",
            title = "Activities and Fragments",
            description = "Understand the building blocks of Android apps",
            category = OnboardingCategory.ANDROID_BASICS,
            order = 12,
            estimatedTime = "45 minutes",
            resources = listOf(
                Resource("Activities Guide", "#", ResourceType.TUTORIAL),
                Resource("Fragments Tutorial", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "layouts_views",
            title = "Layouts and Views",
            description = "Learn about Android UI components and layout systems",
            category = OnboardingCategory.ANDROID_BASICS,
            order = 13,
            estimatedTime = "50 minutes",
            resources = listOf(
                Resource("Layout Guide", "#", ResourceType.TUTORIAL),
                Resource("Views and ViewGroups", "#", ResourceType.TUTORIAL)
            )
        ),
        OnboardingStep(
            id = "intro_tutorials",
            title = "Complete Introductory Tutorials",
            description = "Work through beginner-friendly Android development tutorials",
            category = OnboardingCategory.ANDROID_BASICS,
            order = 14,
            estimatedTime = "90 minutes",
            resources = listOf(
                Resource("Your First App Tutorial", "#", ResourceType.INTERACTIVE_GUIDE),
                Resource("Building a Simple UI", "#", ResourceType.INTERACTIVE_GUIDE)
            )
        ),
        
        // Hands-on Projects
        OnboardingStep(
            id = "hello_world_project",
            title = "Hello World Project",
            description = "Create your first Android application",
            category = OnboardingCategory.PROJECTS,
            order = 15,
            estimatedTime = "45 minutes",
            resources = listOf(
                Resource("Hello World Tutorial", "#", ResourceType.INTERACTIVE_GUIDE)
            )
        ),
        OnboardingStep(
            id = "calculator_project",
            title = "Simple Calculator App",
            description = "Build a basic calculator to practice layouts and event handling",
            category = OnboardingCategory.PROJECTS,
            order = 16,
            estimatedTime = "2 hours",
            resources = listOf(
                Resource("Calculator Project Guide", "#", ResourceType.INTERACTIVE_GUIDE)
            )
        ),
        OnboardingStep(
            id = "todo_project",
            title = "Todo List App",
            description = "Create a todo list app to learn data persistence and lists",
            category = OnboardingCategory.PROJECTS,
            order = 17,
            estimatedTime = "3 hours",
            resources = listOf(
                Resource("Todo App Tutorial", "#", ResourceType.INTERACTIVE_GUIDE)
            )
        )
    )
}