package Ada95_Semantic;

import java.util.ArrayList;

public class JumpTable{
	public ArrayList<Integer> table;
        
	public JumpTable(){
		this.table = new ArrayList<>();
	}
	public JumpTable(int index){
		table = new ArrayList<>();
		table.add(new Integer(index));
	}
        
	public static JumpTable mergeLists(JumpTable firstTable, 
                JumpTable secondTable){
                    JumpTable newTable = new JumpTable();
                    newTable.table.addAll(firstTable.table);		
                    newTable.table.addAll(secondTable.table);
                    return newTable;
	}
		
}
