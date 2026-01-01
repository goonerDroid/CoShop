package com.sublime.coshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.ShoppingList
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_add
import coshop.composeapp.generated.resources.ic_check_circle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ListPickerDropdown(
    shoppingLists: List<ShoppingList>,
    selectedList: ShoppingList,
    onListSelected: (ShoppingList) -> Unit,
    onAddListClick: () -> Unit,
    isAdmin: Boolean,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        shoppingLists.forEach { list ->
            val isSelected = list.id == selectedList.id
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = list.emoji,
                            fontSize = 18.sp,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = list.name,
                            fontSize = 16.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color(0xFF1976D2) else Color(0xFF212121),
                        )
                    }
                },
                onClick = { onListSelected(list) },
                trailingIcon = {
                    if (isSelected) {
                        Image(
                            painter = painterResource(Res.drawable.ic_check_circle),
                            contentDescription = "Selected",
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF1976D2)),
                        )
                    }
                },
            )
        }

        if (isAdmin) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.ic_add),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF1976D2)),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add New List",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF1976D2),
                        )
                    }
                },
                onClick = onAddListClick,
            )
        }
    }
}

@Preview
@Composable
fun ListPickerDropdownPreview() {
    MaterialTheme {
        ListPickerDropdown(
            shoppingLists = listOf(
                ShoppingList("1", "Weekly Essentials", "🛒", "family_1", true),
                ShoppingList("2", "Costco", "📦", "family_1"),
                ShoppingList("3", "Indian Groceries", "🥘", "family_1"),
            ),
            selectedList = ShoppingList("1", "Weekly Essentials", "🛒", "family_1", true),
            onListSelected = {},
            onAddListClick = {},
            isAdmin = true,
            expanded = true,
            onDismissRequest = {},
        )
    }
}
