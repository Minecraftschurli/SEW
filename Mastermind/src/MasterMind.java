import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Georg_Burkl
 * @version 2017-11-20
 */
public class MasterMind {

    /**
     * @param args
     */
    public static void main(@NotNull String[] args) {
        args = new String[]{"1v1", "colors=" + 6, "slots=" + 4, "tries=" + 12, "doubles=" + false};
        createGame(args[0], Arrays.copyOfRange(args, 1, args.length));
    }

    /**
     * @param colors
     * @param slots
     * @param tries
     */
    public static void create1v1Game(int colors, int slots, int tries) {
        MasterMindGUI.InputGUI inputGUI = new MasterMindGUI.InputGUI(colors, slots);
        MasterMindGame game = new MasterMindGame(colors, slots, tries, inputGUI.getAsIntArray(), true, false);
        game.bind(new MasterMindGUI(game));
    }

    /**
     * creates a standard game
     */
    public static void createStandardGame() {
        MasterMindGame game = new MasterMindGame(6, 4, 12, null, true, false);
        game.bind(new MasterMindGUI(game));
    }

    /**
     * creates a custom game
     *
     * @param colors
     * @param slots
     * @param tries
     */
    public static void createBotGame(int colors, int slots, int tries) {
        MasterMindGame game = new MasterMindGame(colors, slots, tries, null, true, false);
        game.bind(new MasterMindGUI(game));
    }

    /**
     * creates a custom game in debug-mode
     *
     * @param colors
     * @param slots
     * @param tries
     */
    private static void createDebugGame(int colors, int slots, int tries, int[] input, boolean preventDoubles) {
        MasterMindGame game = new MasterMindGame(colors, slots, tries, input, preventDoubles, true);
        game.bind(new MasterMindGUI(game));
    }

    /**
     * @param type
     * @param args
     */
    public static void createGame(String type, String... args) {
        try {
            System.out.println(type);
            Object[] startArgs = new Object[5];
            Iterator<String> iterator = new Iterator<>() {
                private int pos = 0;

                @Contract(pure = true)
                public boolean hasNext() {
                    return args.length > pos;
                }

                public String next() {
                    return args[pos++];
                }

                @Contract(" -> fail")
                public void remove() throws UnsupportedOperationException {
                    throw new UnsupportedOperationException("Cannot remove an element of an array.");
                }
            };
            while (iterator.hasNext()) {
                String arg = iterator.next();
                System.out.println(arg);
                String s = arg.substring(arg.lastIndexOf('=') + 1).trim();
                switch (arg.substring(0, arg.lastIndexOf('='))) {
                    case "colors":
                        startArgs[0] = Integer.parseInt(s);
                        break;
                    case "slots":
                        startArgs[1] = Integer.parseInt(s);
                        break;
                    case "tries":
                        startArgs[2] = Integer.parseInt(s);
                        break;
                    case "doubles":
                        startArgs[3] = api.Boolean.parseBoolean(s);
                        break;
                    case "pass":
                        startArgs[4] = s;
                        break;
                    default:
                        break;
                }
            }
            switch (type.toLowerCase()) {
                case "1v1":
                    create1v1Game((Integer) startArgs[0], (Integer) startArgs[1], (Integer) startArgs[2]);
                    break;
                case "standard":
                    createStandardGame();
                    break;
                case "custom":
                    createBotGame((Integer) startArgs[0], (Integer) startArgs[1], (Integer) startArgs[2]);
                    break;
                case "debug":
                    if (!startArgs[4].equals("1234")) return;
                    createDebugGame((Integer) startArgs[0], (Integer) startArgs[1], (Integer) startArgs[2], null, (Boolean) startArgs[3]);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
