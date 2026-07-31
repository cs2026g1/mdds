package io.github.cs2026g1.mdds.modelmanager.ui

import androidx.compose.animation.core.animateRectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cs2026g1.mdds.modelmanager.ReleaseInfo
import androidx.compose.foundation.layout.heightIn

@Composable
fun UpdateDialog (releaseInfo: ReleaseInfo, onInstall: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "Model ${releaseInfo.version} Available",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())
            ) {
                if(releaseInfo.name.isNotBlank()) {
                    Text(
                        text = releaseInfo.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if(releaseInfo.body.isNotBlank()) {
                    Text(
                        text = releaseInfo.body,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },

        confirmButton = {
            Button(onClick = onInstall) {
                Text("Install")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}






