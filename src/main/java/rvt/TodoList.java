package rvt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TodoList {

    private ArrayList<String> tasks;
    private final String filePath = "data/todo.csv";

    private int index = 0;
    private int nextId = 1;
    public TodoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
        
    }
    private void loadFromFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    this.tasks.add(line);
                    index++;
                    String[] parts = line.split(",");
                    int currentId = Integer.parseInt(parts[0]);
                    if (currentId >= nextId) {
                        nextId = currentId + 1;
                    }
                    
                }
            }
            getLastId();
        } catch (IOException e) {
            System.out.println("Error reading file.");
            }
    }

    private boolean updateFile(){
        return false;
    }

    private int getLastId(){
        int lastId = index;
        System.out.println(lastId);
        return lastId;
    }

    public void add(String taskName) {
        String formattedTask = nextId + "," + taskName;

        this.tasks.add(formattedTask);

        saveTaskToFile(formattedTask);

        nextId++;
    }

    private void saveTaskToFile(String taskLine){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(taskLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public void printLastId(){
        int lastId = index;
        System.out.println(lastId);
    }

    public void print() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i));
        }
    }

    public void remove(int number) {
        int removeId = number - 1;

        if (removeId >= 0 && removeId < tasks.size()) {
            tasks.remove(removeId);
            rewriteFile();
            nextId -= 1;
        }
    }

    private void rewriteFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String task : tasks) {
                bw.write(task);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file.");
        }
    }

}