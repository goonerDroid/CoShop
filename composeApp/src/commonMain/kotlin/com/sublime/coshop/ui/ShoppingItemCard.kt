package com.sublime.coshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.ItemCategory
import com.sublime.coshop.data.models.ShoppingItem
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_drag_indicator
import coshop.composeapp.generated.resources.ic_group
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingItemCard(item: ShoppingItem, assignedMemberName: String, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val checkboxColors = CheckboxDefaults.colors(
        checkedColor = Color(0xFF1976D2),
        uncheckedColor = Color(0xFFBDBDBD),
        checkmarkColor = Color.White,
    )
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_drag_indicator),
                contentDescription = "Drag to reorder",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFFBDBDBD)),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Checkbox(
                checked = item.isDone,
                onCheckedChange = onCheckedChange,
                colors = checkboxColors,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (item.isDone) Color(0xFF9E9E9E) else Color(0xFF212121),
                        textDecoration = if (item.isDone) TextDecoration.LineThrough else TextDecoration.None,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CategoryBadge(category = item.category)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.quantity,
                        fontSize = 14.sp,
                        color = Color(0xFF757575),
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Image(
                        painter = painterResource(Res.drawable.ic_group),
                        contentDescription = "Assigned to",
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(Color(0xFF9E9E9E)),
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = assignedMemberName,
                        fontSize = 14.sp,
                        color = Color(0xFF757575),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShoppingItemCardActivePreview() {
    MaterialTheme {
        ShoppingItemCard(
            item = ShoppingItem(
                id = "1",
                name = "Organic Apples",
                quantity = "2 lbs",
                category = ItemCategory.PRODUCE,
                assignedUser = "user_1",
                isDone = false,
            ),
            assignedMemberName = "John",
            onCheckedChange = {},
        )
    }
}

@Preview
@Composable
fun ShoppingItemCardDonePreview() {
    MaterialTheme {
        ShoppingItemCard(
            item = ShoppingItem(
                id = "2",
                name = "Sourdough Bread",
                quantity = "1 loaf",
                category = ItemCategory.BAKERY,
                assignedUser = "user_1",
                isDone = true,
            ),
            assignedMemberName = "John",
            onCheckedChange = {},
        )
    }
}

@Preview
@Composable
fun ShoppingItemCardLongNamePreview() {
    MaterialTheme {
        ShoppingItemCard(
            item = ShoppingItem(
                id = "3",
                name = "Organic Free Range Chicken Breast Boneless",
                quantity = "2 lbs",
                category = ItemCategory.MEAT,
                assignedUser = "user_2",
                isDone = false,
            ),
            assignedMemberName = "Jane",
            onCheckedChange = {},
        )
    }
}
