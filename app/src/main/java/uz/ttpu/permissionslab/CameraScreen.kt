package uz.ttpu.permissionslab

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var permissionState by remember { mutableStateOf("unknown") }
    var denialCount by remember { mutableStateOf(0) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionState = "granted"
        } else {
            denialCount++
            permissionState = if (denialCount >= 2) "permanently_denied" else "rationale"
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        when (permissionState) {
            "granted" -> Text("Camera Permission Granted!")
            "rationale" -> {
                Text("Camera permission is needed to take pictures.")
                Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                    Text("Grant Permission")
                }
            }
            "permanently_denied" -> {
                Text("Permission denied twice. Please enable it in Settings.")
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Open Settings")
                }
            }
            else -> {
                Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                    Text("Request Camera Permission")
                }
            }
        }
    }
}