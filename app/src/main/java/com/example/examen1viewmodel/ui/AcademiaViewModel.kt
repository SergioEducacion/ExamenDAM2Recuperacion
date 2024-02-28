package com.example.examen1viewmodel.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examen1viewmodel.data.AcademiaUIState
import com.example.examen1viewmodel.data.AsignaturaHoras
import com.example.examen1viewmodel.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AcademiaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AcademiaUIState())
    val uiState: StateFlow<AcademiaUIState> = _uiState.asStateFlow()
    private val loterias = DataSource.asignaturas
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




    fun addAsignaturaCantidad(nombre: String) {
        var horasAdd: Int
        try {
            horasAdd = cantidadAddRemoveUsuario.toInt()
        } catch (e: Exception) {
            horasAdd = 1
        }

        val asignatura = asignaturasHoras.firstOrNull { it.asignatura.nombre == nombre }
        if (asignatura == null) {//Nunca debería llegar aquí
            return;
        }
        asignatura.totalHoras += horasAdd;
        val textoUltimaAccion = generarTextoUltimaAccion(
            "añadido",
            horasAdd.toString(),
            asignatura.asignatura.nombre,
            asignatura.asignatura.precioHora.toString()
        );
        ActualizarUiState(textoUltimaAccion)
    }

    fun removeAsignaturaCantidad(nombre: String) {

        var horasEliminadas: Int
        try {
            horasEliminadas = cantidadAddRemoveUsuario.toInt()
        } catch (e: Exception) {
            horasEliminadas = 1
        }

        val asignatura = asignaturasHoras.firstOrNull { it.asignatura.nombre == nombre }
        if (asignatura == null) {//Nunca debería llegar aquí
            return;
        }
        asignatura.totalHoras -= horasEliminadas;
        if (asignatura.totalHoras < 0) {
            asignatura.totalHoras = 0
        }
        val textoUltimaAccion = generarTextoUltimaAccion(
            "eliminado",
            horasEliminadas.toString(),
            asignatura.asignatura.nombre,
            asignatura.asignatura.precioHora.toString()
        );


        ActualizarUiState(textoUltimaAccion)

    }

    private fun ActualizarUiState(textoUltimaAccion: String) {
        val textoResumen = asignaturasHorasToString();
        val nuevoTotalHoras = totalHorasTodasLasAsignaturasContratadas().toString();
        val nuevoTotalPrecio = totalPrecioTodasLasAsignaturasContratadas().toString();

        _uiState.update { currentState ->
            currentState.copy(
                textoMostrarUltimaAccion = textoUltimaAccion,
                textoMostrarResumen = textoResumen,
                totalHoras = nuevoTotalHoras,
                totalPrecio = nuevoTotalPrecio,
            )
        }
    }

    private fun totalHorasTodasLasAsignaturasContratadas() :  Int{
        var total=0;
        for(asignatura in asignaturasHoras)
        {
            total +=asignatura.totalHoras;
        }

        //Otra forma de hacer la suma
        // val total2= asignaturasHoras.sumOf { x->x.totalHoras }

        return total
    }

    private fun totalPrecioTodasLasAsignaturasContratadas() :  Int{
        var total=0;
        for(asignatura in asignaturasHoras)
        {
            total +=asignatura.totalHoras * asignatura.asignatura.precioHora;
        }

        //Otra forma de hacer la suma y multiplicación
         //val total2= asignaturasHoras.sumOf { x->x.totalHoras * x.asignatura.precioHora }

        return total
    }

    private fun asignaturasHorasToString(): String {
        var resultado = "";
        for (ah in asignaturasHoras) {
            if (ah.totalHoras != 0) {
                resultado += "Asig: ${ah.asignatura.nombre} precio/hora: ${ah.asignatura.precioHora} total horas: ${ah.totalHoras}\n"
            }
        }
        return resultado;
    }
    private fun generarTextoUltimaAccion(
        accion: String,
        horas: String,
        nombre: String,
        precio: String
    ): String {
        return "Se han $accion $horas horas de $nombre a $precio €";
    }
}