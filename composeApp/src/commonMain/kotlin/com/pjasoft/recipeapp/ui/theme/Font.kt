package com.pjasoft.recipeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import recipeapp512.composeapp.generated.resources.Res
import recipeapp512.composeapp.generated.resources.*

@Composable
fun andikaFontFamily() = FontFamily(
    Font(Res.font.andika_regular,    weight = FontWeight.Normal),
    Font(Res.font.andika_bold,       weight = FontWeight.Bold),
    Font(Res.font.andika_italic,     weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(Res.font.andika_bolditalic, weight = FontWeight.Bold,   style = FontStyle.Italic),
)

@Composable
fun andikaTypography() = Typography().run {
    val fontFamily = andikaFontFamily()
    copy(
        displayLarge   = displayLarge.copy(fontFamily = fontFamily),
        displayMedium  = displayMedium.copy(fontFamily = fontFamily),
        displaySmall   = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge  = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall  = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge     = titleLarge.copy(fontFamily = fontFamily),
        titleMedium    = titleMedium.copy(fontFamily = fontFamily),
        titleSmall     = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge      = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium     = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall      = bodySmall.copy(fontFamily = fontFamily),
        labelLarge     = labelLarge.copy(fontFamily = fontFamily),
        labelMedium    = labelMedium.copy(fontFamily = fontFamily),
        labelSmall     = labelSmall.copy(fontFamily = fontFamily),
    )
}