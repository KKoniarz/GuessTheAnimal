package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.UnaryOperator;

public class KnowledgeBase {

    private final ObjectMapper objectMapper;
    private AnimalTreeNode knowledgeTree;
    private final String fileName;
    private final ResourceBundle resources;

    public KnowledgeBase(){
        objectMapper = new JsonMapper();
        knowledgeTree = new AnimalTreeNode("");
        fileName = "animals.json";
        this.resources = Main.getResources();
    }
    public KnowledgeBase(String fileName, String extension){
        switch (extension){
            case "xml":
                this.fileName = fileName + ".xml";
                objectMapper = new XmlMapper();
                break;
            case "yaml":
                this.fileName = fileName + ".yaml";
                objectMapper = new YAMLMapper();
                break;
            default:
                this.fileName = fileName +".json";
                objectMapper = new JsonMapper();
                break;
        }
        loadKnowledgeBase();
        this.resources = Main.getResources();
    }

    public AnimalTreeNode getKnowledgeTree(){
        return knowledgeTree;
    }

    private void loadKnowledgeBase(){
        try {
            File tree = new File(fileName);
            if(tree.exists()){
                knowledgeTree = objectMapper.readValue(tree, AnimalTreeNode.class);
            } else {
                knowledgeTree = new AnimalTreeNode("");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveKnowledgeBase(){
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), knowledgeTree);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isEmpty(){
        return knowledgeTree.getValue().equals("");
    }

    //region Printing animal list
    public List<String> getAnimalList(){
        List<String> animalList = new ArrayList<>();
        addAnimalsToList(animalList, knowledgeTree);
        return animalList;
    }

    private void addAnimalsToList(List<String> list, AnimalTreeNode node){
        if(node.isLeaf()) {
            list.add(node.getValue());
        } else {
          addAnimalsToList(list, node.getNoChild());
          addAnimalsToList(list, node.getYesChild());
        }
    }

    public void printAnimalList(){
        List<String> animalList = getAnimalList();
        String[] animalArray = animalList.toArray(new String[0]);
        Arrays.sort(animalArray);
        for(String animal : animalArray){
            System.out.println(animal.replaceFirst(resources.getString("tree.list.print"), " - "));
        }
    }
    //endregion
    //region Delete animal
    public void deleteAnimal (String animal) {
        if(knowledgeTree.getValue().equals(animal)) {
            System.out.println(resources.getString("tree.delete.root"));
            return;
        }
        if(deleteAnimalSearch(knowledgeTree, animal)){
            System.out.println(MessageFormat.format(resources.getString("tree.delete.successful"), animal));
        } else {
            System.out.println(MessageFormat.format(resources.getString("tree.delete.fail"), animal));
        }
    }
    private boolean deleteAnimalSearch(AnimalTreeNode node, String animal){
        if(node.getYesChild() != null){
            if(deleteAnimalSearch(node.getYesChild(), animal)) {
                return true;
            }
        }
        if(node.getNoChild() != null){
            if(deleteAnimalSearch(node.getNoChild(),animal)){
                return true;
            }
        }
        boolean found = node.getValue().equals(animal);
        if(found) {
            node.setValue("");
        }
        return found;
    }
    //endregion
    //region Printing animal information
    private List<String> getAnimalInformation(String animal){
        List<String> animalInformation = new ArrayList<>();
        boolean success = traverseTree(knowledgeTree, animal, animalInformation);
        return success ? animalInformation : null;
    }

    private boolean traverseTree(AnimalTreeNode node, String animal, List<String> animalInformation){
        if(node.getYesChild() != null){
            if(traverseTree(node.getYesChild(),animal, animalInformation)) {
                animalInformation.add(parseQuestionToPositiveSentence(node.getValue()));
                return true;
            }
        }
        if(node.getNoChild() != null){
            if(traverseTree(node.getNoChild(),animal, animalInformation)){
                animalInformation.add(parseQuestionToNegativeSentence(node.getValue()));
                return true;
            }
        }
        return node.getValue().equals(animal);
    }

    public void printAnimalInformation(String animal){
        List<String> animalInformation = getAnimalInformation(animal);
        if(animalInformation != null){
            System.out.println(MessageFormat.format(resources.getString("tree.search.facts"),animal.replaceFirst(resources.getString("article.indefinite"), "")));
            Collections.reverse(animalInformation);
            for(String information : animalInformation){
                System.out.println(information);
            }
        } else {
            System.out.println(MessageFormat.format(resources.getString("tree.search.noFacts"),animal.replaceFirst(resources.getString("article.indefinite"), "")));
        }
    }

    private String parseQuestionToPositiveSentence(String question){
        UnaryOperator<String> parse = (UnaryOperator<String>) resources.getObject("tree.search.parseToPositive");
        return parse.apply(question);
    }

    private String parseQuestionToNegativeSentence(String question){
        UnaryOperator<String> parse = (UnaryOperator<String>) resources.getObject("tree.search.parseToNegative");
        return parse.apply(question);
    }
    //endregion

    //region Printing animal statistics
    private int numberOfAnimals;
    private int numberOfStatements;
    private List<Integer> depths;
    private List<Integer> animalDepths;

    public void printAnimalStatistics(){
        String rootNode = parseQuestionToPositiveSentence(knowledgeTree.getValue());
        numberOfAnimals = 0;
        numberOfStatements = 0;
        depths = new ArrayList<>();
        animalDepths = new ArrayList<>();
        getTreeStatistics(knowledgeTree, 0);

        int totalNumberOfNodes = numberOfAnimals + numberOfStatements;
        int heightOfTheTree = depths.stream().max(Integer::compare).get();
        int minimumAnimalDepth = animalDepths.stream().min(Integer::compare).get();
        double averageAnimalDepth = animalDepths.stream().mapToDouble(x -> (double)x).average().getAsDouble();
        System.out.println(resources.getString("tree.stats.title"));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.root"), rootNode));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.nodes"), totalNumberOfNodes));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.animals"), numberOfAnimals));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.statements"), numberOfStatements));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.height"), heightOfTheTree));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.minimum"), minimumAnimalDepth));
        System.out.println(MessageFormat.format(resources.getString("tree.stats.average"), averageAnimalDepth));
    }
    private void getTreeStatistics(AnimalTreeNode node, int currentDepth){
        if(node.isLeaf()){
            numberOfAnimals++;
            depths.add(currentDepth);
            animalDepths.add(currentDepth);
        } else {
            numberOfStatements++;
            depths.add(currentDepth);
            getTreeStatistics(node.getYesChild(), currentDepth + 1);
            getTreeStatistics(node.getNoChild(), currentDepth + 1);
        }
    }
    //endregion

    //region printing tree
    PrintWriter writer;
    public void printKnowledgeTree() {
        writer = new PrintWriter(System.out, true);
        knowledgeTreePrinter("", knowledgeTree, true);
    }

    public void knowledgeTreePrinter(String prefix, AnimalTreeNode node, boolean isTail) {
        String nodeName = node.getValue().replace(" - ", "");
        String nodeConnection = isTail ? "└ " : "├ ";
        writer.println(prefix + nodeConnection + nodeName);
        if(!node.isLeaf()){
            String newPrefix = prefix + (isTail ? " " : "│ ");
            knowledgeTreePrinter(newPrefix, node.getYesChild(), false);
            knowledgeTreePrinter(newPrefix, node.getNoChild(), true);
        }
    }
    //endregion
}
