package com.sublime.coshop.data

import androidx.compose.ui.graphics.Color
import com.sublime.coshop.data.models.Family
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import com.sublime.coshop.data.models.ShoppingList

object MockData {

    val family = Family(
        id = "family_1",
        name = "Family Grocery",
        subtitle = "Let's shop together!",
    )

    val familyMembers = listOf(
        FamilyMember(
            id = "user_1",
            initial = "J",
            name = "John",
            color = Color(0xFF2196F3),
            isCurrentUser = true,
            isAdmin = true,
            lastCheckedItemName = "Organic Apples",
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
        FamilyMember(
            id = "user_3",
            initial = "B",
            name = "Bob",
            color = Color(0xFF4CAF50),
            isCurrentUser = false,
            isAdmin = false,
            lastCheckedItemName = null,
        ),
    )

    val shoppingLists = listOf(
        ShoppingList("list_1", "Weekly Essentials", "🛒", "family_1", isDefault = true),
        ShoppingList("list_2", "Costco", "📦", "family_1"),
        ShoppingList("list_3", "Indian Groceries", "🥘", "family_1"),
    )

    val shoppingItems = listOf(
        // Weekly Essentials (list_1)
        ShoppingItem("1", "Organic Apples", "2 lbs", ItemCategory.PRODUCE, "user_1", false, "list_1"),
        ShoppingItem("2", "Whole Milk", "1 gallon", ItemCategory.DAIRY, "user_2", false, "list_1"),
        ShoppingItem("3", "Sourdough Bread", "1 loaf", ItemCategory.BAKERY, "user_1", true, "list_1"),
        ShoppingItem("4", "Chicken Breast", "2 lbs", ItemCategory.MEAT, "user_2", false, "list_1"),
        ShoppingItem("5", "Pasta", "1 box", ItemCategory.PANTRY, "user_1", false, "list_1"),
        ShoppingItem("6", "Bananas", "1 bunch", ItemCategory.PRODUCE, "user_1", false, "list_1"),
        ShoppingItem("7", "Greek Yogurt", "2 cups", ItemCategory.DAIRY, "user_3", false, "list_1"),
        ShoppingItem("8", "Eggs", "1 dozen", ItemCategory.DAIRY, "user_2", false, "list_1"),
        ShoppingItem("9", "Spinach", "1 bag", ItemCategory.PRODUCE, "user_1", false, "list_1"),

        // Costco (list_2)
        ShoppingItem("10", "Paper Towels", "12 rolls", ItemCategory.PANTRY, "user_1", false, "list_2"),
        ShoppingItem("11", "Ground Beef", "5 lbs", ItemCategory.MEAT, "user_2", false, "list_2"),
        ShoppingItem("12", "Olive Oil", "2L", ItemCategory.PANTRY, "user_1", false, "list_2"),
        ShoppingItem("13", "Salmon Fillet", "3 lbs", ItemCategory.SEAFOOD, "user_2", false, "list_2"),
        ShoppingItem("14", "Croissants", "12 pack", ItemCategory.BAKERY, "user_2", true, "list_2"),
        ShoppingItem("15", "Cheddar Cheese", "2 lbs", ItemCategory.DAIRY, "user_1", false, "list_2"),

        // Indian Groceries (list_3)
        ShoppingItem("16", "Basmati Rice", "10 lbs", ItemCategory.PANTRY, "user_1", false, "list_3"),
        ShoppingItem("17", "Garam Masala", "1 jar", ItemCategory.PANTRY, "user_2", false, "list_3"),
        ShoppingItem("18", "Ghee", "32 oz", ItemCategory.DAIRY, "user_1", false, "list_3"),
        ShoppingItem("19", "Naan Bread", "6 pack", ItemCategory.BAKERY, "user_3", false, "list_3"),
        ShoppingItem("20", "Paneer", "14 oz", ItemCategory.DAIRY, "user_2", false, "list_3"),
        ShoppingItem("21", "Turmeric Powder", "1 jar", ItemCategory.PANTRY, "user_1", true, "list_3"),
        ShoppingItem("22", "Curry Leaves", "1 bunch", ItemCategory.PRODUCE, "user_3", false, "list_3"),
    )

    const val currentUserId = "user_1"
}
