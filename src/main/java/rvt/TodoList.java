package rvt;

import java.util.ArrayList;
public class TodoList {

    private ArrayList<String> tasks;
    private int index = 0;

    public TodoList() {
        tasks = new ArrayList<>();
    }
      public static void main(String[] args) {
 
      }

    public void add(String task){
        tasks.add(task);
        index++;
    }
    public void print(){
        for (int i = 0; i < index; i++){
            System.out.println((i + 1) + ":" + tasks.get(index));
        }
    }
    public void remove(int number){
        int removeIndex = number - 1;

        if (removeIndex  >= 0 && removeIndex > tasks.size()) {
           tasks.remove(removeIndex);
        index--;
    }

    }

}
