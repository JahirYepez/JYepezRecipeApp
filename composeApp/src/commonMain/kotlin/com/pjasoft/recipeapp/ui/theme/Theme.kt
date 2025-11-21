package com.pjasoft.recipeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pjasoft.recipeapp.ui.BackgroundColorDark
import com.pjasoft.recipeapp.ui.BackgroundColorLight
import com.pjasoft.recipeapp.ui.OutlineDark
import com.pjasoft.recipeapp.ui.OutlineLight
import com.pjasoft.recipeapp.ui.PrimaryColorDark
import com.pjasoft.recipeapp.ui.PrimaryColorLight
import com.pjasoft.recipeapp.ui.SecondaryColorDark
import com.pjasoft.recipeapp.ui.SecondaryColorLight
import com.pjasoft.recipeapp.ui.SurfaceColorDark
import com.pjasoft.recipeapp.ui.SurfaceColorLight
import com.pjasoft.recipeapp.ui.TextPrimaryDark
import com.pjasoft.recipeapp.ui.TextPrimaryLight
import com.pjasoft.recipeapp.ui.TextSecondaryDark
import com.pjasoft.recipeapp.ui.TextSecondaryLight

private val LightColors = lightColorScheme(
    primary = PrimaryColorLight,
    secondary = SecondaryColorLight,
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onPrimary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFFFE8C7),
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = SecondaryColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onPrimary = Color.Black,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF2A2621),
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark
)

@Composable
fun RecipeTheme(
    content : @Composable () -> Unit
){
    val colors = if(isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme (
        colorScheme = colors,
        content = content,
        typography = andikaTypography()
      )
}
