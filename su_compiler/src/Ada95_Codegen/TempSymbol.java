package Ada95_Codegen;

import java.util.HashSet;

public class TempSymbol{
	public VarInfo info;
	public HashSet<String> hashUsageDescription;
	public TempSymbol(VarInfo i){
		this.info=i;
		this.hashUsageDescription=new HashSet<>();
	}
	public TempSymbol(){
		this.info=new VarInfo();
		this.hashUsageDescription=new HashSet<>();
	}

    @Override
	public String toString(){
		return String.format("%s in %s", info, hashUsageDescription);
	}
}
