package Ada95_Semantic;

import java.util.ArrayList;
import java.util.Objects;

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
		product = new ArrayList<>();
		for(VariableSymbol e: st.getTable().values()) {
                    product.add(e.type);
                }
		//this.product = product;
                for(Type t: this.product) {
                    this.width += t.getWidth();
                }
	}	
	
	public Type getComponentType(Object name){
		VariableSymbol component= this.symbolTable.get(name);
		if (component != null){
			return component.type;
                }
                else{
			return null;
                }
	}

    @Override
	public boolean equals(Object o){
		if(o == null){
			return false;
                }
		if(!(o instanceof RecordType)){
			return false;
                }
		if(o == this){
			return true;
                }
		RecordType temp = (RecordType) o;
		return this.name.equalsIgnoreCase(temp.name);
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
		return this.name;
	}
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.symbolTable);
        return hash;
    }
}
