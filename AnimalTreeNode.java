package animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalTreeNode {

    private String value;
    private AnimalTreeNode yesChild = null;
    private AnimalTreeNode noChild = null;

    public AnimalTreeNode(){}

    public AnimalTreeNode(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AnimalTreeNode getYesChild() {
        return this.yesChild;
    }

    public void setYesChild(AnimalTreeNode yesNode) {
        this.yesChild = yesNode;
    }

    public AnimalTreeNode getNoChild() {
        return this.noChild;
    }

    public void setNoChild(AnimalTreeNode noNode) {
        this.noChild = noNode;
    }
    @JsonIgnore
    public void insertYesChild(String value){
        setYesChild(new AnimalTreeNode(value));
    }
    @JsonIgnore
    public void insertNoChild(String value){
        setNoChild(new AnimalTreeNode(value));
    }
    @JsonIgnore
    public boolean isLeaf() {
        return (yesChild == null) && (noChild == null);
    }
}
