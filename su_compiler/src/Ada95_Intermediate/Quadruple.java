package Ada95_Intermediate;

public class Quadruple {
    //Falta hacer esta onda que es para el codigo intermedio
    //Esta es una estructura de control con cuatro campos
    //Operador, argumento1, argumento2 y resultado
    //Se ocupa en el codigo intermedio
    private String operator, argument1, argument2, result;

    public Quadruple(String operator, String argument1, String argument2, String result) {
        this.operator = operator;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.result = result;
    }

    public Quadruple(String operator, String argument1, String result) {
        this.operator = operator;
        this.argument1 = argument1;
        this.result = result;
        this.argument2 = "";
    }

    public Quadruple(String operator, String result) {
        this.operator = operator;
        this.result = result;
        this.argument1 = "";
        this.argument2 = "";
    }

    public Quadruple(String operator){
        this.operator = operator;
        this.argument1 = "";
        this.argument2 = "";
        this.result = "";
    }
    public Quadruple() {
    }

    public String getArgument1() {
        return argument1;
    }

    public void setArgument1(String argument1) {
        this.argument1 = argument1;
    }

    public String getArgument2() {
        return argument2;
    }

    public void setArgument2(String argument2) {
        this.argument2 = argument2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    //"asig" es una asignacion
}
