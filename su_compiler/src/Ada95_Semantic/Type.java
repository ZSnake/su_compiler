package Ada95_Semantic;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Type{
	// Tamano que se ocupa para el tipo especifico
	protected int width;
	// Productos representativos del mismo
	protected ArrayList<Type> product;
	// Nombre del tipo
	public String name;
	
	// Constantes de los tamanos
	public static final int INTEGER_WIDTH = 4;
	public static final int FLOAT_WIDTH = 4;
	public static final int BOOLEAN_WIDTH = 1;
	
	public Type(String name){
		this.product = new ArrayList<>();
		this.width = 0;
		this.name = name;
	}
	
	public Type(){
		this.product = new ArrayList<>();
		this.width = 0;
		this.name = "";
	}

	public Type(ArrayList<Type> type, int width){
		this.product = type;
		this.width = width;
		this.name = "";
	}
	
	public Type(Type type, int width){
		this.product = new ArrayList<>();
		this.product.add(type);
		this.width = width; 
		this.name = "";
	}
	
	public Type(Type type){
		this.product = new ArrayList<>();
		this.product.add(type);
		this.width = type.width;
		this.name = "";
	}
	
	public Type(ArrayList<Type> typeList){
		this.product = new ArrayList<>();
		this.product = typeList;
		this.name = "";
		for(Type type: typeList) {
                    this.width += type.getWidth();
                }
	}

	public Type(ArrayList<Type> type, int width, String name){
		this.product = type;
		this.width = width;
		this.name = name;
	}

	public Type(ArrayList<Type> type, String name){
		this.name = "";
		this.product = type;
		this.width = 0;
	}

	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return width;
	}
	
	public void setProduct(ArrayList<Type> product){
		this.product = product;
	}

	public void setProduct(Type type){
		this.product = new ArrayList<>();
		this.product.add(type);
	}
	
	public void addType(Type type){
		this.product.add(type);
	}

	public void removeType(Type type){
		this.product.remove(type);
	}

	public ArrayList<Type> getProduct(){
		return product;
	}
	
    @Override
	public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.width;
        hash = 67 * hash + Objects.hashCode(this.product);
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }
    @Override
	public abstract String toString();
	public abstract boolean isPrimitive();
	public abstract boolean isNumeric();
	public abstract boolean isDiscrete();	
}
