package ru.zaroslikov.mycoffee.ui.theme.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.zaroslikov.mycoffee.model.Coffee


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(
    coffeeUiState: CoffeeUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (coffeeUiState) {
        is CoffeeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CoffeeUiState.Success -> ResultScreen(
            coffeeList = coffeeUiState.coffee, modifier = modifier.fillMaxWidth()
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
        Text(text = "ошибка")
    }
}

@Composable
fun ResultScreen(
    modifier: Modifier,
    coffeeList: List<Coffee>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
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
                coffeeList = coffeeList,
                modifier = modifier
            )
        }

    }
}

@Composable
private fun InventoryList(
    coffeeList: List<Coffee>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(coffeeList) {
            CoffeeColum(it, modifier = modifier)
        }
    }
}

@Composable
fun CoffeeColum(
    coffee: Coffee,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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