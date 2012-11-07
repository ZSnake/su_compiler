package Ada95_Semantic;

import java.util.ArrayList;

public class ErrorType extends PrimitiveType{
	public ErrorType(Type expectedType){
		this.product = new ArrayList<>();
		this.product.add(expectedType);
		this.width = 0;
	
	}
	
	public ErrorType(){
		this.product = null;
		this.width = 0;
	}

    @Override
	public boolean equals(Object o){
		if(o == null) {
                    return false;
                }
		if(o == this) {
                    return true;
                }
		if(!(o instanceof Type)) {
                    return false;
                }
		if(this.product.isEmpty()) {
                    return false;
                }
		if(!(this.product.get(0).toString().equals(o.toString()))) {
                    return false;
                }
		else {
                    return true;
                }
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
		// A ver que le ponemos de salida xD
                return "";
	}

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}

