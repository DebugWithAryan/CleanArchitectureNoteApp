package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

data class NoteUseCase(
    val getNote: Note,
    val deleteNote: Note
)
