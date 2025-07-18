package com.example.datastoreanilys.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastoreanilys.R
import com.example.datastoreanilys.ViewModel.DataStoreViewModel
import com.example.datastoreanilys.data.UserData

@Composable
fun MainScreen(viewModel: DataStoreViewModel = viewModel()) {
    val user by viewModel.userData.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { isEditing = !isEditing }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (isEditing) {
            EditCardContent(
                user = user,
                onValueChange = { viewModel.updateAll(it) },
                onSave = {
                    viewModel.saveAll()
                    Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                    isEditing = false
                }
            )
        } else {
            ViewCardContent(user = user)
        }
    }
}

@Composable
fun ViewCardContent(user: UserData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.botani),
            contentDescription = "Avatar",
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(user.name, style = MaterialTheme.typography.titleLarge)
        Text(user.profession, style = MaterialTheme.typography.bodyMedium)
        Text("${user.experience} years of Experience", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text("📞 ${user.phone}")
            Text("🔗 ${user.handle}")
            Text("✉️ ${user.email}")
        }
    }
}

@Composable
fun EditCardContent(
    user: UserData,
    onValueChange: (UserData) -> Unit,
    onSave: () -> Unit
) {
    var showContact by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.botani),
            contentDescription = "Avatar",
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )

        OutlinedTextField(
            value = user.name,
            onValueChange = { onValueChange(user.copy(name = it)) },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = user.profession,
            onValueChange = { onValueChange(user.copy(profession = it)) },
            label = { Text("Role") }
        )
        Text("Experience: ${user.experience}")
        Slider(
            value = user.experience.toFloat(),
            onValueChange = { onValueChange(user.copy(experience = it.toInt())) },
            valueRange = 0f..50f
        )

        if (showContact) {
            OutlinedTextField(
                value = user.phone,
                onValueChange = { onValueChange(user.copy(phone = it)) },
                label = { Text("Phone") }
            )
            OutlinedTextField(
                value = user.handle,
                onValueChange = { onValueChange(user.copy(handle = it)) },
                label = { Text("Handle") }
            )
            OutlinedTextField(
                value = user.email,
                onValueChange = { onValueChange(user.copy(email = it)) },
                label = { Text("Email") }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Show Contact Info")
            Switch(checked = showContact, onCheckedChange = { showContact = it })
        }
        Button(onClick = onSave) {
            Text("Save & Close")
        }
    }
}
