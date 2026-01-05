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
    val showEditItemDialog: Boolean = false,
    val showDuplicateDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val showAssignItemDialog: Boolean = false,
    val duplicateItem: ShoppingItem? = null,
    val editingItem: ShoppingItem? = null,
    val pendingItem: PendingItem? = null,
    val itemToDelete: ShoppingItem? = null,
    val itemToAssign: ShoppingItem? = null,
    val recentlyDeletedItem: ShoppingItem? = null,
    val deletedItemIndex: Int = -1,
    val showUndoSnackbar: Boolean = false,
    val revealedItemId: String? = null,
)

@Immutable
data class PendingItem(val name: String, val quantity: String, val category: ItemCategory, val assignedUserId: String)
