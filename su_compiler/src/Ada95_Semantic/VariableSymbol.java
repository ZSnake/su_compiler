package Ada95_Semantic;

import static Ada95_Codegen.VarInfo.UNUSED;
import java.util.HashSet;

public class VariableSymbol{
	//Tipo del simbolo
	public Type type;
	//Direccion en memoria relativa (hexadecimal)
	public long address;
	//Simbolo constante o no
	public boolean constant;
        //Descriptor de Acceso
        public HashSet<String> accessDescriptor;
	//Esta "Vivo"?
	public boolean isAlive;
	//Siguiente uso
	public int nextUse;

        private void postData(){
		this.isAlive = false;
                this.accessDescriptor = new HashSet<String>();
		this.nextUse = 0;
	}
        
	public VariableSymbol(){
		this.type = null;
		this.address = 0x0;
                postData();
	}

	public VariableSymbol(Type t){
		this.type = t;
		this.address = 0x0;
		this.constant = false;
                postData();
	}
	
	public VariableSymbol(Type t, boolean c){
		this.type = t;
		this.address = 0x0;
		this.constant = c;
                postData();
	}

	public VariableSymbol(Type t, long a){
		this.type = t;
		this.address = a;
                postData();
	}
	
	public VariableSymbol(Type t, boolean cons, String initialPlace){
		this.type = t;
		this.address = 0x0;
		this.constant = cons;
                this.isAlive = false;
		this.nextUse = UNUSED;
		this.accessDescriptor = new HashSet<String>();
		this.accessDescriptor.add(initialPlace);
	}
    @Override
	public String toString(){
		return String.format("type: %s, address: %s, alive: %s, next use: %d",
					 type, address, isAlive, nextUse);
	}
    
        
	
}
