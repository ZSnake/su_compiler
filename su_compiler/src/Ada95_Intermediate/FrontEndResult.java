package Ada95_Intermediate;

import Ada95_Codegen.FlatSymbolTable;
import java.util.ArrayList;

public class FrontEndResult{
	public ArrayList<Quadruple> icode;
	public FlatSymbolTable table;
	
	public FrontEndResult(ArrayList<Quadruple> i, FlatSymbolTable st){
		icode=i;
		table=st;
	}
}
