package com.internship.onboarding.data.model

data class OnboardingStep(
    val id: String,
    val title: String,
    val description: String,
    val category: OnboardingCategory,
    val isCompleted: Boolean = false,
    val order: Int,
    val estimatedTime: String,
    val resources: List<Resource> = emptyList()
)

data class Resource(
    val title: String,
    val url: String,
    val type: ResourceType
)

enum class ResourceType {
    DOWNLOAD_LINK,
    TUTORIAL,
    DOCUMENTATION,
    VIDEO,
    INTERACTIVE_GUIDE
}

enum class OnboardingCategory(val displayName: String, val color: Long) {
    ORIENTATION("Company Orientation", 0xFF6200EE),
    ENVIRONMENT_SETUP("Environment Setup", 0xFF3700B3),
    ANDROID_BASICS("Android Development Basics", 0xFF03DAC6),
    PROJECTS("Hands-on Projects", 0xFFFF6200)
}

data class OnboardingProgress(
    val completedSteps: Set<String> = emptySet(),
    val currentStep: String? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)