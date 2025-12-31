package com.sublime.coshop.data.models

import androidx.compose.ui.graphics.Color

enum class ItemCategory(val displayName: String, val color: Color, val icon: String) {
    PRODUCE("Produce", Color(0xFF4CAF50), "🍎"),
    DAIRY("Dairy", Color(0xFF2196F3), "🥛"),
    MEAT("Meat", Color(0xFFE91E63), "🥩"),

    SEAFOOD("Seafood", Color(0xFF00BCD4), "🦐"),
    BAKERY("Bakery", Color(0xFFFF9800), "🍞"),
    PANTRY("Pantry", Color(0xFF9C27B0), "🥫")
}