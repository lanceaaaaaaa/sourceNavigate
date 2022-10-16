package com.luban.note.service;

import com.luban.note.model.NoteModel;

import java.util.List;

public interface NoteService {
    List<NoteModel> getNoteList(NoteModel noteModel);

    void updateNote(NoteModel noteModel);

    void addNote(NoteModel noteModel);

    void delNote(NoteModel noteModel);

    void saveAllNotes(List<NoteModel> nodeList);

    void updatePicPath(NoteModel noteModel);
}
