package Ada95_Codegen;

import Ada95_Semantic.*;
import java.util.HashMap;
import java.util.Map;

public class SequentialSymbolTable{
    
	HashMap<String, VariableSymbol> table;
        
	public SequentialSymbolTable(){
		this.table=new HashMap<>();
	}
        
	public SequentialSymbolTable(SymbolTable table){
		this.table=new HashMap<>();
		Reorder(table);
	}
        
	private void Reorder(SymbolTable v){
		VariableSymbol val;
		for(Map.Entry entry: v.getTable().entrySet()){
			val=(VariableSymbol)entry.getValue();
			String cle = v.getFlatId()+"."+entry.getKey();
			if(!(val.type instanceof FunctionType) && !this.table.containsKey(cle)) {
                            this.table.put(cle, (VariableSymbol)entry.getValue());
                        }
		}
		for(SymbolTable child: v.children) {
                    Reorder(child);
                }
	}

    @Override
	public String toString(){
		StringBuilder s=new StringBuilder();
		for(Map.Entry entry: table.entrySet()){
			s.append(String.format("%s -> %s\n", entry.getKey(), entry.getValue()));
		}
		return s.toString();
	}

	public FindSymbol get(String currentScope, String key){
		if (currentScope.isEmpty())
                {
			return new FindSymbol(0, this.table.get(key));
                }
		String[] tokenized=currentScope.split("_");
		StringBuilder finder=new StringBuilder(currentScope);
		String lookup=currentScope;
		VariableSymbol found=new VariableSymbol();
		int depth=0;
		String l_key=lookup+"."+key;
		for(int i=tokenized.length-1; i>=0; i--){
			if ((found=this.table.get(l_key)) != null) {
                            break;
                        }
			depth=tokenized.length-i;
			lookup=(i==0)? tokenized[i] : finder.substring(0, finder.lastIndexOf("_"+tokenized[i]));
			l_key=lookup+"."+key;
		}
		return new FindSymbol(depth, found);
	}
	
	public void put(String key, VariableSymbol sym){
		this.table.put(key, sym);
	}
}
