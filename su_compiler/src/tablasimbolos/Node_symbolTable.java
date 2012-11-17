/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablasimbolos;

import java.util.ArrayList;

/**
 *
 * @author Solid Snake
 */
public class Node_symbolTable {
    String name;
    String type;
    Node_symbolTable parent;
    ArrayList<Node_symbolTable> children;
    public Node_symbolTable(String name, Node_symbolTable parent, String type) {
        this.type = type;
        this.parent = parent;
        this.name = name;
        this.children = new ArrayList();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node_symbolTable getParent() {
        return parent;
    }

    public void setParent(Node_symbolTable padre) {
        this.parent = padre;
    }
    
    public void addChildren(Node_symbolTable hijo){
        children.add(hijo);
    }
    
    public void removeChildren(int index){
        children.remove(index);
    }
    
    
    
    
    
    
    
}
