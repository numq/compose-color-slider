package library.slider

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import library.extension.hue
import library.extension.saturation
import library.extension.value

@Composable
fun HueColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    color: Color,
    onColorChange: (Color) -> Unit,
) {
    require(color.isSpecified) { "Color should be specified" }

    val updatedOnColorChange by rememberUpdatedState(onColorChange)

    val hue by remember(color) {
        derivedStateOf {
            color.hue()
        }
    }

    val gradientBrush = remember {
        Brush.horizontalGradient(colors = List(360) { angle ->
            Color.hsv(
                hue = angle.toFloat(), saturation = 1f, value = 1f
            )
        })
    }

    ColorSlider(
        modifier = modifier,
        thumbRadius = thumbRadius,
        thumbColor = thumbColor,
        gradientBrush = gradientBrush,
        indicatorOffsetPercentage = hue / 360f,
        onIndicatorOffsetPercentageChange = { offsetPercentage ->
            updatedOnColorChange(
                Color.hsv(
                    offsetPercentage * 360f,
                    color.saturation(),
                    color.value()
                )
            )
        },
    )
}