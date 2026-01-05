package com.sublime.coshop.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sublime.coshop.data.models.ShoppingItem
import coshop.composeapp.generated.resources.Res
import coshop.composeapp.generated.resources.ic_delete
import coshop.composeapp.generated.resources.ic_edit
import coshop.composeapp.generated.resources.ic_group
import org.jetbrains.compose.resources.painterResource
import kotlin.math.abs
import kotlin.math.roundToInt

private val RevealThreshold = 150.dp
private val ActionButtonsWidth = 250.dp
private val ActionButtonWidth = 83.dp
private const val SwipeAnimationDuration = 300
private const val CloseRevealThreshold = 50f
private const val SwipeDetectionThreshold = 10f

@Composable
fun SwipeableShoppingItemCard(
    item: ShoppingItem,
    assignedMemberName: String,
    assignedMemberColor: Color? = null,
    isRevealed: Boolean,
    onReveal: () -> Unit,
    onHideReveal: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    onDelete: () -> Unit,
    onModifyUser: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(isRevealed) {
        if (!isDragging && !isRevealed) {
            dragOffset = 0f
        }
    }

    val density = LocalDensity.current
    val revealThresholdPx = with(density) { RevealThreshold.toPx() }
    val actionButtonWidthPx = with(density) { ActionButtonsWidth.toPx() }

    val targetOffset = when {
        isRevealed && !isDragging -> -actionButtonWidthPx
        isDragging -> dragOffset
        else -> 0f
    }

    val animatedOffset by animateFloatAsState(
        targetValue = targetOffset,
        animationSpec = tween(
            durationMillis = if (isDragging) 0 else SwipeAnimationDuration,
            easing = FastOutSlowInEasing,
        ),
        label = "swipeOffset",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .pointerInput(item.id) {
                detectTapGestures(
                    onTap = {
                        if (isRevealed) {
                            val xPos = it.x
                            val cardWidth = size.width.toFloat()
                            val buttonAreaStart = cardWidth + animatedOffset

                            if (xPos < buttonAreaStart) {
                                onHideReveal()
                            }
                        }
                    },
                )
            }
            .pointerInput(item.id) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        if (dragOffset < -revealThresholdPx) {
                            onReveal()
                            dragOffset = -actionButtonWidthPx
                        } else {
                            dragOffset = 0f
                        }
                        isDragging = false
                    },
                    onDragCancel = {
                        dragOffset = 0f
                        isDragging = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        val newOffset = dragOffset + dragAmount

                        if (isRevealed && dragAmount > 0) {
                            dragOffset = newOffset.coerceAtMost(0f)
                            if (newOffset >= -CloseRevealThreshold) {
                                onHideReveal()
                            }
                        } else {
                            dragOffset = if (newOffset < 0) newOffset else 0f
                        }
                    },
                )
            },
    ) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isRevealed || abs(animatedOffset) > SwipeDetectionThreshold) {
                Box(
                    modifier = Modifier
                        .width(ActionButtonWidth)
                        .fillMaxHeight()
                        .background(Color(0xFFEF5350))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onDelete() },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }

                Box(
                    modifier = Modifier
                        .width(ActionButtonWidth)
                        .fillMaxHeight()
                        .background(Color(0xFFAB47BC))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onModifyUser() },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_group),
                        contentDescription = "Modify User",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }

                Box(
                    modifier = Modifier
                        .width(ActionButtonWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 12.dp, bottomEnd = 12.dp))
                        .background(Color(0xFF42A5F5))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onItemClick() },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }
            }
        }

        ShoppingItemCard(
            item = item,
            assignedMemberName = assignedMemberName,
            assignedMemberColor = assignedMemberColor,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.offset { IntOffset(animatedOffset.roundToInt(), 0) },
        )
    }
}
