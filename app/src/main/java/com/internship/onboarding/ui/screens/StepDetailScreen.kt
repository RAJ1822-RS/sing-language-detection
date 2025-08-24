package com.internship.onboarding.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internship.onboarding.data.model.OnboardingStep
import com.internship.onboarding.data.model.Resource
import com.internship.onboarding.data.model.ResourceType
import com.internship.onboarding.ui.viewmodel.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepDetailScreen(
    stepId: String,
    onBackClick: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val step = uiState.steps.find { it.id == stepId }
    
    if (step == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Step not found")
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Step Details") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(step.category.color),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StepHeader(step = step, onCompleted = { 
                    viewModel.markStepCompleted(step.id)
                })
            }
            
            item {
                StepDescription(step = step)
            }
            
            if (step.resources.isNotEmpty()) {
                item {
                    ResourcesSection(resources = step.resources)
                }
            }
            
            item {
                when (step.category.displayName) {
                    "Environment Setup" -> EnvironmentSetupGuide(step = step)
                    "Android Development Basics" -> AndroidBasicsGuide(step = step)
                    "Hands-on Projects" -> ProjectGuide(step = step)
                    else -> CompanyOrientationGuide(step = step)
                }
            }
        }
    }
}

@Composable
fun StepHeader(
    step: OnboardingStep,
    onCompleted: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(step.category.color).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = step.category.displayName,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(step.category.color),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Checkbox(
                    checked = step.isCompleted,
                    onCheckedChange = { if (!step.isCompleted) onCompleted() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(step.category.color)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Estimated time: ${step.estimatedTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun StepDescription(step: OnboardingStep) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = step.description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )
        }
    }
}

@Composable
fun ResourcesSection(resources: List<Resource>) {
    val context = LocalContext.current
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Resources",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            resources.forEach { resource ->
                ResourceItem(
                    resource = resource,
                    onClick = {
                        if (resource.url != "#") {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resource.url))
                            context.startActivity(intent)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResourceItem(
    resource: Resource,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getResourceIcon(resource.type),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resource.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = resource.type.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = "Open",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun EnvironmentSetupGuide(step: OnboardingStep) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Setup Instructions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            when (step.id) {
                "android_studio_install" -> AndroidStudioInstructions()
                "sdk_install" -> SDKInstructions()
                "git_setup" -> GitInstructions()
                "github_account" -> GitHubInstructions()
                else -> GeneralSetupInstructions(step)
            }
        }
    }
}

@Composable
fun AndroidStudioInstructions() {
    Column {
        InstructionStep("1", "Visit the Android Studio download page")
        InstructionStep("2", "Download the installer for your operating system")
        InstructionStep("3", "Run the installer and follow the setup wizard")
        InstructionStep("4", "Launch Android Studio and complete initial setup")
        InstructionStep("5", "Verify installation by creating a new project")
    }
}

@Composable
fun SDKInstructions() {
    Column {
        InstructionStep("1", "Open Android Studio")
        InstructionStep("2", "Go to Tools > SDK Manager")
        InstructionStep("3", "Install the latest Android SDK Platform")
        InstructionStep("4", "Install Android SDK Build-Tools")
        InstructionStep("5", "Install Google Play services")
    }
}

@Composable
fun GitInstructions() {
    Column {
        InstructionStep("1", "Download Git from the official website")
        InstructionStep("2", "Install Git with default settings")
        InstructionStep("3", "Configure your name: git config --global user.name \"Your Name\"")
        InstructionStep("4", "Configure your email: git config --global user.email \"your.email@example.com\"")
        InstructionStep("5", "Verify installation: git --version")
    }
}

@Composable
fun GitHubInstructions() {
    Column {
        InstructionStep("1", "Visit github.com and click 'Sign up'")
        InstructionStep("2", "Choose a username and create your account")
        InstructionStep("3", "Verify your email address")
        InstructionStep("4", "Set up two-factor authentication (recommended)")
        InstructionStep("5", "Generate SSH keys for secure authentication")
    }
}

@Composable
fun AndroidBasicsGuide(step: OnboardingStep) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Learning Guide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            when (step.id) {
                "android_fundamentals" -> AndroidFundamentalsGuide()
                "activities_fragments" -> ActivitiesFragmentsGuide()
                "layouts_views" -> LayoutsViewsGuide()
                else -> TutorialGuide(step)
            }
        }
    }
}

@Composable
fun AndroidFundamentalsGuide() {
    Column {
        ConceptCard("App Components", "Activities, Services, Broadcast Receivers, Content Providers")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Application Lifecycle", "How Android manages app states and memory")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Manifest File", "App configuration and permissions")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Resources", "Layouts, strings, images, and other assets")
    }
}

@Composable
fun ActivitiesFragmentsGuide() {
    Column {
        ConceptCard("Activity Lifecycle", "onCreate, onStart, onResume, onPause, onStop, onDestroy")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Fragment Lifecycle", "Fragment states and lifecycle methods")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Navigation", "Moving between activities and fragments")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Data Passing", "Intent extras and fragment arguments")
    }
}

@Composable
fun LayoutsViewsGuide() {
    Column {
        ConceptCard("View and ViewGroup", "Basic building blocks of UI")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Common Layouts", "LinearLayout, RelativeLayout, ConstraintLayout")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("UI Components", "TextView, Button, ImageView, EditText")
        Spacer(modifier = Modifier.height(8.dp))
        ConceptCard("Event Handling", "Click listeners and user interactions")
    }
}

@Composable
fun ProjectGuide(step: OnboardingStep) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Project Instructions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            when (step.id) {
                "hello_world_project" -> HelloWorldProject()
                "calculator_project" -> CalculatorProject()
                "todo_project" -> TodoProject()
                else -> GeneralProject(step)
            }
        }
    }
}

@Composable
fun HelloWorldProject() {
    Column {
        ProjectStep("Create New Project", "Start a new Android project in Android Studio")
        ProjectStep("Design Layout", "Add a TextView to display 'Hello World!'")
        ProjectStep("Customize Text", "Change text color, size, and font")
        ProjectStep("Add Interaction", "Add a button to change the message")
        ProjectStep("Test App", "Run on emulator or physical device")
    }
}

@Composable
fun CalculatorProject() {
    Column {
        ProjectStep("Plan UI", "Design calculator layout with buttons and display")
        ProjectStep("Create Layout", "Use GridLayout or LinearLayout for buttons")
        ProjectStep("Add Logic", "Implement basic arithmetic operations")
        ProjectStep("Handle Input", "Process button clicks and update display")
        ProjectStep("Test Functionality", "Verify all operations work correctly")
    }
}

@Composable
fun TodoProject() {
    Column {
        ProjectStep("Design Data Model", "Create Todo item class")
        ProjectStep("Build UI", "List view with add/delete functionality")
        ProjectStep("Implement Storage", "Save todos using SharedPreferences")
        ProjectStep("Add Features", "Mark as complete, edit todos")
        ProjectStep("Polish UI", "Add animations and better styling")
    }
}

@Composable
fun CompanyOrientationGuide(step: OnboardingStep) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "What to Expect",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            when (step.id) {
                "orientation_culture" -> CultureOrientationGuide()
                "orientation_goals" -> GoalsOrientationGuide()
                "team_introduction" -> TeamIntroductionGuide()
                else -> GeneralOrientationGuide()
            }
        }
    }
}

@Composable
fun CultureOrientationGuide() {
    Column {
        OrientationPoint("Company Values", "Learn about our core principles and values")
        OrientationPoint("Work Culture", "Understand our collaborative environment")
        OrientationPoint("Communication", "How we communicate and share knowledge")
        OrientationPoint("Growth Mindset", "Our approach to learning and development")
    }
}

@Composable
fun GoalsOrientationGuide() {
    Column {
        OrientationPoint("Internship Objectives", "What you'll accomplish during your time here")
        OrientationPoint("Learning Goals", "Skills and knowledge you'll develop")
        OrientationPoint("Success Metrics", "How we'll measure your progress")
        OrientationPoint("Future Opportunities", "Potential career paths and growth")
    }
}

@Composable
fun TeamIntroductionGuide() {
    Column {
        OrientationPoint("Team Structure", "Meet your team members and their roles")
        OrientationPoint("Mentor Assignment", "Get to know your assigned mentor")
        OrientationPoint("Communication Channels", "Slack, email, and meeting schedules")
        OrientationPoint("Team Rituals", "Daily standups, code reviews, and team events")
    }
}

@Composable
fun InstructionStep(number: String, instruction: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            modifier = Modifier.size(24.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = instruction,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ConceptCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ProjectStep(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Code,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun OrientationPoint(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Group,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun GeneralSetupInstructions(step: OnboardingStep) {
    Text(
        text = "Follow the setup instructions provided in the resources section. Make sure to verify your installation before marking this step as complete.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun TutorialGuide(step: OnboardingStep) {
    Text(
        text = "Complete the tutorials and exercises linked in the resources section. Take your time to understand each concept before moving to the next.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun GeneralProject(step: OnboardingStep) {
    Text(
        text = "Follow the project guide in the resources section. Don't hesitate to ask your mentor for help if you get stuck.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun GeneralOrientationGuide() {
    Text(
        text = "Attend the scheduled orientation session and actively participate in discussions. Take notes and ask questions about anything unclear.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun getResourceIcon(resourceType: ResourceType): ImageVector {
    return when (resourceType) {
        ResourceType.DOWNLOAD_LINK -> Icons.Default.Download
        ResourceType.TUTORIAL -> Icons.Default.School
        ResourceType.DOCUMENTATION -> Icons.Default.Description
        ResourceType.VIDEO -> Icons.Default.PlayCircle
        ResourceType.INTERACTIVE_GUIDE -> Icons.Default.TouchApp
    }
}