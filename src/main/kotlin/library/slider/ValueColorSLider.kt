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
fun ValueColorSlider(
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

    val value by remember(color) {
        derivedStateOf {
            color.value()
        }
    }

    val gradientBrush = remember(hue) {
        Brush.horizontalGradient(colors = listOf(Color.hsv(hue, 1f, 0f), Color.hsv(hue, 1f, 1f)))
    }

    ColorSlider(
        modifier = modifier,
        thumbRadius = thumbRadius,
        thumbColor = thumbColor,
        gradientBrush = gradientBrush,
        indicatorOffsetPercentage = value,
        onIndicatorOffsetPercentageChange = { offsetPercentage ->
            updatedOnColorChange(
                Color.hsv(
                    color.hue(),
                    color.saturation(),
                    offsetPercentage
                )
            )
        },
    )
}