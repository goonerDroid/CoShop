package com.sublime.coshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.Family
import com.sublime.coshop.data.models.FamilyMember
import com.sublime.coshop.data.models.ShoppingList
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_chevron_down
import coshop.composeapp.generated.resources.ic_crown
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HeaderSection(
    family: Family,
    currentUser: FamilyMember,
    completedCount: Int,
    totalCount: Int,
    currentList: ShoppingList? = null,
    shoppingLists: List<ShoppingList> = emptyList(),
    onListSelected: (ShoppingList) -> Unit = {},
    onAddListClick: () -> Unit = {}
) {
    var showListPicker by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFE3F2FD),
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = currentUser.color
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = currentUser.initial,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    if (currentUser.isAdmin) {
                        Surface(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd),
                            shape = CircleShape,
                            color = Color(0xFFFFC107)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(Res.drawable.ic_crown),
                                    contentDescription = "Admin",
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(
                            text = family.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                        Row(
                            modifier = Modifier
                                .clickable(enabled = shoppingLists.isNotEmpty()) {
                                    showListPicker = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currentList?.let { "${it.emoji} ${it.name}" } ?: family.subtitle,
                                fontSize = 14.sp,
                                color = Color(0xFF757575)
                            )
                            if (shoppingLists.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.ic_chevron_down),
                                    contentDescription = "Select list",
                                    modifier = Modifier.size(16.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFF757575))
                                )
                            }
                        }
                    }

                    if (currentList != null) {
                        ListPickerDropdown(
                            shoppingLists = shoppingLists,
                            selectedList = currentList,
                            onListSelected = { list ->
                                onListSelected(list)
                                showListPicker = false
                            },
                            onAddListClick = {
                                onAddListClick()
                                showListPicker = false
                            },
                            isAdmin = currentUser.isAdmin,
                            expanded = showListPicker,
                            onDismissRequest = { showListPicker = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Text(
                    text = "$completedCount of $totalCount items",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { if (totalCount > 0) completedCount.toFloat() / totalCount else 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF1976D2),
                trackColor = Color(0xFFBBDEFB)
            )
        }
    }
}

@Preview
@Composable
fun HeaderSectionPreview() {
    val shoppingLists = listOf(
        ShoppingList("1", "Weekly Essentials", "🛒", "family_1", true),
        ShoppingList("2", "Costco", "📦", "family_1"),
        ShoppingList("3", "Indian Groceries", "🥘", "family_1")
    )
    MaterialTheme {
        HeaderSection(
            family = Family(
                id = "1",
                name = "Family Grocery",
                subtitle = "Shopping together"
            ),
            currentUser = FamilyMember(
                id = "user_1",
                initial = "J",
                name = "John",
                color = Color(0xFF2196F3),
                isCurrentUser = true,
                isAdmin = true,
                lastCheckedItemName = "Organic Apples"
            ),
            completedCount = 2,
            totalCount = 5,
            currentList = shoppingLists.first(),
            shoppingLists = shoppingLists,
            onListSelected = {},
            onAddListClick = {}
        )
    }
}

@Preview
@Composable
fun HeaderSectionNonAdminPreview() {
    val shoppingLists = listOf(
        ShoppingList("1", "Weekly Essentials", "🛒", "family_1", true),
        ShoppingList("2", "Costco", "📦", "family_1")
    )
    MaterialTheme {
        HeaderSection(
            family = Family(
                id = "1",
                name = "Smith Family",
                subtitle = "Shopping together"
            ),
            currentUser = FamilyMember(
                id = "user_2",
                initial = "S",
                name = "Sarah",
                color = Color(0xFFE91E63),
                isCurrentUser = true,
                isAdmin = false,
                lastCheckedItemName = "Whole Milk"
            ),
            completedCount = 4,
            totalCount = 6,
            currentList = shoppingLists.first(),
            shoppingLists = shoppingLists,
            onListSelected = {},
            onAddListClick = {}
        )
    }
}
