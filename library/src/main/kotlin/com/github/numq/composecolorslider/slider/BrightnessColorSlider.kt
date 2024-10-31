package com.github.numq.composecolorslider.slider

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BrightnessColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    hue: Float,
    value: Float,
    onValueChange: (Float) -> Unit,
) = ValueColorSlider(
    modifier = modifier,
    thumbRadius = thumbRadius,
    thumbColor = thumbColor,
    hue = hue,
    value = value,
    onValueChange = onValueChange
)
