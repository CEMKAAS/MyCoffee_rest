package ru.zaroslikov.mycoffee.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.zaroslikov.mycoffee.R
import ru.zaroslikov.mycoffee.model.Coffee
import ru.zaroslikov.mycoffee.model.CoffeeDTO
import ru.zaroslikov.mycoffee.navigation.NavigationDestination
import ru.zaroslikov.mycoffee.ui.theme.MarsTopAppBar


object StartDestination : NavigationDestination {
    override val route = "start"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navigateToProfile: () -> Unit,
    coffeeViewModel: CoffeeViewModel = viewModel(factory = CoffeeViewModel.Factory)
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MarsTopAppBar(
                scrollBehavior = scrollBehavior,
                navigateToProfile = navigateToProfile
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Добавить") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            FirstScreen(modifier = Modifier.padding(it), coffeeViewModel)

            if (showBottomSheet) {
                var title by remember { mutableStateOf("") }
                var count by remember { mutableStateOf("") }
                var isErrorTitle by rememberSaveable { mutableStateOf(false) }
                var isErrorCount by rememberSaveable { mutableStateOf(false) }

                fun validateTitle(text: String) {
                    isErrorTitle = text == ""
                }

                fun validateCount(text: String) {
                    isErrorCount = if (text == "") {
                        true
                    } else if (text.toInt() >= 10) {
                        true
                    } else false
                }

                fun errorBoolean(): Boolean {
                    isErrorTitle = title == ""
                    isErrorCount = if (count == "") {
                        true
                    } else if (count.toInt() >= 10) {
                        true
                    } else false
                    return !(isErrorTitle || isErrorCount)
                }

                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)

                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                ) {

                    Text("Сколько Вы сегодня выпили кружек кофе?", modifier = modifier)

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            validateTitle(it)
                        },
                        label = { Text("Имя") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 10.dp)
                            .padding(horizontal = 10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        supportingText = {
                            if (isErrorTitle) {
                                Text(
                                    text = "Не указано имя",
                                    color = MaterialTheme.colorScheme.error
                                )
                            } else {
                                Text("Укажите имя")
                            }
                        }
                    )
                    OutlinedTextField(
                        value = count,
                        onValueChange = {
                            count = it
                            validateCount(it)
                        },
                        label = { Text("Количество") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 10.dp)
                            .padding(horizontal = 10.dp),
                        suffix = { Text(text = "Шт.") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        supportingText = {
                            if (isErrorCount) {
                                if (count.equals("")) {
                                    Text(
                                        text = "Не указано кол-во или ко",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                } else {
                                    Text(
                                        text = "Нельзя выпить столько кофе за день",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            } else {
                                Text("Укажите кол-во")
                            }
                        },
                        isError = isErrorCount,
                    )
                    Button(
                        onClick = {
                            if (errorBoolean()) {
                                scope.launch {
                                    coffeeViewModel.postCoffees(
                                        CoffeeDTO(
                                            title,
                                            count.toInt()
                                        )
                                    )
                                }
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 20.dp)
                            .padding(horizontal = 10.dp)
                    )
                    {
                        Text("Добавить")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    coffeeViewModel: CoffeeViewModel
) {
    when (val coffeeUiState = coffeeViewModel.coffeeUiState) {
        is CoffeeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CoffeeUiState.Success -> ResultScreen(
            coffeeList = coffeeUiState.coffee, modifier = modifier
        )

        is CoffeeUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "Загрузка")
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Загрузка")
    }
}

@Composable
fun ResultScreen(
    modifier: Modifier,
    coffeeList: List<Coffee>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (coffeeList.isEmpty()) {
            Column(modifier = modifier.padding(15.dp)) {
                Text(
                    text = "Нет подключения",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontSize = 20.sp,
                )
            }
        } else {
            InventoryList(
                coffeeList = coffeeList
            )
        }

    }
}

@Composable
private fun InventoryList(
    coffeeList: List<Coffee>,
) {
    LazyColumn {
        items(coffeeList) {
            CoffeeColum(coffeeList.indexOf(it), it)
        }
    }
}

@Composable
fun CoffeeColum(
    number: Int,
    coffee: Coffee
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "${number + 1}",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Text(
                text = coffee.name,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Text(
                text = coffee.count.toString(),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun Dialogs(
    onDismissRequest: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var isErrorTitle by rememberSaveable { mutableStateOf(false) }

    fun validateTitle(text: String) {
        isErrorTitle = text == ""
    }

    fun errorBoolean(): Boolean {
        isErrorTitle = title == ""
        return !(isErrorTitle)
    }


    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
        ) {

            Column(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Укажите имя, чтобы вносить кофе",
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                    fontSize = 19.sp, fontWeight = FontWeight.SemiBold
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        validateTitle(it)
                    },
                    label = { Text("Кол-во птенцов") },
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                    suffix = { Text(text = "Шт.") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (isErrorTitle) {
                            Text(
                                text = "Не указано имя",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text("Укажите имя")
                        }
                    },
                    isError = isErrorTitle,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {

                    TextButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.padding(8.dp),

                        ) {
                        Text("Отмена")
                    }

                    TextButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Зарегистрироваться")
                    }
                }
            }
        }
    }
}


