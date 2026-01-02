package com.sublime.coshop.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.MockData
import com.sublime.coshop.ui.theme.CoShopColors
import com.sublime.coshop.viewmodels.ShoppingListViewModel
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel = remember { ShoppingListViewModel() }) {
    val family = MockData.family
    val familyMembers = MockData.familyMembers
    val currentUser = remember(familyMembers) { familyMembers.first { it.isCurrentUser } }

    val uiState = viewModel.uiState.value
    val shoppingLists = viewModel.shoppingLists.value
    val currentList = viewModel.currentList.value
    val filteredItems = viewModel.filteredItems.value
    val completedCount = viewModel.completedCount.value
    val totalCount = viewModel.totalCount.value
    val allCount = viewModel.allCount.value
    val mineCount = viewModel.mineCount.value
    val activeCount = viewModel.activeCount.value
    val doneCount = viewModel.doneCount.value

    val memberNameById = remember(familyMembers) {
        familyMembers.associate { it.id to it.name }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoShopColors.Background),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                family = family,
                currentUser = currentUser,
                completedCount = completedCount,
                totalCount = totalCount,
                currentList = currentList,
                shoppingLists = shoppingLists,
                onListSelected = { list -> viewModel.selectList(list.id) },
                onAddListClick = { viewModel.showAddListDialog() },
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    FamilyMembersSection(
                        familyMembers = familyMembers,
                        onAddMemberClick = { /* TODO: Phase 4 */ },
                    )
                }

                stickyHeader {
                    ShoppingListHeader(
                        selectedTab = uiState.selectedTab,
                        onTabSelected = { viewModel.selectTab(it) },
                        allCount = allCount,
                        mineCount = mineCount,
                        activeCount = activeCount,
                        doneCount = doneCount,
                    )
                }

                if (filteredItems.isEmpty()) {
                    item {
                        EmptyListState(
                            modifier = Modifier.padding(top = 64.dp),
                        )
                    }
                } else {
                    items(filteredItems, key = { it.id }) { item ->
                        ShoppingItemCard(
                            item = item,
                            assignedMemberName = memberNameById[item.assignedUser] ?: "Unknown",
                            onCheckedChange = { checked -> viewModel.toggleItemDone(item.id, checked) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { viewModel.showAddItemDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = CoShopColors.Primary,
            contentColor = CoShopColors.Surface,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = "Add item",
                colorFilter = ColorFilter.tint(CoShopColors.Surface),
            )
        }
    }

    // Dialogs
    if (uiState.showAddListDialog) {
        AddListDialog(
            onDismiss = { viewModel.hideAddListDialog() },
            onConfirm = { name, emoji ->
                viewModel.addList(name, emoji, family.id)
            },
        )
    }

    if (uiState.showAddItemDialog) {
        AddItemDialog(
            familyMembers = familyMembers,
            onDismiss = { viewModel.hideAddItemDialog() },
            onConfirm = { name, quantity, category, assignedUserId ->
                viewModel.addOrCheckDuplicateItem(name, quantity, category, assignedUserId)
            },
        )
    }

    if (uiState.showDuplicateDialog) {
        val duplicate = uiState.duplicateItem
        val pending = uiState.pendingItem

        if (duplicate != null && pending != null) {
            DuplicateItemDialog(
                existingItem = duplicate,
                existingAssigneeName = memberNameById[duplicate.assignedUser] ?: "Unknown",
                newAssigneeName = memberNameById[pending.assignedUserId] ?: "Unknown",
                onAction = { action -> viewModel.handleDuplicateAction(action) },
            )
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
