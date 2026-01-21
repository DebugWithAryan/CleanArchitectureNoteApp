package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NoteUseCase(
    val getNote: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val getNotes: GetNote
)
