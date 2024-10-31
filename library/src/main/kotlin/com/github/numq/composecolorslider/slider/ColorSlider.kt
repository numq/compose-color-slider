package com.github.numq.composecolorslider.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    gradientBrush: Brush,
    indicatorOffsetPercentage: Float,
    onIndicatorOffsetPercentageChange: (Float) -> Unit,
) {
    val updatedOnIndicatorOffsetPercentageChange by rememberUpdatedState(onIndicatorOffsetPercentageChange)

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val sliderSize = remember(maxWidth, thumbRadius) {
            Size(width = maxWidth.value, height = thumbRadius * 2f)
        }

        val innerRadius = remember(thumbRadius) {
            thumbRadius / 2f
        }

        val thumbThickness = remember(thumbRadius, innerRadius) {
            thumbRadius - innerRadius
        }

        val trackSize = remember(sliderSize, thumbRadius, thumbThickness) {
            Size(width = sliderSize.width - thumbThickness * 2f, height = thumbRadius)
        }

        val indicatorOffset by remember(trackSize, indicatorOffsetPercentage) {
            derivedStateOf {
                indicatorOffsetPercentage * trackSize.width
            }
        }

        val thumbPath = remember(thumbRadius, innerRadius, thumbThickness) {
            Path().apply {
                val outerCircle = Path().apply {
                    addOval(
                        Rect(
                            Offset(-thumbRadius, 0f),
                            Size(thumbRadius * 2f, thumbRadius * 2f)
                        )
                    )
                }

                val innerCircle = Path().apply {
                    addOval(
                        Rect(
                            Offset(-innerRadius, thumbThickness),
                            Size(innerRadius * 2f, innerRadius * 2f)
                        )
                    )
                }

                op(outerCircle, innerCircle, PathOperation.Difference)
            }
        }

        Canvas(modifier = Modifier.fillMaxWidth().height(thumbRadius.dp * 2f).pointerInput(Unit) {
            detectTapGestures(onTap = { (x, _) ->
                updatedOnIndicatorOffsetPercentageChange((x / size.width).coerceIn(0f, 1f))
            })
        }.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                change.consume()
                change.position.let { (x, _) ->
                    updatedOnIndicatorOffsetPercentageChange((x / size.width).coerceIn(0f, 1f))
                }
            }
        }) {
            drawLine(
                brush = gradientBrush,
                start = Offset(x = 0f, y = trackSize.height),
                end = Offset(x = trackSize.width + thumbThickness * 2f, y = trackSize.height),
                strokeWidth = trackSize.height
            )

            translate(left = indicatorOffset + thumbThickness) {
                drawPath(path = thumbPath, color = thumbColor)
            }
        }
    }
}