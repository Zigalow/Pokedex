package dtu.group21.ui.shared

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import okhttp3.internal.wait

data class PaginationElement(
    val key: Any? = null,
    val content: @Composable () -> Unit
)

@Composable
fun PaginatedColumn(
    loadPrevious: @Composable () -> Unit,
    loadFollowing: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    elements: List<PaginationElement>
) {
    val scrollState = rememberLazyListState()

    val visibleIndexesState = remember {
        derivedStateOf { scrollState.layoutInfo.visibleItemsInfo.map { it.index } }
    }

    if (elements.isNotEmpty()) {
        val firstIndex = elements.indices.first
        val lastIndex = elements.indices.last

        if (firstIndex in visibleIndexesState.value) {
            loadPrevious()
        }
        if (lastIndex in visibleIndexesState.value) {
            loadFollowing()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = scrollState,
    ) {
        for (element in elements) {
            item(key = element.key) {
                element.content()
            }
        }
    }
}