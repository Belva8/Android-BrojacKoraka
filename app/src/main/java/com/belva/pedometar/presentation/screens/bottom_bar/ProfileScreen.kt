 package com.belva.pedometar.presentation.screens.bottom_bar

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.belva.pedometar.R
import com.belva.pedometar.ui.theme.TextDefaultColor

 // ekran za uređivanje profila , omogućuje uređujivanje inf o profilu i mjenjanje slike profila.

@Composable
@Preview
fun ProfileScreen() {
    var notification by rememberSaveable { mutableStateOf("") }

    if (notification.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification, Toast.LENGTH_LONG).show()
        notification = ""
    }

    var name by rememberSaveable { mutableStateOf("Ime") }
    var username by rememberSaveable { mutableStateOf("Korisničko ime") }
    var bio by rememberSaveable { mutableStateOf("Opis") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier

                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Odbij",
                modifier = Modifier.clickable { notification = "Odbijeno" },
                color = Color.Black
            )
            Text(
                text = "Spremi",
                modifier = Modifier.clickable { notification = "Profil je ažuriran" },
                color = Color.Black
            )
        }

        ProfileImage()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Ime", color = MaterialTheme.colors.primary) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black
                )
            )
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Korisničko ime", color = MaterialTheme.colors.TextDefaultColor) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.TextDefaultColor
                )
            )
            TextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio", color = MaterialTheme.colors.TextDefaultColor) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.TextDefaultColor
                ),
                singleLine = false,
                modifier = Modifier
                    .height(150.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri: Uri? -> uri?.let { imageUri.value = it.toString() }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Promjeni sliku profila",
            color = MaterialTheme.colors.TextDefaultColor
        )
    }
}