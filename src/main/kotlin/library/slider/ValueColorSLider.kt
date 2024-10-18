package library.slider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ValueColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    hue: Float,
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    require(hue in 0f..360f) { "Hue should be within 0f..360f" }

    require(value in 0f..1f) { "Saturation should be within 0f..1f" }

    val updatedOnValueChange by rememberUpdatedState(onValueChange)

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
            updatedOnValueChange(offsetPercentage)
        },
    )
}