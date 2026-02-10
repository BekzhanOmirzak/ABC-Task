package com.home.abc.data.datasource

import com.home.abc.R
import com.home.abc.domain.model.ImageSource
import com.home.abc.domain.model.ListItem
import com.home.abc.domain.model.Page

object DemoDataSource {
    private const val REMOTE_IMAGE_WIDTH = 900
    private const val REMOTE_IMAGE_HEIGHT = 500
    private const val ITEMS_PER_PAGE = 25
    private const val ITEM_ID_PAGE_MULTIPLIER = 1_000
    private const val PAGE_DISPLAY_OFFSET = 1

    private val labels = listOf(
        "Apple", "Banana", "Orange", "Blueberry", "Mango",
        "Pear", "Plum", "Watermelon", "Grapes", "Peach",
        "Kiwi", "Cherry"
    )

    private val images = listOf(
        ImageSource.Remote(remoteImageUrl(seed = "river")),
        ImageSource.Remote(remoteImageUrl(seed = "forest")),
        ImageSource.Remote(remoteImageUrl(seed = "mountain")),
        ImageSource.Local(R.drawable.ic_launcher_background)
    )

    fun getPages(): List<Page> {
        return images.mapIndexed { pageIndex, image ->
            val items = (1..ITEMS_PER_PAGE).map { index ->
                val label = labels[(pageIndex + index) % labels.size]
                ListItem(
                    id = pageIndex * ITEM_ID_PAGE_MULTIPLIER + index,
                    title = label,
                    subtitle = "List item ${pageIndex + PAGE_DISPLAY_OFFSET} • #$index"
                )
            }
            Page(
                id = pageIndex,
                image = image,
                items = items
            )
        }
    }

    private fun remoteImageUrl(seed: String): String =
        "https://picsum.photos/seed/$seed/$REMOTE_IMAGE_WIDTH/$REMOTE_IMAGE_HEIGHT"
}
