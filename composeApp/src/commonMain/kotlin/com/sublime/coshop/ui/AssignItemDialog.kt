package com.sublime.coshop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ShoppingItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AssignItemDialog(item: ShoppingItem, familyMembers: List<FamilyMember>, onAssign: (String) -> Unit, onDismiss: () -> Unit) {
    var selectedUserId by remember(item.assignedUser) { mutableStateOf(item.assignedUser) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
            ) {
                Text(
                    text = "Assign \"${item.name}\" to Family",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                )

                Spacer(modifier = Modifier.height(24.dp))

                familyMembers.forEach { member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                selectedUserId = member.id
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = selectedUserId == member.id,
                            onCheckedChange = null,
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF0D1B2A),
                                uncheckedColor = Color(0xFF9E9E9E),
                            ),
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = member.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF212121),
                            )

                            Text(
                                text = "${member.name.lowercase()}@example.com",
                                fontSize = 14.sp,
                                color = Color(0xFF757575),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onAssign(selectedUserId) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D1B2A),
                        contentColor = Color.White,
                    ),
                ) {
                    Text(
                        text = "Assign",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF212121),
                    ),
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AssignItemDialogPreview() {
    MaterialTheme {
        AssignItemDialog(
            item = ShoppingItem(
                id = "1",
                name = "Pasta",
                quantity = "1 box",
                category = com.sublime.coshop.data.models.ItemCategory.PANTRY,
                assignedUser = "user_1",
                isDone = false,
                listId = "list_1",
            ),
            familyMembers = listOf(
                FamilyMember(
                    id = "user_1",
                    initial = "J",
                    name = "John Doe",
                    color = Color(0xFF2196F3),
                    isCurrentUser = true,
                    isAdmin = true,
                    lastCheckedItemName = "Organic Apples",
                ),
                FamilyMember(
                    id = "user_2",
                    initial = "J",
                    name = "Jane Smith",
                    color = Color(0xFFE53935),
                    isCurrentUser = false,
                    isAdmin = false,
                    lastCheckedItemName = "Whole Milk",
                ),
                FamilyMember(
                    id = "user_3",
                    initial = "B",
                    name = "Bob Johnson",
                    color = Color(0xFF4CAF50),
                    isCurrentUser = false,
                    isAdmin = false,
                    lastCheckedItemName = null,
                ),
            ),
            onAssign = {},
            onDismiss = {},
        )
    }
}
