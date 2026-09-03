package tarsila.costalonga.notasapp.ui.main.compose

import android.content.ClipData
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.ui.core.compose.ChangeThemeDialog
import tarsila.costalonga.notasapp.ui.core.compose.ItemMenuType
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.SearchLayoutBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.core.compose.util.PreviewParams
import tarsila.costalonga.notasapp.ui.core.compose.util.getTextDecoration
import tarsila.costalonga.notasapp.ui.main.MainViewModel
import tarsila.costalonga.notasapp.ui.main.NoteListUiState

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    mainEvent: (MainEvent) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notesListState by viewModel.noteListUiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.event) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.event.collect { event ->
                    when (event) {
                        MainEvent.OnAddNoteClicked -> {
                            mainEvent(MainEvent.OnAddNoteClicked)
                        }

                        is MainEvent.OnItemListClicked -> {
                            mainEvent(MainEvent.OnItemListClicked(event.noteId))
                        }

                        is MainEvent.OnOptionsMenuClicked -> {
                            mainEvent(MainEvent.OnOptionsMenuClicked(event.itemMenu))
                        }

                        is MainEvent.OnThemeOptionClicked -> {
                            mainEvent(MainEvent.OnThemeOptionClicked(event.themeMode))
                        }
                    }
                }
            }
        }
    }

    MainCompose(
        uiState = uiState,
        notesListState = notesListState,
        uiIntent = { viewModel.handleIntent(it) },
    )
}

@Composable
private fun MainCompose(
    uiState: MainUiState,
    uiIntent: (MainIntent) -> Unit,
    notesListState: NoteListUiState,
) {
    var showChangeThemeDialog by rememberSaveable { mutableStateOf(false) }
    var searchTerm by rememberSaveable { mutableStateOf("") }
    var filteredNotas: List<Note>

    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()

    if (showChangeThemeDialog) {
        ChangeThemeDialog(
            onDismissRequest = { showChangeThemeDialog = false },
            themeModes = uiState.themeMode,
            onThemeClick = {
                showChangeThemeDialog = false
                uiIntent(MainIntent.OnThemeOptionClick(it))
            },
        )
    }

    Scaffold(
        topBar = {
            if (uiState.isSearchEnabled) {
                SearchLayoutBar(
                    hasFocus = true,
                    onArrowBackClicked = {
                        uiIntent(MainIntent.OnArrowBackClick)
                        searchTerm = ""
                    },
                    searchTerm = searchTerm,
                    onSearchTermChanged = { searchTerm = it },
                )
            } else {
                MyTopAppBar(
                    isMainFragment = true,
                    onMenuClick = { itemMenu ->
                        if (itemMenu == ItemMenuType.TEMA) {
                            showChangeThemeDialog = true
                        } else {
                            uiIntent(MainIntent.OnOptionsMenuClick(itemMenu))
                        }
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { uiIntent(MainIntent.OnAddNoteClick) },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        },
    ) { padding ->
        AnimatedContent(
            notesListState,
            transitionSpec = {
                fadeIn(tween(200, delayMillis = 300))
                    .togetherWith(fadeOut(animationSpec = tween(300)))
            },
            label = "mainScreenAnimation",
        ) { notesListState ->
            when (notesListState) {
                NoteListUiState.Error -> Unit
                NoteListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is NoteListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .padding(NoteTheme.spacing.spacer8),
                        content = {
                            filteredNotas = performFilterInTitle(searchTerm, notesListState.allNotes)

                            items(filteredNotas.size) {
                                val nota = filteredNotas[it]
                                ItemList(
                                    nota = nota,
                                    onItemClicked = { uiIntent(MainIntent.OnItemListClick(nota.id)) },
                                    onCheckedChange = { checkedStatus ->
                                        uiIntent(MainIntent.OnCheckboxClick(nota, checkedStatus))
                                    },
                                    onItemLongClicked = {
                                        scope.launch {
                                            val clipData = ClipData.newPlainText(
                                                "noteTitle", nota.title,
                                            )
                                            clipboardManager.setClipEntry(ClipEntry(clipData))
                                        }
                                    },
                                )
                            }
                        },
                    )
                }
            }
        }

    }
}

fun performFilterInTitle(
    searchedText: String,
    allNotas: List<Note>,
): List<Note> {
    return if (searchedText.isNotEmpty()) {
        val resultList = mutableListOf<Note>()
        for (nota in allNotas) {
            if (nota.title.lowercase().contains(searchedText.lowercase())) {
                resultList.add(nota)
            }
        }
        resultList.toList()
    } else {
        allNotas
    }
}

@Composable
fun ItemList(
    nota: Note,
    onItemClicked: () -> Unit,
    onItemLongClicked: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by rememberSaveable { mutableStateOf(nota.isFinished) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(NoteTheme.spacing.spacer4)
            .combinedClickable(
                onClick = onItemClicked,
                onLongClick = onItemLongClicked,
            ),
    ) {
        /* TODO: drag and drop is disabled until a solution for compose is released
         Icon(
                painter = painterResource(id = R.drawable.drag_indicator_24),
                contentDescription = null,
                tint = NoteTheme.colors.primaryVariant
            ) */
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onCheckedChange(it)
            },
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = nota.title,
            style =
                NoteTheme.typography.bodyLarge.copy(
                    textDecoration = getTextDecoration(checkedState),
                ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewMain(
    @PreviewParameter(PreviewParams::class) listOfNotas: List<Note>,
) {
    NoteComposeTheme {
        MainCompose(
            notesListState = NoteListUiState.Success(listOfNotas),
            uiState = MainUiState(
                themeMode = listOf(),
            ),
            uiIntent = {},
        )
    }
}
