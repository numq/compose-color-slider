package library.slider

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BrightnessColorSlider(
    modifier: Modifier,
    thumbRadius: Float = 8f,
    thumbColor: Color = Color.White,
    color: Color,
    onColorChange: (Color) -> Unit,
) = ValueColorSlider(
    modifier = modifier,
    thumbRadius = thumbRadius,
    thumbColor = thumbColor,
    color = color,
    onColorChange = onColorChange
)
