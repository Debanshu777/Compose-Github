package com.debanshu777.compose_github.ui.feature_profile.components

import android.graphics.drawable.PictureDrawable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.caverock.androidsvg.SVG
import com.debanshu777.compose_github.ui.feature_profile.state.ProfileStatsState
import com.debanshu777.compose_github.utils.Chart

@OptIn(ExperimentalUnitApi::class)
@Composable
fun UserStatsSection(userStats: ProfileStatsState?) {
    val svgString =
        "<svg xmlns=\"http://www.w3.org/2000/svg\"/>"
    //convert SVG string to an object of type SVG
    val svg = SVG.getFromString(userStats?.data?.rendered?.plugins?.isocalendar?.svg ?: svgString)
    //create a drawable from svg
    val drawable = PictureDrawable(svg.renderToPicture(800, 400))
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Contributions",
            fontWeight = FontWeight.W300,
            fontSize = TextUnit(value = 18F, type = TextUnitType.Sp),
        )
        Card(
            modifier = Modifier
                .padding(vertical = 5.dp),
            elevation = 5.dp,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Text(
                            text = "Max Streak",
                            fontSize = TextUnit(value = 12F, type = TextUnitType.Sp),
                        )
                        Text(
                            text = "${userStats?.data?.rendered?.plugins?.isocalendar?.streak?.max ?: 0}",
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit(value = 13F, type = TextUnitType.Sp),
                        )
                    }
                    Column {
                        Text(
                            text = "Average Commit",
                            fontSize = TextUnit(value = 12F, type = TextUnitType.Sp),
                        )
                        Text(
                            text = "${userStats?.data?.rendered?.plugins?.isocalendar?.average ?: 0}",
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit(value = 13F, type = TextUnitType.Sp),
                        )
                    }
                    Column {
                        Text(
                            text = "Current Streak",
                            fontSize = TextUnit(value = 12F, type = TextUnitType.Sp),
                        )
                        Text(
                            text = "${userStats?.data?.rendered?.plugins?.isocalendar?.streak?.current ?: "0"}",
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit(value = 13F, type = TextUnitType.Sp),
                        )
                    }
                }
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    model = drawable,
                    contentScale = ContentScale.None,
                    contentDescription = "User Avatar"
                )
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Most Used Languages",
            fontWeight = FontWeight.W300,
            fontSize = TextUnit(value = 18F, type = TextUnitType.Sp),
        )
        Card(
            modifier = Modifier
                .padding(vertical = 5.dp),
            elevation = 5.dp,
        ) {
            ShowChart(userStats)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShowChart(userStats: ProfileStatsState?) {
    var showChart by remember {
        mutableStateOf(true)
    }
    var map: MutableMap<Pair<String, String>, Float> = HashMap()
    userStats?.data?.rendered?.plugins?.languages?.favorites?.sortedBy { it.value }
        .let { favorites ->
            favorites?.map {
                map.put(Pair(it.name, it.color), it.value.toFloat() * 100)
            }
            if (map.isNotEmpty()) {
                val result = map.toList().sortedBy { (_, value) -> value }.takeLast(6).toMap()
                Chart(
                    data = result, height = 150.dp, isExpanded = true,
                )
            }
        }
}