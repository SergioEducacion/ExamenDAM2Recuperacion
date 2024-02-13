package com.example.examen1viewmodel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.examen1viewmodel.data.Asignatura


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAcademia(
    modifier: Modifier = Modifier,
    loterias: ArrayList<Asignatura> = DataSource.loterias,
    onClickCambiarPantalla: () -> Unit,
    viewModelApostar: ApostarViewModel,
    uiState: ApostarUIState
) {

    Column() {
        Text(
            text = "Bienvenido a la academia de Sergio/xxxx",
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                //.weight(0.25f)
                .padding(start = 20.dp, top = 10.dp)
        )
        AsignaturasScroll(
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

        /*
        Button(
            onClick = onClickCambiarPantalla,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Cambiar de pantalla")
        }*/
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
            //.height(200.dp)
            .background(Color.LightGray)
            //.weight(0.25f)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ultima acción:\n"+uiState.textoMostrarUltimaAccion,
            modifier = modifier.background(Color.Magenta).fillMaxWidth()
        )
        Text(
            text = "Resumen:\n"+uiState.textoMostrarResumen,
            modifier = modifier.background(Color.White).fillMaxWidth()
        )
    }
}

@Composable
private fun AsignaturasScroll(
    modifier: Modifier,
    asignaturas: ArrayList<Asignatura>,
    viewModelApostar: ApostarViewModel,
    uiState: ApostarUIState,

    ) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = modifier.height(300.dp)
            .fillMaxWidth()
    ) {
        items(asignaturas) { asignatura ->
            Card(
                modifier = modifier
                    .padding(8.dp)

            ) {
                Text(
                    text = "Asig: ${asignatura.nombre}",
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(
                    text = "€/hora: ${asignatura.precioHora.toString()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .padding(20.dp)
                )
                Row(          modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                Button(
                    onClick =
                    {
                        viewModelApostar.addAsignaturaCantidad(
                            asignatura.nombre,
                        )
                    },
                    modifier = Modifier
                ) {
                    Text(text = "+")
                }

                Button(
                    onClick =
                    {
                        viewModelApostar.removeAsignaturaCantidad(
                            asignatura.nombre,
                        )
                    },
                    modifier = Modifier
                ) {
                    Text(text = "-")
                }
            }}
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldyBoton(viewModelApostar: ApostarViewModel, uiState: ApostarUIState) {

    Row() {
        TextField(
            value = viewModelApostar.cantidadAddRemoveUsuario,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onValueChange = { viewModelApostar.setCantidadAddRemoveFromUsuario(it) },
            label = { Text("Horas a contratar o a eliminar") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
}

