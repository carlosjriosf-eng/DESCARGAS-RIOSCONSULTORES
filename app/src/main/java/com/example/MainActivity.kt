package com.example

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.MyApplicationTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(dynamicColor = false) {
                val projectViewModel: ProjectViewModel = viewModel()
                val activePdfViewerFile by projectViewModel.activePdfViewerFile.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // Show regular header if PDF viewer is not full screen
                        if (activePdfViewerFile == null) {
                            PortalHeader(viewModel = projectViewModel)
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (activePdfViewerFile != null) {
                            // Full-screen simulated and actual PDF visualizer/downloader
                            PdfViewerScreen(
                                viewModel = projectViewModel,
                                file = activePdfViewerFile!!,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            // Catalog list & category chips
                            CatalogScreen(
                                viewModel = projectViewModel,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Sliding overlay registration modal
                        RegistrationModal(viewModel = projectViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun RiosConsultoresLogo(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Greek Column Canvas
        Canvas(
            modifier = Modifier
                .size(width = 44.dp, height = 48.dp)
        ) {
            val orangeColor = Color(0xFFF55A05) // Corporate orange
            val colWidth = 4.dp.toPx()
            val colHeight = size.height * 0.7f
            val spacing = 7.dp.toPx()
            
            // Draw steps on top (Greek pediment/capitals)
            // Step 1 (widest, top):
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = size.width * 0.05f, y = size.height * 0.05f),
                size = androidx.compose.ui.geometry.Size(width = size.width * 0.85f, height = 3.dp.toPx())
            )
            // Step 2 (middle):
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = size.width * 0.1f, y = size.height * 0.05f + 3.dp.toPx() + 1.dp.toPx()),
                size = androidx.compose.ui.geometry.Size(width = size.width * 0.75f, height = 3.dp.toPx())
            )
            // Step 3 (bottom of capital):
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = size.width * 0.15f, y = size.height * 0.05f + 6.dp.toPx() + 2.dp.toPx()),
                size = androidx.compose.ui.geometry.Size(width = size.width * 0.65f, height = 3.dp.toPx())
            )

            // Draw three vertical pillars
            val columnsStartX = size.width * 0.18f
            val pillarsY = size.height * 0.05f + 9.dp.toPx() + 3.dp.toPx()
            val pillarHeight = size.height * 0.8f - pillarsY
            
            // Column 1
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = columnsStartX, y = pillarsY),
                size = androidx.compose.ui.geometry.Size(width = colWidth, height = pillarHeight)
            )
            // Column 2
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = columnsStartX + colWidth + spacing, y = pillarsY),
                size = androidx.compose.ui.geometry.Size(width = colWidth, height = pillarHeight)
            )
            // Column 3
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = columnsStartX + (colWidth + spacing) * 2f, y = pillarsY),
                size = androidx.compose.ui.geometry.Size(width = colWidth, height = pillarHeight)
            )
            
            // Column base line
            drawRect(
                color = orangeColor,
                topLeft = androidx.compose.ui.geometry.Offset(x = columnsStartX - 2.dp.toPx(), y = pillarsY + pillarHeight),
                size = androidx.compose.ui.geometry.Size(width = (colWidth + spacing) * 2f + colWidth + 4.dp.toPx(), height = 3.dp.toPx())
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Vertical divider line (thin orange/gray line in the logo)
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(42.dp)
                .background(Color(0xFFF55A05).copy(alpha = 0.5f))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Brand Name Text Column
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rios",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color(0xFF0F0F0F),
                fontFamily = FontFamily.SansSerif,
                lineHeight = 20.sp
            )
            Text(
                text = "Consultores",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFF55A05),
                fontFamily = FontFamily.SansSerif,
                lineHeight = 16.sp
            )
            
            // Row with orange line and "Abogado en ruta"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFFF55A05))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Abogado en ruta",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkTheme) Color.LightGray else Color(0xFF757575),
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}

@Composable
fun PortalHeader(viewModel: ProjectViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1E1E1E), // Deep Charcoal
            Color(0xFF3C3C3C)  // Slate
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        shadowElevation = 6.dp,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                RiosConsultoresLogo(isDarkTheme = true)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Biblioteca de Recursos Legales Gratuitos",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // User Info Badge
            if (userProfile != null) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.clickable { viewModel.unregisterUser() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Registered",
                            tint = Color(0xFF4ADE80),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = userProfile!!.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 100.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    tonalElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color.Red, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "No inscrito",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatalogScreen(viewModel: ProjectViewModel, modifier: Modifier = Modifier) {
    val catalog by viewModel.catalog.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val categories = listOf("Todos", "Contratos", "Derecho Laboral", "Derecho Civil", "Derecho Comercial", "Derecho de Familia")
    val filteredCatalog = catalog.filter { file ->
        val matchesCategory = selectedCategory == null || selectedCategory == "Todos" || file.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                file.title.contains(searchQuery, ignoreCase = true) ||
                file.description.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome banner branded for Rios Consultores
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "¡Descarga de Guías y Plantillas Legales!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Para exportar y descargar las plantillas de Rios Consultores, completa tu inscripción gratuita de Lector Autorizado.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.DownloadForOffline,
                        contentDescription = "Download Banner",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(52.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar plantillas por palabras clave...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Borrar búsqueda")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        // Category Selection Header
        item {
            Text(
                text = "Áreas del Derecho / Recursos",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Category Selection Chips Row
        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectCategory(category) },
                        label = { Text(category) },
                        shape = RoundedCornerShape(12.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Section Title for documents
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Biblioteca de Documentos Autorizados",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // List of legal templates (each shows Title, Category, and Description)
        items(filteredCatalog, key = { it.id }) { file ->
            BookCard(
                file = file,
                modifier = Modifier.animateItem(),
                onAction = { viewModel.selectFileForAction(file) }
            )
        }
    }
}

@Composable
fun BookCard(file: FreeFile, modifier: Modifier = Modifier, onAction: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Category Badge & Size
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = file.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Attachment,
                        contentDescription = "Size",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${file.fileSize} • ${file.pages} pág.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Title & Author
            Text(
                text = file.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Por ${file.author}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = file.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button
            Button(
                onClick = onAction,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        contentDescription = "Descargar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Obtener y Descargar PDF Gratis",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationModal(viewModel: ProjectViewModel) {
    val showForm by viewModel.showRegisterForm.collectAsState()

    val tempName by viewModel.tempName.collectAsState()
    val tempLastName by viewModel.tempLastName.collectAsState()
    val tempEmail by viewModel.tempEmail.collectAsState()
    val tempPhone by viewModel.tempPhone.collectAsState()
    val tempPassword by viewModel.tempPassword.collectAsState()
    val isSignInMode by viewModel.isSignInMode.collectAsState()

    val nameError by viewModel.nameError.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val isAuthLoading by viewModel.isAuthLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }

    if (showForm) {
        Dialog(
            onDismissRequest = { viewModel.dismissRegisterForm() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { viewModel.dismissRegisterForm() },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(16.dp)
                        .clickable(enabled = false) {}, // Prevent closing when tapping inside dialog
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Brand Logo Centered
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            RiosConsultoresLogo(isDarkTheme = false)
                        }

                        // Tab selector for Sign Up vs Sign In
                        TabRow(
                            selectedTabIndex = if (isSignInMode) 1 else 0,
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Tab(
                                selected = !isSignInMode,
                                onClick = { viewModel.isSignInMode.value = false },
                                text = { Text("Registrarse", fontWeight = FontWeight.Bold) }
                            )
                            Tab(
                                selected = isSignInMode,
                                onClick = { viewModel.isSignInMode.value = true },
                                text = { Text("Iniciar Sesión", fontWeight = FontWeight.Bold) }
                            )
                        }

                        // Error Banner if firebase/auth operation failed
                        if (authError != null) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = authError!!,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Text(
                            text = if (isSignInMode) {
                                "Accede a tu cuenta de Lector Autorizado para descargar y ver guías y plantillas legales gratuitas."
                            } else {
                                "Para descargar las plantillas de Rios Consultores, completa tu inscripción gratuita de Lector Autorizado."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                        if (!isSignInMode) {
                            // Name input
                            OutlinedTextField(
                                value = tempName,
                                onValueChange = { viewModel.tempName.value = it },
                                label = { Text("Nombre") },
                                placeholder = { Text("Ej. Juan") },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Nombre"
                                    )
                                },
                                isError = nameError != null,
                                supportingText = {
                                    if (nameError != null) {
                                        Text(text = nameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Last Name input
                            OutlinedTextField(
                                value = tempLastName,
                                onValueChange = { viewModel.tempLastName.value = it },
                                label = { Text("Apellido") },
                                placeholder = { Text("Ej. Pérez") },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.PersonOutline,
                                        contentDescription = "Apellido"
                                    )
                                },
                                isError = lastNameError != null,
                                supportingText = {
                                    if (lastNameError != null) {
                                        Text(text = lastNameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Email input
                        OutlinedTextField(
                            value = tempEmail,
                            onValueChange = { viewModel.tempEmail.value = it },
                            label = { Text("Correo Electrónico") },
                            placeholder = { Text("juan.perez@ejemplo.com") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email"
                                )
                            },
                            isError = emailError != null,
                            supportingText = {
                                if (emailError != null) {
                                    Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        if (!isSignInMode) {
                            // Phone input
                            OutlinedTextField(
                                value = tempPhone,
                                onValueChange = { viewModel.tempPhone.value = it },
                                label = { Text("Teléfono de Contacto") },
                                placeholder = { Text("Ej. +56 9 1234 5678") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = "Teléfono"
                                    )
                                },
                                isError = phoneError != null,
                                supportingText = {
                                    if (phoneError != null) {
                                        Text(text = phoneError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Password input
                        OutlinedTextField(
                            value = tempPassword,
                            onValueChange = { viewModel.tempPassword.value = it },
                            label = { Text("Contraseña") },
                            placeholder = { Text("Mínimo 6 caracteres") },
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Contraseña"
                                )
                            },
                            trailingIcon = {
                                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            },
                            isError = passwordError != null,
                            supportingText = {
                                if (passwordError != null) {
                                    Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Submit and cancel buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.dismissRegisterForm() },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            ) {
                                Text("Cancelar")
                            }

                            Button(
                                onClick = { viewModel.registerUser() },
                                enabled = !isAuthLoading,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1.2f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                if (isAuthLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isSignInMode) Icons.Default.Login else Icons.Default.HowToReg,
                                            contentDescription = if (isSignInMode) "Entrar" else "Inscribir"
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isSignInMode) "Iniciar Sesión" else "Registrarse",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PdfViewerScreen(viewModel: ProjectViewModel, file: FreeFile, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsState()
    val downloadStatus by viewModel.downloadStatus.collectAsState()

    var currentPage by remember { mutableStateOf(0) }
    val totalPages = file.chapters.size

    val isRegistered = userProfile != null

    // Reset current page when switching documents
    LaunchedEffect(file) {
        currentPage = 0
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        // Top Toolbar of the Reader
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    IconButton(onClick = { viewModel.closePdfViewer() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = file.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Modo Visor de PDF (Simulado)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Page count indicator
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "Pág. ${currentPage + 1} de $totalPages",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Active notification / download state
        AnimatedVisibility(visible = downloadStatus != null) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = when (downloadStatus) {
                    "generating" -> MaterialTheme.colorScheme.tertiaryContainer
                    "success" -> Color(0xFFE8F5E9)
                    else -> MaterialTheme.colorScheme.errorContainer
                }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (downloadStatus == "generating") {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Generando PDF real adaptado con tus datos...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    } else if (downloadStatus == "success") {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Guardado",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "¡PDF real generado con éxito! Guardado en la carpeta de documentos.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { viewModel.setDownloadStatus(null) },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color(0xFF1B5E20))
                        }
                    }
                }
            }
        }

        // The Paper Book Container
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Document background representing a physical sheet of paper
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.72f), // PDF Page standard ratio
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // Running Header (Watermark of the registered user)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Licencia de Rios Consultores",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            color = Color(0xFFF55A05).copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold
                        )
                        
                        if (isRegistered) {
                            Text(
                                text = "Autorizado para: ${userProfile?.name} ${userProfile?.lastName} • Tel: ${userProfile?.phone}",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 8.sp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(0.7f),
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(14.dp))

                    // Document Body
                    val chapter = file.chapters.getOrNull(currentPage)
                    if (chapter != null) {
                        Text(
                            text = chapter.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = chapter.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.85f),
                            lineHeight = 22.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Running Footer
                    Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = file.author,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Página ${currentPage + 1} de $totalPages",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Action controls and Page Switcher
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Page swapper
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (currentPage > 0) currentPage-- },
                        enabled = currentPage > 0
                    ) {
                        Icon(Icons.Default.NavigateBefore, contentDescription = "Pág Anterior")
                    }

                    Text(
                        text = "Capítulo ${currentPage + 1} de $totalPages: ${file.chapters.getOrNull(currentPage)?.title?.take(22) ?: ""}...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    IconButton(
                        onClick = { if (currentPage < totalPages - 1) currentPage++ },
                        enabled = currentPage < totalPages - 1
                    ) {
                        Icon(Icons.Default.NavigateNext, contentDescription = "Pág Siguiente")
                    }
                }

                // Interactive export triggers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.closePdfViewer() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ver Catálogo")
                    }

                    Button(
                        onClick = {
                            if (isRegistered) {
                                // Trigger actual native PDF creation and share!
                                generateAndExportActualPdf(
                                    context = context,
                                    file = file,
                                    user = userProfile!!,
                                    viewModel = viewModel
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50) // Green representing successful download
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF Real")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Exportar PDF Real", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Programmatic native PDF Document writer.
 * Dynamically draws the free booklet on canvas and saves a real .pdf file to public space.
 */
fun generateAndExportActualPdf(
    context: Context,
    file: FreeFile,
    user: UserProfile,
    viewModel: ProjectViewModel
) {
    viewModel.setDownloadStatus("generating")

    try {
        val pdfDocument = PdfDocument()
        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 12f
            isAntiAlias = true
        }
        val titlePaint = Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 18f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val headerPaint = Paint().apply {
            color = android.graphics.Color.rgb(117, 117, 117) // Cool gray for metadata
            textSize = 8f
            isAntiAlias = true
        }
        val brandPaint = Paint().apply {
            color = android.graphics.Color.rgb(245, 90, 5) // Brand orange (0xFFF55A05)
            textSize = 11f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val brandSubPaint = Paint().apply {
            color = android.graphics.Color.rgb(60, 60, 60) // Dark slate gray
            textSize = 8f
            isAntiAlias = true
        }

        // Draw each chapter as a distinct PDF page
        file.chapters.forEachIndexed { index, chapter ->
            // Letter size page dimensions (612 x 792 points)
            val pageInfo = PdfDocument.PageInfo.Builder(612, 792, index + 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas: Canvas = page.canvas

            // Page Background
            canvas.drawColor(android.graphics.Color.WHITE)

            // Draw the corporate logo icon (3 pillars + pediment) in the header!
            val colPaint = Paint().apply {
                color = android.graphics.Color.rgb(245, 90, 5)
                style = Paint.Style.FILL
            }
            // Columns
            canvas.drawRect(36f, 22f, 38f, 38f, colPaint)
            canvas.drawRect(41f, 22f, 43f, 38f, colPaint)
            canvas.drawRect(46f, 22f, 48f, 38f, colPaint)
            // Pediment
            canvas.drawRect(34f, 19f, 50f, 21f, colPaint)
            canvas.drawRect(35f, 17f, 49f, 19f, colPaint)
            // Base
            canvas.drawRect(34f, 38f, 50f, 40f, colPaint)

            // Header Branding Text
            canvas.drawText("RIOS CONSULTORES • Abogado en ruta", 56f, 29f, brandPaint)
            canvas.drawText("Biblioteca Digital Legal - Licencia de Lector Autorizado", 56f, 39f, brandSubPaint)

            // Personalized Watermark of the registered user
            val licenseUserText = "Documento exclusivo para: ${user.name} ${user.lastName} (${user.email}) - Tel: ${user.phone}"
            canvas.drawText(licenseUserText, 36f, 53f, headerPaint)

            // Horizontal separator
            val linePaint = Paint().apply {
                color = android.graphics.Color.LTGRAY
                strokeWidth = 1f
            }
            canvas.drawLine(36f, 64f, 576f, 64f, linePaint)

            // Chapter Title
            canvas.drawText(chapter.title, 36f, 100f, titlePaint)

            // Body Multi-line Word Wrap text drawing
            val bodyText = chapter.content
            val contentX = 36f
            var contentY = 140f
            val maxLineWidth = 540f // 576 - 36

            // Break up content paragraph into wrapped lines
            val words = bodyText.split(" ")
            var lineBuilder = StringBuilder()
            
            for (word in words) {
                val testLine = if (lineBuilder.isEmpty()) word else "${lineBuilder} $word"
                val textWidth = textPaint.measureText(testLine)
                if (textWidth > maxLineWidth) {
                    canvas.drawText(lineBuilder.toString(), contentX, contentY, textPaint)
                    contentY += 20f
                    lineBuilder = StringBuilder(word)
                } else {
                    lineBuilder.append(if (lineBuilder.isEmpty()) word else " $word")
                }
            }
            if (lineBuilder.isNotEmpty()) {
                canvas.drawText(lineBuilder.toString(), contentX, contentY, textPaint)
            }

            // Page Footer
            canvas.drawLine(36f, 740f, 576f, 740f, linePaint)
            canvas.drawText("Recurso Gratuito • Autor: ${file.author}", 36f, 756f, headerPaint)
            canvas.drawText("Página ${index + 1} de ${file.chapters.size}", 510f, 756f, headerPaint)

            pdfDocument.finishPage(page)
        }

        // Save PDF file to the device storage cache or files dir
        val outputDirectory = context.getExternalFilesDir(null) ?: context.cacheDir
        val safeFileName = "${file.id}_${System.currentTimeMillis()}.pdf"
        val pdfFile = File(outputDirectory, safeFileName)
        val fileOutputStream = FileOutputStream(pdfFile)
        
        pdfDocument.writeTo(fileOutputStream)
        pdfDocument.close()
        fileOutputStream.close()

        // Inform success
        viewModel.setDownloadStatus("success")
        Toast.makeText(context, "Archivo PDF guardado: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()

        // Offer standard file sharing intent to send to WhatsApp, Mail or PDF readers immediately
        try {
            val fileUri: Uri = Uri.fromFile(pdfFile)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_SUBJECT, file.title)
                putExtra(Intent.EXTRA_TEXT, "Hola! Te comparto mi documento gratuito '${file.title}' generado a mi nombre.")
                putExtra(Intent.EXTRA_STREAM, fileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF de ${file.title}"))
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo lanzar selector de envío directo: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    } catch (e: Exception) {
        viewModel.setDownloadStatus("error")
        Toast.makeText(context, "Error generando PDF real: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
