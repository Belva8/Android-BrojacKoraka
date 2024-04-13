package com.belva.pedometar.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belva.pedometar.room_db.DailyStepsEntity
import com.belva.pedometar.ui.theme.TextDefaultColor
import java.time.LocalDate

//  DailyStepsRow koja predstavlja jedan redak u korisničkom sučelju koji prikazuje podatke o dnevnim koracima:

@Composable
fun DailyStepsRow (
item : DailyStepsEntity)
{
Row (
    modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    horizontalArrangement = Arrangement.SpaceBetween
)
    {
    Row(
        horizontalArrangement = Arrangement.Start
    ) {
Text(
    text = LocalDate.ofEpochDay(item.epochDay).toString(),
    style = TextStyle(
        color=MaterialTheme.colors.TextDefaultColor,
        fontSize = 14.sp
    )
)
    }
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = item.steps.toString(),
                style = TextStyle(
                    color=MaterialTheme.colors.TextDefaultColor,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = item.timeStamp,
                style = TextStyle(
                    color=MaterialTheme.colors.TextDefaultColor,
                    fontSize = 14.sp
                )
            )

        }

}

}



