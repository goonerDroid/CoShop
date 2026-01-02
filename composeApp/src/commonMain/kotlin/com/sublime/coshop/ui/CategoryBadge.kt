package com.sublime.coshop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.sublime.coshop.data.models.ItemCategory
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryBadge(category: ItemCategory, modifier: Modifier = Modifier) {
    var showLabel by remember { mutableStateOf(false) }

    // Auto-hide label after 2 seconds
    LaunchedEffect(showLabel) {
        if (showLabel) {
            delay(2000)
            showLabel = false
        }
    }

    Box(
        modifier = modifier
            .size(28.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { showLabel = !showLabel },
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Badge icon (fixed size)
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = category.color.copy(alpha = 0.12f),
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = category.icon,
                fontSize = 16.sp,
            )
        }

        // Show label as a Popup that floats outside the layout
        if (showLabel) {
            Popup(
                alignment = Alignment.CenterStart,
                offset = IntOffset(x = 36, y = 0),
            ) {
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                ) {
                    Text(
                        text = category.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF424242),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryBadgeProducePreview() {
    MaterialTheme {
        CategoryBadge(category = ItemCategory.PRODUCE)
    }
}

@Preview
@Composable
fun CategoryBadgeDairyPreview() {
    MaterialTheme {
        CategoryBadge(category = ItemCategory.DAIRY)
    }
}

@Preview
@Composable
fun CategoryBadgeSeafoodPreview() {
    MaterialTheme {
        CategoryBadge(category = ItemCategory.SEAFOOD)
    }
}
