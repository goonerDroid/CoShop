package com.sublime.coshop.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.ShoppingItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DeleteConfirmDialog(item: ShoppingItem, assignedMemberName: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = "Delete \"${item.name}\"?",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        },
        text = {
            Column {
                Text(
                    text = "Quantity: ${item.quantity}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Category: ${item.category.icon} ${item.category.displayName}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Assigned to: $assignedMemberName",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "This action cannot be undone.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Delete",
                    color = Color(0xFFD32F2F),
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
fun DeleteConfirmDialogPreview() {
    MaterialTheme {
        DeleteConfirmDialog(
            item = ShoppingItem(
                id = "1",
                name = "Organic Apples",
                quantity = "2 lbs",
                category = com.sublime.coshop.data.models.ItemCategory.PRODUCE,
                assignedUser = "user_1",
                isDone = false,
                listId = "list_1",
            ),
            assignedMemberName = "John",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
