package com.example.examen1viewmodel.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examen1viewmodel.data.ApostarUIState
import com.example.examen1viewmodel.data.AsignaturaHoras
import com.example.examen1viewmodel.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ApostarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ApostarUIState())
    val uiState: StateFlow<ApostarUIState> = _uiState.asStateFlow()
    private val loterias = DataSource.loterias
    private var asignaturasHoras = ArrayList<AsignaturaHoras>();

    fun iniciarlizarValores() {
        for (asig in loterias) {
            asignaturasHoras.add(AsignaturaHoras(asig, 0))
        }
    }


    var cantidadAddRemoveUsuario by mutableStateOf("")
        private set;


    fun setCantidadAddRemoveFromUsuario(cantidadAddRemoveUsuarioPasado: String) {
        cantidadAddRemoveUsuario = cantidadAddRemoveUsuarioPasado
    }

    private fun asignaturasHorasToString(): String {
        var resultado = "";
        for (ah in asignaturasHoras) {
            if (ah.totalHoras != 0) {
                resultado += "Asig: ${ah.asignatura.nombre} precio hora: ${ah.asignatura.precioHora} total horas: ${ah.totalHoras}\n"
            }
        }
        return resultado;
    }


    fun addAsignaturaCantidad(nombre: String) {
        var horasAdd: Int
        try {
            horasAdd = cantidadAddRemoveUsuario.toInt()
        } catch (e: Exception) {
            horasAdd = 1
        }

        val loteria = asignaturasHoras.firstOrNull { it.asignatura.nombre == nombre }
        if (loteria == null) {//Nunca debería llegar aquí
            return;
        }
        loteria.totalHoras += horasAdd;
        var textoUltimaAccion = generarTextoUltimaAccion(
            "añadido",
            horasAdd.toString(),
            loteria.asignatura.nombre,
            loteria.asignatura.precioHora.toString()
        );
        var textoResumen = asignaturasHorasToString();

        _uiState.update { currentState ->
            currentState.copy(
                textoMostrarUltimaAccion = textoUltimaAccion,
                textoMostrarResumen = textoResumen,
            )
        }
    }

    fun removeAsignaturaCantidad(nombre: String) {

        var horasEliminadas: Int
        try {
            horasEliminadas = cantidadAddRemoveUsuario.toInt()
        } catch (e: Exception) {
            horasEliminadas = 1
        }

        val loteria = asignaturasHoras.firstOrNull { it.asignatura.nombre == nombre }
        if (loteria == null) {//Nunca debería llegar aquí
            return;
        }
        loteria.totalHoras -= horasEliminadas;
        if (loteria.totalHoras < 0) {
            loteria.totalHoras = 0
        }
        var textoUltimaAccion = generarTextoUltimaAccion(
            "eliminado",
            horasEliminadas.toString(),
            loteria.asignatura.nombre,
            loteria.asignatura.precioHora.toString()
        );
        var textoResumen = asignaturasHorasToString();

        _uiState.update { currentState ->
            currentState.copy(
                textoMostrarUltimaAccion = textoUltimaAccion,
                textoMostrarResumen = textoResumen,
            )
        }

    }

    private fun generarTextoUltimaAccion(
        accion: String,
        horas: String,
        nombre: String,
        precio: String
    ): String {
        return "Se han $accion $horas horas de la $nombre Y con $precio";
    }
}