package com.sublime.coshop.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.models.Family
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.FilterTab
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen() {
    val currentUserId = "user_1"

    val family = Family(
        id = "family_1",
        name = "Family Grocery",
        subtitle = "Let's shop together!"
    )

    val familyMembers = listOf(
        FamilyMember(
            id = "user_1",
            initial = "J",
            name = "John",
            color = Color(0xFF2196F3),
            isCurrentUser = true,
            isAdmin = true,
            lastCheckedItemName = "Organic Apples"
        ),
        FamilyMember(
            id = "user_2",
            initial = "J",
            name = "Jane",
            color = Color(0xFFE53935),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemName = "Whole Milk"
        ),
        FamilyMember(
            id = "user_3",
            initial = "B",
            name = "Bob",
            color = Color(0xFF4CAF50),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemName = null
        )
    )

    var items by remember {
        mutableStateOf(
            listOf(
                ShoppingItem("1", "Organic Apples", "2 lbs", ItemCategory.PRODUCE, "user_1", false),
                ShoppingItem("2", "Whole Milk", "1 gallon", ItemCategory.DAIRY, "user_2", false),
                ShoppingItem("3", "Sourdough Bread", "1 loaf", ItemCategory.BAKERY, "user_1", true),
                ShoppingItem("4", "Chicken Breast", "2 lbs", ItemCategory.MEAT, "user_2", false),
                ShoppingItem("5", "Pasta", "1 box", ItemCategory.PANTRY, "user_1", false),
                ShoppingItem("6", "Salmon Fillet", "1 lb", ItemCategory.SEAFOOD, "user_2", false),
                ShoppingItem("7", "Bananas", "1 bunch", ItemCategory.PRODUCE, "user_1", false),
                ShoppingItem("8", "Greek Yogurt", "2 cups", ItemCategory.DAIRY, "user_3", false),
                ShoppingItem("9", "Bagels", "6 pack", ItemCategory.BAKERY, "user_1", true),
                ShoppingItem("10", "Ground Beef", "1 lb", ItemCategory.MEAT, "user_2", false),
                ShoppingItem("11", "Olive Oil", "500ml", ItemCategory.PANTRY, "user_1", false),
                ShoppingItem("12", "Shrimp", "1 lb", ItemCategory.SEAFOOD, "user_3", false),
                ShoppingItem("13", "Avocados", "3 pack", ItemCategory.PRODUCE, "user_2", false),
                ShoppingItem("14", "Cheddar Cheese", "8 oz", ItemCategory.DAIRY, "user_1", false),
                ShoppingItem("15", "Croissants", "4 pack", ItemCategory.BAKERY, "user_2", true),
                ShoppingItem("16", "Bacon", "12 oz", ItemCategory.MEAT, "user_1", false),
                ShoppingItem("17", "Rice", "2 lbs", ItemCategory.PANTRY, "user_3", false),
                ShoppingItem("18", "Spinach", "1 bag", ItemCategory.PRODUCE, "user_1", false),
                ShoppingItem("19", "Eggs", "1 dozen", ItemCategory.DAIRY, "user_2", false),
                ShoppingItem("20", "Tuna Steak", "2 pieces", ItemCategory.SEAFOOD, "user_1", false)
            )
        )
    }

    var selectedTab by remember { mutableStateOf(FilterTab.ALL) }

    val completedCount = items.count { it.isDone }
    val totalCount = items.size
    val currentUser = familyMembers.first { it.isCurrentUser }

    val allCount = items.size
    val mineCount = items.count { it.assignedUser == currentUserId }
    val activeCount = items.count { !it.isDone }
    val doneCount = items.count { it.isDone }

    val filteredItems = when (selectedTab) {
        FilterTab.ALL -> items
        FilterTab.MINE -> items.filter { it.assignedUser == currentUserId }
        FilterTab.ACTIVE -> items.filter { !it.isDone }
        FilterTab.DONE -> items.filter { it.isDone }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Fixed Header
        HeaderSection(
            family = family,
            currentUser = currentUser,
            completedCount = completedCount,
            totalCount = totalCount
        )

        // Scrollable Content
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Family Members Section (scrolls with content)
            item {
                FamilyMembersSection(
                    familyMembers = familyMembers,
                    onAddMemberClick = { /* TODO */ }
                )
            }

            // Sticky Filter Tabs
            stickyHeader {
                ShoppingListHeader(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    allCount = allCount,
                    mineCount = mineCount,
                    activeCount = activeCount,
                    doneCount = doneCount
                )
            }

            // Shopping Items (cards)
            items(filteredItems, key = { it.id }) { item ->
                val assignedMember = familyMembers.find { it.id == item.assignedUser }
                ShoppingItemCard(
                    item = item,
                    assignedMemberName = assignedMember?.name ?: "Unknown",
                    onCheckedChange = { checked ->
                        items = items.map {
                            if (it.id == item.id) it.copy(isDone = checked) else it
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun ShoppingListScreenPreview() {
    MaterialTheme {
        ShoppingListScreen()
    }
}