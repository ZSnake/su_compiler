package Ada95_Intermediate;

import Ada95_Codegen.FlatSymbolTable;
import java.util.ArrayList;

/**Clase para el resultado del front end:
la lista de cuádruplos y el mapa de tablas de símbolos*/

public class FrontEndResult{
	public ArrayList<Quadruple> icode;
	public FlatSymbolTable table;
	
	public FrontEndResult(ArrayList<Quadruple> i, FlatSymbolTable st){
		icode=i;
		table=st;
	}
}
