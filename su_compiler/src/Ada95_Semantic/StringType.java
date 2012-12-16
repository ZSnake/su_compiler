package Ada95_Semantic;

public class StringType extends Type{
	 //String length
	 public StringType(int length){
                super();
                this.width = length;
        }

    @Override
        public boolean equals(Object o){
                if(o == null) {
                    return false;
                }
                if(!(o instanceof StringType)) {
                    return false;
                }
                if(o == this) {
                    return true;
                }
                return true;
        }
	
    @Override
	public boolean isNumeric(){
		return false;
	}
	
    @Override
	public boolean isDiscrete(){
		return false;
	}
        
    @Override
        public String toString(){
                return "String";
        }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
