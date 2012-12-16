package Ada95_Codegen;

public class BasicBlock{
	public String label;
	public int beginning;
	public int end;
	public static final String defaultPrefix="label";
        
	public BasicBlock(){
		this.label="";
		this.beginning=0;
		this.end=0;
	}

	public BasicBlock(String l, int b, int e){
		this.label=l;
		this.beginning=b;
		this.end=e;
	}

	public BasicBlock(String l, int b){
		this.label=l;
		this.beginning=b;		
	}
	public String toString(){
		return String.format("%s: [%d - %d]", this.label, this.beginning, this.end);
	}
}
