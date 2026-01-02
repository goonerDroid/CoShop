package com.sublime.coshop.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.FamilyMember
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FamilyMembersSection(familyMembers: List<FamilyMember>, onAddMemberClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(familyMembers) { member ->
                FamilyMemberAvatar(
                    member = member,
                    onAddClick = if (member.isAdmin) onAddMemberClick else null,
                )
            }
        }
    }
}

@Composable
fun FamilyMemberAvatar(member: FamilyMember, onAddClick: (() -> Unit)? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp),
    ) {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (member.isCurrentUser) {
                Surface(
                    modifier = Modifier.size(72.dp),
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE91E63),
                                Color(0xFF9C27B0),
                            ),
                        ),
                    ),
                ) {}
            }

            Surface(
                modifier = Modifier
                    .size(if (member.isCurrentUser) 62.dp else 64.dp),
                shape = CircleShape,
                color = member.color,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = member.initial,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                }
            }

            if (member.isAdmin && onAddClick != null) {
                Surface(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onAddClick() },
                        ),
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "+",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (member.isCurrentUser) "You" else member.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212121),
        )

        Text(
            text = member.lastCheckedItemName ?: "No items",
            fontSize = 12.sp,
            color = Color(0xFF9E9E9E),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun FamilyMembersCardPreview() {
    MaterialTheme {
        FamilyMembersSection(
            familyMembers = listOf(
                FamilyMember(
                    "1",
                    "J",
                    "John",
                    Color(0xFF2196F3),
                    isCurrentUser = true,
                    isAdmin = true,
                    lastCheckedItemName = "Organic Apples",
                ),
                FamilyMember(
                    "2",
                    "J",
                    "Jane",
                    Color(0xFFE53935),
                    isCurrentUser = false,
                    isAdmin = false,
                    lastCheckedItemName = "Whole Milk",
                ),
                FamilyMember(
                    "3",
                    "B",
                    "Bob",
                    Color(0xFF4CAF50),
                    isCurrentUser = false,
                    isAdmin = false,
                    lastCheckedItemName = null,
                ),
            ),
            onAddMemberClick = {},
        )
    }
}

@Preview
@Composable
fun FamilyMemberAvatarAdminPreview() {
    MaterialTheme {
        FamilyMemberAvatar(
            member = FamilyMember(
                "1",
                "J",
                "John",
                Color(0xFF2196F3),
                isCurrentUser = true,
                isAdmin = true,
                lastCheckedItemName = "Organic Apples",
            ),
            onAddClick = {},
        )
    }
}

@Preview
@Composable
fun FamilyMemberAvatarNonAdminPreview() {
    MaterialTheme {
        FamilyMemberAvatar(
            member = FamilyMember(
                "2",
                "J",
                "Jane",
                Color(0xFFE53935),
                isCurrentUser = false,
                isAdmin = false,
                lastCheckedItemName = "Organic Apples",
            ),
            onAddClick = null,
        )
    }
}
