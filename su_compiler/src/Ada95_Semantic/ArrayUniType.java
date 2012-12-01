package Ada95_Semantic;

public class ArrayUniType extends Type{
    private Type range;
    private int iUniIndex;
    private int fUniIndex;
    
    public ArrayUniType(){
            this.range = null;
            this.width = 0;
            this.iUniIndex = 0;
            this.fUniIndex = 0;
            this.name = "array";
    }
    
    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
}
