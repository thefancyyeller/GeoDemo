package Geonauts;

/**
 * Separate entry point that does not extend Application.
 * This allows IntelliJ to run/debug the app directly without
 * needing javafx:run or module-path VM arguments.
 */
public class Main {
    public static void main(String[] args) {
        Launcher.main(args);
    }
}
