package Ada95_Semantic;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SymbolTable{
	//Tabla para el scope actual
	private LinkedHashMap<String, Symbol> table;
	//Scope inmediato foraneo
	public SymbolTable parent;
	//Direccion relativa
	public int relAddress;
	//Identificador de la tabla	
	public String id;
	//Hijos de la tabla actual
	public ArrayList<SymbolTable> children;

	public SymbolTable(SymbolTable parent){
		table = new LinkedHashMap<>();
		this.parent = parent;
		this.relAddress = parent.relAddress;
                this.id = "";
		this.children = new ArrayList<>();
	}
	
	public SymbolTable(SymbolTable parent, String id){
		table = new LinkedHashMap<>();
		this.parent = parent;
		this.relAddress = 0;
                this.id = id;
		this.children = new ArrayList<>();
	}

	public SymbolTable(){
		table = new LinkedHashMap<>();
		this.parent = null;
		this.relAddress = 0;	
		this.id = "";
		this.children = new ArrayList<>();
	}
	
	public SymbolTable(String id){
		table=new LinkedHashMap<>();
		this.parent = null;
		this.relAddress = 0;
                this.id = id;
		this.children = new ArrayList<>();
	}
	
	public SymbolTable getParent(){
		return this.parent;
	}
	
	public LinkedHashMap<String, Symbol> getTable(){
		return this.table;
	}	

	public boolean put(Object oid, Symbol type){
		String soid;
                soid =(String)oid;
		soid = soid.toLowerCase();
		if(!(this.table.containsKey(soid))){
			type.address = this.relAddress;
			relAddress += type.type.width;
			table.put(soid, type);
			return true;
		}
                else{
			return false;
		}
	}
        
}
