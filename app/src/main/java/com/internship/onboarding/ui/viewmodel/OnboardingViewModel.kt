package com.internship.onboarding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.onboarding.data.model.OnboardingProgress
import com.internship.onboarding.data.model.OnboardingStep
import com.internship.onboarding.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadOnboardingData()
    }

    private fun loadOnboardingData() {
        viewModelScope.launch {
            combine(
                repository.progress,
                MutableStateFlow(repository.getAllSteps())
            ) { progress, steps ->
                val stepsWithProgress = steps.map { step ->
                    step.copy(isCompleted = progress.completedSteps.contains(step.id))
                }
                OnboardingUiState(
                    steps = stepsWithProgress,
                    progress = progress,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun markStepCompleted(stepId: String) {
        viewModelScope.launch {
            repository.markStepCompleted(stepId)
        }
    }

    fun setCurrentStep(stepId: String) {
        viewModelScope.launch {
            repository.setCurrentStep(stepId)
        }
    }

    fun getProgressPercentage(): Float {
        val totalSteps = _uiState.value.steps.size
        val completedSteps = _uiState.value.progress.completedSteps.size
        return if (totalSteps > 0) completedSteps.toFloat() / totalSteps else 0f
    }

    fun getStepsByCategory() = _uiState.value.steps.groupBy { it.category }

    fun getNextIncompleteStep(): OnboardingStep? {
        return _uiState.value.steps
            .filter { !it.isCompleted }
            .minByOrNull { it.order }
    }
}

data class OnboardingUiState(
    val steps: List<OnboardingStep> = emptyList(),
    val progress: OnboardingProgress = OnboardingProgress(),
    val isLoading: Boolean = true
)