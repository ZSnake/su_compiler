/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablasimbolos;

/**
 *
 * @author Solid Snake
 */
public class TablaSimbolos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Node_symbolTable root = new Node_symbolTable("raiz", null, "type");
        Node_symbolTable hijo1 = new Node_symbolTable("hijo 1 raiz", root, "type 324");
        Node_symbolTable hijo2 = new Node_symbolTable("hijo 2 raiz", root, "type 5");
        Node_symbolTable hijo3 = new Node_symbolTable("hijo 3 raiz", root, "type 1");
        root.children.add(hijo1);
        root.children.add(hijo2);
        root.children.add(hijo3);
        Node_symbolTable hijo11 = new Node_symbolTable("hijo 1 hijo 1", hijo1, "type different");
        hijo1.children.add(hijo11);
        Tree_symbolTable t = new Tree_symbolTable(root);
        t.recorrerArbol();
        System.out.println(t.searchByName("hijo 1 hijo 1", t.getRoot()).getType());
        System.out.println(t.containsElement(hijo1, hijo11));
    }
}
