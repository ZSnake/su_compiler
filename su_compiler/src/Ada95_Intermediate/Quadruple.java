package Ada95_Intermediate;

import Ada95_Codegen.VarInfo;
import java.util.HashMap;

public class Quadruple {
	public String operador;
	public String arg1;
	public String arg2;
	public String res;	
	public HashMap<String, VarInfo> info;	
	private void initInfo(){
		this.info=new HashMap<>();
		info.put("arg1", new VarInfo());
		info.put("arg2", new VarInfo());
		info.put("res", new VarInfo());
	}

	public Quadruple(){
		operador=null;
		arg1=null;
		arg2=null;
		res=null;
		initInfo();
	}
	public Quadruple( String operador,  String arg1,  String arg2,  String res){
		this.operador=operador;
		this.arg1=arg1;
		this.arg2=arg2;
		this.res=res;
		initInfo();
	}	
	/*Para operadores unarios*/
	public Quadruple(String operador, String arg1,  String res){
		this.operador=operador;
		this.arg1=arg1;
		this.arg2=null;
		this.res=res;
		initInfo();
	}	
	/*Para sentencias como goto*/
	public Quadruple( String operador,  String res){
		this.operador=operador;
		this.arg1=null;
		this.arg2=null;
		this.res=res;
		initInfo();
	}	
    @Override
	public String  toString(){
		String r=(!res.isEmpty()) ? res+":=" : "";
		String a1=(!arg1.isEmpty()) ? arg1+" " : "";
		String o= (!operador.isEmpty()) ? operador+ " " : "";
		/*Para que el if se mire pinta: */
		if(operador.contains("if")) {
                    return operador.split("_")[0]+" "+arg1+" "+operador.split("_")[1]+" "+ arg2+" goto "+res;
                }
		else if(operador.contains(":=")) {
                    return res+" := "+ arg1;
                }
		else if(operador.equals("goto")) {
                    return "goto "+res;
                }
		else if(operador.matches("put|call|param|glbl|function|return|initFunction")) {
                    return operador+" "+ arg1+" "+arg2;
                }
		else if(operador.equals("get")) {
                    return res+":= get "+arg1;
                }
		return r+a1+o+arg2;
	}
}
