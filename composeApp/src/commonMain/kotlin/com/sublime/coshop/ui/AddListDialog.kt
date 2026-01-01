package com.sublime.coshop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

private val presetEmojis = listOf("🛒", "🥘", "📦", "🏪", "🥬", "🧹", "🍎", "🥛", "🍞", "🥩", "🦐", "🥫")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddListDialog(onDismiss: () -> Unit, onConfirm: (name: String, emoji: String) -> Unit) {
    var listName by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("🛒") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Create New List",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = listName,
                    onValueChange = { listName = it },
                    label = { Text("List Name") },
                    placeholder = { Text("e.g., Costco, Weekly Essentials") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose an icon",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    presetEmojis.forEach { emoji ->
                        val isSelected = emoji == selectedEmoji
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color(0xFFE3F2FD) else Color(0xFFF5F5F5),
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color(0xFF1976D2) else Color.Transparent,
                                    shape = CircleShape,
                                )
                                .clickable { selectedEmoji = emoji },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(listName, selectedEmoji) },
                enabled = listName.isNotBlank(),
            ) {
                Text(
                    text = "Create",
                    color = if (listName.isNotBlank()) Color(0xFF1976D2) else Color(0xFFBDBDBD),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = Color(0xFF757575),
                )
            }
        },
    )
}

@Preview
@Composable
fun AddListDialogPreview() {
    MaterialTheme {
        AddListDialog(
            onDismiss = {},
            onConfirm = { _, _ -> },
        )
    }
}
