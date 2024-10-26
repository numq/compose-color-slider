package application

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
import controls.HSVControls
import controls.RGBControls
import extension.hue
import extension.saturation
import extension.value
import slider.BrightnessColorSlider
import slider.HueColorSlider
import slider.SaturationColorSlider

@OptIn(ExperimentalStdlibApi::class)
fun main() = singleWindowApplication(title = "Color Slider") {
    val initialColor = remember { Color.Red }

    val (hue, setHue) = remember { mutableStateOf(initialColor.hue()) }

    val (saturation, setSaturation) = remember { mutableStateOf(initialColor.saturation()) }

    val (value, setValue) = remember { mutableStateOf(initialColor.value()) }

    val backgroundColor by remember(hue, saturation, value) {
        derivedStateOf {
            Color.hsv(hue = hue, saturation = saturation, value = value)
        }
    }

    val indicationColor = remember(backgroundColor) {
        if (backgroundColor.luminance() < .5f) Color.White else Color.Black
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            Text(text = backgroundColor.toArgb().toHexString(), color = indicationColor)
        }
        HueColorSlider(
            modifier = Modifier.fillMaxWidth(), hue = hue, onHueChange = setHue
        )
        SaturationColorSlider(
            modifier = Modifier.fillMaxWidth(), hue = hue, saturation = saturation, onSaturationChange = setSaturation
        )
        BrightnessColorSlider(
            modifier = Modifier.fillMaxWidth(), hue = hue, value = value, onValueChange = setValue
        )
        RGBControls(
            modifier = Modifier.fillMaxWidth(),
            tint = indicationColor,
            color = backgroundColor,
            onColorChange = { color ->
                setHue(color.hue())
                setSaturation(color.saturation())
                setValue(color.value())
            }
        )
        HSVControls(
            modifier = Modifier.fillMaxWidth(),
            tint = indicationColor,
            color = backgroundColor,
            onColorChange = { color ->
                setHue(color.hue())
                setSaturation(color.saturation())
                setValue(color.value())
            }
        )
    }
}