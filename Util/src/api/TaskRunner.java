package api;

import java.util.LinkedList;
import java.util.List;

public class TaskRunner {

    private static final List<Task> tasksHigh = new LinkedList<>();
    private static final List<Task> tasksNormal = new LinkedList<>();
    private static final List<Task> tasksLow = new LinkedList<>();

    private TaskRunner(){}

    public static void main(String[] args) {
        while (true){
            runAllScheduledTasks();
        }
    }

    private static void runAllScheduledTasks() {
        for (Task task : tasksHigh){
            task.run();
            System.out.println(task.toString());
            tasksHigh.remove(task);
        }
        for (Task task : tasksNormal){
            task.run();
            System.out.println(task.toString());
            tasksNormal.remove(task);
        }
        for (Task task : tasksLow){
            task.run();
            System.out.println(task.toString());
            tasksLow.remove(task);
        }
    }

    public static void addScheduledTask(Task task){
        switch (task.priority){
            case LOW:
                tasksLow.add(task);
                break;
            case HIGH:
                tasksHigh.add(task);
                break;
            case NORMAL:
            default:
                tasksNormal.add(task);
                break;
        }
    }

    public abstract static class Task implements Runnable{
        public final Priority priority;

        public Task(Priority priority){
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "Task: "+super.toString()+" is now running at priority: "+priority.toString();
        }
    }

    public enum Priority{
        HIGH,
        NORMAL,
        LOW;

        @Override
        public String toString() {
            switch (this){
                case HIGH:
                    return "HIGH";
                case NORMAL:
                    return "NORMAL";
                case LOW:
                    return "LOW";
                default:
                    return "";
            }
        }
    }
}
