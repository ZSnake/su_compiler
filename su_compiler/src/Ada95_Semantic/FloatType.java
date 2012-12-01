package Ada95_Semantic;

public class FloatType extends Type{
    public FloatType(){
            super();
            this.width = 4;
            this.name = "float";
    }

    public FloatType(String name){
            super();
            this.width = 4;
            this.name = name;
    }

    @Override
    public boolean equals(Object o){
            if(o == null || !(o instanceof FloatType)) {
                return false;
            }
            else if(o == this) {
                return true;
            }
            return this.name.equalsIgnoreCase(((FloatType)o).name);
    }

    @Override
    public boolean isPrimitive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNumeric(){
            return true;
    }

    @Override
    public boolean isDiscrete(){
            return false;
    }

    @Override
    public String toString(){
            String n = (name.equalsIgnoreCase("float"))? "" : name+":";
            return n + "Float";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}

