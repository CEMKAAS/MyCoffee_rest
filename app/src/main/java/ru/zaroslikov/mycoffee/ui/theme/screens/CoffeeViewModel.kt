package ru.zaroslikov.mycoffee.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.zaroslikov.mycoffee.MyCoffeeApplication
import ru.zaroslikov.mycoffee.data.CoffeeRepository
import ru.zaroslikov.mycoffee.model.Coffee
import java.io.IOException

sealed interface CoffeeUiState {
    data class Success(val coffee: List<Coffee>) : CoffeeUiState
    object Error : CoffeeUiState
    object Loading : CoffeeUiState
}


class CoffeeViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {

    var coffeeUiState: CoffeeUiState by mutableStateOf(CoffeeUiState.Loading)
    private set
            init {
                getCoffees()
            }

    fun getCoffees() {
        viewModelScope.launch {
            coffeeUiState = CoffeeUiState.Loading
            coffeeUiState = try {
                val listResult = coffeeRepository.getCoffees()
                CoffeeUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                CoffeeUiState.Error
            } catch (e: HttpException) {
                CoffeeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyCoffeeApplication)
                val marsPhotosRepository = application.container.coffeeRepository
                CoffeeViewModel(coffeeRepository = marsPhotosRepository)
            }
        }
    }
}