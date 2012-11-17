/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablasimbolos;

/**
 *
 * @author Solid Snake
 */
public class Tree_symbolTable {
    Node_symbolTable root;

    public Tree_symbolTable(Node_symbolTable raiz) {
        this.root = raiz;
    }

    public Node_symbolTable getRoot() {
        return root;
    }

    public void setRoot(Node_symbolTable raiz) {
        this.root = raiz;
    }
    
    public void recorrerArbol(){
        recorrido(root, 0);
    }

    public void recorrido(Node_symbolTable actual, int level){
        for (int i = 0; i <= level; i++) {
            System.out.print("-");
        }
        System.out.println(actual.type);
        
        for (int i = 0; i < actual.children.size(); i++) {
            recorrido(actual.children.get(i), level++);
        }
    }
    
    public boolean containsElement(Node_symbolTable nodoActual, Node_symbolTable nodoBuscado){
        if(nodoActual.children.contains(nodoBuscado))
            return true;
        
        if(!nodoActual.children.isEmpty()){
            for (int i = 0; i < 10; i++) {
                if(containsElement(nodoActual.children.get(i),  nodoBuscado))
                    return true;
            }
        }
        return false;
    }
    
    public Node_symbolTable searchByName(String name, Node_symbolTable nodoActual){
        if(nodoActual.name.equals(name))
            return nodoActual;
        if(!nodoActual.children.isEmpty())
            for(int i = 0; i < nodoActual.children.size(); i++){
                Node_symbolTable temp = searchByName(name, nodoActual.children.get(i));
                if(temp != null)
                    return temp; 
            }
        return null;
    }
    
}
