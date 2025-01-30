package com.example.todoapp.model;

public class Task {

    private String text;
    private boolean isDone;

    public Task(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public Task(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
