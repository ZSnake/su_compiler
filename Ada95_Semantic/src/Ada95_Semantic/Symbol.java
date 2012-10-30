package Ada95_Semantic;

public class Symbol{
	//Tipo del simbolo
	public Type type;
	//Direccion en memoria relativa (hexadecimal)
	public long address;
	//Simbolo constante o no
	public boolean constant;
        
	public Symbol(){
		this.type = null;
		this.address = 0x0;
	}

	public Symbol(Type t){
		this.type = t;
		this.address = 0x0;
		this.constant = false;
	}
	
	public Symbol(Type t, boolean c){
		this.type = t;
		this.address = 0x0;
		this.constant = c;
	}

	public Symbol(Type t, long a){
		this.type = t;
		this.address = a;
	}
	
	public Symbol(Type t, boolean cons, String initialPlace){
		this.type = t;
		this.address = 0x0;
		this.constant = cons;
	}
    @Override
	public String toString(){
		String cons = (constant) ? "Constant ":"";
		return String.format("%s%s@%s",
					 cons, type, address);
	}
	
}
