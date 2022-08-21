package animals;

import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Game {

    AnimalTreeNode tree;
    AnimalTreeNode currentNode;
    private final Scanner scn;
    private final ResourceBundle resources = Main.getResources();
    private final UnaryOperator<String> finalGuess;
    private final UnaryOperator<String> getDistinctionQuestion;

    public Game(AnimalTreeNode tree) {
        this.tree = tree;
        scn = new Scanner(System.in);
        this.finalGuess = (UnaryOperator<String>) resources.getObject("game.finalGuess");
        this.getDistinctionQuestion = (UnaryOperator<String>) resources.getObject("game.question");
    }

    public void askForFavouriteAnimal(){
        System.out.println(resources.getString("animal.askFavourite"));
        tree.setValue(getAnimalFromPlayerInput());
    }

    public String getAnimalFromPlayerInput() {
            UnaryOperator<String> processName = (UnaryOperator<String>) resources.getObject("animal.name");
            return processName.apply(scn.nextLine().toLowerCase());
    }

    private boolean getYesNoAnswer() {
      while(true){
          String answer = scn.nextLine().toLowerCase();
          if(answer.matches(resources.getString("positiveAnswers"))) return true;
          if(answer.matches(resources.getString("negativeAnswers"))) return false;
          clarify();
      }
    }

    private void clarify() {
        Random rand = new Random();
        String[] askAgain = resources.getStringArray("ask.again");
        System.out.println(askAgain[rand.nextInt(askAgain.length)]);
    }

    //Pretty nifty recursive method for the whole guessing/tree-traversing stuff imo :)
    //You pass the root as the argument and if it's a leaf
    //(which in this case means that it is a name of the animal),
    //program asks user about the animal and returns if the guess was right or wrong
    //if the node is not leaf then node's value is a question
    //program asks the question and depending on the answer,
    //method is invoked recursively for the yes or the no child
    //in effect we traverse the whole tree all the way to the leaf when we get definitive answer
    //and the whole thing collapses
    //endNode stores the reference to the node at which the function collapsed, so we know where
    //to add next question and animal pair in case the program guessed incorrectly

    private boolean guess(AnimalTreeNode node){
        if(node.isLeaf()){
            currentNode = node;
            if(node.getValue().equals("")){
                return false;
            }
            System.out.println(finalGuess.apply(node.getValue()));
            return getYesNoAnswer();
        } else {
            System.out.println(node.getValue());
            if(getYesNoAnswer()){
                return guess(node.getYesChild());
            } else {
                return guess(node.getNoChild());
            }
        }
    }

    //-----------------------------------------------------------------------------------------

    public void start(){
        boolean keepPlaying = true;
        while (keepPlaying) {
            System.out.println(resources.getString("game.think") + "\n" +
                               resources.getString("game.enter"));

            scn.nextLine();

            if(guess(tree)){
                guessedCorrectly();
            } else {
                guessedIncorrectly();
            }
            keepPlaying = askToContinue();
        }
        String[] thanks = resources.getStringArray("game.thanks");
        System.out.println(thanks[new Random().nextInt(thanks.length)]);
    }

    public String getStatement(String firstAnimal, String secondAnimal) {
        boolean validStatement;
        String statement;
        do {
            System.out.println(MessageFormat.format(resources.getString("statement.prompt"),
                                                    firstAnimal, secondAnimal));
            statement = scn.nextLine().toLowerCase();
            validStatement = statement.matches(resources.getString("statement.pattern"));
            if(!validStatement){
                System.out.println(resources.getString("statement.error"));
            }
        } while (!validStatement);
        return statement;
    }

    public boolean askAboutDistinction(String secondAnimal){
        System.out.println(MessageFormat.format(resources.getString("game.isCorrect"),secondAnimal));
        return getYesNoAnswer();
        //System.out.println(resources.getString("game.learned"));
        //return isFactTrueForTheSecondAnimal;
    }

    private void guessedCorrectly() {
        String[] winMessage = resources.getStringArray("game.win");
        System.out.println(winMessage[new Random().nextInt(winMessage.length)]);
    }

    private void guessedIncorrectly(){
        System.out.println(resources.getString("game.giveUp"));
        String newAnimal = getAnimalFromPlayerInput();
        if(currentNode.getValue().equals("")) {
            currentNode.setValue(newAnimal);
            System.out.println(resources.getString("game.learned"));
            return;
        }
        String statement = getStatement(currentNode.getValue(), newAnimal);
        boolean isFactTrueForNewAnimal = askAboutDistinction(newAnimal);
        String distinctionQuestion = getDistinctionQuestion.apply(statement);
        String factAboutNewAnimal;
        String factAboutOldAnimal;
        if(isFactTrueForNewAnimal){
            currentNode.insertYesChild(newAnimal);
            currentNode.insertNoChild(currentNode.getValue());
            factAboutNewAnimal = parseQuestionToPositiveSentence(distinctionQuestion)
                    .replaceFirst(resources.getString("game.writeFact"),
                            newAnimal.replaceFirst(
                                    resources.getString("article.indefinite"),
                                    resources.getString("article.definite")));
            factAboutOldAnimal = parseQuestionToNegativeSentence(distinctionQuestion)
                    .replaceFirst(resources.getString("game.writeFact"),
                            currentNode.getValue().replaceFirst(
                                    resources.getString("article.indefinite"),
                                    resources.getString("article.definite")));
        } else {
            currentNode.insertNoChild(newAnimal);
            currentNode.insertYesChild(currentNode.getValue());
            factAboutNewAnimal = parseQuestionToNegativeSentence(distinctionQuestion)
                    .replaceFirst(resources.getString("game.writeFact"),
                                  newAnimal.replaceFirst(
                                                         resources.getString("article.indefinite"),
                                                         resources.getString("article.definite")));
            factAboutOldAnimal = parseQuestionToPositiveSentence(distinctionQuestion)
                    .replaceFirst(resources.getString("game.writeFact"),
                            currentNode.getValue().replaceFirst(
                                    resources.getString("article.indefinite"),
                                    resources.getString("article.definite")));
        }
        currentNode.setValue(distinctionQuestion);
        System.out.println(factAboutNewAnimal);
        System.out.println(factAboutOldAnimal);
        System.out.println(resources.getString("game.learned"));
    }

    private String parseQuestionToPositiveSentence(String question){
        UnaryOperator<String> parse = (UnaryOperator<String>) resources.getObject("tree.search.parseToPositive");
        return parse.apply(question);
    }

    private String parseQuestionToNegativeSentence(String question){
        UnaryOperator<String> parse = (UnaryOperator<String>) resources.getObject("tree.search.parseToNegative");
        return parse.apply(question);
    }

    private boolean askToContinue(){
        String[] again = resources.getStringArray("game.again");
        System.out.println(again[new Random().nextInt(again.length)]);
        return getYesNoAnswer();
    }
}
