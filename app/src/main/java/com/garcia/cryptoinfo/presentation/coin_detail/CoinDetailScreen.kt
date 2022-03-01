package com.garcia.cryptoinfo.presentation.coin_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.garcia.cryptoinfo.common.Constants
import com.garcia.cryptoinfo.presentation.coin_detail.components.CoinTag
import com.garcia.cryptoinfo.presentation.coin_detail.components.TeamListItem
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        state.coin?.let { coin ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier = Modifier.weight(8f)
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    "${Constants.COIN_LOGO_BASE_URL}${coin.coinId}${Constants.LOGO_PNG}",
                                    builder = {
                                        crossfade(true)
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "${coin.rank} - ${coin.name} (${coin.symbol})",
                                style = MaterialTheme.typography.h2,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 12.dp)
                            )
                        }
                        val status = "${if(coin.isNew) "New ${coin.type}! -" else ""} ${if(coin.isActive) "Active" else "Inactive"}"
                        Text(
                            text = status,
                            color = if(coin.isActive) Color.Green else Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = rememberImagePainter(
                            "${Constants.CHART_BASE_URL}${coin.coinId}${Constants.CHART_7DAYS}",
                            builder = {
                                crossfade(true)
                                decoder(SvgDecoder(LocalContext.current))
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)

                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = coin.description,
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.h3,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        coin.tags.forEach { tag ->
                            CoinTag(tag = tag)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                if(coin.team.isNotEmpty()){
                    item {
                        Text(
                            text = "Team members",
                            style = MaterialTheme.typography.h3,
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    items(coin.team) { teamMember ->
                        TeamListItem(
                            teamMember = teamMember,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        )
                        Divider()
                    }
                }
            }
        }

        state.error?.let {
            Text(
                text = it.message ?: LocalContext.current.getString(it.resourceId),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}