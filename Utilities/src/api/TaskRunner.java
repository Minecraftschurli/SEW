package api;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskRunner {

    private static final List<ITask> tasksHigh = new LinkedList<>();
    private static final List<ITask> tasksNormal = new LinkedList<>();
    private static final List<ITask> tasksLow = new LinkedList<>();
    public static final Timer timer = new Timer();

    public static final TaskRunner INSTANCE = new TaskRunner();

    private TaskRunner() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runAllScheduledTasks();
            }
        },0,1);
    }

    private static void runAllScheduledTasks() {
        for (Runnable task : tasksHigh){
            task.run();
            System.out.println(task.toString());
            tasksHigh.remove(task);
        }
        for (Runnable task : tasksNormal){
            task.run();
            System.out.println(task.toString());
            tasksNormal.remove(task);
        }
        for (Runnable task : tasksLow){
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

    public static void addScheduledTask(ITask task){
        TaskRunner.addScheduledTask(new TaskRunner.Task(TaskRunner.Priority.NORMAL) {
            @Override
            public void run() {
                task.run();
            }
        });
    }

    public interface ITask extends Runnable {}

    public abstract static class Task implements ITask {
        public final Priority priority;

        public Task(Priority priority){
            this.priority = priority;
        }

        public Task(){
            this.priority = Priority.NORMAL;
        }

        @Override
        public String toString() {
            return "Task: "+super.toString()+" is now running at priority: "+priority.toString();
        }
    }

    public enum Priority {
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
