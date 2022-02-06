package com.example.getanadvice.get_advice_feature.presentation.common

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.*
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.coroutines.*

@Composable
fun MainArch() {
    val initial = remember {
        mutableStateOf(Offset.Zero)
    }

    val maxRadius = remember {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlternativeChoosingArc(
    background: Color = MaterialTheme.colors.primary,
    initial: Offset,
    maxRadius: Float
) {
    val boxSize = -250f

    var touchOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val scope = rememberCoroutineScope()

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
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = background,
                    center = Offset(offsetX, offsetY),
                    radius = circleRadius.value
                )
            }
    ) {
        Text(
            text = "Hai I'm a text",
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .size(400.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomEnd)
//                .pointerInput(Unit) {
//                    coroutineScope {
//                        forEachGesture {
//                            awaitPointerEventScope {
//                                val pointerId = awaitFirstDown().id
//                                launch { circleRadius.animateTo(620f) }
//                                do {
//                                    val event = awaitPointerEvent()
//                                    drag(pointerId = pointerId) {
//                                        val positionOffset = it.positionChange()
//                                        if (positionOffset.x < 0f || positionOffset.y < 0f) {
//                                            launch {
//                                                circleRadius.snapTo(
//                                                    circleRadius.value + 25f
//                                                )
//                                            }
//                                        }
//                                    }
//                                    if (circleRadius.value > maxRadius + 200f) {
//                                        navigate = true
//                                        launch {
//                                            circleRadius.animateTo(
//                                                targetValue = maxRadius * 2f + 350f,
//                                                animationSpec = spring(
//                                                    stiffness = Spring.StiffnessVeryLow,
//                                                    dampingRatio = Spring.DampingRatioNoBouncy
//                                                )
//                                            )
//                                        }
//                                    } else {
//                                        navigate = false
//                                        launch {
//                                            circleRadius.animateTo(
//                                                targetValue = 600f,
//                                                animationSpec = spring(
//                                                    dampingRatio = Spring.DampingRatioNoBouncy,
//                                                    stiffness = Spring.StiffnessVeryLow
//                                                )
//                                            )
//                                        }
//                                    }
//                                } while (event.changes.any { it.pressed })
//                            }
//                        }
//                    }
//                }
//                .pointerInteropFilter {
//                    val mVelocityTracker = VelocityTracker()
//                    scope.launch {
//                        when (it.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                mVelocityTracker.resetTracking()
//                                circleRadius.animateTo(700f)
//                                touchOffset = Offset(it.x, it.y)
//                            }
//                            MotionEvent.ACTION_UP -> {
//                                if (circleRadius.value > maxRadius) {
//                                    circleRadius.animateTo(
//                                        targetValue = maxRadius * 2f + 350f,
//                                        animationSpec = spring(
//                                            stiffness = Spring.StiffnessVeryLow,
//                                            dampingRatio = Spring.DampingRatioNoBouncy
//                                        )
//                                    )
//                                } else {
//                                    circleRadius.animateTo(
//                                        targetValue = 600f,
//                                        animationSpec = spring(
//                                            dampingRatio = Spring.DampingRatioNoBouncy,
//                                            stiffness = Spring.StiffnessVeryLow
//                                        )
//                                    )
//                                }
//                            }
//                            MotionEvent.ACTION_MOVE -> {
//                                val currentPosition = Offset(it.x, it.y)
//                                val positionChanged = currentPosition - touchOffset
//                                if (positionChanged.x < 0f ||  positionChanged.y < 0f) {
//                                    circleRadius.snapTo(
//                                        circleRadius.value + 20f
//                                    )
//                                }
//                            }
//                        }
//                    }
//                    true
//                }
                .pointerInput(Unit) {
                    var animate = false
                    var newRadius = 0f
                    var velocity = 0f
                    val decay = splineBasedDecay<Float>(this)
                    val velocityTracker = VelocityTracker()
                    coroutineScope {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (dragAmount.x < 0 || dragAmount.y < 0) {
                                    launch {
                                        circleRadius.snapTo(circleRadius.value + 50f)
                                    }
                                }

                                velocityTracker.addPosition(
                                    change.uptimeMillis,
                                    change.position
                                )

                                val velocityX = velocityTracker.calculateVelocity().x
                                val velocityY = velocityTracker.calculateVelocity().y
                                velocity = Math.max(velocityX, velocityY)

                                newRadius = decay.calculateTargetValue(
                                    initialValue = circleRadius.value,
                                    initialVelocity = velocity
                                )

                                circleRadius.updateBounds(
                                    upperBound = maxRadius * 2f + 350f,
                                    lowerBound = 600f
                                )

                                Log.i("basim", newRadius.toString())

                                if (Math.abs(newRadius) >= maxRadius - 200f) {
                                    animate = true
                                }
                            },
                            onDragEnd = {
                                launch {
                                    if (circleRadius.value > maxRadius || animate) {
                                        circleRadius.animateTo(
                                            targetValue = maxRadius * 2f + 350f,
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessVeryLow,
                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                            )
                                        )
                                    } else {
                                        circleRadius.animateTo(
                                            initialVelocity = velocity,
                                            targetValue = 600f,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                                stiffness = Spring.StiffnessVeryLow
                                            )
                                        )
                                    }
                                }
                            },
                            onDragStart = {
                                launch {
                                    circleRadius.animateTo(
                                        700f
                                    )
                                }
                            }
                        )
                    }
                }
        )
    }
}