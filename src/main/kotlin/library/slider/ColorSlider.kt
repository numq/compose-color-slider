package library.slider

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import library.extension.hue
import library.extension.saturation
import library.extension.value

@Composable
fun ColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    color: Color,
    onColorChange: (Color) -> Unit,
) {
    require(color.isSpecified) { "Color should be specified" }

    val updatedOnColorChange by rememberUpdatedState(onColorChange)

    val saturation by remember(color) {
        derivedStateOf {
            color.saturation()
        }
    }

    val value by remember(color) {
        derivedStateOf {
            color.value()
        }
    }

    var indicatorOffsetPercentage by remember { mutableStateOf(Offset.Zero) }

    val calculatedIndicatorOffsetPercentage by remember(color) {
        derivedStateOf {
            Offset(x = color.hue() / 360f, y = 0f)
        }
    }

    LaunchedEffect(calculatedIndicatorOffsetPercentage) {
        if (calculatedIndicatorOffsetPercentage != indicatorOffsetPercentage) {
            indicatorOffsetPercentage = calculatedIndicatorOffsetPercentage
        }
    }

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val sliderSize = remember(maxWidth, thumbRadius) {
            Size(width = maxWidth.value, height = thumbRadius * 2f)
        }

        val trackSize = remember(sliderSize, thumbRadius) {
            Size(width = sliderSize.width - (thumbRadius * 2f), height = thumbRadius)
        }

        val indicatorOffset by remember(trackSize, indicatorOffsetPercentage) {
            derivedStateOf {
                Offset(x = indicatorOffsetPercentage.x * trackSize.width, y = trackSize.center.y)
            }
        }

        val calculatedColor by remember(indicatorOffset, trackSize, saturation, value) {
            derivedStateOf {
                Color.hsv(
                    hue = indicatorOffset.x / trackSize.width * 360f,
                    saturation = saturation,
                    value = value
                )
            }
        }

        LaunchedEffect(calculatedColor) {
            if (calculatedColor != color) {
                updatedOnColorChange(calculatedColor)
            }
        }

        Canvas(modifier = Modifier.fillMaxWidth().height(thumbRadius.dp * 2f).pointerInput(Unit) {
            detectTapGestures(onTap = { (x, _) ->
                indicatorOffsetPercentage = Offset(x = (x / size.width).coerceIn(0f, 1f), y = 0f)
            })
        }.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                change.consume()
                change.position.let { (x, _) ->
                    indicatorOffsetPercentage = Offset(x = (x / size.width).coerceIn(0f, 1f), y = 0f)
                }
            }
        }) {
            drawLine(
                brush = Brush.horizontalGradient(colors = List(360) { angle ->
                    Color.hsv(hue = angle.toFloat(), saturation = saturation, value = value)
                }),
                start = Offset(x = thumbRadius, y = trackSize.height),
                end = Offset(x = trackSize.width + thumbRadius, y = trackSize.height),
                strokeWidth = trackSize.height
            )
            drawCircle(
                color = thumbColor,
                radius = thumbRadius,
                center = Offset(x = thumbRadius + indicatorOffset.x, y = trackSize.height)
            )
            drawCircle(
                color = color,
                radius = thumbRadius * .5f,
                center = Offset(x = thumbRadius + indicatorOffset.x, y = trackSize.height)
            )
        }
    }
}