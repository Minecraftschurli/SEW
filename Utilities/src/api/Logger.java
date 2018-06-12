package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author georg
 *
 */
public class Logger {

    private static final CustomHashMap<String, Logger> REGISTERED_LOGGERS = new CustomHashMap<>();
	
	private boolean cleared;
    public static final Logger DEFAULT_LOGGER = registerNewLogger("DEFAULT");
    private final ArrayList<Exception> loggedExceptions = new ArrayList<>();
    private boolean clearable;
    private String name;

    private Logger(String name, boolean clearable) {
        this.clearable = clearable;
        this.name = name;
    }

    private Logger(String name) {
        this(name, true);
    }

    public static Logger registerNewLogger(String name, boolean clearable) throws Exception {
        synchronized (REGISTERED_LOGGERS) {
            if (REGISTERED_LOGGERS.containsKey(name))
                throw new Exception("Logger for name " + name + " already Exists!");
            return REGISTERED_LOGGERS.put(name, new Logger(name, clearable));
        }
    }

    static Logger registerNewLogger(String name) {
        synchronized (REGISTERED_LOGGERS) {
            return REGISTERED_LOGGERS.put(name, new Logger(name, false));
        }
    }
	
	public void log(Exception e) {
        synchronized (REGISTERED_LOGGERS) {
            Logger l = REGISTERED_LOGGERS.get(this.name);
            l.loggedExceptions.add(e);
        }
	}

    public List<Exception> getLog() {
        synchronized (REGISTERED_LOGGERS) {
            Logger l = REGISTERED_LOGGERS.get(this.name);
            return l.loggedExceptions;
        }
    }

	public void clearLog(){
        synchronized (REGISTERED_LOGGERS) {
            Logger l = REGISTERED_LOGGERS.get(this.name);
            if (l.clearable) {
                l.loggedExceptions.clear();
                l.cleared = true;
                System.out.println("The Logger has been cleared!");
            }
        }
    }

	public void dispLog(){
        synchronized (REGISTERED_LOGGERS) {
            Logger l = REGISTERED_LOGGERS.get(this.name);
            if (!l.loggedExceptions.isEmpty()) {
                if (l.cleared) System.err.println("Logger has been cleared before!");
                System.err.println("--------Logger:Start--------");
                System.err.println(l.toString());
                System.err.println("--------Logger:End----------");
            } else {
                if (!l.cleared) System.err.println("Logger is empty! No Exceptions occurred.");
                else
                    System.err.println("Logger is empty! No Exceptions occurred since the last time the Logger has been cleared.");
            }
        }
    }

    @Override
    public String toString() {
        synchronized (REGISTERED_LOGGERS) {
            Logger l = REGISTERED_LOGGERS.get(this.name);
            StringBuilder sb = new StringBuilder();
            l.loggedExceptions.forEach(e -> sb.append(e).append("\n"));
            return sb.toString();
        }
    }

    static class Helper {
        public synchronized static Map<String, Logger> getLogger() {
            return REGISTERED_LOGGERS;
        }
    }
}
