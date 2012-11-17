package Ada95_Semantic;
import java.util.ArrayList;
import java.util.Objects;

public class FunctionType extends Type{
	//Rango del tipo de la funcion
	private Type range;
        
	public FunctionType(){
		this.range = null;
		this.width = 0;
		this.name = "function";
	}
		
	public FunctionType(Type range){
		super();
		this.range = range;
		this.width = 0;
		this.name = "function";
	}

	public FunctionType(Type range, ArrayList<Type> domain){
		this.product = domain;
		this.range = range;
		this.width = 0;
		this.name = "function";
	}

	public FunctionType(ArrayList<Type> domain){
		this.product = domain;
		this.width = 0;
		this.range = null;
		this.name = "function";
	}

	
	public Type getRange(){
		return this.range;
	}

	public void setRange(Type r){
		range = r;
	}

    @Override
        public boolean equals(Object o){
                if(o == null) {
                    return false;
                }
                if(!(o instanceof FunctionType)) {
                    return false;
                }
                if(o == this) {
                    return true;
                }
                FunctionType temp = (FunctionType) o;
                if (temp.getWidth() !=  this.width) {
                    return false;
                }
		if (!(temp.getRange().equals(this.range))) {
                    return false;
                }
                if(temp.getProduct().size() != this.product.size()) {
                    return false;
                }
                ArrayList<Type> tempProduct = temp.getProduct();
                for(int i = 0;i<this.product.size();i++) {
                    if(!(tempProduct.get(i).equals(this.product.get(i)))) {
                        return false;
                    }
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
	public boolean isPrimitive(){
		return false;
	}

    @Override
	public String toString(){
		return this.range.toString();
	}
		
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.range);
        return hash;
    }
}
