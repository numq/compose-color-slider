package example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import library.slider.ColorSlider

@OptIn(ExperimentalStdlibApi::class)
fun main() = singleWindowApplication(title = "Color Slider") {
    val (backgroundColor, setBackgroundColor) = remember { mutableStateOf(Color.Red) }

    Column(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            Text(
                text = backgroundColor.toArgb().toHexString(),
                color = if (backgroundColor.luminance() < .5f) Color.White else Color.Black
            )
        }
        ColorSlider(modifier = Modifier.fillMaxWidth(), color = backgroundColor, onColorChange = setBackgroundColor)
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopCenter) {
            RGBControls(
                modifier = Modifier.fillMaxWidth(),
                color = backgroundColor,
                onColorChange = setBackgroundColor
            )
        }
    }
}