/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package su_compiler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Solid Snake
 */
public class Whole_Main {
    public static void main (String args[]){
        //new File("C:\\Users\\Solid Snake\\Documents\\jflex-1.4.3\\ADA95\\try.txt"))
        Analizador al;
        try {
            al = new Analizador(new lexer(new FileInputStream("C:\\Users\\Solid Snake\\Desktop\\trying.txt")));
            al.parse();
            System.out.println(al.imprimirErrores());
            
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
}


