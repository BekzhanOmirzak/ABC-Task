package com.home.abc.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.home.abc.R
import com.home.abc.domain.model.ImageSource
import com.home.abc.domain.model.ListItem
import com.home.abc.domain.model.Page
import com.home.abc.presentation.image.RemoteImageLoader
import com.home.abc.ui.theme.Dimens
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(state: MainState, onIntent: (MainIntent) -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = state.selectedPage,
        pageCount = { state.pages.size }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                onIntent(MainIntent.PageChanged(page))
            }
    }

    LaunchedEffect(state.selectedPage) {
        if (pagerState.currentPage != state.selectedPage) {
            pagerState.scrollToPage(state.selectedPage)
        }
    }

    val currentPage = state.pages.getOrNull(state.selectedPage)
    val filteredItems = state.filteredItems

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onIntent(MainIntent.ShowStats) }
            ) {
                Icon(imageVector = Icons.Default.BarChart, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = Dimens.l,
                    vertical = Dimens.m
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.m)
            ) {
                item {
                    CarouselSection(
                        pages = state.pages,
                        pagerState = pagerState
                    )
                }
                item {
                    DotsIndicator(
                        totalDots = state.pages.size,
                        selectedIndex = pagerState.currentPage
                    )
                }
                stickyHeader {
                    SearchBar(
                        query = state.query,
                        onQueryChange = { onIntent(MainIntent.QueryChanged(it)) }
                    )
                }
                items(filteredItems, key = { it.id }) { item ->
                    ListRow(
                        item = item,
                        image = currentPage?.image
                    )
                }
                if (filteredItems.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.no_items_match_your_search),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = Dimens.m)
                        )
                    }
                }
            }

            if (state.isStatsVisible) {
                StatsSheet(
                    stats = state.stats,
                    onDismiss = { onIntent(MainIntent.HideStats) }
                )
            }
        }
    }
}


@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun CarouselSection(
    pages: List<Page>,
    pagerState: PagerState
) {
    if (pages.isEmpty()) return
    HorizontalPager(
        state = pagerState,
        pageSpacing = Dimens.s,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.carouselHeight)
    ) { page ->
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(Dimens.l + Dimens.xxs)
        ) {
            AppImage(
                source = pages[page].image,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    if (totalDots <= MainScreenConstants.MIN_DOTS_FOR_INDICATOR) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.xs)
                    .size(Dimens.s - Dimens.xxs)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = Dimens.s)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            singleLine = true,
            shape = RoundedCornerShape(Dimens.m)
        )
    }
}

@Composable
private fun ListRow(
    item: ListItem,
    image: ImageSource?
) {
    Card(
        shape = RoundedCornerShape(Dimens.l),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (image != null) {
                AppImage(
                    source = image,
                    modifier = Modifier
                        .size(Dimens.xxxl)
                        .clip(RoundedCornerShape(Dimens.m))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(Dimens.xxxl)
                        .clip(RoundedCornerShape(Dimens.m))
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
            }
            Spacer(modifier = Modifier.width(Dimens.m))
            Column {
                Text(text = item.title, fontWeight = FontWeight.SemiBold)
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatsSheet(
    stats: StaticState?,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.xl,
                    vertical = Dimens.l
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.m)
        ) {
            Text(text = stringResource(R.string.list_statistics), style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.s - Dimens.xxs)) {
                stats?.pageCounts?.forEach { page ->
                    Text(text = "List ${page.pageIndex + MainScreenConstants.PAGE_DISPLAY_OFFSET} (${page.count} items)")
                }
            }
            Spacer(modifier = Modifier.height(Dimens.s - Dimens.xxs))
            Text(
                text = stringResource(R.string.top_characters_current_list),
                style = MaterialTheme.typography.titleSmall
            )
            val topChars = stats?.topChars.orEmpty()
            if (topChars.isEmpty()) {
                Text(text = stringResource(R.string.no_characters_to_analyze))
            } else {
                topChars.forEach { item ->
                    Text(text = "${item.char} = ${item.count}")
                }
            }
            Spacer(modifier = Modifier.height(Dimens.m))
        }
    }
}

@Composable
private fun AppImage(
    source: ImageSource,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    when (source) {
        is ImageSource.Local -> {
            Image(
                painter = painterResource(id = source.resId),
                contentDescription = null,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is ImageSource.Remote -> {
            RemoteImage(url = source.url, modifier = modifier, contentScale = contentScale)
        }
    }
}

@Composable
private fun RemoteImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val bitmapState by produceState<ImageBitmap?>(initialValue = null, url) {
        value = RemoteImageLoader.load(url)?.asImageBitmap()
    }

    if (bitmapState != null) {
        Image(
            bitmap = bitmapState!!,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimens.xl - Dimens.xxs),
                strokeWidth = Dimens.xxs
            )
        }
    }
}

private object MainScreenConstants {
    const val MIN_DOTS_FOR_INDICATOR = 1
    const val PAGE_DISPLAY_OFFSET = 1
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(
        state = MainState(),
        onIntent = {}
    )
}
