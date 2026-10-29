package uz.ttpu.permissionslab

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun LocationScreen() {
    val context = LocalContext.current
    var locationStatus by remember { mutableStateOf("none") }

    fun checkLocationPermissions() {
        val fine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        locationStatus = when {
            fine -> "precise"
            coarse -> "approximate"
            else -> "none"
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val fineGranted = results[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = results[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        locationStatus = when {
            fineGranted -> "precise"
            coarseGranted -> "approximate"
            else -> "none"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Location Status: $locationStatus",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                launcher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Request Location Permission")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Manual check button to verify state after background return
        Button(
            onClick = { checkLocationPermissions() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check Current Status")
        }
    }
}