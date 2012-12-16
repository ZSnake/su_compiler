package Ada95_Codegen;
import Ada95_Semantic.*;
import java.util.HashMap;
import java.util.Map;
public class FlatSymbolTable{
	HashMap<String, VariableSymbol> table;
	public FlatSymbolTable(){
		this.table=new HashMap<String, VariableSymbol>();
	}
	/**Hace un recorrido en profundidad de la tabla para aplanarla.*/
	public FlatSymbolTable(SymbolTable table){
		this.table=new HashMap<String, VariableSymbol>();
		pre_order_traverse(table);
	}
	/**Hace un recorrido en orden previo del árbol del cual la tabla v es raíz: no meter las funciones ni records!*/
	private void pre_order_traverse(SymbolTable v){		
		//agregar los símbolos de esta tabla a la tabla:
		VariableSymbol val=null;
		for(Map.Entry entry: v.getTable().entrySet()){
			val=(VariableSymbol)entry.getValue();
			String cle = v.getFlatId()+"."+entry.getKey();
			if(!(val.type instanceof FunctionType) && !this.table.containsKey(cle))
				this.table.put(cle, (VariableSymbol)entry.getValue());
		}
		//hacer el recorrido en preorden de los hijos:
		for(SymbolTable child: v.children)
			pre_order_traverse(child);
	}

	/**Representa la tabla*/
	public String toString(){
		StringBuilder s=new StringBuilder();
		for(Map.Entry entry: table.entrySet()){
			s.append(String.format("%s -> %s\n", entry.getKey(), entry.getValue()));
		}
		return s.toString();
	}

	/**Para obtener un valor: buscar en currentScope, y de ahí, en sus ancestros.*/
	public SymbolLookup get(String currentScope, String key){
		if (currentScope.isEmpty())
			return new SymbolLookup(0, this.table.get(key));
		String[] tokenized=currentScope.split("_");
		StringBuilder finder=new StringBuilder(currentScope);
		String lookup=currentScope;
		VariableSymbol found=new VariableSymbol();
		int q_depth=0;
		String l_key=lookup+"."+key;
		//ir en reversa
		for(int i=tokenized.length-1; i>=0; i--){
			if ((found=this.table.get(l_key)) != null)
				break;
			q_depth=tokenized.length-i;
			lookup=(i==0)? tokenized[i] : finder.substring(0, finder.lastIndexOf("_"+tokenized[i]));
			l_key=lookup+"."+key;
		}

		//en base a la profundidad de éste y del llamado, devolver el número de saltos:
		int saltos=q_depth;
		return new SymbolLookup(q_depth, found);	
	}
	
	/**Para poner algo en esta tabla*/
	public void put(String key, VariableSymbol sym){
		this.table.put(key, sym);
	}
	/**Para obtener la dirección de un símbolo*/
}
