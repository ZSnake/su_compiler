package Ada95_Intermediate;

import Ada95_Codegen.SequentialSymbolTable;
import java.util.ArrayList;

public class FrontEndResult{
	public ArrayList<Quadruple> quads;
	public SequentialSymbolTable sst;
	
	public FrontEndResult(ArrayList<Quadruple> q, SequentialSymbolTable st){
		quads=q;
		sst=st;
	}
}
