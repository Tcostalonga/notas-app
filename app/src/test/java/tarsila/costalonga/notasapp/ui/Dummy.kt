package tarsila.costalonga.notasapp.ui

import tarsila.costalonga.notasapp.data.local.Note

object Dummy {
    val noteOne = Note(12, "Title", "Description", sort = 1)
    val noteTwo = Note(13, "Title", "Description", sort = 2)
    val noteThree = Note(14, "Title", "Description", sort = 3)

    val listOfNotes = listOf(noteOne, noteTwo, noteThree)
}
