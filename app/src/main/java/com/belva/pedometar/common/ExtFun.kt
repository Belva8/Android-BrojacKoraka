package com.belva.pedometar.common

import android.content.Context
import android.widget.Toast


//Extension funkcja  za prikazivanje toast poruka.
// da nemoram uvijek pisat toast nego pozoves funk
fun Context.showToast(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
) {

    Toast.makeText(this,msg,duration).show()
}