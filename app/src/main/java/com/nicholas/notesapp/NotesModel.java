package com.nicholas.notesapp;

public class NotesModel {
    private String id;
    private String title;
    private String description;

    public NotesModel( ) {

    }

    public NotesModel(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static char getTxt_Ref() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
