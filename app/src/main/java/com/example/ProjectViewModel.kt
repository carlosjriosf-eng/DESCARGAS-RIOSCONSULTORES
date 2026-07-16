package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.auth.FirebaseAuth

data class UserProfile(
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String
)

data class FreeFile(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val fileSize: String,
    val pages: Int,
    val author: String,
    val chapters: List<Chapter>
)

data class Chapter(
    val title: String,
    val content: String
)

class ProjectViewModel : ViewModel() {
    
    // Centralized user profile registration state
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    // Available catalog of high-quality legal resources
    private val _catalog = MutableStateFlow(listOf(
        FreeFile(
            id = "contrato_arrendamiento",
            title = "Guía de Contratos de Arrendamiento de Vivienda Urbana",
            category = "Contratos",
            description = "Un manual técnico detallado con recomendaciones legales, cláusulas de reajuste, responsabilidades de mantención y plantillas para redactar contratos seguros.",
            fileSize = "2.1 MB",
            pages = 3,
            author = "Rios Consultores - Abogado en ruta",
            chapters = listOf(
                Chapter(
                    title = "Capítulo 1: Cláusulas Esenciales",
                    content = "Todo contrato de arrendamiento debe especificar con precisión la identificación de las partes (arrendador y arrendatario), la descripción detallada del inmueble, el canon de arriendo acordado, los plazos de vigencia, prórrogas y las formas de pago. Se aconseja estipular penalidades claras por mora para evitar litigios prolongados."
                ),
                Chapter(
                    title = "Capítulo 2: Reajustes de Renta",
                    content = "De conformidad con la legislación civil vigente, los cánones de arrendamiento urbano pueden incrementarse anualmente conforme a la variación oficial del Índice de Precios al Consumidor (IPC). Es nula cualquier cláusula de reajuste que supere este límite legal en viviendas destinadas al uso habitacional habitual."
                ),
                Chapter(
                    title = "Capítulo 3: Reparaciones y Mejoras",
                    content = "Por regla general, las reparaciones necesarias para mantener la vivienda en estado de servir para el fin pactado corresponden exclusivamente al arrendador. En cambio, las mejoras locativas que derivan de deterioros comunes causados por el uso normal del habitante corresponden al arrendatario, salvo estipulación en contrario."
                )
            )
        ),
        FreeFile(
            id = "derechos_laborales",
            title = "Manual de Derechos Laborales e Indemnizaciones",
            category = "Derecho Laboral",
            description = "Aprende a calcular finiquitos, indemnizaciones por años de servicio, aviso previo, causales válidas de término de contrato y cómo actuar frente a despidos injustificados.",
            fileSize = "1.8 MB",
            pages = 3,
            author = "Rios Consultores - Abogado en ruta",
            chapters = listOf(
                Chapter(
                    title = "Capítulo 1: Jornada de Trabajo y Horas Extras",
                    content = "La jornada de trabajo debe estar estrictamente definida en el contrato individual. Toda jornada realizada que exceda los límites legales pactados constituye hora extraordinaria y debe registrarse en los sistemas de control de asistencia de la empresa, pagándose obligatoriamente con el recargo mínimo legal sobre la remuneración ordinaria."
                ),
                Chapter(
                    title = "Capítulo 2: Causales Legales de Término",
                    content = "El empleador solo puede rescindir de forma unilateral la relación de trabajo invocando taxativamente causales contempladas por el código del trabajo. Dichas causales de despido requieren de una carta formal de notificación remitida dentro de los plazos de ley, detallando fundadamente los hechos específicos que la motivan."
                ),
                Chapter(
                    title = "Capítulo 3: El Autodespido o Despido Indirecto",
                    content = "Si la empresa incurre en incumplimientos contractuales graves (como el retraso reiterado de remuneraciones o la omisión de cotizaciones previsionales), el trabajador está legalmente facultado para poner fin al contrato de manera unilateral bajo la figura del autodespido, conservando el derecho al cobro de indemnizaciones."
                )
            )
        ),
        FreeFile(
            id = "guia_despidos",
            title = "Guía Práctica sobre Despidos e Indemnizaciones",
            category = "Derecho Laboral",
            description = "Un manual de autodefensa laboral ante despidos injustificados, término de contrato, finiquitos, cobro de indemnizaciones por años de servicio y aviso previo.",
            fileSize = "2.2 MB",
            pages = 3,
            author = "Rios Consultores - Abogado en ruta",
            chapters = listOf(
                Chapter(
                    title = "Capítulo 1: Causales de Despido e Injustificación",
                    content = "Un despido solo es válido si se fundamenta en causales legales específicas expresadas en la carta de despido. Si el empleador invoca causales ambiguas como 'necesidades de la empresa' sin justificación técnica o financiera real, o bien acusa faltas sin pruebas fehacientes, el trabajador puede demandar despido injustificado o indebido ante los tribunales para exigir recargos legales de hasta un 150% en sus indemnizaciones."
                ),
                Chapter(
                    title = "Capítulo 2: Liquidación de Finiquito e Indemnizaciones",
                    content = "El finiquito debe detallar todas las sumas adeudadas: indemnización por años de servicio (si corresponde), indemnización sustitutiva de aviso previo (cuando no se avisa con 30 días de anticipación), vacaciones proporcionales no tomadas (feriado proporcional), y las remuneraciones pendientes del último mes trabajado. El trabajador tiene el derecho legal irrenunciable a firmar el finiquito con una 'Reserva de Derechos' escrita de puño y letra para poder reclamar judicialmente diferencias posteriores."
                ),
                Chapter(
                    title = "Capítulo 3: Procedimiento de Reclamo Administrativo",
                    content = "Antes de iniciar una demanda judicial, el primer paso recomendado es interponer un reclamo formal ante la Inspección del Trabajo o ente administrativo del trabajo de su jurisdicción. Esto convoca a una audiencia de conciliación obligatoria entre el trabajador y el empleador. Si no se logra acuerdo en esta instancia, se emitirá un acta que habilita el inicio de acciones judiciales directas patrocinadas por un abogado laboralista."
                )
            )
        ),
        FreeFile(
            id = "constitucion_sociedades",
            title = "Guía Técnica de Constitución de Empresas y SpA",
            category = "Derecho Comercial",
            description = "Estrategias de constitución para emprendedores: diferencias claves entre Sociedades por Acciones (SpA), de Responsabilidad Limitada y empresas individuales.",
            fileSize = "2.4 MB",
            pages = 3,
            author = "Rios Consultores - Abogado en ruta",
            chapters = listOf(
                Chapter(
                    title = "Capítulo 1: Elección de Estructura Jurídica",
                    content = "Formalizar tu negocio con la estructura adecuada protege tu patrimonio personal al separarlo del de la empresa. Las Sociedades por Acciones (SpA) destacan por su versatilidad y facilidad para integrar inversionistas futuros, mientras que las de Responsabilidad Limitada aseguran mayor control entre socios de confianza."
                ),
                Chapter(
                    title = "Capítulo 2: Redacción del Objeto y Capital Social",
                    content = "El estatuto constitucional define la personalidad de la sociedad. El objeto social debe redactarse de forma amplia para abarcar diversas actividades económicas conexas, y el capital social debe ser suscrito y pagado conforme a las necesidades reales del negocio y plazos viables declarados en el acta estatutaria."
                ),
                Chapter(
                    title = "Capítulo 3: Formalidades Tributarias",
                    content = "Constituida la sociedad en el registro correspondiente, el paso crítico inmediato es realizar el trámite formal de inicio de actividades y obtención de RUT ante la administración tributaria. Determinar con la asesoría debida el régimen impositivo óptimo previene costosas multas y dobles tributaciones innecesarias."
                )
            )
        ),
        FreeFile(
            id = "demanda_alimentos",
            title = "Formulario y Guía de Demanda de Pensión Alimenticia",
            category = "Derecho de Familia",
            description = "Instrucciones de tramitación, criterios del tribunal para fijar montos de pensión de alimentos, mediación familiar obligatoria y medidas de apremio.",
            fileSize = "1.5 MB",
            pages = 3,
            author = "Rios Consultores - Abogado en ruta",
            chapters = listOf(
                Chapter(
                    title = "Capítulo 1: El Derecho de Alimentos",
                    content = "La pensión de alimentos comprende los aportes necesarios para sustento, educación, salud, habitación y recreación de los alimentarios menores de edad. Por regla general, ambos progenitores deben contribuir al sustento de los hijos en proporción directa a sus respectivas capacidades económicas actuales."
                ),
                Chapter(
                    title = "Capítulo 2: La Mediación Previa Obligatoria",
                    content = "Previo a interponer formalmente un juicio por alimentos ante los tribunales de familia, las leyes exigen someterse a una instancia obligatoria de mediación colectiva. Si se logra un acuerdo, se eleva a rango judicial homologado, de lo contrario, el mediador emite el acta de mediación frustrada habilitando la vía de la demanda."
                ),
                Chapter(
                    title = "Capítulo 3: Apremios por Incumplimiento",
                    content = "Frente al incumplimiento reiterado del pago decretado de pensión de alimentos, el ordenamiento jurídico provee severas medidas de apremio para garantizar el cobro, tales como la retención de devoluciones tributarias, el arraigo nacional, la retención de fondos bancarios e inclusive la suspensión de licencias de conducir."
                )
            )
        )
    ))
    val catalog: StateFlow<List<FreeFile>> = _catalog.asStateFlow()

    // State for searching and filtering categories
    private val _selectedCategory = MutableStateFlow<String?>("Todos")
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    
    val searchQuery = MutableStateFlow("")

    // Temporary registration states for the form UI
    val tempName = MutableStateFlow("")
    val tempLastName = MutableStateFlow("")
    val tempEmail = MutableStateFlow("")
    val tempPhone = MutableStateFlow("")
    val tempPassword = MutableStateFlow("")
    val isSignInMode = MutableStateFlow(false) // Toggle sign in (true) vs register (false)

    // Form validation error messages
    val nameError = MutableStateFlow<String?>(null)
    val lastNameError = MutableStateFlow<String?>(null)
    val emailError = MutableStateFlow<String?>(null)
    val phoneError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)

    private val _isAuthLoading = MutableStateFlow(false)
    val isAuthLoading: StateFlow<Boolean> = _isAuthLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    // Safe instance retrieval of FirebaseAuth to prevent crashes if not initialized
    private val firebaseAuth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Current file chosen for download/visualizer
    private val _selectedFileForAction = MutableStateFlow<FreeFile?>(null)
    val selectedFileForAction: StateFlow<FreeFile?> = _selectedFileForAction.asStateFlow()

    // Control dialogs & screens
    private val _showRegisterForm = MutableStateFlow(false)
    val showRegisterForm: StateFlow<Boolean> = _showRegisterForm.asStateFlow()

    private val _activePdfViewerFile = MutableStateFlow<FreeFile?>(null)
    val activePdfViewerFile: StateFlow<FreeFile?> = _activePdfViewerFile.asStateFlow()

    private val _downloadStatus = MutableStateFlow<String?>(null) // "idle", "generating", "success", "error"
    val downloadStatus: StateFlow<String?> = _downloadStatus.asStateFlow()

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun selectFileForAction(file: FreeFile) {
        _selectedFileForAction.value = file
        // If not registered, trigger the signup overlay
        if (_userProfile.value == null) {
            _showRegisterForm.value = true
        } else {
            // Already registered! Immediately show the simulated PDF visualizer
            _activePdfViewerFile.value = file
        }
    }

    fun dismissRegisterForm() {
        _showRegisterForm.value = false
        clearFormErrors()
    }

    fun closePdfViewer() {
        _activePdfViewerFile.value = null
        _downloadStatus.value = null
    }

    private fun clearFormErrors() {
        nameError.value = null
        lastNameError.value = null
        emailError.value = null
        phoneError.value = null
        passwordError.value = null
        _authError.value = null
    }

    fun registerUser(): Boolean {
        var isValid = true
        clearFormErrors()

        val isSignIn = isSignInMode.value
        val nameVal = tempName.value.trim()
        val lastNameVal = tempLastName.value.trim()
        val emailVal = tempEmail.value.trim()
        val phoneVal = tempPhone.value.trim()
        val passwordVal = tempPassword.value.trim()

        if (!isSignIn) {
            if (nameVal.isEmpty()) {
                nameError.value = "El nombre es obligatorio"
                isValid = false
            } else if (nameVal.length < 2) {
                nameError.value = "Nombre demasiado corto"
                isValid = false
            }

            if (lastNameVal.isEmpty()) {
                lastNameError.value = "El apellido es obligatorio"
                isValid = false
            } else if (lastNameVal.length < 2) {
                lastNameError.value = "Apellido demasiado corto"
                isValid = false
            }

            if (phoneVal.isEmpty()) {
                phoneError.value = "El teléfono es obligatorio"
                isValid = false
            } else if (phoneVal.length < 7) {
                phoneError.value = "El teléfono debe tener al menos 7 dígitos"
                isValid = false
            }
        }

        val emailPattern = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        if (emailVal.isEmpty()) {
            emailError.value = "El correo electrónico es obligatorio"
            isValid = false
        } else if (!emailVal.matches(Regex(emailPattern))) {
            emailError.value = "Formato de correo electrónico inválido"
            isValid = false
        }

        if (passwordVal.isEmpty()) {
            passwordError.value = "La contraseña es obligatoria"
            isValid = false
        } else if (passwordVal.length < 6) {
            passwordError.value = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        if (isValid) {
            val auth = firebaseAuth
            if (auth != null) {
                _isAuthLoading.value = true
                _authError.value = null

                if (isSignIn) {
                    // Firebase Sign In
                    auth.signInWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener { task ->
                            _isAuthLoading.value = false
                            if (task.isSuccessful) {
                                val firebaseUser = task.result?.user
                                val displayName = firebaseUser?.displayName ?: ""
                                val parts = displayName.split(" ")
                                val firstName = parts.getOrNull(0) ?: "Lector"
                                val lastName = parts.getOrNull(1) ?: "Autorizado"
                                val profile = UserProfile(
                                    name = firstName,
                                    lastName = lastName,
                                    email = firebaseUser?.email ?: emailVal,
                                    phone = "+56 9 9999 9999"
                                )
                                _userProfile.value = profile
                                _showRegisterForm.value = false
                                _selectedFileForAction.value?.let { file ->
                                    _activePdfViewerFile.value = file
                                }
                            } else {
                                _authError.value = task.exception?.localizedMessage ?: "Error al iniciar sesión."
                            }
                        }
                } else {
                    // Firebase Sign Up / Register
                    auth.createUserWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser = task.result?.user
                                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                    .setDisplayName("$nameVal $lastNameVal")
                                    .build()
                                firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                                    _isAuthLoading.value = false
                                    val profile = UserProfile(
                                        name = nameVal,
                                        lastName = lastNameVal,
                                        email = emailVal,
                                        phone = phoneVal
                                    )
                                    _userProfile.value = profile
                                    _showRegisterForm.value = false
                                    _selectedFileForAction.value?.let { file ->
                                        _activePdfViewerFile.value = file
                                    }
                                }
                            } else {
                                _isAuthLoading.value = false
                                _authError.value = task.exception?.localizedMessage ?: "Error de registro."
                            }
                        }
                }
            } else {
                // Graceful fallback for local development or missing setup
                val profile = UserProfile(
                    name = if (isSignIn) "Lector" else nameVal,
                    lastName = if (isSignIn) "Autorizado" else lastNameVal,
                    email = emailVal,
                    phone = if (isSignIn) "+56 9 9999 9999" else phoneVal
                )
                _userProfile.value = profile
                _showRegisterForm.value = false
                _selectedFileForAction.value?.let { file ->
                    _activePdfViewerFile.value = file
                }
            }
            return true
        }
        return false
    }

    fun unregisterUser() {
        try {
            firebaseAuth?.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _userProfile.value = null
        tempName.value = ""
        tempLastName.value = ""
        tempEmail.value = ""
        tempPhone.value = ""
        tempPassword.value = ""
        isSignInMode.value = false
        _activePdfViewerFile.value = null
        _selectedFileForAction.value = null
        clearFormErrors()
    }

    fun setDownloadStatus(status: String?) {
        _downloadStatus.value = status
    }
}
