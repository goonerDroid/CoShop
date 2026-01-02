package com.sublime.coshop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ItemCategory
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddItemDialog(
    familyMembers: List<FamilyMember>,
    onDismiss: () -> Unit,
    onConfirm: (name: String, quantity: String, category: ItemCategory, assignedUserId: String) -> Unit,
) {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ItemCategory.PRODUCE) }
    var selectedMemberId by remember { mutableStateOf(familyMembers.firstOrNull()?.id ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Item",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    placeholder = { Text("e.g., Organic Apples") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    placeholder = { Text("e.g., 2 lbs") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Category",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ItemCategory.entries.forEach { category ->
                        val isSelected = category == selectedCategory
                        Surface(
                            modifier = Modifier
                                .clickable { selectedCategory = category },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) category.color.copy(alpha = 0.15f) else Color(0xFFF5F5F5),
                            border = androidx.compose.foundation.BorderStroke(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) category.color else Color.Transparent,
                            ),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = category.icon,
                                    fontSize = 16.sp,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = category.displayName,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) category.color else Color(0xFF757575),
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Assign to",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    familyMembers.forEach { member ->
                        val isSelected = member.id == selectedMemberId
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { selectedMemberId = member.id },
                        ) {
                            Box {
                                Surface(
                                    modifier = Modifier.size(44.dp),
                                    shape = CircleShape,
                                    color = member.color,
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 3.dp else 0.dp,
                                        color = if (isSelected) Color(0xFF1976D2) else Color.Transparent,
                                    ),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = member.initial,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (member.isCurrentUser) "You" else member.name,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF1976D2) else Color(0xFF757575),
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            val isFormValid = itemName.isNotBlank() && quantity.isNotBlank()
            TextButton(
                onClick = { onConfirm(itemName, quantity, selectedCategory, selectedMemberId) },
                enabled = isFormValid,
            ) {
                Text(
                    text = "Add",
                    color = if (isFormValid) Color(0xFF1976D2) else Color(0xFFBDBDBD),
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
fun AddItemDialogPreview() {
    MaterialTheme {
        AddItemDialog(
            familyMembers = listOf(
                FamilyMember(
                    id = "user_1",
                    initial = "J",
                    name = "John",
                    color = Color(0xFF2196F3),
                    isCurrentUser = true,
                    isAdmin = true,
                    lastCheckedItemName = null,
                ),
                FamilyMember(
                    id = "user_2",
                    initial = "S",
                    name = "Sarah",
                    color = Color(0xFFE91E63),
                    isCurrentUser = false,
                    isAdmin = false,
                    lastCheckedItemName = null,
                ),
            ),
            onDismiss = {},
            onConfirm = { _, _, _, _ -> },
        )
    }
}
