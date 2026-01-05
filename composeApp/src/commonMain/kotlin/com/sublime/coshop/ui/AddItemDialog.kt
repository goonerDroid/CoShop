package com.sublime.coshop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    familyMembers: List<FamilyMember>,
    onDismiss: () -> Unit,
    onConfirm: (name: String, quantity: String, category: ItemCategory, assignedUserId: String) -> Unit,
    editingItem: ShoppingItem? = null,
) {
    var itemName by remember { mutableStateOf(editingItem?.name ?: "") }
    var quantity by remember { mutableStateOf(editingItem?.quantity ?: "") }
    var selectedCategory by remember { mutableStateOf(editingItem?.category ?: ItemCategory.PRODUCE) }
    var selectedMemberId by remember { mutableStateOf(editingItem?.assignedUser ?: familyMembers.firstOrNull()?.id ?: "") }
    var categoryExpanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (editingItem != null) "Edit Item" else "Add Item",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        modifier = Modifier.weight(1f),
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = "Close",
                            tint = Color(0xFF757575),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Item Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    placeholder = { Text("Pasta", color = Color(0xFF9E9E9E)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFE0E0E0),
                        unfocusedIndicatorColor = Color(0xFFE0E0E0),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Quantity",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    placeholder = { Text("1 box", color = Color(0xFF9E9E9E)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFE0E0E0),
                        unfocusedIndicatorColor = Color(0xFFE0E0E0),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Category",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121),
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it },
                ) {
                    TextField(
                        value = selectedCategory.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(androidx.compose.material3.ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                        ),
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false },
                    ) {
                        ItemCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = { Text("${category.icon} ${category.displayName}") },
                                onClick = {
                                    selectedCategory = category
                                    categoryExpanded = false
                                },
                            )
                        }
                    }
                }

                if (editingItem == null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Assign to",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        familyMembers.forEach { member ->
                            val isSelected = member.id == selectedMemberId
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = { selectedMemberId = member.id },
                                    )
                                    .padding(end = 16.dp),
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

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onConfirm(itemName, quantity, selectedCategory, selectedMemberId) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D1B2A),
                        contentColor = Color.White,
                    ),
                    enabled = itemName.isNotBlank() && quantity.isNotBlank(),
                ) {
                    Text(
                        text = if (editingItem != null) "Save Changes" else "Add Item",
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
