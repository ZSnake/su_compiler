package Ada95_Codegen;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Reg extends TempReg{
        public Reg(){
		descriptor=new LinkedHashMap<>();
                String[] info=floatTemps.split("_");
                String prefix;
		prefix=info[0];
		int step;
                step = Integer.parseInt(info[1]);
                String[] bounds;
		bounds=info[2].split("-");                      
		for(int i=Integer.parseInt(bounds[0]);i<=Integer.parseInt(bounds[1]);i+=step){
			descriptor.put(String.format("%s%d",prefix,i), new HashSet<String>(1));
		}
	}
}
