package com.sublime.coshop.data.models

import androidx.compose.runtime.Immutable

@Immutable
data class ShoppingList(
    val id: String,
    val name: String,
    val emoji: String,
    val familyId: String,
    val isDefault: Boolean = false
)
