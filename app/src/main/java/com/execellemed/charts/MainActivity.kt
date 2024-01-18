package com.execellemed.charts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.execellemed.charts.ui.theme.ChartsTheme
import com.execellemed.charts.zhCityPicker.modal.AddressNode
import com.execellemed.charts.zhCityPicker.view.BaseCitySelector
import com.execellemed.charts.zhCityPicker.view.ZhCitySelector

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChartsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Charts()
                    val items = (1..12).map { "北京" }
                    val page1 = listOf(
                        AddressNode(name = "安徽", code = "10", letter = "A"),
                        AddressNode(name = "北京", code = "10", letter = "B"),
                        AddressNode(name = "重庆", code = "10", letter = "C"),
                        AddressNode(name = "福建", code = "10", letter = "F"),
                        AddressNode(name = "伤害", code = "10", letter = "S"),
                        AddressNode(name = "重庆", code = "10", letter = "C"),
                        AddressNode(name = "福建", code = "10", letter = "F"),
                        AddressNode(name = "重庆", code = "10", letter = "C"),
                        AddressNode(name = "福建", code = "10", letter = "F"),
                        AddressNode(name = "河南", code = "10", letter = "H"),
                        AddressNode(name = "河北", code = "10", letter = "H"),
                        AddressNode(name = "河南", code = "10", letter = "D"),

                        )
                    val page2 = listOf(
                        AddressNode(name = "河南", code = "10", letter = "F"),
                        AddressNode(name = "河北", code = "10", letter = "F"),
                    )
                    val page3 = listOf(
                        AddressNode(name = "河南2222", code = "10", letter = "F"),
                        AddressNode(name = "上海", code = "10", letter = "F"),
                    )
                    val page4 = listOf(
                        AddressNode(name = "福建", code = "10", letter = "F"),
                        AddressNode(name = "重庆", code = "10", letter = "C"),
                        AddressNode(name = "福建", code = "10", letter = "F"),
                        AddressNode(name = "河南", code = "10", letter = "H"),
                    )
                    val page = listOf(
                        page1,
                        page2,
                        page3,
                        page4
                    )
                    ZhCitySelector(cityList = page , hotCityList = items){list->
                        list.forEach {
                            println(it)
                        }
                    }
                }
            }
        }
    }
}

