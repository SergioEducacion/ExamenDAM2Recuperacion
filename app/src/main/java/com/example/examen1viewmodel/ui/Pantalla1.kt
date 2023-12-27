package com.example.examen1viewmodel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.examen1viewmodel.data.ApostarUIState
import com.example.examen1viewmodel.data.DataSource
import com.example.examen1viewmodel.data.LoteriaTipo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLoteria(
    modifier: Modifier = Modifier,
    loterias: ArrayList<LoteriaTipo> = DataSource.loterias,
    onClickCambiarPantalla: () -> Unit,
    viewModelApostar: ApostarViewModel,
    uiState: ApostarUIState
) {

    Column() {
        Text(
            text = "Bienvenido a apuestas ViewModel",
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                //.weight(0.25f)
                .padding(start = 20.dp, top = 50.dp)
        )
        LoteriasScroll(
            modifier,
            loterias,
            viewModelApostar,
            uiState,
        )

        TextFieldyBoton(
            viewModelApostar,
            uiState,
        )
        TextoActualizandose(modifier, uiState)

        Button(
            onClick = onClickCambiarPantalla,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Cambiar de pantalla")
        }
    }
}

@Composable
private fun TextoActualizandose(
    modifier: Modifier,
    uiState: ApostarUIState,

    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray)
            //.weight(0.25f)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.textoMostrar,
        )
        Text(
            text = "Has jugado " + uiState.jugadoVeces + " veces en loteria",
        )
        Text(
            text = "Has gastado " + uiState.gastadoTotal + " euros en loteria",
        )
        Text(
            text = "Has ganado " + uiState.ganadoTotal + " euros en loteria",
        )
    }
}

@Composable
private fun LoteriasScroll(
    modifier: Modifier,
    loterias: ArrayList<LoteriaTipo>,
    viewModelApostar: ApostarViewModel,
    uiState: ApostarUIState,

    ) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(loterias) { loteria ->
            Card(
                modifier = modifier
                    .padding(8.dp)
                    .width(250.dp)
            ) {
                Text(
                    text = "Nombre: ${loteria.nombre}",
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(
                    text = "Premio: ${loteria.premio.toString()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .padding(20.dp)
                )
                Button(
                    onClick =
                    {
                        viewModelApostar.intentarApuesta(
                            loteria.nombre,
                            uiState.loteriaCantidadApostada
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Apostar")
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldyBoton(viewModelApostar: ApostarViewModel, uiState: ApostarUIState) {

    Row() {
        TextField(
            value = uiState.loteriaNombre,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            onValueChange = { viewModelApostar.setLoteriaAApostar(it) },
            label = { Text("Loteria") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = uiState.loteriaCantidadApostada,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            onValueChange = { viewModelApostar.setCantidadAApostar(it) },
            label = { Text("Dinero apostado") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
    Button(
        onClick =
        {
            viewModelApostar.intentarApuesta(uiState.loteriaNombre, uiState.loteriaCantidadApostada)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Jugar loteria escrita")
        //Text(text = ejemplo.value)
    }
}

