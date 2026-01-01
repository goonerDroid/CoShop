package com.sublime.coshop.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.ItemCategory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryBadge(category: ItemCategory) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = category.color.copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(1.dp, category.color.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.icon,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = category.displayName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = category.color
            )
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