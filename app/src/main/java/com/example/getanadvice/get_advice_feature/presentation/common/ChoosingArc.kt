package com.example.getanadvice.get_advice_feature.presentation.common

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.example.getanadvice.R
import kotlin.math.roundToInt

@Composable
fun ChoosingArc(
    background: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier,
    initial: Offset

) {
    val boxSize = with(LocalDensity.current) {
        80.dp.toPx()
    }

    var offsetX by remember {
        mutableStateOf(initial.x - boxSize )
    }

    var offsetY by remember {
        mutableStateOf(initial.y - boxSize)
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .fillMaxSize()
            .clip(shape = CircleShape)
            .background(
                color = background
            )
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    Log.i("window", "x = $offsetX y = $offsetY")
                }

            }

    ) {
        Text(
            text = stringResource(id = R.string.yes),
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun MainArch() {
    val initial = remember {
        mutableStateOf(Offset.Zero)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            initial.value = it.boundsInRoot().bottomRight
            Log.i("window", it.boundsInRoot().bottomRight.toString())
        }
    ) {
        if (initial.value != Offset.Zero) {
            AlternativeChoosingArc(initial = initial.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChoosingArc() {

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlternativeChoosingArc(
    background: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier,
    initial: Offset
) {
    val boxSize = -250f

    var offsetX by remember {
        mutableStateOf(initial.x - boxSize )
    }

    var offsetY by remember {
        mutableStateOf(initial.y - boxSize)
    }

    var radius by remember {
        mutableStateOf(600f)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = Color.Green,
                center = Offset(offsetX, offsetY),
                radius = radius
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if (change.pressed) {
                            if (dragAmount.x < 0 || dragAmount.y < 0) {
                                radius += 50f
                            } else {
                                radius -= 50f
                            }
                        }
                    }
                }
        )
    }
}

//How to tackle

//check position change

//check position change value to some value that you want

//also check the is OutofBound function

//then do the trick