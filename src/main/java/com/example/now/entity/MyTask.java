package com.example.now.entity;

public class MyTask {
    private Task task;

    private int finished;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public MyTask() {
    }

    public MyTask(Task task) {
        this.task = task;
    }

    public MyTask(Task task, int finished) {
        this.task = task;
        this.finished = finished;
    }
}
