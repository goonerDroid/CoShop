package com.sublime.coshop.data.models

import androidx.compose.ui.graphics.Color

data class FamilyMember(
    val id: String,
    val initial: String,
    val name: String,
    val color: Color,
    val isCurrentUser: Boolean = false,
    val isAdmin: Boolean = false,
    val lastCheckedItemName: String? = null,
)
