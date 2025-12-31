package com.sublime.coshop.data.models

data class ShoppingItem(
    val id:String,
    val name:String,
    val quantity: String,
    val category: ItemCategory,
    val assignedUser: String,
    val isDone: Boolean = false,
)
