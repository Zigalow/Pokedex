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

@Composable
fun PaginatedColumn(
    loadPrevious: () -> Unit,
    loadFollowing: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    val scrollState = rememberLazyListState()

    val itemCountState = remember {
        derivedStateOf { scrollState.layoutInfo.totalItemsCount }.asIntState()
    }

    if (itemCountState.intValue != 0) {
        if (!scrollState.canScrollBackward) {
            loadPrevious()
        }
        if (!scrollState.canScrollForward) {
            loadFollowing()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = scrollState,
    ) {
        content()
    }
}