import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Yappa {

    public static final String LINE = "____________________________________________________________";
    public static final String LOGO = "__   __                    \n"
            + "\\ \\ / /_ _ _ __  _ __  __ _ \n"
            + " \\ V / _` | '_ \\| '_ \\/ _` |\n"
            + "  | | (_| | |_) | |_) | (_| |\n"
            + "  |_|\\__,_| .__/| .__/ \\__,_|\n"
            + "          |_|   |_|          \n";

    public static List<String> inputList = new ArrayList<>(100);

    public static void main(String[] args) {
        printGreeting();
        inputAndEcho();
        printMessage("\t Catch you later :)!");
    }

    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Good " + getTimeOfDay() + "! I'm Yappa. Ready to yap and get stuff done!");
        System.out.println("What are we tackling today? Let's do this!");
        System.out.println(LINE);
    }

    private static void printList() {
        System.out.println(LINE);
        for (int i = 0; i < inputList.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + inputList.get(i));
        }
        System.out.println(LINE);
    }

    private static void printMessage(String text) {
        System.out.println(LINE);
        System.out.println(text);
        System.out.println(LINE);
    }

    public static String getTimeOfDay() {
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        String period;

        if (currentHour >= 12 && currentHour < 17) {
            period = "Afternoon";
        } else if (currentHour >= 17 && currentHour < 21) {
            period = "Evening";
        } else {
            period = "Morning";
        }

        return period;
    }

    public static void inputAndEcho() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                input = input.trim();

                if (input.equalsIgnoreCase("bye")) {
                    break;
                }

                if (input.equalsIgnoreCase("list")) {
                    printList();
                } else {
                    inputList.add(input);
                    printMessage("\tadded: " + input);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }
}
