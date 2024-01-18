package com.execellemed.charts

import BezierCurveCharts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.execellemed.charts.chart.IntradayInfo
import com.execellemed.charts.chart.LineChart
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.random.Random


fun createRandodDoubleList(): List<Double> {
    val list = mutableListOf<Double>()
    val random = java.util.Random()
    (0..20).forEach { _ ->
        list.add(random.nextDouble() * 10.0)
    }
    return list
}

@Composable
fun Charts() {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            item { Spacer(modifier = Modifier.height(40.dp)) }
            item { Text(text = "Compose charts", style = MaterialTheme.typography.headlineMedium) }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                val mutableDoubleChartData = remember {
                    mutableStateListOf<Double>().apply { addAll(createRandodDoubleList()) }
                }
                val data = remember {
                    mutableStateListOf<IntradayInfo>(
                        IntradayInfo(date = LocalDateTime.now(), close = 33.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 33.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 34.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 35.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 33.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 33.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 38.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 38.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 37.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 33.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 30.4),
                        IntradayInfo(date = LocalDateTime.now(), close = 39.4),
                    )
                }
                LaunchedEffect(Unit) {
                    while (mutableDoubleChartData.size < 100) {
                        delay(2000)
                        mutableDoubleChartData.add(Random.nextDouble() * 2)
                        if (data.size>20) data.removeAt(0)
                        data.add(IntradayInfo(date = LocalDateTime.now(), close = 33 + Random.nextDouble() * 4))
                        println(33 + Random.nextDouble() * 2)
                        println(data[data.size - 1])
                    }
                }
                Box(
                    Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Line()
                    BezierCurveCharts(values = mutableDoubleChartData)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.White)
                )

                Box(
                    Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Line()
                    LineChart(
                        infos = data,
                        modifier = Modifier
                            .height(190.dp)
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

fun getBounds(list: List<Float>): Pair<Float, Float> {
    var min = Float.MAX_VALUE
    var max = -Float.MAX_VALUE
    list.forEach {
        min = min.coerceAtMost(it)
        max = max.coerceAtLeast(it)
    }
    return Pair(min, max)
}

