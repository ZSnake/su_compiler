package Ada95_Semantic;

public class BackPatchResult{
	public JumpTable next;
	public JumpTable trueJump;
	public JumpTable falseJump;

	public BackPatchResult(){
		next = new JumpTable();
		trueJump = new JumpTable();
		falseJump = new JumpTable();
	}

	public BackPatchResult(JumpTable next){
		this.next = next;
		this.trueJump = new JumpTable();
		this.falseJump = new JumpTable();
	}

	public BackPatchResult(JumpTable trueJump, JumpTable falseJump){
		this.next = new JumpTable();
		this.trueJump = trueJump;
		this.falseJump = falseJump;
	}
	
	public BackPatchResult(JumpTable next, JumpTable trueJump, JumpTable falseJump){
		this.next = next;
		this.trueJump = trueJump;
		this.falseJump = falseJump;
	}
}
