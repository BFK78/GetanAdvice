package com.example.getanadvice.get_advice_feature.presentation.common

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.*
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
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
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint
import kotlinx.coroutines.*
import kotlin.math.max
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

    var maxRadius = remember {
        mutableStateOf(0f)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            val intOffset = Offset(it.size.width / 2f, it.size.height / 2f)
            initial.value = it.boundsInRoot().bottomRight
            maxRadius.value = (initial.value - intOffset).getDistance()
        }
    ) {
        if (initial.value != Offset.Zero) {
            AlternativeChoosingArc(initial = initial.value, maxRadius = maxRadius.value)
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
    initial: Offset,
    maxRadius: Float
) {
    val boxSize = -250f

    var navigate by remember {
        mutableStateOf(false)
    }

    val circleRadius = remember {
        Animatable(600f)
    }

    Log.i("maxRadius" , maxRadius.toString())

    val offsetX by remember {
        mutableStateOf(initial.x - boxSize)
    }

    val offsetY by remember {
        mutableStateOf(initial.y - boxSize)
    }

    LaunchedEffect(key1 = navigate) {
        if (navigate) {
            delay(300)
            Log.i("basim", " navigate ionthe go")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = background,
                center = Offset(offsetX, offsetY),
                radius = circleRadius.value
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    coroutineScope {
                        forEachGesture {
                            awaitPointerEventScope {
                                val pointerId = awaitFirstDown().id
                                do {
                                    val event = awaitPointerEvent()
                                    drag(pointerId = pointerId) {
                                        val positionOffset = it.positionChange()
                                        if (it.pressed) {
                                            if (positionOffset.x < 0f || positionOffset.y < 0f) {
                                                launch {
                                                    circleRadius.snapTo(
                                                        circleRadius.value + 50f
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    if (circleRadius.value > maxRadius + 200f) {
                                        navigate = true
                                        launch {
                                            circleRadius.animateTo(
                                                targetValue = maxRadius * 2f + 350f,
                                                animationSpec = spring(
                                                    stiffness = Spring.StiffnessVeryLow,
                                                    dampingRatio = Spring.DampingRatioNoBouncy
                                                )
                                            )
                                        }
                                    } else {
                                        navigate = false
                                        launch {
                                            circleRadius.animateTo(
                                                targetValue = 600f,
                                                animationSpec = spring(
                                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                                    stiffness = Spring.StiffnessVeryLow
                                                )
                                            )
                                        }
                                    }
                                } while (event.changes.any { it.positionChanged() })
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