package com.sublime.coshop.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import com.sublime.coshop.data.MockData
import com.sublime.coshop.data.models.DuplicateAction
import com.sublime.coshop.data.models.FilterTab
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import com.sublime.coshop.data.models.ShoppingList
import com.sublime.coshop.ui.state.PendingItem
import com.sublime.coshop.ui.state.ShoppingListUiState
import com.sublime.coshop.utils.IdGenerator
import com.sublime.coshop.utils.ValidationUtils

class ShoppingListViewModel {
    private val _items = mutableStateOf(MockData.shoppingItems)
    val items: State<List<ShoppingItem>> = _items

    private val _shoppingLists = mutableStateOf(MockData.shoppingLists)
    val shoppingLists: State<List<ShoppingList>> = _shoppingLists

    private val _uiState = mutableStateOf(
        ShoppingListUiState(
            selectedListId = MockData.shoppingLists.firstOrNull { it.isDefault }?.id
                ?: MockData.shoppingLists.firstOrNull()?.id
                ?: "",
        ),
    )
    val uiState: State<ShoppingListUiState> = _uiState

    val itemsForCurrentList = derivedStateOf {
        _items.value.filter { it.listId == _uiState.value.selectedListId }
    }

    val currentList = derivedStateOf {
        _shoppingLists.value.firstOrNull { it.id == _uiState.value.selectedListId }
            ?: _shoppingLists.value.firstOrNull()
            ?: ShoppingList(
                id = "",
                name = "No Lists",
                emoji = "📝",
                familyId = "",
                isDefault = true,
            )
    }

    val completedCount = derivedStateOf {
        itemsForCurrentList.value.count { it.isDone }
    }

    val totalCount = derivedStateOf {
        itemsForCurrentList.value.size
    }

    val allCount = derivedStateOf {
        itemsForCurrentList.value.count { !it.isDone }
    }

    val mineCount = derivedStateOf {
        itemsForCurrentList.value.count { it.assignedUser == MockData.currentUserId && !it.isDone }
    }

    val mineTotalCount = derivedStateOf {
        itemsForCurrentList.value.count { it.assignedUser == MockData.currentUserId }
    }

    val activeCount = derivedStateOf {
        itemsForCurrentList.value.count { !it.isDone }
    }

    val doneCount = derivedStateOf {
        itemsForCurrentList.value.count { it.isDone }
    }

    val filteredItems = derivedStateOf {
        when (_uiState.value.selectedTab) {
            FilterTab.ALL -> itemsForCurrentList.value
            FilterTab.MINE -> itemsForCurrentList.value.filter {
                it.assignedUser == MockData.currentUserId
            }
            FilterTab.ACTIVE -> itemsForCurrentList.value.filter { !it.isDone }
            FilterTab.DONE -> itemsForCurrentList.value.filter { it.isDone }
        }
    }

    fun selectList(listId: String) {
        // Only select if the list exists
        if (_shoppingLists.value.any { it.id == listId }) {
            _uiState.value = _uiState.value.copy(selectedListId = listId)
        }
    }

    fun selectTab(tab: FilterTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun showAddListDialog() {
        _uiState.value = _uiState.value.copy(showAddListDialog = true)
    }

    fun hideAddListDialog() {
        _uiState.value = _uiState.value.copy(showAddListDialog = false)
    }

    fun showAddItemDialog() {
        _uiState.value = _uiState.value.copy(showAddItemDialog = true)
    }

    fun hideAddItemDialog() {
        _uiState.value = _uiState.value.copy(showAddItemDialog = false)
    }

    fun hideDuplicateDialog() {
        _uiState.value = _uiState.value.copy(
            showDuplicateDialog = false,
            duplicateItem = null,
            pendingItem = null,
        )
    }

    fun addList(name: String, emoji: String, familyId: String) {
        val normalizedName = ValidationUtils.normalizeListName(name)
        val validation = ValidationUtils.validateListName(normalizedName)

        if (!validation.isValid) return

        val newList = ShoppingList(
            id = IdGenerator.generateListId(),
            name = normalizedName,
            emoji = emoji,
            familyId = familyId,
            isDefault = false,
        )

        _shoppingLists.value = _shoppingLists.value + newList
        _uiState.value = _uiState.value.copy(
            selectedListId = newList.id,
            showAddListDialog = false,
        )
    }

    fun addOrCheckDuplicateItem(name: String, quantity: String, category: ItemCategory, assignedUserId: String) {
        val normalizedName = ValidationUtils.normalizeItemName(name)
        val normalizedQuantity = ValidationUtils.normalizeQuantity(quantity)

        val nameValidation = ValidationUtils.validateItemName(normalizedName)
        val quantityValidation = ValidationUtils.validateQuantity(normalizedQuantity)

        if (!nameValidation.isValid || !quantityValidation.isValid) return

        val existingItem = itemsForCurrentList.value.find {
            it.name.equals(normalizedName, ignoreCase = true) && !it.isDone
        }

        if (existingItem != null) {
            _uiState.value = _uiState.value.copy(
                showAddItemDialog = false,
                showDuplicateDialog = true,
                duplicateItem = existingItem,
                pendingItem = PendingItem(
                    name = normalizedName,
                    quantity = normalizedQuantity,
                    category = category,
                    assignedUserId = assignedUserId,
                ),
            )
        } else {
            addItem(normalizedName, normalizedQuantity, category, assignedUserId)
            hideAddItemDialog()
        }
    }

    fun handleDuplicateAction(action: DuplicateAction) {
        val duplicate = _uiState.value.duplicateItem
        val pending = _uiState.value.pendingItem

        if (duplicate == null || pending == null) {
            hideDuplicateDialog()
            return
        }

        when (action) {
            DuplicateAction.REASSIGN -> {
                _items.value = _items.value.map {
                    if (it.id == duplicate.id) {
                        it.copy(
                            assignedUser = pending.assignedUserId,
                            quantity = pending.quantity.ifBlank { it.quantity },
                            category = pending.category,
                        )
                    } else {
                        it
                    }
                }
                hideDuplicateDialog()
            }

            DuplicateAction.UPDATE_QUANTITY -> {
                _items.value = _items.value.map {
                    if (it.id == duplicate.id) {
                        it.copy(quantity = pending.quantity.ifBlank { it.quantity })
                    } else {
                        it
                    }
                }
                hideDuplicateDialog()
            }

            DuplicateAction.ADD_SEPARATE -> {
                addItem(
                    pending.name,
                    pending.quantity,
                    pending.category,
                    pending.assignedUserId,
                )
                hideDuplicateDialog()
            }

            DuplicateAction.CANCEL -> {
                hideDuplicateDialog()
            }
        }
    }

    fun toggleItemDone(itemId: String, isDone: Boolean) {
        _items.value = _items.value.map {
            if (it.id == itemId) it.copy(isDone = isDone) else it
        }
    }

    private fun addItem(name: String, quantity: String, category: ItemCategory, assignedUserId: String) {
        val newItem = ShoppingItem(
            id = IdGenerator.generateItemId(),
            name = name,
            quantity = quantity,
            category = category,
            assignedUser = assignedUserId,
            isDone = false,
            listId = _uiState.value.selectedListId,
        )
        _items.value = _items.value + newItem
    }
}
