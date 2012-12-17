package Ada95_Codegen;

public class Block{
	public String label;
	public int start;
	public int finish;
	public static final String defLabel="label";
        
	public Block(){
		this.label="";
		this.start=0;
		this.finish=0;
	}

	public Block(String l, int b, int e){
		this.label=l;
		this.start=b;
		this.finish=e;
	}

	public Block(String l, int b){
		this.label=l;
		this.start=b;		
	}
	public String toString(){
		return String.format("%s, start: %d, finish: %d", this.label, this.start, this.finish);
	}
}
