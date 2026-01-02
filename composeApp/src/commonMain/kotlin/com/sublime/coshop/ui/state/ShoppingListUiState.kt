package com.sublime.coshop.ui.state

import androidx.compose.runtime.Immutable
import com.sublime.coshop.data.models.FilterTab
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem

@Immutable
data class ShoppingListUiState(
    val selectedListId: String = "",
    val selectedTab: FilterTab = FilterTab.ALL,
    val showAddListDialog: Boolean = false,
    val showAddItemDialog: Boolean = false,
    val showDuplicateDialog: Boolean = false,
    val duplicateItem: ShoppingItem? = null,
    val pendingItem: PendingItem? = null,
)

@Immutable
data class PendingItem(val name: String, val quantity: String, val category: ItemCategory, val assignedUserId: String)
