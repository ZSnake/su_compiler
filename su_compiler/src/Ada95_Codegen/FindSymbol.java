package Ada95_Codegen;

import Ada95_Semantic.VariableSymbol;

public class FindSymbol{
	public int jumps;
        
	public VariableSymbol symbol;

	public FindSymbol(){
		jumps=0;
		symbol=null;
	}

	public FindSymbol(int s, VariableSymbol sym){
		jumps=s;
		symbol=sym;
	}

    @Override
	public String toString(){
		return String.format("%d {%s}", jumps, symbol);
	}
} 
