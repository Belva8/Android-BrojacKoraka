package com.belva.pedometar.presentation.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belva.pedometar.ui.theme.DarkPastel
import com.belva.pedometar.ui.theme.Dimens
import com.belva.pedometar.ui.theme.TextDefaultColor

//za tekst u apk

@Composable
fun CustomText(
    text: String,
    leadingIcon: Painter
) {
    Row(
        modifier = Modifier.padding(Dimens.One),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            painter = leadingIcon,
            tint = DarkPastel,
            contentDescription = null
        )

        Text(
            text = text,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.TextDefaultColor),
            modifier = Modifier.padding(Dimens.One)
        )
    }
}