package com.luban.note.model;

import java.io.Serializable;

public class NoteModel implements Serializable {

    private String id;
    private String noteId;
    private String noteContent;
    private String noteFrom;
    private double notex;
    private double notey;
    private String sourceId;
    private String targetId;
    private String rootId;
    private String contentEleId;
    private String queryContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteFrom() {
        return noteFrom;
    }

    public void setNoteFrom(String noteFrom) {
        this.noteFrom = noteFrom;
    }

    public double getNotex() {
        return notex;
    }

    public void setNotex(double notex) {
        this.notex = notex;
    }

    public double getNotey() {
        return notey;
    }

    public void setNotey(double notey) {
        this.notey = notey;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getContentEleId() {
        return contentEleId;
    }

    public void setContentEleId(String contentEleId) {
        this.contentEleId = contentEleId;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }
}
