package animals;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static KnowledgeBase knowledgeBase;
    private static boolean running = true;
    private static ResourceBundle resources;
    private static String fileName;

     public static void main(String[] args) {
         initLang();
         knowledgeBase = new KnowledgeBase(fileName, args.length != 2 ? "json" : args[1]);
         Game game = new Game(knowledgeBase.getKnowledgeTree());

         UI.greetings();
         if(knowledgeBase.isEmpty()){
            game.askForFavouriteAnimal();
         }
         UI.welcome();
         while(running){
             switch (UI.selectAction()) {
                 case "1":
                     game.start();
                     knowledgeBase.saveKnowledgeBase();
                     break;
                 case "2":
                     System.out.println(resources.getString("tree.list.animals"));
                     knowledgeBase.printAnimalList();
                     break;
                 case "3":
                     System.out.println(resources.getString("animal.prompt"));
                     String animalSearch = game.getAnimalFromPlayerInput();
                     knowledgeBase.printAnimalInformation(animalSearch);
                     break;
                 //case "4":
                 //    System.out.println(resources.getString("animal.prompt"));
                 //    String animalDelete = game.getAnimalFromPlayerInput();
                 //    knowledgeBase.deleteAnimal(animalDelete);
                 //    break;
                 case "4":
                     knowledgeBase.printAnimalStatistics();
                     break;
                 case "5":
                     knowledgeBase.printKnowledgeTree();
                     break;
                 case "0":
                     running = false;
                     break;
                 default:
                     System.out.println(MessageFormat.format(resources.getString("menu.error"), 5));
             }
         }
         UI.goodBye();
    }

    private static void initLang(){
         switch(Locale.getDefault().getLanguage()){
             case "eo":
                 resources = ResourceBundle.getBundle("animals.resources.Animals_eo");
                 fileName = "animals_eo";
                 break;
             default:
                 resources = ResourceBundle.getBundle("animals.resources.Animals_en");
                 fileName = "animals";
                 break;
         }
    }

    public static ResourceBundle getResources(){
         return resources;
    }
}
