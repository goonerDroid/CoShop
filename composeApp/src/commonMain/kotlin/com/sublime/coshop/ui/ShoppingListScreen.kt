package com.sublime.coshop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sublime.coshop.data.models.Family
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem

@Composable
fun ShoppingListScreen() {
    val currentUserId = "user_1"
    val isCurrentUserAdmin = true

    val family = Family(
        id = "family_1", name = "Family Grocery", subtitle = "Lets shop together!"
    )

    val familyMembers = listOf(
        FamilyMember(
            "user_1",
            "J",
            "John",
            Color(0xFF2196F3),
            isCurrentUser = true,
            isAdmin = true,
            lastCheckedItemCategory = ItemCategory.BAKERY
        ), FamilyMember(
            "user_2",
            "J",
            "Jane",
            Color(0xFFE53935),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemCategory = ItemCategory.DAIRY
        ), FamilyMember(
            "user_3",
            "B",
            "Bob",
            Color(0xFF4CAF50),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemCategory = null
        )
    )

    var items by remember {
        mutableStateOf(
            listOf(
                ShoppingItem("1", "Organic Apples", "2 lbs", ItemCategory.PRODUCE, "user_1", true),
                ShoppingItem("2", "Whole Milk", "1 gallon", ItemCategory.DAIRY, "user_2", true),
                ShoppingItem("3", "Sourdough Bread", "1 loaf", ItemCategory.BAKERY, "user_1", true),
                ShoppingItem("4", "Chicken Breast", "2 lbs", ItemCategory.MEAT, "user_2", true),
                ShoppingItem("5", "Pasta", "1 box", ItemCategory.PANTRY, "user_1", false)
            )
        )
    }

    val completedCount = items.count { it.isDone }
    val totalCount = items.size
    val currentUser = familyMembers.first { it.isCurrentUser }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))
    ) {
        HeaderSection(
            family = family,
            currentUser = currentUser,
            completedCount = completedCount,
            totalCount = totalCount
        )
    }
}