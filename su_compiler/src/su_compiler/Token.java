package su_compiler;




import java.util.Set;
import java_cup.runtime.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author Marco
 */
public class Token {
    
    private int id;
    private String identificador;
    private int fila;
    private int columna;

    
    public Token()
    {
        this(0,"",0,0);
    }
    
    public Token(int id, String ident, int fila, int columna)
    {
        this.setId(id);
        this.setIdentificador(ident);
        this.setFila(fila);
        this.setColumna(columna);
        
    }
    
     /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String toString(){
        return id + ".-" + identificador;
    }
}
