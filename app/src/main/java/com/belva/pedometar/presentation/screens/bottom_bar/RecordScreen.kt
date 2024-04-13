package com.belva.pedometar.presentation.screens.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.belva.pedometar.common.DateUtil
import com.belva.pedometar.common.showToast
import com.belva.pedometar.domain.viewmodel.stats.Response
import com.belva.pedometar.domain.viewmodel.stats.StatsViewModel
import com.belva.pedometar.presentation.screens.components.BarChart
import com.belva.pedometar.presentation.screens.components.CustomButtonBox
import com.belva.pedometar.presentation.screens.components.DailyStepsRow
import com.belva.pedometar.room_db.DailyStepsEntity
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordScreen(
    statsViewModel: StatsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val responseState =  statsViewModel.responseState

    val scaffoldState = rememberScaffoldState()

    var pageShowed by remember { mutableStateOf(0) }
    var listShowed by remember { mutableStateOf(emptyList<BarChartInput>()) } // Dohvaća sublist  koraka na temelju trenutne stranice i poziva funkcija callback s rezultatom.

    var demoList by remember {
        mutableStateOf(emptyList<DailyStepsEntity>())
    }

    var stepsFullList by remember { mutableStateOf(emptyList<DailyStepsEntity>()) }
    var limitPage by remember { mutableStateOf(0) }



    if(responseState is Response.Success) {
        // if(true) {
        stepsFullList = responseState.data


        // getDemoList (list = mutableListOf()) {
        //   demoList = it
        //  }
        val size = stepsFullList.size
        limitPage = ceil(size/7.0).toInt()

        getListShowed(
            page = pageShowed,
            list= stepsFullList
        )
        {

        }


        // val size = demoList.size
        val fromIndex: Int = if (size > 7) size - 7 else 0

        val tempList = mutableListOf<BarChartInput>()
        for (i in (fromIndex until size)) {
            val date = LocalDate.ofEpochDay(demoList[i].epochDay)
            val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.US)
            tempList.add(
                BarChartInput(
                    date.format(formatter),
                    demoList[i].steps.toInt()
                )
            )
        }

        listShowed = tempList
    }


    if(responseState is Response.Success) {

        stepsFullList= responseState.data
        val size = stepsFullList.size
        limitPage = ceil(size/7.0).toInt()

        getListShowed(
            page = pageShowed,
            list = stepsFullList
        )
        {
            listShowed=it
        }


    }




    Scaffold(
        topBar = {},
        content = { innerPadding ->
            MainContent(
                innerPadding = innerPadding,
                allDaysSteps = stepsFullList,
                listShowed = listShowed,
                onLeftButton = {
                    if(pageShowed<(limitPage-1)) {
                        pageShowed += 1
                        getListShowed(
                            page = pageShowed,
                            list = stepsFullList
                        ) {
                            listShowed = it
                        }
                    } else {
                        context.showToast("Last Page!")

                    }

                },


                onRightButton = {
                    if(pageShowed >0 ) {
                        pageShowed -=1
                        getListShowed(
                            page = pageShowed,
                            list = stepsFullList
                        ) {
                            listShowed =it
                        }
                    } else {
                        context.showToast("First Page !")
                    }

                }
            )
        },
        floatingActionButton = {},
        scaffoldState = scaffoldState
    )
}

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    allDaysSteps: List<DailyStepsEntity>,
    listShowed: List<BarChartInput>,
    onLeftButton: () -> Unit,
    onRightButton: () -> Unit
) {
    if (listShowed.isNotEmpty()) {
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            BarChart(
                data = listShowed,
                height = 360.dp,
                onSwipeRight= onLeftButton,
                onSwipeLeft = onRightButton
            )

            Spacer(modifier = Modifier.height(24.dp))
            CustomButtonBox(
                onLeftButton = onLeftButton,
                onRightButton = onRightButton

            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                coroutineScope.launch {
                    scrollState.scrollToItem(allDaysSteps.size-1)
                }
                itemsIndexed(allDaysSteps) {
                        _,item ->
                    DailyStepsRow(item= item)

                }
            }
        }
    }
}

private fun getListShowed(page : Int , list : List<DailyStepsEntity>,callBack : (List<BarChartInput>) ->Unit )
{
    val size  = list.size
    var fromIndex = size - (7* (page*1))
    if (fromIndex <0 ) fromIndex = 0
    val toIndex = (size - (7* page)) -1

    val tempList = mutableListOf<BarChartInput>()
    for(i  in (fromIndex..toIndex)) {
        val date = LocalDate.ofEpochDay(list[i].epochDay)
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.US)
        tempList.add(
            BarChartInput(
                date.format(formatter),
                list[i].steps.toInt()
            )
        )
    }

    callBack.invoke(tempList)


}




data class BarChartInput(
    val timeStamp: String,
    val steps: Int
)

private fun getDemoList(
    list: MutableList<DailyStepsEntity>,
    callBack: (List<DailyStepsEntity>) -> Unit
) {
    val tempList = mutableListOf(
        DailyStepsEntity(DateUtil.getToday()-19, 15500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-18, 4500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-17, 2500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-15, 13500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-14, 17500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-13, 4500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-12, 15500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-11, 4500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-10, 2500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-9, 13500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-7, 17500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-6, 4500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-4, 10500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-3, 3500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-2, 6500, DateUtil.formattedCurrentTime()),
        DailyStepsEntity(DateUtil.getToday()-1, 13500, DateUtil.formattedCurrentTime())
    )

    list.forEach { tempList.add(it) }
    callBack.invoke(tempList)
}
