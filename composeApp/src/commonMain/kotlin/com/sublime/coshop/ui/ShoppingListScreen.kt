package com.sublime.coshop.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.MockData
import com.sublime.coshop.data.models.FilterTab
import com.sublime.coshop.data.models.ShoppingList
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen() {
    val family = MockData.family
    val familyMembers = MockData.familyMembers
    var shoppingLists by remember { mutableStateOf(MockData.shoppingLists) }
    val currentUserId = MockData.currentUserId

    var selectedListId by remember {
        mutableStateOf(shoppingLists.first { it.isDefault }.id)
    }

    val currentList = remember(selectedListId, shoppingLists) {
        shoppingLists.first { it.id == selectedListId }
    }

    var showAddListDialog by remember { mutableStateOf(false) }

    var items by remember { mutableStateOf(MockData.shoppingItems) }

    val memberNameById = remember(familyMembers) {
        familyMembers.associate { it.id to it.name }
    }

    var selectedTab by remember { mutableStateOf(FilterTab.ALL) }

    val currentUser = remember(familyMembers) { familyMembers.first { it.isCurrentUser } }

    val itemsForCurrentList by remember {
        derivedStateOf { items.filter { it.listId == selectedListId } }
    }

    val completedCount by remember { derivedStateOf { itemsForCurrentList.count { it.isDone } } }
    val totalCount by remember { derivedStateOf { itemsForCurrentList.size } }

    val allCount by remember { derivedStateOf { itemsForCurrentList.size } }
    val mineCount by remember { derivedStateOf { itemsForCurrentList.count { it.assignedUser == currentUserId } } }
    val activeCount by remember { derivedStateOf { itemsForCurrentList.count { !it.isDone } } }
    val doneCount by remember { derivedStateOf { itemsForCurrentList.count { it.isDone } } }

    val filteredItems = when (selectedTab) {
        FilterTab.ALL -> itemsForCurrentList
        FilterTab.MINE -> itemsForCurrentList.filter { it.assignedUser == currentUserId }
        FilterTab.ACTIVE -> itemsForCurrentList.filter { !it.isDone }
        FilterTab.DONE -> itemsForCurrentList.filter { it.isDone }
    }

    val onItemCheckedChange = remember<(String, Boolean) -> Unit> {
        { itemId, checked ->
            items = items.map {
                if (it.id == itemId) it.copy(isDone = checked) else it
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                family = family,
                currentUser = currentUser,
                completedCount = completedCount,
                totalCount = totalCount,
                currentList = currentList,
                shoppingLists = shoppingLists,
                onListSelected = { list -> selectedListId = list.id },
                onAddListClick = { showAddListDialog = true }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    FamilyMembersSection(
                        familyMembers = familyMembers,
                        onAddMemberClick = { /* TODO */ }
                    )
                }

                stickyHeader {
                    ShoppingListHeader(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        allCount = allCount,
                        mineCount = mineCount,
                        activeCount = activeCount,
                        doneCount = doneCount
                    )
                }

                if (filteredItems.isEmpty()) {
                    item {
                        EmptyListState(
                            modifier = Modifier.padding(top = 64.dp)
                        )
                    }
                } else {
                    items(filteredItems, key = { it.id }) { item ->
                        ShoppingItemCard(
                            item = item,
                            assignedMemberName = memberNameById[item.assignedUser] ?: "Unknown",
                            onCheckedChange = { checked -> onItemCheckedChange(item.id, checked) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { /* TODO: Add item */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color(0xFF1976D2),
            contentColor = Color.White
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = "Add item",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }

    if (showAddListDialog) {
        AddListDialog(
            onDismiss = { showAddListDialog = false },
            onConfirm = { name, emoji ->
                val newList = ShoppingList(
                    id = "list_${System.currentTimeMillis()}",
                    name = name,
                    emoji = emoji,
                    familyId = family.id,
                    isDefault = false
                )
                shoppingLists = shoppingLists + newList
                selectedListId = newList.id
                showAddListDialog = false
            }
        )
    }
}


@Preview
@Composable
fun ShoppingListScreenPreview() {
    MaterialTheme {
        ShoppingListScreen()
    }
}