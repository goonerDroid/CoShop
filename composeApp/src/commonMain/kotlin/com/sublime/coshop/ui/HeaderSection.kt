package com.sublime.coshop.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublime.coshop.data.models.Family
import com.sublime.coshop.data.models.FamilyMember
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_crown
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HeaderSection(
    family: Family,
    currentUser: FamilyMember,
    completedCount: Int,
    totalCount: Int
) {
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = family.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Text(
                        text = family.subtitle,
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
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
            totalCount = 5
        )
    }
}

@Preview
@Composable
fun HeaderSectionNonAdminPreview() {
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
            totalCount = 6
        )
    }
}
