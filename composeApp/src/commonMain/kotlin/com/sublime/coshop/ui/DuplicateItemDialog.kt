package com.sublime.coshop.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.DuplicateAction
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DuplicateItemDialog(
    existingItem: ShoppingItem,
    existingAssigneeName: String,
    newAssigneeName: String,
    onAction: (DuplicateAction) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onAction(DuplicateAction.CANCEL) },
        title = {
            Text(
                text = "\"${existingItem.name}\" is already in the list",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        },
        text = {
            Column {
                val quantityText = if (existingItem.quantity.isNotBlank()) {
                    " (${existingItem.quantity})"
                } else {
                    ""
                }
                Text(
                    text = "Assigned to: $existingAssigneeName$quantityText",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "What would you like to do?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121),
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            ) {
                TextButton(
                    onClick = { onAction(DuplicateAction.REASSIGN) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Reassign to $newAssigneeName",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                TextButton(
                    onClick = { onAction(DuplicateAction.INCREASE_QUANTITY) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Increase quantity",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Medium,
                    )
                }

                TextButton(
                    onClick = { onAction(DuplicateAction.ADD_SEPARATE) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Add as separate item",
                        color = Color(0xFF757575),
                        fontWeight = FontWeight.Medium,
                    )
                }

                TextButton(
                    onClick = { onAction(DuplicateAction.CANCEL) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFF757575),
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun DuplicateItemDialogPreview() {
    MaterialTheme {
        DuplicateItemDialog(
            existingItem = ShoppingItem(
                id = "1",
                name = "Organic Milk",
                quantity = "2 gallons",
                category = ItemCategory.DAIRY,
                assignedUser = "user_2",
                isDone = false,
                listId = "list_1",
            ),
            existingAssigneeName = "Jane",
            newAssigneeName = "John",
            onAction = {},
        )
    }
}
