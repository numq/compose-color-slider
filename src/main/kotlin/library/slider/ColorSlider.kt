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

    val indicatorOffsetPercentage by remember(color) {
        derivedStateOf {
            Offset(x = color.hue() / 360f, y = 0f)
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

        Canvas(modifier = Modifier.fillMaxWidth().height(thumbRadius.dp * 2f).pointerInput(Unit) {
            detectTapGestures(onTap = { (x, _) ->
                updatedOnColorChange(
                    Color.hsv(
                        hue = (x / size.width).coerceIn(0f, 1f) * 360f,
                        saturation = 1f,
                        value = 1f
                    )
                )
            })
        }.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                change.consume()
                change.position.let { (x, _) ->
                    updatedOnColorChange(
                        Color.hsv(
                            hue = (x / size.width).coerceIn(0f, 1f) * 360f,
                            saturation = 1f,
                            value = 1f
                        )
                    )
                }
            }
        }) {
            drawLine(
                brush = Brush.horizontalGradient(colors = List(360) { angle ->
                    Color.hsv(hue = angle.toFloat(), saturation = 1f, value = 1f)
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