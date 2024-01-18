import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun BezierCurveCharts(
    values: SnapshotStateList<Double>,
    lineWidth: Float = 8f,
    lineColors: List<Color> = listOf(Color.Black, Color.Black),
) {
    var xPointBefore by remember { mutableStateOf(0f) }
    var yPointBefore by remember { mutableStateOf(0f) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent).padding(horizontal = 35.dp),
        ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(8.dp)
        ) {
            val path = Path()

            val xBounds = Pair(0f, (values.size - 1).toFloat())
            val yBounds = com.execellemed.charts.getBounds(values)

            val scaleX = size.width / (xBounds.second - xBounds.first)
            val scaleY = size.height / (yBounds.second - yBounds.first)
            val yMove = -yBounds.first * scaleY

            values.forEachIndexed { index, value ->
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
                brush = Brush.linearGradient(lineColors),
                style = Stroke(width = lineWidth)
            )
        }
    }
}

fun getBounds(values: List<Double>): Pair<Double, Double> {
    val minValue = values.minOrNull() ?: 0.0
    val maxValue = values.maxOrNull() ?: 0.0
    return Pair(minValue, maxValue)
}

