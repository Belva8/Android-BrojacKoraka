package com.belva.pedometar.presentation.screens.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.belva.pedometar.common.GestureAssociation
import com.belva.pedometar.presentation.screens.bottom_bar.BarChartInput
import com.belva.pedometar.ui.theme.BottomBarColor
import com.belva.pedometar.ui.theme.RoyalBlue

// funkcija   generira vizualizaciju trakastog grafikona na temelju dobivenih podataka itd.

@Composable

fun BarChart(
    data: List<BarChartInput>,
    height: Dp,
    barCornersRadius: Float = 25f,
    barWidth: Float = 80f,
    labelOffset: Float = 60f,
    labelColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    var screenSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .border(width = 1.dp, color = MaterialTheme.colors.BottomBarColor, shape = shape)
            .background(color = backgroundColor, shape = shape)
            .pointerInput(Unit) {
                GestureAssociation.associationGesture(
                    this,
                    onSwipeRight = onSwipeRight,
                    onSwipeLeft = onSwipeLeft
                )

            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
            onDraw = {
                screenSize = size
                val spaceBetweenBars = (size.width - (data.size * barWidth)) / (data.size - 1)

                val stepsList = data.map { it.steps }
                val maxBarHeight = stepsList.maxOrNull() ?: 0

                val barScale = size.height / maxBarHeight
                val paint = Paint().apply {
                    color = labelColor.toArgb()
                    textAlign = Paint.Align.CENTER
                    textSize = 15.dp.toPx()
                    isFakeBoldText = true
                }

                var spaceStep = 0f

                for (item in data) {
                    val topLeft = Offset(
                        x = spaceStep,
                        y = size.height - item.steps * barScale - labelOffset
                    )

                    val barColor = when (item.steps) {
                        in (0..4999) -> Color.Yellow
                        in (5000..9999) -> Color.Green
                        else -> RoyalBlue
                    }

                    drawRoundRect(
                        color = barColor,
                        topLeft = topLeft,
                        size = Size(
                            width = barWidth,
                            height = size.height - topLeft.y - labelOffset
                        ),
                        cornerRadius = CornerRadius(barCornersRadius, barCornersRadius)
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        item.timeStamp,
                        spaceStep + barWidth/ 2,
                        size.height,
                        paint


                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        item.steps.toString(),
                        spaceStep + barWidth/ 2,
                        topLeft.y-16,
                        paint
                    )

                    spaceStep += spaceBetweenBars + barWidth



                }
            }
        )
    }
}

