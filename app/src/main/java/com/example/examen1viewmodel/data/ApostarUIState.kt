package com.example.examen1viewmodel.data

data class ApostarUIState(
    val loteriaNombre: String = "",
    val loteriaCantidadApostada: String = "0",
    val jugadoVeces:Int =0,
    val gastadoTotal: Int=0,
    val ganadoTotal: Int=0,
    val textoMostrar: String ="No has jugado ninguna loteria",
)
