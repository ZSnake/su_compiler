package Ada95_Codegen;

import Ada95_Semantic.VariableSymbol;

public class SymbolLookup{
	public int saltos;
	public VariableSymbol symbol;

	public SymbolLookup(){
		saltos=0;
		symbol=null;
	}

	public SymbolLookup(int s, VariableSymbol sym){
		saltos=s;
		symbol=sym;
	}

    @Override
	public String toString(){
		return String.format("%d {%s}", saltos, symbol);
	}

} 
