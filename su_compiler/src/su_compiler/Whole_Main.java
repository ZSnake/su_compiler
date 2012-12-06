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
        AnalizadorSymbolTable alst;
        AnalizadorSemantic als;
        try {
            lexer l = new lexer(new FileInputStream("C:\\Users\\Solid Snake\\Documents\\EntregaProyecto_10911203_10911246\\su_compiler\\Material Extra\\trying.txt"));
            lexerSymbolTable lst = new lexerSymbolTable(new FileInputStream("C:\\Users\\Solid Snake\\Documents\\EntregaProyecto_10911203_10911246\\su_compiler\\Material Extra\\trying.txt"));
            lexerSemantic ls = new lexerSemantic(new FileInputStream("C:\\Users\\Solid Snake\\Documents\\EntregaProyecto_10911203_10911246\\su_compiler\\Material Extra\\trying.txt"));
            al = new Analizador(l);
            alst = new AnalizadorSymbolTable(lst);
            als = new AnalizadorSemantic(ls);
            al.parse();
            alst.parse();
            try{
                System.out.println(alst.action_obj.currentScope);
            }catch(Exception e){
                System.out.println("null stuff");
            }
            als.scope = alst.action_obj.currentScope;
            System.out.println("end parsing symbol table");
            als.parse();
            System.out.println("----------------------------");
            System.out.println(al.imprimirErrores());
            
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }  
    }
}


