package com.luban.note.dao;

import com.luban.note.model.NoteModel;

import java.util.List;

public interface NoteDao {
    List<NoteModel> getNoteList(NoteModel noteModel);
    
    Integer getMaxNoteId();

    void updateNote(NoteModel noteModel);

    void addNote(NoteModel noteModel);

    void delNote(String noteId);

    void updateBatchNote(List<NoteModel> updateNoteList);

    void updatePicPath(NoteModel noteModel);
}
