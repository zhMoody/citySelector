package com.execellemed.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun lineCharts(data: List<Double>) {
    if (data.isNotEmpty()) {
        var xPointBefore by remember { mutableStateOf(0f) }
        var yPointBefore by remember { mutableStateOf(0f) }

        val clippedData = clipToRange(data, 30.0, 50.0)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp)
        ) {
            val path = Path()

            val xBounds = Pair(0f, (clippedData.size - 1).toFloat())
            val yBounds = getBounds(clippedData)

            val scaleX = size.width / (xBounds.second - xBounds.first)
            val scaleY = size.height / (yBounds.second - yBounds.first)
            val yMove = -yBounds.first * scaleY

            clippedData.forEachIndexed { index, value ->
                val xPoint = index.toFloat() * scaleX
                val yPoint = (size.height - (value.toFloat() * scaleY) + yMove).toFloat()

                if (index == 0) {
                    path.moveTo(xPoint, yPoint)
                } else {
                    val controlX = xPointBefore + (xPoint - xPointBefore) / 2
                    val controlY = yPointBefore
                    path.cubicTo(controlX, controlY, controlX, yPoint, xPoint, yPoint)
                }

                xPointBefore = xPoint
                yPointBefore = yPoint
            }

            drawPath(
                path = path,
                brush = Brush.linearGradient(listOf(Color.Blue, Color.Green)),
                style = Stroke(width = 4f)
            )
        }
    }
}

fun clipToRange(values: List<Double>, min: Double, max: Double): List<Double> {
    return values.map { it.coerceIn(min, max) }
}

fun getBounds(values: List<Double>): Pair<Double, Double> {
    val minValue = values.minOrNull() ?: 0.0
    val maxValue = values.maxOrNull() ?: 0.0
    return Pair(minValue, maxValue)
}

@Preview(showBackground = true)
@Composable
fun BezierCurveChartPreview() {
    lineCharts(listOf(35.6, 34.543, 35.5))
}
