package Ada95_Semantic;

public class BooleanType extends PrimitiveType{
        public BooleanType(){
                super();
                this.width = Type.BOOLEAN_WIDTH;
		this.name = "boolean";
        }
        
	public BooleanType(String name){
                super();
    	        this.width = Type.BOOLEAN_WIDTH;
		this.name = name;
        }

    @Override
        public boolean equals(Object o){
                if(o == null) {
                    return false;
                }
                if(!(o instanceof BooleanType)) {
                    return false;
                }
                if(o == this) {
                    return true;
                }	
                return this.name.equalsIgnoreCase(((BooleanType)o).name);
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

