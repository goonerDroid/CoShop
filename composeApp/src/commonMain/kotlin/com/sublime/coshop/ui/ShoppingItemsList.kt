package com.sublime.coshop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingItemsList(items: List<ShoppingItem>, familyMembers: List<FamilyMember>, onItemCheckedChange: (ShoppingItem, Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items, key = { it.id }) { item ->
            val assignedMember = familyMembers.find { it.id == item.assignedUser }
            ShoppingItemCard(
                item = item,
                assignedMemberName = assignedMember?.name ?: "Unknown",
                onCheckedChange = { checked -> onItemCheckedChange(item, checked) },
            )
        }
    }
}

@Preview
@Composable
fun ShoppingItemsListPreview() {
    val familyMembers = listOf(
        FamilyMember(
            id = "user_1",
            initial = "J",
            name = "John",
            color = Color(0xFF2196F3),
            isCurrentUser = true,
            isAdmin = true,
            lastCheckedItemName = "Sourdough Bread",
        ),
        FamilyMember(
            id = "user_2",
            initial = "J",
            name = "Jane",
            color = Color(0xFFE53935),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemName = "Whole Milk",
        ),
    )
    val items = listOf(
        ShoppingItem("1", "Organic Apples", "2 lbs", ItemCategory.PRODUCE, "user_1", false),
        ShoppingItem("2", "Whole Milk", "1 gallon", ItemCategory.DAIRY, "user_2", false),
        ShoppingItem("3", "Sourdough Bread", "1 loaf", ItemCategory.BAKERY, "user_1", true),
    )

    MaterialTheme {
        ShoppingItemsList(
            items = items,
            familyMembers = familyMembers,
            onItemCheckedChange = { _, _ -> },
        )
    }
}
