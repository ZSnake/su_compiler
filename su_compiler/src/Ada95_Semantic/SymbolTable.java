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
        
        public Symbol get(Object oid){
		String soid=(String)oid;
		soid = soid.toLowerCase();
		Symbol found = new Symbol();
		String [] idSplit = soid.split("\\.");
		for(SymbolTable t=this; t != null; t= t.getParent()){
			found=t.getTable().get(idSplit[0]);
			if(found != null){
				if(idSplit.length == 1) {
                                    return found;
                                }
				else {
                                    break;
                                }
			}
		}
		if (found == null) {	
			return null;
                }			
		if(!(found.type instanceof RecordType)) {
			return null;
                }	
		RecordType f;
		for(int i=1; i < idSplit.length-1; i++){
			f=(RecordType)found.type;		
			found=f.symbolTable.getTable().get(idSplit[i]);
			if(found != null){
				if(!(found.type instanceof RecordType)) {
					return null;
                                }
			}else{
				return null;
			}
			
		}
		f=(RecordType)found.type;
		found=f.symbolTable.getTable().get(idSplit[idSplit.length-1]);
		return found;
	}

	public void addChild(SymbolTable child){
		this.children.add(child);
	}
        
}
