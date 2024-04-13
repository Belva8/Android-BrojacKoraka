package com.belva.pedometar.common

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.PointerInputScope
import kotlin.math.abs

enum class SwipeDirections {
    DOWN,
    LEFT,
    RIGHT,
    UP,
    UNKNOWN

}

//Unutar detectDragGestures, određuje smjer povlačenja na temelju promjene koordinata (dragAmount) i update-a swipeDirections
object GestureAssociation {
    suspend fun associationGesture(
pointerInputScope: PointerInputScope, //Predstavlja opseg za događaje unosa pokazivača.
onSwipeLeft: () ->Unit = {},
onSwipeRight: () ->Unit = {},
onSwipeDown: () ->Unit = {},
onSwipeUp: () ->Unit = {}
    ) {

var swipeDirections  = SwipeDirections.UNKNOWN

        pointerInputScope.apply {
            detectDragGestures(
                onDrag = {
                    change, dragAmount ->  change.consume()
                    val (x,y) = dragAmount

                    swipeDirections = if(abs(x) > abs(y)) {
                        when
                        {
                            x  >  0 -> SwipeDirections.RIGHT
                            x  <  0 -> SwipeDirections.LEFT
                            else -> SwipeDirections.UNKNOWN
                        }
                    }
                    else {
                        when {
                            y  >  0 -> SwipeDirections.DOWN
                            y  <  0 -> SwipeDirections.UP
                            else -> SwipeDirections.UNKNOWN
                        }
                    }
                },
                onDragEnd = {
                    when (swipeDirections) {
                        SwipeDirections.RIGHT -> onSwipeRight()
                        SwipeDirections.LEFT -> onSwipeLeft()
                        SwipeDirections.DOWN -> onSwipeDown()
                        SwipeDirections.UP -> onSwipeUp()
                        else -> Unit
                    }
                }
            )
        }
    }
}