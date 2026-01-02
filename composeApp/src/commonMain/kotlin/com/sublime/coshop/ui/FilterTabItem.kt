package com.sublime.coshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.FilterTab
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_circle
import coshop.composeapp.generated.resources.ic_list
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterTabItem(tab: FilterTab, icon: DrawableResource, count: Int, totalCount: Int? = null, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = tab.displayName,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(
                    if (isSelected) Color(0xFF1976D2) else Color(0xFF757575),
                ),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = tab.displayName,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF1976D2) else Color(0xFF757575),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) Color(0xFF1976D2).copy(alpha = 0.1f) else Color(0xFFF5F5F5),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = count.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isSelected) Color(0xFF1976D2) else Color(0xFF9E9E9E),
                    )
                    if (totalCount != null) {
                        Text(
                            text = "/$totalCount",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isSelected) Color(0xFF1976D2).copy(alpha = 0.6f) else Color(0xFFBDBDBD),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(3.dp)
                .background(
                    color = if (isSelected) Color(0xFF1976D2) else Color.Transparent,
                    shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp),
                ),
        )
    }
}

@Preview
@Composable
fun FilterTabItemSelectedPreview() {
    MaterialTheme {
        FilterTabItem(
            tab = FilterTab.ALL,
            icon = Res.drawable.ic_list,
            count = 5,
            totalCount = 8,
            isSelected = true,
            onClick = {},
        )
    }
}

@Preview
@Composable
fun FilterTabItemUnselectedPreview() {
    MaterialTheme {
        FilterTabItem(
            tab = FilterTab.ACTIVE,
            icon = Res.drawable.ic_circle,
            count = 4,
            isSelected = false,
            onClick = {},
        )
    }
}
