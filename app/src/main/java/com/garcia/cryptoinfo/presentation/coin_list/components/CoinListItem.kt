package com.garcia.cryptoinfo.presentation.coin_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.garcia.cryptoinfo.common.Constants.CHART_7DAYS
import com.garcia.cryptoinfo.common.Constants.CHART_BASE_URL
import com.garcia.cryptoinfo.common.Constants.COIN_LOGO_BASE_URL
import com.garcia.cryptoinfo.common.Constants.LOGO_PNG
import com.garcia.cryptoinfo.domain.model.Coin

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (Coin) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Image(
                painter = rememberImagePainter(
                    "$COIN_LOGO_BASE_URL${coin.id}$LOGO_PNG",
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "${coin.rank} - ${coin.name} (${coin.symbol})",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(
                        start = 15.dp,
                        end = 15.dp
                    )
                    .sizeIn(minWidth = 100.dp, maxWidth = 200.dp)
            )
        }

        Image(
            painter = rememberImagePainter(
                "$CHART_BASE_URL${coin.id}$CHART_7DAYS",
                builder = {
                    crossfade(true)
                    decoder(SvgDecoder(LocalContext.current))
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(96.dp,32.dp)
                .align(CenterVertically)
        )
    }
}