package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NotesRepository
import kotlin.jvm.Throws

class AddNoteUseCase (
    private val repository: NotesRepository
){
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the can't be empty.")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the can't be empty.")

        }
        repository.insertNote(note)
    }
}