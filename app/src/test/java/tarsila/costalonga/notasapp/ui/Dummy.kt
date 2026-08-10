package tarsila.costalonga.notasapp.ui

import tarsila.costalonga.notasapp.data.local.Note

object Dummy {

    val expectedNote = Note(
        id = 9293,
        title = "Title Note 1",
        description = "Description Note 1",
        createdAt = 4876,
        updatedAt = 5336,
        isFinished = false,
        sort = 1,
    )

    val noteOne = Note(12, "Title", "Description", sort = 1)
    val noteTwo = Note(13, "Title", "Description", sort = 2)
    val noteThree = Note(14, "Title", "Description", sort = 3)

    val listOfNotes = listOf(noteOne, noteTwo, noteThree)
}
