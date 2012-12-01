package Ada95_Semantic;

public class IntegerType extends Type{
    public IntegerType(){
        super();
        this.width = 4;
        this.name = "integer";
    }

    public IntegerType(String name){
        super();
        this.width = 4;
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof IntegerType)) {
            return false;
        }
        if(o == this) {
            return true;
        }
        return this.name.equalsIgnoreCase(((IntegerType)o).name);
    }
    
    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public boolean isNumeric(){
        return true;
    }

    @Override
    public boolean isDiscrete(){
        return true;
    }

    @Override
    public String toString(){
        String n = (name.equalsIgnoreCase("integer"))? "" : name+":";
        return n + "Integer";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
