package com.github.numq.composecolorslider.slider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun HueColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    hue: Float,
    onHueChange: (Float) -> Unit,
) {
    require(hue in 0f..360f) { "Hue should be within 0f..360f" }

    val updatedOnHueChange by rememberUpdatedState(onHueChange)

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
            updatedOnHueChange(offsetPercentage * 360f)
        },
    )
}