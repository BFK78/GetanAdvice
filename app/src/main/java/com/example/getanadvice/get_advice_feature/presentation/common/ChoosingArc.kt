package com.example.getanadvice.get_advice_feature.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
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
            .drawBehind {

            }
    ) {
        Text(
            text = stringResource(id = R.string.yes),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChoosingArc() {

}