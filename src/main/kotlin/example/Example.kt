package example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import library.extension.hue
import library.extension.saturation
import library.extension.value
import library.slider.ColorSlider

@OptIn(ExperimentalStdlibApi::class)
fun main() = singleWindowApplication(title = "Color Slider") {
    val (backgroundColor, setBackgroundColor) = remember { mutableStateOf(Color.Red) }

    val backgroundColorHue by remember(backgroundColor) {
        derivedStateOf {
            backgroundColor.hue()
        }
    }

    val backgroundColorSaturation by remember(backgroundColor) {
        derivedStateOf {
            backgroundColor.saturation()
        }
    }

    val backgroundColorValue by remember(backgroundColor) {
        derivedStateOf {
            backgroundColor.value()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = backgroundColor.toArgb().toHexString(),
                color = if (backgroundColor.luminance() < .5f) Color.White else Color.Black
            )
        }
        ColorSlider(
            modifier = Modifier.fillMaxWidth(), color = backgroundColor, onColorChange = setBackgroundColor
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("RGB")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Red",
                    value = backgroundColor.red,
                    onValueChange = { red ->
                        setBackgroundColor(backgroundColor.copy(red = red))
                    }
                )
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Green",
                    value = backgroundColor.green,
                    onValueChange = { green ->
                        setBackgroundColor(backgroundColor.copy(green = green))
                    }
                )
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Blue",
                    value = backgroundColor.blue,
                    onValueChange = { blue ->
                        setBackgroundColor(backgroundColor.copy(blue = blue))
                    }
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("HSV")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Hue",
                    value = backgroundColorHue,
                    valueRange = 0f..360f,
                    onValueChange = { hue ->
                        setBackgroundColor(
                            Color.hsv(
                                hue = hue,
                                saturation = backgroundColorSaturation,
                                value = backgroundColorValue
                            )
                        )
                    }
                )
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Saturation",
                    value = backgroundColorSaturation,
                    onValueChange = { saturation ->
                        setBackgroundColor(
                            Color.hsv(
                                hue = backgroundColorHue,
                                saturation = saturation,
                                value = backgroundColorValue
                            )
                        )
                    }
                )
                LabeledSlider(
                    modifier = Modifier.weight(1f, fill = false),
                    label = "Value",
                    value = backgroundColorValue,
                    onValueChange = { value ->
                        setBackgroundColor(
                            Color.hsv(
                                hue = backgroundColorHue,
                                saturation = backgroundColorSaturation,
                                value = value
                            )
                        )
                    }
                )
            }
        }
    }
}