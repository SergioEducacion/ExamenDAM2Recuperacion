package com.example.examen1viewmodel.ui

import androidx.lifecycle.ViewModel
import com.example.examen1viewmodel.data.ApostarUIState
import com.example.examen1viewmodel.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ApostarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ApostarUIState())
    val uiState: StateFlow<ApostarUIState> = _uiState.asStateFlow()
    private val loterias = DataSource.loterias

    fun setLoteriaAApostar(escritoEnLoteriaNombre: String) {
        _uiState.update { currentState ->
            currentState.copy(
                loteriaNombre = escritoEnLoteriaNombre
            )
        }
    }

    fun setCantidadAApostar(escritoEnApostar: String) {
        _uiState.update { currentState ->
            currentState.copy(
                loteriaCantidadApostada = escritoEnApostar
            )
        }
    }





    fun intentarApuesta(loteriaNombrePasado: String, loteriaCantidadApostada: String): Unit {
        var jugadoVeces: Int = uiState.value.jugadoVeces
        var gastadoTotal: Int = uiState.value.gastadoTotal
        var ganadoTotal: Int = uiState.value.ganadoTotal
        var textoMostrar: String

        //Uso "FirstOrNull" en vez de un bucle
        val loteria = loterias.firstOrNull { it.nombre == loteriaNombrePasado }
        if (loteria == null) {
            textoMostrar = "No existe ninguna loteria con ese nombre"
            _uiState.update { currentState ->
                currentState.copy(textoMostrar = textoMostrar)
            }
            return
        }
        val cantidadApostada: Int
        try {
            cantidadApostada = loteriaCantidadApostada.toInt()
            if (cantidadApostada <= 0) {
                textoMostrar = "No se puede comprar una loteria con 0 euros"
                _uiState.update { currentState ->
                    currentState.copy(textoMostrar = textoMostrar)
                }
                return
            }
        } catch (e: Exception) {
            textoMostrar = "Cantidad apostada no es un entero"
            _uiState.update { currentState ->
                currentState.copy(textoMostrar = textoMostrar)
            }
            return
        }

        val numeroLoteriaSacado = (0..4).random()
        val numeroLoteriaComprado = (0..4).random()

        gastadoTotal += cantidadApostada
        jugadoVeces += 1
        if (numeroLoteriaSacado == numeroLoteriaComprado) {
            textoMostrar = "Has ganado la lotería"
            ganadoTotal += loteria.precioHora * cantidadApostada
        } else {
            textoMostrar = "Has perdido a la lotería"
        }

        _uiState.update { currentState ->
            currentState.copy(
                gastadoTotal = gastadoTotal,
                jugadoVeces = jugadoVeces,
                ganadoTotal = ganadoTotal,
                textoMostrar = textoMostrar,
            )
        }
    }

    fun addAsignaturaCantidad(nombre: String, loteriaCantidadApostada: String) {

    }

    fun removeAsignaturaCantidad(nombre: String, loteriaCantidadApostada: String) {

    }
}