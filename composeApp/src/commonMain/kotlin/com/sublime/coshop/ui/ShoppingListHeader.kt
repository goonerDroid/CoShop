package com.sublime.coshop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.models.FilterTab
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_check_circle
import coshop.composeapp.generated.resources.ic_circle
import coshop.composeapp.generated.resources.ic_list
import coshop.composeapp.generated.resources.ic_person
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingListHeader(
    selectedTab: FilterTab,
    onTabSelected: (FilterTab) -> Unit,
    allCount: Int,
    allTotalCount: Int,
    mineCount: Int,
    mineTotalCount: Int,
    activeCount: Int,
    doneCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterTabItem(
            tab = FilterTab.ALL,
            icon = Res.drawable.ic_list,
            count = allCount,
            totalCount = allTotalCount,
            isSelected = selectedTab == FilterTab.ALL,
            onClick = { onTabSelected(FilterTab.ALL) },
        )
        FilterTabItem(
            tab = FilterTab.MINE,
            icon = Res.drawable.ic_person,
            count = mineCount,
            totalCount = mineTotalCount,
            isSelected = selectedTab == FilterTab.MINE,
            onClick = { onTabSelected(FilterTab.MINE) },
        )
        FilterTabItem(
            tab = FilterTab.ACTIVE,
            icon = Res.drawable.ic_circle,
            count = activeCount,
            isSelected = selectedTab == FilterTab.ACTIVE,
            onClick = { onTabSelected(FilterTab.ACTIVE) },
        )
        FilterTabItem(
            tab = FilterTab.DONE,
            icon = Res.drawable.ic_check_circle,
            count = doneCount,
            isSelected = selectedTab == FilterTab.DONE,
            onClick = { onTabSelected(FilterTab.DONE) },
        )
    }
}

@Preview
@Composable
fun ShoppingListHeaderPreview() {
    MaterialTheme {
        ShoppingListHeader(
            selectedTab = FilterTab.ALL,
            onTabSelected = {},
            allCount = 5,
            allTotalCount = 8,
            mineCount = 3,
            mineTotalCount = 5,
            activeCount = 5,
            doneCount = 3,
        )
    }
}

@Preview
@Composable
fun ShoppingListHeaderMineSelectedPreview() {
    MaterialTheme {
        ShoppingListHeader(
            selectedTab = FilterTab.MINE,
            onTabSelected = {},
            allCount = 5,
            allTotalCount = 8,
            mineCount = 3,
            mineTotalCount = 5,
            activeCount = 5,
            doneCount = 3,
        )
    }
}
