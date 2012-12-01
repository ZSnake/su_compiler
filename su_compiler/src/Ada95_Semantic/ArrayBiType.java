package Ada95_Semantic;

public class ArrayBiType extends Type{
    private Type range;
    private int iUniIndex;
    private int fUniIndex;
    private int iBiIndex;
    private int fBiIndex;
    
    public ArrayBiType(){
            this.range = null;
            this.width = 0;
            this.iUniIndex = 0;
            this.fUniIndex = 0;
            this.iBiIndex = 0;
            this.fBiIndex = 0;
            this.name = "bidimensional array";
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
