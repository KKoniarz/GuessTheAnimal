package animals.resources;

import java.util.ListResourceBundle;
import java.util.function.UnaryOperator;

public class Animals_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"article.indefinite","an?"},
                {"article.definite","the"},
                //region UI messages
                {"greetings" , new String[]{
                        "Good morning!",
                        "Good afternoon!",
                        "Good evening",
                        "Hi Night Owl!",
                        "Hi Early Bird"
                }},
                {"welcome", "Welcome to the animal’s expert system!"},
                {"goodbye", new String[]{
                                        "Bye!",
                                        "Bye, bye!",
                                        "See you later!",
                                        "See you soon!",
                                        "Talk to you later!",
                                        "I’m off!",
                                        "It was nice seeing you!",
                                        "See ya!",
                                        "See you later, alligator!",
                                        "In a while, crocodile!",
                                        "Hasta la vista, baby!",
                                        "Adios, amigos!",
                                        "Au revoir!",
                                        "Adieu!",
                                        "Have a nice one!"
                }},
                {"positiveAnswers", "(y|yes|yeah|yep|sure|right|affirmative|correct|indeed|you bet|exactly|you said it)[.!]?"},
                {"negativeAnswers", "(n|no( way)?|nah|nope|negative|i don't think so|yeah no)[.!]?"},

                {"menu.title", "\nWhat do you want to do:"},
                {"menu.exit", "Exit"},
                {"menu.error", "Please enter the number from 0 up to {0}"},
                {"menu.items.play", "Play the guessing game"},
                {"menu.items.list", "List of all animals"},
                {"menu.items.search", "Search for an animal"},
                {"menu.items.delete", "Delete an animal"},
                {"menu.items.statistics", "Calculate statistics"},
                {"menu.items.print", "Print the Knowledge Tree"},
                //endregion
                //region animal
                {"animal.wantLearn", "I want to learn about animals."},
                {"animal.askFavourite", "Which animal do you like most?"},
                //{"animal.nice", new String[] {
                //                            "Nice!",
                //                            "Great!",
                //                            "Wonderful!",
                //                            "Awesome!",
                //                            "Excellent!",
                //                            "Marvelous!"
                //}},
                {"animal.learnedMuch", "\nI've learned so much about animals!"},
                {"animal.prompt", "Enter the animal:"},
                {"animal.name", (UnaryOperator<String>) name -> {
                    String nameWithoutArticles = name.replaceFirst("^(the )|(an? )","");
                   if(nameWithoutArticles.matches("[aeiou].*")) {
                       return "an " + nameWithoutArticles;
                   } else {
                       return "a " + nameWithoutArticles;
                   }
                }},
                {"animal.error", "\nThe animal should be entered in the following format:\n" +
                                 "a/an + name of the animal, for example, “an elephant”."},
                {"ask.again", new String[]{
                                            "Come on, yes or no?",
                                            "Please enter yes or no.",
                                            "Funny, I still don’t understand, is it yes or no?",
                                            "Sorry, it must be yes or no.",
                                            "Let’s try again: yes or no?",
                                            "I’m not sure I caught you: was it yes or no?",
                                            "Oh, it’s too complicated for me: just say me yes or no.",
                                            "I’m filling a bit intrigued: just say yes or no.",
                                            "I am a bit confused, give me a simple answer: yes or no",
                                            "Oh, no, don’t try to confuse me: say yes or no.",
                                            "Could you please simply say yes or no?",
                                            "Sorry, may I ask you again: was it yes or no?",
                }},
                {"statement.prompt", "Specify a fact that distinguishes {0} from {1}.\n" +
                                     "The sentence should be of the format: 'It can/has/is ...'."},
                {"statement.error", "The examples of a statement:\n" +
                                    "- It can fly\n" +
                                    "- It has horns\n" +
                                    "- It is a mammal"},
                {"statement.pattern", "it (can|has|is)\\b.+\\.?"},
                //endregion
                //region Game
                {"game.letsPlay", "Let's play a game!"},
                {"game.think", "You think of an animal, and I guess it."},
                {"game.enter", "Press enter when you’re ready."},
                {"game.win", new String[]{
                        "It's great that I got it right!",
                        "I didn’t even hope that it would be possible to guess!"
                }},
                {"game.giveUp", "I give up. What animal do you have in mind?"},
                {"game.isCorrect", "Is the statement correct for {0}"},
                {"game.writeFact", "It"},
                {"game.learned", "I have learned so much about animals!"},
                //{"game.distinguish", "I can distinguish these animals by asking the question:"},
                {"game.thanks", new String[]{
                        "That was funny!",
                        "It was nice to play with you!",
                        "It was a pleasure for me to know you better!",
                        "Thank you for playing!",
                        "Thank you! I had too much fun!"
                }},
                {"game.again", new String[]{
                        "Want to try again?",
                        "Would you like to play again?",
                        "Do you want to repeat?",
                        "Want to play again?",
                        "One more game?",
                        "Do you want to play again?"
                }},
                {"game.finalGuess", (UnaryOperator<String>) name -> "Is it " + name + "?"},
                {"game.question", (UnaryOperator<String>) statement -> {
                    String noDot = statement.replaceFirst("\\.","").toLowerCase();
                    if(noDot.matches("it has (.+)")){
                        return noDot.replaceFirst("it has (.+)", "does it have $1?");
                    }
                    return noDot.replaceFirst("it (can|is) (.+)", "$1 it $2?");
                    //}
                }},
                //endregion
                //region Tree
                {"tree.list.animals", "Here are the animals (facts) I know:"},
                {"tree.list.print", "an?"},
                {"tree.search.facts", "Facts about the{0}:"},
                {"tree.search.noFacts", "No facts about the{0}."},
                {"tree.search.parseToPositive", (UnaryOperator<String>) question -> {
                    String withDot = question.replaceFirst("\\?",".").toLowerCase();
                    if(withDot.matches("does it have (.+)")){
                        return withDot.replaceFirst("does it have (.+)", "It has $1");
                    } else if (withDot.matches("can it (.+)")) {
                        return withDot.replaceFirst("can it (.+)", "It can $1");
                    } else {
                        return withDot.replaceFirst("is it (.+)", "It is $1");
                    }
                }},
                {"tree.search.parseToNegative", (UnaryOperator<String>) question ->{
                    String withDot = question.replaceFirst("\\?",".").toLowerCase();
                    if(withDot.matches("does it have (.+)")){
                        return withDot.replaceFirst("does it have (.+)", "It doesn't have $1");
                    } else if (withDot.matches("can it (.+)")) {
                        return withDot.replaceFirst("can it (.+)", "It can't $1");
                    } else {
                        return withDot.replaceFirst("is it (.+)", "It isn't $1");
                    }
                }},
                //{"tree.search.printf", "\\ - %s%n"},
                {"tree.delete.root", "Can’t delete the only animal from the root."},
                {"tree.delete.successful", "The {0} was deleted from the knowledge base."},
                {"tree.delete.fail", "The {0} was not found in the knowledge base."},
                {"tree.stats.title", "The Knowledge Tree stats"},
                {"tree.stats.root",       "- root node                    {0}"},
                {"tree.stats.nodes",      "- total number of nodes        {0,number,integer}"},
                {"tree.stats.animals",    "- total number of animals      {0,number,integer}"},
                {"tree.stats.statements", "- total number of statements   {0,number,integer}"},
                {"tree.stats.height",     "- height of the tree           {0,number,integer}"},
                {"tree.stats.minimum",    "- minimum depth                {0,number,integer}"},
                {"tree.stats.average",    "- average depth                {0,number,##0.0}"},
                //{"tree.print.vertical", "│"},
                //{"tree.print.branch", "├"},
                //{"tree.print.corner", "└"},
                //{"tree.print.printf", "%1$s%2$s %3$s%n"}
                //endregion
        };
    }
}
