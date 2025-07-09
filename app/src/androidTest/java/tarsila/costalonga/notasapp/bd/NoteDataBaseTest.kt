package tarsila.costalonga.notasapp.bd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.local.NoteDao
import tarsila.costalonga.notasapp.data.local.NoteRoom
import tarsila.costalonga.notasapp.rules.InstantTaskRule

@RunWith(AndroidJUnit4::class)
class NoteDataBaseTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteRoom

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteRoom::class.java,
        ).build()
        noteDao = db.noteDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runTest {
        val note = Note(12, "Title", "Description", sort = 1)
        noteDao.insertNote(note)
        val byId = noteDao.getNoteById(12)
        byId.test {

            val expectedNote = awaitItem()
            assertThat(note).isEqualTo(expectedNote)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun writeAndDeleteNote() = runTest {
        val note = Note(12, "Title", "Description", sort = 1)
        noteDao.insertNote(note)

        noteDao.deleteNote(note)

        val deletedNote = noteDao.getNoteById(12)
        deletedNote.test {
            val expectedNote = awaitItem()
            assertThat(expectedNote).isEqualTo(null)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun getAllNotes() = runTest {
        val noteOne = Note(12, "Title", "Description", sort = 1)
        val noteTwo = Note(13, "Title", "Description", sort = 2)
        val noteThree = Note(14, "Title", "Description", sort = 3)

        val list = listOf(noteOne, noteTwo, noteThree)
        list.forEach { note ->
            noteDao.insertNote(note)
        }

        val flowOfAllNotes = noteDao.getAllNotes()

        flowOfAllNotes.test {
            val expectedResult = awaitItem()
            assertThat(expectedResult).isEqualTo(list)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun getActiveNotes() = runTest {
        val noteOne = Note(12, "Title", "Description", sort = 1, isFinished = true)
        val noteTwo = Note(13, "Title", "Description", sort = 2)
        val noteThree = Note(14, "Title", "Description", sort = 3)

        val list = listOf(noteOne, noteTwo, noteThree)
        list.forEach { note ->
            noteDao.insertNote(note)
        }

        val activeNotesCount = noteDao.getActiveNotes()
        assertThat(activeNotesCount).isEqualTo(2)
    }
}
