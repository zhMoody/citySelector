package com.execellemed.charts.zhCityPicker.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.execellemed.charts.zhCityPicker.modal.AddressNode
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseCitySelector(
    cityList: List<List<AddressNode>>,
    hotCityList: List<String>,
    callback: (List<AddressNode>) -> Unit
) {
    var pagerIdx by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 4 }
    )
    val coroutineScope = rememberCoroutineScope()
    val selectedList = remember { mutableStateListOf<AddressNode>() }
    val selectText = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(pagerState.currentPage) {
        pagerIdx = pagerState.currentPage
        println("------------>>>>>>>>>>$pagerIdx")
    }
    LaunchedEffect(selectedList.size == 4) {
        callback.invoke(selectedList)
    }
    Column(Modifier.background(Color.White)) {
        Text(
            text = "请选择所在地区",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 30.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Row(
            Modifier.padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(text = "中国大陆", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "香港/澳门", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(selectedList.size) {
                selectedList[it].name?.let { it1 ->
                    Box(Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                       selectText.value = selectedList[it].name
                    }) {
                        Text(
                            text = it1,
                            color = if (pagerIdx == it) Color(0xff887dfd) else Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item {
                if (selectedList.size < 4) Text(
                    text = "请选择",
                    color = Color(0xff887dfd),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        AnimatedVisibility(
            visible = pagerIdx <= 0,
            enter = fadeIn(animationSpec = tween(durationMillis = 10)),
            exit = fadeOut(animationSpec = tween(durationMillis = 10)),
        ) {
            Column {
                Box(
                    Modifier.padding(horizontal = 30.dp)
                ) {
                    Text(text = "热门城市", color = Color(0xff887dfd), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                LazyVerticalGrid(
                    contentPadding = PaddingValues(horizontal = 50.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    columns = GridCells.Fixed(4),
                    content = {
                        items(hotCityList.size) {
                            Box(modifier = Modifier.clickable {
                                if (pagerIdx == 0) selectedList.clear()
                                removeItemsFromIndex(selectedList, pagerIdx)
                                if (selectedList.size <= pagerIdx) selectedList.add(
                                    AddressNode(
                                        name = hotCityList[it],
                                        code = "10",
                                        letter = "B"
                                    )
                                )
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerIdx + 1)
                                }
                            }) {
                                Text(text = hotCityList[it], fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    })
            }
        }
        Box {
            HorizontalPager(
                state = pagerState,
                pageNestedScrollConnection = rememberNestedScrollInteropConnection(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> PageContent(
                        groupedData = groupDataByLetterToArray(cityList[0]),
                        selectText = selectText,
                        selectItemCallback = {
                            if (pagerIdx == 0) selectedList.clear()
                            removeItemsFromIndex(selectedList, pagerIdx)
                            if (selectedList.size <= pagerIdx) it?.let { it1 -> selectedList.add(it1) }
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        })

                    1 -> PageContent(
                        groupedData = groupDataByLetterToArray(cityList[1]),
                        selectText = selectText,
                        selectItemCallback = {
                            if (pagerIdx == 0) selectedList.clear()
                            removeItemsFromIndex(selectedList, pagerIdx)
                            if (selectedList.size <= pagerIdx) it?.let { it1 -> selectedList.add(it1) }
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        })

                    2 -> PageContent(
                        groupedData = groupDataByLetterToArray(cityList[2]),
                        selectText = selectText,
                        selectItemCallback = {
                            if (pagerIdx == 0) selectedList.clear()
                            removeItemsFromIndex(selectedList, pagerIdx)
                            if (selectedList.size <= pagerIdx) it?.let { it1 -> selectedList.add(it1) }
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(3)
                            }
                        })

                    3 -> PageContent(
                        groupedData = groupDataByLetterToArray(cityList[3]),
                        selectText = selectText,
                        selectItemCallback = {
                            if (pagerIdx == 0) selectedList.clear()
                            removeItemsFromIndex(selectedList, pagerIdx)
                            if (selectedList.size <= pagerIdx) it?.let { it1 -> selectedList.add(it1) }
                        })

                    else -> error("Invalid Page index")
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageContent(
    groupedData: SnapshotStateList<Pair<String?, List<AddressNode>>>,
    selectText: MutableState<String?>,
    selectItemCallback: (AddressNode?) -> Unit,
) {
    val state: LazyListState = rememberLazyListState()
    var currentHeader by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(state.firstVisibleItemIndex) {
        currentHeader = state.firstVisibleItemIndex
    }
    fun jumpToItem(idx: Int) {
        if (idx == 0) {
            coroutineScope.launch {
                state.animateScrollToItem(index = 0)
                return@launch
            }
        }
        var position = 0
        var length = 0
        groupedData.forEachIndexed { i, item ->
            if (groupedData[idx].first == groupedData[i].first) {
                position = i * 45 + length * 45
            }
            length += item.second.size
        }
        coroutineScope.launch {
            println(position)
            state.animateScrollToItem(index = idx, scrollOffset = position)
        }
    }
    Box() {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(start = 30.dp, end = 60.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            groupedData.forEachIndexed { _, (letter, nodes) ->
                stickyHeader {
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.White)
                    ) {
                        letter?.let {
                            Text(
                                text = it, fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color(0xFFDEEBF4))
                    )
                }

                items(nodes.size) {
                    nodes[it].name?.let { it1 ->
                        Box(modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                selectItemCallback.invoke(nodes[it])
                                selectText.value = nodes[it].name
                            }) {
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                AnimatedVisibility(visible = nodes[it].name == selectText.value) {
                                    Icon(
                                        Icons.Default.Done,
                                        contentDescription = "",
                                        Modifier.size(16.dp),
                                        tint = Color(0xFF887DFD)
                                    )
                                }
                                Text(
                                    text = it1, fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 10.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .background(Color(0xFFF6F8FB), shape = RoundedCornerShape(10.dp)),
            ) {
                Column(
                    Modifier.padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    groupedData.forEachIndexed { index, letter ->
                        Box(
                            Modifier
                                .clickable {
                                    jumpToItem(index)
                                }
                        ) {
                            Text(
                                text = letter.first ?: "",
                                fontWeight = if (index == currentHeader) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 10.sp,
                                color = Color(0xFF01192B),
                                modifier = Modifier
                                    .scale(if (index == currentHeader) 1.3f else 1f)
                                    .padding(horizontal = 6.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun removeItemsFromIndex(list: MutableList<AddressNode>, index: Int) {
    if (index >= 0 && index < list.size) {
        list.subList(index, list.size).clear()
    }
}

fun groupDataByLetterToArray(data: List<AddressNode>): SnapshotStateList<Pair<String?, List<AddressNode>>> {
    val d = data.groupBy { it.letter }
        .map { (letter, nodes) -> letter to nodes }
        .sortedBy { it.first }
    return mutableStateListOf(*d.toTypedArray())
}
