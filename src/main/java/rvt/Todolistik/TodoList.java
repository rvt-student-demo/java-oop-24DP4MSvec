package rvt.Todolistik;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

    private final TodoDB db = new TodoDB();
    private List<TodoItem> tasks;

    public TodoList() {
        loadFromDatabase();
    }

    public boolean checkEventStrings(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9āčēģīķļņšūžĀČĒĢĪĶĻŅŠŪŽ ]*$");
    }

    private void loadFromDatabase() {
        this.tasks = new ArrayList<>(db.findAll());
    }

    public List<TodoItem> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public boolean removeById(int id) {
        boolean removed = db.removeById(id);
        if (removed) {
            loadFromDatabase();
        }
        return removed;
    }

    public void add(String taskName) {
        if (!checkEventStrings(taskName)) {
            System.out.println("Kļūda: Aktivitatei jāsatur tikai burti/cipari un jābūt vismaz 3 simbolus garai!");
            return;
        }

        db.add(taskName);
        loadFromDatabase();
    }

    public void printLastId() {
        System.out.println(tasks.size());
    }

    public void print() {
        for (TodoItem task : tasks) {
            System.out.println(task);
        }
    }
}
