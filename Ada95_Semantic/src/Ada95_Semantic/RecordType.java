package Ada95_Semantic;

import java.util.ArrayList;

public class RecordType extends Type{
	public SymbolTable symbolTable;
	public RecordType(){
		super();
		symbolTable = new SymbolTable();
		name = "";
	}
	public RecordType(String name, SymbolTable st){
		this.name = name;
		this.symbolTable = st;
		ArrayList<Type> product = new ArrayList<Type>();
		for(Symbol e: st.getTable().values())
			product.add(e.type);
		this.product = product;
                for(Type t: this.product)
                        this.width += t.getWidth();
	}	
	
	public Type getComponentType(Object name){
		Symbol component= this.symbolTable.get(name);
		if (component != null)
			return component.type;
		else
			return null;
	}

	public String toString(){
		return this.name;
	}

	public boolean isPrimitive(){
		return false;
	}

	public boolean equals(Object o){
		if(o == null)
			return false;
		if(!(o instanceof RecordType))
			return false;
		if(o == this)
			return true;
		RecordType temp = (RecordType) o;
		return this.name.equalsIgnoreCase(temp.name);
	}
	
	public boolean isNumeric(){
		return false;
	}

	public boolean isDiscrete(){
		return false;
	}
}
