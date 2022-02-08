package com.example.getanadvice.get_advice_feature.presentation.common

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.getanadvice.get_advice_feature.presentation.Screen
import kotlinx.coroutines.*

@Composable
fun MainArch(
    text: String,
    onNavigate: (String) -> Unit
) {
    val initialR = remember {
        mutableStateOf(Offset.Zero)
    }

    val initialL = remember {
        mutableStateOf(Offset.Zero)
    }

    val maxRadius = remember {
        mutableStateOf(0f)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            val intOffset = Offset(it.size.width / 2f, it.size.height / 2f)
            initialR.value = it.boundsInRoot().bottomRight
            initialL.value = it.boundsInRoot().bottomLeft
            maxRadius.value = (initialR.value - intOffset).getDistance()
        }
    ) {
        if (initialR.value != Offset.Zero) {
            AlternativeChoosingArc(initialR = initialR.value, maxRadius = maxRadius.value, initialL = initialL.value,text = text, onNavigate = onNavigate)
        }
    }
}

@Composable
fun AlternativeChoosingArc(
    yesBackground: Color = MaterialTheme.colors.primaryVariant,
    noBackground: Color = MaterialTheme.colors.secondary,
    initialR: Offset?,
    initialL: Offset?,
    maxRadius: Float,
    text: String,
    onNavigate: (String) -> Unit
) {
    var destination by remember {
        mutableStateOf("")
    }

    var onDragR by remember {
        mutableStateOf(false)
    }
    var onDragL by remember {
        mutableStateOf(false)
    }

    val alphaL by animateFloatAsState(targetValue = if (onDragR) 0f else 1f,
    animationSpec = tween(500))

    val alphaR by animateFloatAsState(targetValue = if (onDragL) 0f else 1f,
        animationSpec = tween(500))

    val boxSize = -250f

    var navigate by remember {
        mutableStateOf(false)
    }

    val circleRadiusR = remember {
        Animatable(600f)
    }

    val offsetXR by remember {
        mutableStateOf(initialR?.x?.minus(boxSize) ?: 0f)
    }

    val offsetYR by remember {
        mutableStateOf(initialR?.y?.minus(boxSize) ?: 0f)
    }

    val circleRadiusL = remember {
        Animatable(600f)
    }

    val offsetXL by remember {
        mutableStateOf(initialL?.x?.plus(boxSize) ?: 0f)
    }

    val offsetYL by remember {
        mutableStateOf(initialL?.y?.minus(boxSize) ?: 0f)
    }

    LaunchedEffect(key1 = navigate) {
        if (navigate) {
            delay(300)
            onNavigate(destination)
            Log.i("basim", destination)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
            .drawBehind {
                drawCircle(
                    color = yesBackground,
                    center = Offset(offsetXR, offsetYR),
                    radius = circleRadiusR.value,
                    alpha = alphaR
                )

                drawCircle(
                    color = noBackground,
                    center = Offset(offsetXL, offsetYL),
                    radius = circleRadiusL.value,
                    alpha = alphaL
                )
            }
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h4
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(200.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    var animate = false
                    var newRadius: Float
                    var velocity = 0f
                    val decay = splineBasedDecay<Float>(this)
                    val velocityTracker = VelocityTracker()
                    coroutineScope {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (dragAmount.x < 0 || dragAmount.y < 0) {
                                    launch {
                                        circleRadiusR.snapTo(circleRadiusR.value + 50f)
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
                                    initialValue = circleRadiusR.value,
                                    initialVelocity = velocity
                                )

                                circleRadiusR.updateBounds(
                                    upperBound = maxRadius * 2f + 350f,
                                    lowerBound = 600f
                                )

                                if (Math.abs(newRadius) >= maxRadius - 200f) {
                                    animate = true
                                }

                            },
                            onDragEnd = {
                                launch {
                                    if (circleRadiusR.value > maxRadius || animate) {
                                        navigate = true
                                        destination = Screen.AdviceScreen.route
                                        circleRadiusR.animateTo(
                                            targetValue = maxRadius * 2f + 350f,
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessVeryLow,
                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                            )
                                        )
                                    } else {
                                        onDragR = false
                                        circleRadiusR.animateTo(
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
                                onDragR = true
                                launch {
                                    circleRadiusR.animateTo(
                                        700f
                                    )
                                }
                            }
                        )
                    }
                }
        ) {
            Text(
                text = "Yes!",
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
                    .alpha(alphaR),
                style = MaterialTheme.typography.h5
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(200.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomStart)
                .pointerInput(Unit) {
                    var animate = false
                    var newRadius: Float
                    var velocity = 0f
                    val decay = splineBasedDecay<Float>(this)
                    val velocityTracker = VelocityTracker()
                    coroutineScope {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (dragAmount.x < 0 || dragAmount.y < 0) {
                                    launch {
                                        circleRadiusL.snapTo(circleRadiusL.value + 50f)
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
                                    initialValue = circleRadiusL.value,
                                    initialVelocity = velocity
                                )

                                circleRadiusL.updateBounds(
                                    upperBound = maxRadius * 2f + 350f,
                                    lowerBound = 600f
                                )

                                if (Math.abs(newRadius) >= maxRadius - 200f) {
                                    animate = true
                                }

                            },
                            onDragEnd = {
                                launch {
                                    if (circleRadiusL.value > maxRadius || animate) {
                                        navigate = true
                                        destination = Screen.NonAdviceScreen.route
                                        navigate = true
                                        circleRadiusL.animateTo(
                                            targetValue = maxRadius * 2f + 350f,
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessVeryLow,
                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                            )
                                        )
                                    } else {
                                        onDragL = false
                                        circleRadiusL.animateTo(
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
                                onDragL = true
                                launch {
                                    circleRadiusL.animateTo(
                                        700f
                                    )
                                }
                            }
                        )
                    }
                }
        ) {
            Text(
                text = "No!",
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp)
                    .alpha(alpha = alphaL),
                style = MaterialTheme.typography.h5
            )
        }
    }
}
