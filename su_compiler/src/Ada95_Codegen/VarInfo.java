package Ada95_Codegen;

public class VarInfo{
	public boolean isAlive;
	public int nextUse;
	public static final int UNUSED=-1;
        
	public VarInfo(){
		isAlive=false;
		nextUse=UNUSED;
	}

	public VarInfo(boolean alive, int use){
		isAlive = alive;
		nextUse = use;
	}

    @Override
	public String toString(){
		String used=(nextUse == -1) ? " is never used" :" is used in " + String.valueOf(nextUse);
		return String.format("%s and %s", isAlive, used);
	}
}
