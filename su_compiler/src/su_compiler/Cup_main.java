/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package su_compiler;

/**
 *
 * @author Solid Snake
 */
public class Cup_main {

    /**
     * @param args the command line arguments
 
 */
    public static void main(String[] args) {
        String opciones[] = new String[5];
        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";
        //Le damos la dirección
        opciones[1] = "C:\\Users\\Solid Snake\\Documents\\jflex-1.4.3\\ADA95";
        //Seleccionamos la opción de nombre de archivo
        opciones[2] = "-parser";
        //Le damos el nombre que queremos que tenga
        opciones[3] = "Analizador";
        //Le decimos donde se encuentra el archivo .cup
        opciones[4] = "C:\\Users\\Solid Snake\\Documents\\jflex-1.4.3\\ADA95\\parser.cup";
        try {
            java_cup.Main.main(opciones);
        } catch (Exception e) {
            System.out.print(e);
        }   

    }
}
