package com.pjasoft.recipeapp.ui.Screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pjasoft.recipeapp.domain.dtos.Prompt
import com.pjasoft.recipeapp.domain.dtos.RecipeDTO
import com.pjasoft.recipeapp.domain.utils.Preferences
import com.pjasoft.recipeapp.domain.utils.hideKeyboard
import com.pjasoft.recipeapp.ui.Components.CustomOutlinedTextField
import com.pjasoft.recipeapp.ui.Components.LoadingOverlay
import com.pjasoft.recipeapp.ui.theme.RecipeTheme
import com.pjasoft.recipeapp.ui.Screens.HomeScreen.Components.GeneratedRecipe
import com.pjasoft.recipeapp.ui.Screens.HomeScreen.Components.Header
import com.pjasoft.recipeapp.ui.Screens.HomeScreen.Components.RecipeCard
import com.pjasoft.recipeapp.ui.Screens.HomeScreen.Components.RecipeItems
import com.pjasoft.recipeapp.ui.Screens.HomeScreenRoute
import com.pjasoft.recipeapp.ui.Screens.LoginScreenRoute
import com.pjasoft.recipeapp.ui.viewModels.RecipeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val colors = MaterialTheme.colorScheme
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    //todo change this to view model

    val scope = rememberCoroutineScope()
    var prompt by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val viewModel : RecipeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return RecipeViewModel() as T
            }
        }
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeContentPadding()//paddin para dinamic island y notch del iphone
            .padding(5.dp)
    ){
        //header
        item {
            Header(
                onLoguot = {
                Preferences.clearSettings()
                navController.navigate(LoginScreenRoute){
                    popUpTo(HomeScreenRoute){
                        inclusive = true
                    }
                }
            })
        }
        //fin del header

        item {
            Text(
                text = "Crea, Cocina, Comparte y disfruta",
                style = MaterialTheme
                    .typography
                    .headlineMedium
                    .copy(fontWeight = FontWeight.ExtraBold)
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = prompt,
                onValueChange = { prompt = it},
                trailingIcon = Icons.Default.AutoAwesome,
                placeHolder = "Escribe tus ingredientes...",
                onTrailingIconClick = {
                    hideKeyboard(focusManager)
                    viewModel.generateRecipe(
                        prompt = Prompt(
                            ingredients = prompt
                        )
                    )
                    scope.launch {
                        sheetState.partialExpand()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        hideKeyboard(focusManager)
                        viewModel.generateRecipe(
                            prompt = Prompt(
                                ingredients =  prompt
                            )
                        )
                        scope.launch {
                            sheetState.partialExpand()
                        }
                    }
                )
            )
        }

        item {
            Text(
                text = "Tus recetas recientes",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.recipes){ recipe ->
                    RecipeCard(recipe){
                        scope.launch {
                            val recipeDTO = RecipeDTO(
                                category = recipe.category,
                                ingredients = recipe.ingredients,
                                instructions = recipe.instructions,
                                minutes = recipe.minutes,
                                prompt = "",
                                stars = recipe.stars,
                                title = recipe.title,
                                imageUrl = recipe.imageUrl ?: ""
                            )
                            viewModel.showModalFromList(
                                recipe = recipeDTO
                            )
                            sheetState.partialExpand()
                        }
                    }
                }
            }
        }

        //QUICK IDEAS
        item {
            val tags = listOf(
                "Rapidas (10 min)",
                "Pocas calorias",
                "Sin horno",
                "Desayunos"
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Ideas Rapidas",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(tags){ tag ->
                    Text(
                        text = tag,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.primary.copy(alpha = 0.1f))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable{

                            },
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(alpha = 0.1f))
                    .padding(20.dp)
                    .clickable{
                        //generar receta aleatoria
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "No sabes que cocinar hoy?",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Genera una receta aleatoria"
                    )
                }
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Todas tus recetas",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        //todas las recetas
        items(viewModel.recipes) { recipe ->
            RecipeItems(recipe){
                scope.launch {
                    val recipeDTO = RecipeDTO(
                        category = recipe.category,
                        ingredients = recipe.ingredients,
                        instructions = recipe.instructions,
                        minutes = recipe.minutes,
                        prompt = "",
                        stars = recipe.stars,
                        title = recipe.title,
                        imageUrl = recipe.imageUrl ?: ""
                    )
                    viewModel.showModalFromList(
                        recipe = recipeDTO
                    )
                    sheetState.partialExpand()
                }
            }
        }
    }
    //modal
    if (viewModel.showSheet){
        ModalBottomSheet(
            onDismissRequest = {viewModel.hideModal()},
            dragHandle = { BottomSheetDefaults.DragHandle()},
            containerColor = MaterialTheme.colorScheme.surface,
            sheetState = sheetState
        )
        {
            var textButton = "Cerrar"
            var onclick: () -> Unit = {
                scope.launch {
                    viewModel.hideModal()
                    sheetState.hide()
                }
            }
            if (viewModel.isGenerated){
                textButton = "Guardar"
                onclick = {
                    scope.launch {
                        viewModel.hideModal()
                        sheetState.hide()
                    }
                    viewModel.saveRecipeInDb()
                }
            }

            GeneratedRecipe(
                recipe = viewModel.generatedRecipe,
                textButton = textButton,
                onClick = onclick
            )
        }
    }

    if (viewModel.isLoading){
        LoadingOverlay(colors)
    }

}



@Preview
@Composable
fun HomeScreenView(){
    RecipeTheme {
        HomeScreen(navController = rememberNavController())
    }
}