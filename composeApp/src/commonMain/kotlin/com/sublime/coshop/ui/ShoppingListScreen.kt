package com.sublime.coshop.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.MockData
import com.sublime.coshop.ui.theme.CoShopColors
import com.sublime.coshop.viewmodels.ShoppingListViewModel
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_add
import coshop.composeapp.generated.resources.ic_check_circle
import kotlinx.coroutines.delay
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
    val mineTotalCount = viewModel.mineTotalCount.value
    val activeCount = viewModel.activeCount.value
    val doneCount = viewModel.doneCount.value

    val memberNameById = remember(familyMembers) {
        familyMembers.associate { it.id to it.name }
    }

    val memberColorById = remember(familyMembers) {
        familyMembers.associate { it.id to it.color }
    }

    val listState = rememberLazyListState()

    LaunchedEffect(uiState.selectedTab) {
        if (uiState.revealedItemId != null) {
            viewModel.hideReveal()
        }
    }

    LaunchedEffect(currentList.id) {
        if (uiState.revealedItemId != null) {
            viewModel.hideReveal()
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress && uiState.revealedItemId != null) {
            viewModel.hideReveal()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoShopColors.Background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                if (uiState.revealedItemId != null) {
                    viewModel.hideReveal()
                }
            },
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
                onHeaderInteraction = { viewModel.hideReveal() },
            )

            LazyColumn(
                state = listState,
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
                        allTotalCount = totalCount,
                        mineCount = mineCount,
                        mineTotalCount = mineTotalCount,
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
                    items(
                        filteredItems,
                        key = { it.id },
                    ) { item ->
                        SwipeableShoppingItemCard(
                            item = item,
                            assignedMemberName = memberNameById[item.assignedUser] ?: "Unknown",
                            assignedMemberColor = memberColorById[item.assignedUser],
                            isRevealed = uiState.revealedItemId == item.id,
                            onReveal = { viewModel.revealItem(item.id) },
                            onHideReveal = { viewModel.hideReveal() },
                            onCheckedChange = { checked ->
                                viewModel.toggleItemDone(item.id, checked)
                                if (uiState.revealedItemId != null) {
                                    viewModel.hideReveal()
                                }
                            },
                            onItemClick = { viewModel.showEditItemDialog(item) },
                            onDelete = { viewModel.showDeleteConfirmDialog(item) },
                            onModifyUser = { viewModel.handleModifyUser(item) },
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                viewModel.hideReveal()
                viewModel.showAddItemDialog()
            },
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

        AnimatedVisibility(
            visible = uiState.showUndoSnackbar && uiState.recentlyDeletedItem != null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        ) {
            uiState.recentlyDeletedItem?.let { deletedItem ->
                LaunchedEffect(Unit) {
                    delay(5000)
                    viewModel.confirmPermanentDelete()
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    shadowElevation = 8.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f),
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_check_circle),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(Color(0xFF4CAF50)),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "\"${deletedItem.name}\" removed",
                                color = Color(0xFF212121),
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        TextButton(
                            onClick = { viewModel.undoDelete() },
                        ) {
                            Text(
                                text = "Undo",
                                color = Color(0xFF2196F3),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        }
    }

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

    if (uiState.showEditItemDialog) {
        val editingItem = uiState.editingItem

        if (editingItem != null) {
            AddItemDialog(
                editingItem = editingItem,
                familyMembers = familyMembers,
                onDismiss = { viewModel.hideEditItemDialog() },
                onConfirm = { name, quantity, category, assignedUserId ->
                    viewModel.editItem(
                        editingItem.id,
                        name,
                        quantity,
                        category,
                        assignedUserId,
                    )
                },
            )
        }
    }

    if (uiState.showDeleteConfirmDialog) {
        val itemToDelete = uiState.itemToDelete

        if (itemToDelete != null) {
            DeleteConfirmDialog(
                item = itemToDelete,
                assignedMemberName = memberNameById[itemToDelete.assignedUser] ?: "Unknown",
                onConfirm = { viewModel.confirmDelete() },
                onDismiss = { viewModel.hideDeleteConfirmDialog() },
            )
        }
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

    if (uiState.showAssignItemDialog) {
        val itemToAssign = uiState.itemToAssign

        if (itemToAssign != null) {
            AssignItemDialog(
                item = itemToAssign,
                familyMembers = familyMembers,
                onAssign = { assignedUserId ->
                    viewModel.assignItemToUser(itemToAssign.id, assignedUserId)
                },
                onDismiss = { viewModel.hideAssignItemDialog() },
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
