package Ada95_Codegen;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class TempReg{
	public String temps="$t_1_0-9";
	public String savedTemps="$s_1_0-7";
	public String floatTemps="$f_2_0-31";
	public LinkedHashMap<String, HashSet<String>> descriptor;

	public TempReg(){

		descriptor=new LinkedHashMap<>();
		String[] t_info=temps.split("_");
		String[] s_info=savedTemps.split("_");
		String[][] meta={t_info, s_info};
		int step;
		String[] bounds;
		String prefix;
		for(String[] info: meta ){
			prefix=info[0];
			step=Integer.parseInt(info[1]);
			bounds=info[2].split("-");			
			for(int i=Integer.parseInt(bounds[0]);i<=Integer.parseInt(bounds[1]);i+=step){
				descriptor.put(String.format("%s%d",prefix,i), new HashSet<String>());
			}
		}
	}

	public HashSet<String> get(String key){
		return descriptor.get(key);
	}

	public void update(String key, String var){
		descriptor.get(key).add(var);
	}

    @Override
	public String toString(){
		StringBuilder s=new StringBuilder();
		for(Map.Entry entry:descriptor.entrySet()){
			s.append(String.format("%s:\t%s\n",entry.getKey(),entry.getValue()));
		}
		return s.toString();
	}
	
	public String getEmpty(){
		HashSet<String> value;
		for(Map.Entry entry: descriptor.entrySet()){
			value=(HashSet<String>)entry.getValue();
			if(value.isEmpty()) {
                            return entry.getKey().toString();
                        }
		}
		return null;
	}
        
	public String getEmpty(HashSet<String> discarded){
		LinkedHashSet<String> difference=new LinkedHashSet<>(this.descriptor.keySet());
		difference.removeAll(discarded);
		HashSet<String> value;
		//sólo buscar entre las que aún no han sido descartadas:
		for(String key: difference){
			value=this.descriptor.get(key);
			if(value.isEmpty()) {
                            return key;
                        }
		}
		return null;
	}
}
