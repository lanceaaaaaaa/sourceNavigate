package com.luban.note.service.impl;

import com.luban.note.dao.NoteDao;
import com.luban.note.model.NoteModel;
import com.luban.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;

    @Override
    public List<NoteModel> getNoteList(NoteModel noteModel) {
        return noteDao.getNoteList(noteModel);
    }

    @Override
    public void updateNote(NoteModel noteModel) {
        noteDao.updateNote(noteModel);
    }

    @Override
    public void addNote(NoteModel noteModel) {
        Integer maxNoteId = noteDao.getMaxNoteId();
        noteModel.setId(UUID.randomUUID().toString().replace("-",""));
        noteModel.setNoteId("n"+(maxNoteId+1));
        noteModel.setContentEleId("div"+(maxNoteId+1));
        noteDao.addNote(noteModel);
    }

    @Override
    public void delNote(NoteModel noteModel) {
        noteDao.delNote(noteModel.getNoteId());
    }

    @Override
    public void saveAllNotes(List<NoteModel> nodeList) {
        NoteModel selectModel = new NoteModel();
        selectModel.setRootId(nodeList.get(0).getRootId());
        List<NoteModel> dataNoteList = noteDao.getNoteList(selectModel);
        List<NoteModel> updateNoteList = new ArrayList<>();
        for (NoteModel dataNoteModel : dataNoteList) {
            for (NoteModel sendNoteModel : nodeList) {
                    if(sendNoteModel.getNoteId().equals(dataNoteModel.getNoteId())){
                        if((sendNoteModel.getNotex() != dataNoteModel.getNotex()) || (sendNoteModel.getNotey() != dataNoteModel.getNotey())){
                            updateNoteList.add(sendNoteModel);
                        }
                    }
            }
        }
        if(updateNoteList != null && updateNoteList.size() > 0){
            noteDao.updateBatchNote(updateNoteList);
        }
    }
}
