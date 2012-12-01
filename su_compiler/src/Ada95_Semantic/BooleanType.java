package Ada95_Semantic;

public class BooleanType extends Type{
    public BooleanType(){
            super();
            this.width = 1;
            this.name = "boolean";
    }

    public BooleanType(String name){
            super();
            this.width = 1;
            this.name = name;
    }

    @Override
    public boolean equals(Object o){
            if(o == null || !(o instanceof BooleanType)) {
                return false;
            }
            else if(o == this) {
                return true;
            }
            return this.name.equalsIgnoreCase(((BooleanType)o).name);
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public boolean isNumeric(){
            return false;
    }

    @Override
    public boolean isDiscrete(){
            return true;
    }

    @Override
    public String toString(){
            String n = (name.equalsIgnoreCase("boolean"))? "" : name+":";
            return n + "Boolean";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}

