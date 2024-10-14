package library.extension

import androidx.compose.ui.graphics.Color

internal fun Color.hue(): Float {
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)

    val delta = max - min

    return when {
        delta == 0f -> 0f

        max == red -> (60 * ((green - blue) / delta) + 360) % 360

        max == green -> (60 * ((blue - red) / delta) + 120) % 360

        else -> (60 * ((red - green) / delta) + 240) % 360
    }
}

internal fun Color.saturation(): Float {
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)

    val delta = max - min

    return if (max == 0f) 0f else delta / max
}

internal fun Color.value() = maxOf(red, green, blue)