package animals;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UI {

    private  static final ResourceBundle resources = Main.getResources();
    private static final String[] GREETINGS = resources.getStringArray("greetings");
    private static final String WELCOME = resources.getString("welcome");
    private static final String[] GOODBYE = resources.getStringArray("goodbye");

    public static void greetings(){
        int currentHour = LocalTime.now().getHour();
        if(currentHour < 3){
            System.out.println(GREETINGS[3] + "\n");
        } else if (currentHour < 5) {
            System.out.println(GREETINGS[4] + "\n");
        } else if (currentHour < 12) {
            System.out.println(GREETINGS[0] + "\n");
        } else if (currentHour < 18) {
            System.out.println(GREETINGS[1] + "\n");
        } else {
            System.out.println(GREETINGS[2] + "\n");
        }
    }

    public static void welcome(){
        System.out.println(WELCOME);
    }

    public static void goodBye() {
        Random rand = new Random();
        System.out.println(GOODBYE[rand.nextInt(GOODBYE.length)]);
    }

    public static String  selectAction(){
        System.out.println(resources.getString("menu.title"));
        System.out.println("1. " + resources.getString("menu.items.play"));
        System.out.println("2. " + resources.getString("menu.items.list"));
        System.out.println("3. " + resources.getString("menu.items.search"));
        //System.out.println("4. " + resources.getString("menu.items.delete"));
        System.out.println("4. " + resources.getString("menu.items.statistics"));
        System.out.println("5. " + resources.getString("menu.items.print"));
        System.out.println("0. " + resources.getString("menu.exit"));
        return new Scanner(System.in).next();
        }
}
