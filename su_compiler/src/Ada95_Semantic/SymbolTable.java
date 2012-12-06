package Ada95_Semantic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import su_compiler.*;

public class SymbolTable{
	//Tabla para el scope actual
	private LinkedHashMap<String, VariableSymbol> table;
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
	
	public LinkedHashMap<String, VariableSymbol> getTable(){
		return this.table;
	}	

	public boolean put(Object oid, VariableSymbol type){
		String soid;
                soid =oid.toString();
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
        
        public VariableSymbol get(Object oid){
		String soid=(String)oid;
		soid = soid.toLowerCase();
		VariableSymbol found = new VariableSymbol();
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
		return found;
	}

	public void addChild(SymbolTable child){
		this.children.add(child);
	}
        
        public void printTree(SymbolTable actual, int level){
            for (int i = 0; i <= level; i++) {
                System.out.print("-");
            }
            System.out.print(actual.id);
            String content = "{";
            Object keyset[] = actual.getTable().keySet().toArray();
            for (int i = 0; i < keyset.length; i++) {
                content += actual.getTable().get(keyset[i]).id + ", ";
            }
            content += "}";
            System.out.println(content);
            level++;
            for (int i = 0; i < actual.children.size(); i++) {
                printTree(actual.children.get(i), level);
            }
        }
        
        public SymbolTable findChild(String id){
            for (SymbolTable child : children) {
                if(child.id.equals(id))
                    return child;
            }
            return null;
        }
        
}
