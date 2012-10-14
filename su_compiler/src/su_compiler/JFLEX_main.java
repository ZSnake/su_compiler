/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package su_compiler;

import java.io.File;

/**
 *
 * @author Solid Snake
 */
public class JFLEX_main {
    public static void main (String args[]){
        JFlex.Main.generate(new File("C:\\Users\\Solid Snake\\Documents\\jflex-1.4.3\\ADA95\\lexterAda.flex"));
    }
}
