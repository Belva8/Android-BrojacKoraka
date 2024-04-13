package com.belva.pedometar.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.belva.pedometar.ui.theme.Purple200


//stvara  okvir koji sadrži gumbe s lijevom i desnom strelicom, koji se mogu koristiti za navigaciju

@Composable
fun  CustomButtonBox(
onLeftButton : () -> Unit,
onRightButton : () -> Unit
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(100))
            .background(Purple200)
    ) {
        IconButton(
            onClick = onLeftButton,
            modifier = androidx.compose.ui.Modifier.align(Alignment.CenterStart)
        )
        {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null )
        }
        IconButton(
            onClick = onRightButton,
            modifier = androidx.compose.ui.Modifier.align(Alignment.CenterStart)
        )
        {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null )

        }


    }
}