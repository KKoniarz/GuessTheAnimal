package animals.resources;

import java.util.ListResourceBundle;
import java.util.function.UnaryOperator;

public class Animals_eo extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"article.indefinite",""},
                    {"article.definite","la "},
                    //region UI messages
                    {"greetings" , new String[]{
                            "Bonan matenon!",
                            "Bonan tagon!",
                            "Bonan vesperon!",
                            "Saluton Nokta Strigo!",
                            "Saluton Frua Birdo!"
                    }},
                    {"welcome", "Bonvenon al la sperta sistemo de la besto!"},
                    {"goodbye", new String[]{
                                            "Ĝis!",
                                            "Ĝis revido!",
                                            "Estis agrable vidi vin!"
                    }},
                    {"positiveAnswers", "(j|jes|certe)[.!]?"},
                    {"negativeAnswers", "(n|ne)[.!]?"},
                    {"menu.title", "Kion vi volas fari:"},
                    {"menu.exit", "Eliri"},
                    {"menu.error", "Bonvolu enigi numeron de 0 ĝis {0}"},
                    {"menu.items.play", "Ludi la divenludon"},
                    {"menu.items.list", "Listo de ĉiuj bestoj"},
                    {"menu.items.search", "Serĉi beston"},
                    {"menu.items.delete", "Forigi beston"},
                    {"menu.items.statistics", "Kalkuli statistikojn"},
                    {"menu.items.print", "Printi la Sciarbon"},
                    //endregion
                    //region animal
                    {"animal.wantLearn", "Mi volas lerni pri bestoj."},
                    {"animal.askFavourite", "Kiun beston vi plej ŝatas?"},
                    //{"animal.nice", new String[] {
                    //                            "Bela!",
                    //                            "Mirinde!",
                    //                            "Mojosa!",
                    //                            "Bonege!",
                    //                            "Mirinda!"
                    //}},
                    {"animal.learnedMuch", "\nMi lernis tiom multe pri bestoj!"},
                    {"animal.prompt", "Enigu la nomon de besto:"},
                    {"animal.name", (UnaryOperator<String>) name -> name.replaceFirst("^la ","")},
                    {"animal.error", "\nLa besto devas esti enmetita en la jenan formaton:\n" +
                            "nomo de la besto, ekzemple, \"elefanto\"."},
                    {"ask.again", new String[]{
                            "Bonvolu enigi jes aŭ ne.",
                            "Amuze, mi ankoraŭ ne komprenas, ĉu jes aŭ ne?",
                            "Pardonu, devas esti jes aŭ ne.",
                            "Ni provu denove: ĉu jes aŭ ne?",
                            "Pardonu, ĉu mi rajtas demandi vin denove: ĉu tio estis jes aŭ ne?"
                    }},
                    {"statement.prompt", "Indiku fakton, kiu distingas {0} de {1}.\n" +
                            "La frazo devas esti de la formato: 'Ĝi ...'."},
                    {"statement.error", "La ekzemploj de aserto:\n" +
                            "- Ĝi povas flugi\n" +
                            "- Ĝi havas kornojn\n" +
                            "- Ĝi estas mamulo"},
                    {"statement.pattern", "ĝi (povas|havas|estas|loĝas)\\b.+\\.?"},
                    //endregion
                    //region Game
                    {"game.letsPlay", "Ni ludu!"},
                    {"game.think", "Vi pensu pri besto, kaj mi divenos ĝin."},
                    {"game.enter", "Premu enen kiam vi pretas."},
                    {"game.win", new String[]{
                            "Bonege, ke mi trafis ĝin ĝuste!"
                    }},
                    {"game.giveUp", "Mi rezignas. Kiun beston vi havas en la kapo?"},
                    {"game.isCorrect", "Ĉu la aserto ĝustas por la {0}?"},
                    {"game.writeFact", "Ĝi"},
                    {"game.learned", "Mi lernis la jenajn faktojn pri bestoj:"},
                    //{"game.distinguish", "Mi povas distingi ĉi tiujn bestojn per la demando:"},
                    {"game.thanks", new String[]{
                            "Tio estis amuza!",
                            "Estis agrable ludi kun vi!",
                            "Dankon pro ludado!",
                            "Dankon! Mi tro amuziĝis!",
                    }},
                    {"game.again", new String[]{
                            "Ĉu vi volas provi denove?",
                            "Ĉu vi ŝatas ludi denove?",
                            "Ĉu vi volas ripeti?",
                            "Ĉu vi volas ludi denove?",
                            "Ankoraŭ unu ludo?",
                            "Ĉu vi volas ludi denove?"
                    }},
                    {"game.finalGuess", (UnaryOperator<String>) name -> "Ĉu ĝi estas " + name + "?"},
                    {"game.question", (UnaryOperator<String>) statement ->{
                        String questionMark = statement.replaceFirst("\\.","").toLowerCase();
                        return questionMark.replaceFirst("ĝi ","") + "?";
                    }},
                    //endregion
                    //region Tree
                    {"tree.list.animals", "Jen la bestoj, kiujn mi konas:"},
                    {"tree.list.print", ""},
                    {"tree.search.facts", "Faktoj pri la {0}:"},
                    {"tree.search.noFacts", "Neniuj faktoj pri la {0}."},
                    {"tree.search.parseToPositive", (UnaryOperator<String>) question -> {
                        String withDot = question.replaceFirst("\\?",".").toLowerCase();
                        return withDot.replaceFirst("", "Ĝi ");
                    }},
                    {"tree.search.parseToNegative", (UnaryOperator<String>) question ->{
                        String withDot = question.replaceFirst("\\?",".").toLowerCase();
                        return withDot.replaceFirst("", "Ĝi ne ");
                    }},
                    //{"tree.search.printf", "\\ - %s%n"},
                    {"tree.delete.root", "Ne eblas forigi la solan beston de la radiko."},
                    {"tree.delete.successful", "La {0} estis forigita de la sciobazo."},
                    {"tree.delete.fail", "La {0} ne troviĝis en la sciobazo."},
                    {"tree.stats.title", "La statistiko de la Scio-Arbo"},
                    {"tree.stats.root",       "- radika nodo                  {0}"},
                    {"tree.stats.nodes",      "- totala nombro de nodoj       {0,number,integer}"},
                    {"tree.stats.animals",    "- totala nombro de bestoj      {0,number,integer}"},
                    {"tree.stats.statements", "- totala nombro de deklaroj    {0,number,integer}"},
                    {"tree.stats.height",     "- alteco de la arbo            {0,number,integer}"},
                    {"tree.stats.minimum",    "- minimuma profundo            {0,number,integer}"},
                    {"tree.stats.average",    "- averaĝa profundo             {0,number,##0.0}"},
                    //{"tree.print.vertical", "│"},
                    //{"tree.print.branch", "├"},
                    //{"tree.print.corner", "└"},
                    //{"tree.print.printf", "%1$s%2$s %3$s%n"}
                    //endregion
            };
        }
}
