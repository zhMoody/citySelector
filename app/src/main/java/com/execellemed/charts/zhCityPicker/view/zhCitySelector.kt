package com.execellemed.charts.zhCityPicker.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.execellemed.charts.zhCityPicker.modal.AddressNode


@Composable
fun ZhCitySelector(
    cityList: List<List<AddressNode>>,
    hotCityList: List<String>,
    callback:(List<AddressNode>)->Unit
) {
    BaseCitySelector(
        cityList = cityList,
        hotCityList = hotCityList,
        callback = callback
    )
}


@Preview
@Composable
fun A() {
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
    ZhCitySelector(cityList = page , hotCityList = items){
        println(it)
    }
}