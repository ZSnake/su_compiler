package Ada95_Codegen;

import static Ada95_Codegen.VarInfo.UNUSED;
import Ada95_Intermediate.*;
import Ada95_Semantic.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Stack;

public class ParserCodegen{
	public ArrayList<Quadruple> icode;
	public FlatSymbolTable st;
	
	private final static String DECLARERS="function|glbl";
	private final static String BEGINNERS="initFunction|call";
	private final static String JUMPS="if.*|goto";
	private final static String ENDERS="exit|glblExit";
	private final static String ERASABLES="function";
	private final static String FE_TEMP="%t[0-9]+";
	private final static String PSEUDOINSTRUCTIONS="initFunction|exit|call";
	private final static String INTEGER_LITERAL="[0-9]+";
	private final static String FLOAT_LITERAL="[0-9]+\\.[0-9]+";
	private final static String STRING_LITERAL="\".*\"";
	private final static String CONSTANT=String.format("%s|%s|%s", INTEGER_LITERAL, FLOAT_LITERAL, STRING_LITERAL);
	private final static String NOT_VAR=String.format("integer|string|float|boolean|%s", CONSTANT);
	private final static String NOT_REGISTRABLE=String.format("integer|string|float|boolean|%s", STRING_LITERAL);
	private final static String REGISTER="\\$[atsf][0-9]+";
	private final static String OPERATOR="if.*|add|sub|neg|not|abs|mul|div|mod|rem|put|get";
	private final static String THREEWAY_OPERATOR="add|sub|mul|div";
	private final static String TWINE_OPERATOR="neg|not|rem|abs";
	private final static String PROLOGUED="initFunction";
	private final static String EPILOGUED="exit";
	private final static String IN_STACK="-?([0-9])?+\\(\\$[sf]p\\)";
	private final static String COPY=":=";
	public final static int  WORD_LENGTH=4;

	/*MAPAS DE OPERACIONES:*/
	public HashMap<String, String> BRANCH_OPERATIONS;
	public HashMap<String, String> SYSTEM_SERVICES;
	/*TODO: AQUI FIJO VAN MÁS COSAS*/

	/*Lo que ocupa internamente*/
	private int dataMessages;
	private ArrayList<BasicBlock> basicBlocks;
	private StringBuilder data;
	private StringBuilder text;
	//contiene los temporales y el siguiente uso de los mismos. 
	private HashMap<String, TempSymbol> frontEndTemps;
	//los descriptores de registros:
	private Reg floatDescriptor;
	private TempReg   regDescriptor;
	/**El constructor, recibe del front-end las cosas y del main si debuggear*/
	public ParserCodegen(ArrayList<Quadruple> i, FlatSymbolTable t, boolean dbg){
		data=new StringBuilder("\t.data\n");
		text=new StringBuilder("\t.text\n");
		this.basicBlocks=new ArrayList<>();
		frontEndTemps=new HashMap<>();
		floatDescriptor=new Reg();
		regDescriptor=new TempReg();
		dataMessages=0;
		icode=i;
		st=t;
		initMaps();
		this.icode = reorderCode(this.icode);
		findBasicBlocks(this.icode);
		getNextUse();
	}
	private void initMaps(){
		BRANCH_OPERATIONS=new HashMap<>();
		BRANCH_OPERATIONS.put("==", "beq");
		BRANCH_OPERATIONS.put("/=", "bneq");
		BRANCH_OPERATIONS.put("<", "blt");
		BRANCH_OPERATIONS.put(">", "bgt");
		BRANCH_OPERATIONS.put("<=", "ble");
		BRANCH_OPERATIONS.put(">=", "bge");

		SYSTEM_SERVICES=new HashMap<>();
		SYSTEM_SERVICES.put("put_integer", "1");
		SYSTEM_SERVICES.put("put_boolean", "1");
		SYSTEM_SERVICES.put("put_float", "2");
		SYSTEM_SERVICES.put("put_double", "3");
		SYSTEM_SERVICES.put("put_string", "4");
		SYSTEM_SERVICES.put("get_integer", "5");
		SYSTEM_SERVICES.put("get_boolean", "5");
		SYSTEM_SERVICES.put("get_float", "6");
		SYSTEM_SERVICES.put("get_double", "7");
		SYSTEM_SERVICES.put("get_string", "8");
		SYSTEM_SERVICES.put("sbrk", "9");
		SYSTEM_SERVICES.put("exit", "10");
	}
	/**El método que mueve todo el código de la parte declarativa de un subprograma 
	   al cuerpo. ¡Importante para poder generar los bloques básicos!*/	
	private ArrayList<Quadruple> reorderCode(ArrayList<Quadruple> code){
		ArrayList<Quadruple> reordered=new ArrayList<>	();
		//cada vez que encuentre un function, poner la parte declarativa acá
		Stack<ArrayList<Quadruple>> declarativePart=new Stack<>();
		//acordate de saltarte los records!
		boolean stackMode=false;		
		ArrayList<Quadruple> toStack=new ArrayList<>();
		//reordenamiento propio
		declarativePart.push(new ArrayList<Quadruple>());
		for(Quadruple instruction: code){
			if(reordered.contains(instruction)) {
                            continue;
                        }
			//si es un declarador, meter todo en el stack
			if(instruction.operador.matches(DECLARERS)){
				stackMode=true;
				if(!toStack.isEmpty()){
					declarativePart.push(toStack);
					toStack=new ArrayList<>();
				}
				if(!instruction.operador.matches(ERASABLES)) {
                                    reordered.add(instruction);
                                }
				continue;
			}else if(instruction.operador.matches(BEGINNERS)){
				if(!toStack.isEmpty()){
					declarativePart.push(toStack);
					toStack=new ArrayList<>();
				}
				stackMode=false;
				reordered.add(instruction);
				if(!declarativePart.isEmpty()){
					reordered.addAll(declarativePart.pop());
					if(!declarativePart.isEmpty()) {
                                            toStack=declarativePart.pop();
                                        }
					else {
                                            toStack=new ArrayList<>();
                                        }
				}
				
				continue;
			}else if(instruction.operador.matches(ENDERS)){
				stackMode=true;
				reordered.add(instruction);
				continue;
			}else if(stackMode){
				toStack.add(instruction);
				continue;
			}else{
				reordered.add(instruction);
			}
	
				
		}
		//ahora, arreglar los gotos:
		int j=0;
		for(Quadruple i: reordered){
			if(i.operador.matches(JUMPS)){
                                if (!"".equals(i.res)) {
                                    i.res=String.valueOf(Integer.parseInt(i.res)
                                          -(code.indexOf(i)-reordered.indexOf(i)));
                                }
                                else {
                                    i.res=String.valueOf(0-(code.indexOf(i)-reordered.indexOf(i)));
                                }
			}
		}

	return reordered;	
	}

	/**El método que determina los bloques básicos: puesto que el código ya está reordenado, toma las
	   salidas, los if y los goto como los terminadores de un bloque. Luego de una salida
	   no debería haber nada más que una entrada a función o record, así que está bien, no?*/
	private void findBasicBlocks(ArrayList<Quadruple> code){
		//encontrar las instrucciones líderes
		HashSet<Integer> leaderSet=new HashSet<>();
		Integer iteration;
		Quadruple instruction;
		for(int i=1; i<code.size();i++){
			instruction=code.get(i);
			iteration=new Integer(i);
			if(instruction.operador.matches(JUMPS)){
				leaderSet.add(new Integer(instruction.res));
				if(i <= code.size()-1) {
                                    leaderSet.add(iteration+1);
                                }
			}else if(instruction.operador.matches(BEGINNERS)){
				leaderSet.add(iteration);
			}
			//llenar lo de los temporales:
			if(instruction.res.matches(FE_TEMP)){
				this.frontEndTemps.put(instruction.res, new TempSymbol(new VarInfo(false, UNUSED)));
			}
		}
		//convertir el set en lista:		
		ArrayList<Integer> leaders=new ArrayList<>();		
		leaders.addAll(leaderSet);
		//ordenar los líderes:
		Collections.sort(leaders);
		Integer leader;
		String label;
		int i;
		for(i=0;i<leaders.size()-1;i++){
			leader=leaders.get(i).intValue();
			instruction=code.get(leader);
			label=(instruction.operador.matches("initFunction")) ? instruction.arg1 : String.format("%s%d", "label",i);
			this.basicBlocks.add(
					new BasicBlock(label, leader, leaders.get(i+1).intValue()-1)
				);
		}
		//terminar el último bloque básico:
		this.basicBlocks.add(new BasicBlock(String.format("%s%d", "label", i), leaders.get(i++), code.size()-1));
	}//findBasicBlocks

	/**Función que establece la información de una variable*/
	private void setVarInfo(String var, String currentScope, boolean isAlive, int nextUse){
		VariableSymbol sym;
			if(!var.isEmpty() && !var.matches(NOT_VAR)){
				if(var.matches(FE_TEMP)){
					this.frontEndTemps.put(var, new TempSymbol(new VarInfo(isAlive, nextUse)));
				}else{
					SymbolLookup t=this.st.get(currentScope, var);
					sym=(t==null)?null:t.symbol;
					if(sym != null){
						sym.isAlive=isAlive;
						sym.nextUse=nextUse;
					}
				}
			}
	}//setVarInfo
	
	/**Obtiene la información de una variable*/
	private VarInfo getVarInfo(String var, String currentScope){
		VariableSymbol sym;
			if(!var.isEmpty() && !var.matches(NOT_VAR)){
				if(var.matches(FE_TEMP)){
					return this.frontEndTemps.get(var).info;
				}else{
					SymbolLookup t=this.st.get(currentScope, var);
					sym=(t==null)?null:t.symbol;
					if(sym != null){
						return new VarInfo(sym.isAlive, sym.nextUse);
					}else{
						return null;
					}
				}
			}else{
				return null;
			}
	}//setVarInfo

	/**La función que determina la información de siguiente uso de las direcciones en las instrucciones
	*  OBSTACULOS: alguna de las direcciones podría no estar en ninguna tabla o ser una constante
		       no debería generar para pseudo-instrucciones*/
	private void getNextUse(){
		Quadruple instruction;
		String currentScope="";
		VarInfo temp;
		VariableSymbol var;
		HashMap<String, String> dirs=new HashMap<>(3);
		for(BasicBlock block: this.basicBlocks){
			for(int i=block.end; i>=block.beginning;i--){
				instruction=icode.get(i);
				if(instruction.operador.matches(ENDERS)){
					currentScope=instruction.arg1;				
				}
				//no generar info de siguiente uso para estas:
				if(instruction.operador.matches(PSEUDOINSTRUCTIONS)) {
                                    continue;
                                }
				//hay instrucciones que no tienen ninguna dirección
				if(!instruction.res.isEmpty()){dirs.put("res", instruction.res);}
				if(!instruction.arg1.isEmpty()){dirs.put("arg1", instruction.arg1);}
				if(!instruction.arg2.isEmpty()){dirs.put("arg2", instruction.arg2);}
				//adjuntar a la instrucción la info en la st de las variables
				for(Map.Entry dir: dirs.entrySet()){
					if(dir.getValue().toString().matches(FE_TEMP)){//es un temporal
						temp = this.frontEndTemps.get(dir.getValue().toString()).info;
						instruction.info.put(
							dir.getKey().toString(),
							temp
						);
					}else{//es una variable normal					
						SymbolLookup t=this.st.get(currentScope, dir.getValue().toString());
						var=(t==null)?null:t.symbol;
						if(var != null){
							instruction.info.put(
								dir.getKey().toString(),
								new VarInfo(var.isAlive, var.nextUse)
							);
						}
					}				
				}//fin del paso 1

				//poner en la tabla de símbolos al lado izquierdo como inactivo y no usado:
				setVarInfo(instruction.res, currentScope, false, UNUSED);
				//poner al lado derecho como activo y usado en esta instrucción:
				setVarInfo(instruction.arg1, currentScope, true, i);
				setVarInfo(instruction.arg2, currentScope, true, i);
		
			}//iterar en reversa sobre las instrucciones del bloque básico.
		}//por cada bloque básico
	}//getNextuse

	/**La función para obtener los registros de una instrucción:
	   Por cuestiones de tiempo, asumiré que son enteros...
	   Las variables temporales SOLO pueden estar en registros, así que se les dará prioridad.
	   (es decir, nada de guardarlas en otro lado).	
	 */
	private HashMap<String, String> obtenReg(Quadruple I, 
						String currentScope, BasicBlock block, int instruction, boolean isCopy){
		//determinar el tipo: ¿cómo saber el tipo para instrucciones tipo $t1=$t1+$t3 ?
		/*En todas las operaciones enteras, el segundo operando puede ser una constante, entonces
		  Si es constante, no nos tomemos la molestia de buscarle un registro.*/
		HashMap<String, String> dirs=new HashMap<>();		
		dirs.put("y", I.arg1);
		if(!isCopy){
			dirs.put("z", I.arg2);
			dirs.put("x", I.res);
		}
		HashMap<String, String> retVal=new HashMap<>();
		//conseguir registros para x, y und z
		String key, value, register;		
		HashSet<String> accessDescriptor=new HashSet<>();
		HashSet<String> regdesc;
		int regScore;
		HashMap<String, Integer> scores=new HashMap<>();
		VarInfo v_info;
		boolean inReg;
		for(Map.Entry dir: dirs.entrySet()){
			inReg=false;
			key=dir.getKey().toString();
			value=dir.getValue().toString();
			//saltárselo si está vacío:
			if(value.isEmpty()){retVal.put(key, ""); continue;}
			if(!value.matches(NOT_REGISTRABLE) && 
				!(value.matches(INTEGER_LITERAL) && (key.equals("z") || key.equals("x")))){
				//1. Ver si YA está en un registro.
				if(value.matches(FE_TEMP)){//es un temporal
					accessDescriptor=this.frontEndTemps.get(value).accessDescriptor;
				}else if(!value.matches(INTEGER_LITERAL) && this.st.get(currentScope, value).symbol != null){
                                        accessDescriptor=this.st.get(currentScope, value).symbol.accessDescriptor;
				}	
				for(String place: accessDescriptor){
					//si está en un registro, devolverlo. Ya terminamos con esta var.
					inReg=false;
					if(place.matches(REGISTER)){						
						retVal.put(key, place );
						inReg=true;
						break;//ya encontramos que está en un registro!
					}					
				}//iterar en el descriptor de acceso
				if(inReg){continue;}
				//2. Aún aquí? Ok. Tratemos de darle un registro vacío:				
				LinkedHashSet<String> attempts=new LinkedHashSet<>();
				register=this.regDescriptor.getEmpty();				
				attempts.add(register);
				//si ya está, seguir buscando hasta que encontremos uno vacío:
				while(register != null && retVal.containsValue(register)){
					register=this.regDescriptor.getEmpty(attempts);					
					attempts.add(register);
				}
				if(register != null){
					retVal.put(key, register);
					continue;
				}
				boolean good=false;
				//3. OK, estamos fregados va? Bueno, busquemos registro por registro cuál darle!
				for(Map.Entry reg: this.regDescriptor.descriptor.entrySet()){
					regScore=0;
					regdesc=(HashSet<String>)reg.getValue();
					register=reg.getKey().toString();
					//por cada una de las variables en este descriptor, veamos qué ondas:
					for(String var: regdesc){
						if(var.matches(FE_TEMP)){
							accessDescriptor=this.frontEndTemps.get(var).accessDescriptor;
						}else{//es una variable normal
							accessDescriptor=this.st.get(currentScope,var).symbol.accessDescriptor;
						}
						String ix=dirs.get("x");
						v_info=getVarInfo(currentScope, var);
						//si el descriptor dice que está en algún otro lado...
						if(accessDescriptor.size()>1){
							good=true;		
						//si v es x y x no vuelve a salir en I:
						}else if(var.equals(ix) && !(ix.equals(dirs.get("y")) 
							|| ix.equals(dirs.get("z")))){
							good=true;
						}
						//si v no se utiliza otra vez en este bloque:
						else if(v_info != null && v_info.nextUse< instruction) {
                                                    good=true;
                                                }
						//ni aún así? Generar un derrame:
						else if(v_info != null && !var.matches(FE_TEMP)){
							store(currentScope, var);
							regScore++;
						}
						if(good){break;}
						good=false;
					}//iterar en las variables del registro
					scores.put(register, new Integer(regScore));
				}//iterar en los registros
				//encontrar la menor score que no esté ya en los elegidos:
				int lesser=Integer.MAX_VALUE;
				int current;
				String lesserReg="";
				for(Map.Entry s: scores.entrySet()){
					current=((Integer)s.getValue()).intValue();
					if(current < lesser && !retVal.containsValue(s.getKey().toString())) {
                                            lesserReg=s.getKey().toString();
                                        }
				}
				retVal.put(key, lesserReg);
			}else{
				retVal.put(key, "");
			}
		}
		//si es copia, le damos a x el de y
		if(isCopy){retVal.put("x", retVal.get("y"));}
		return retVal;
	}//obtenerReg
	
	/**Para generar un guardado de una variable*/
	private void store(String var, String currentScope){
		long address=this.st.get(currentScope, var).symbol.address;
		text.append(String.format("\t sw %s, %d($fp)", var, address));
	}	
	
	/**En base a un número de instrucción, determina qué bloque básico la contiene y devuelve la etiqueta de éste*/
	private String getLabel(String destino){
		int destino_entero=Integer.parseInt(destino);
		for(BasicBlock b: this.basicBlocks){
			if(b.beginning==destino_entero) {
                            return b.label;
                        }
		}
		return "ERROR";
	}
	/**La función loca que hace la generación*/
	public void assemble(String filename){
		/*Hay dos tipos de instrucciones:
		 1. Las que ocupan registros: operaciones, copias, put/get y param.
			1.1 las copias son un caso especial.
		 2. Las que no: como los inits, exit y call*/			
		Quadruple instruction;
		int variable_space=0;
		String currentScope="";
		int paramCount=0;//cuenta cuántos parámetros van
		HashSet<String> blockVariables;
		LinkedHashSet<String> pushedTemps; //los temporales que un bloque guarde
		HashMap<String, String> registros;
		HashSet<String> ad;
		String mainProcedure="";
		//poner global función principal y ponerle main también, porque MIPS la ocupa
		if(icode.get(0).operador.matches("glbl")){
			//text.append(String.format("\t.globl %s\nmain:\n", icode.get(0).arg1));
			mainProcedure=icode.get(0).arg1;
			text.append("\t.globl main\n");
			//currentScope=icode.get(1).arg1;			
		}
		for(BasicBlock block: this.basicBlocks){
			//reinicializar las variables del bloque
			blockVariables=new HashSet<>();
			pushedTemps=new LinkedHashSet<>();
			//poner la etiqueta:
			if(block.label.equals(mainProcedure) || this.basicBlocks.size() == 1) {
                            text.append("main:\n");
                        }
			text.append(String.format("_%s:\n", block.label));
			//por cada instrucción en este bloque:
			for(int i=block.beginning; i<=block.end; i++){
				instruction=this.icode.get(i);
				//al final del bloque básico, guardar las variables vivas:
				if(i==block.end){
					/*TODO: guardar variables vivas!*/
					for(String blockVar: blockVariables){
						if(blockVar.matches(FE_TEMP)){
						//si es temporal, dejémosla morir:
							HashSet <String>key;
							for(String place: this.frontEndTemps.get(blockVar).accessDescriptor){
								if((key=this.regDescriptor.get(place)) != null) {
                                                                    key.remove(blockVar);
                                                                }
							}
						/* O LA GUARDAMOS?
							if(this.frontEndTemps.get(blockVar).info.isAlive){
								//push onto the stack
								pushedTemps.add(blockVar);
								text.append(
								 String.format("\t %s %s ,%d($sp)", 
										"sw", pushedTemps.size(), 
										getLocation(currentScope, blockVar))
								);
								this.frontEndTemps.get(blockVar).accessDescriptor.add(
												String.format("%d($sp)",
												pushedTemps.size()));
							}//temporal viva
						*/
						}else{//es una variable:
							VariableSymbol info=this.st.get(currentScope, blockVar).symbol;
							if(info.isAlive){
								//TODO: actualizar el descriptor de acceso
								text.append(
								 String.format("\t %s %s %d($fp)\n", 
										"sw", info.address,
										getLocation(currentScope,blockVar) )
								);
								info.accessDescriptor.add(String.format("%d($fp)",
											   info.address));
							}//está viva al salir
						}//variable
					}//para cada variable usada en el bloque
				}//guardar variables vivas
				
				//si es una prologada, poner el prólogo:
				if(instruction.operador.matches(PROLOGUED)){
					variable_space=Integer.parseInt(instruction.arg2);	
					//meter ra y fp:
					text.append(String.format("\tsub $sp, $sp, %d\n", 8+variable_space));
					text.append("\tsw $ra, ($sp)\n");
					text.append("\tsw $fp, 4($sp)\n");
					//reservar el espacio para variables:
					text.append(String.format("\tsub $fp, $sp, %d\n", variable_space));
					//inicializar el stack pointer:
					text.append("\tmove $sp, $fp\n");					
					currentScope=instruction.arg1;
					continue;
				}//prologadas

				//si es un epílogo, escribirlo
				if(instruction.operador.matches(EPILOGUED)){
					//poner la etiqueta:
					text.append(String.format("_exit_%s:\n", instruction.arg1));
					//reestablecer el sp:
					text.append(String.format("\tadd $sp, $fp, %d\n", variable_space));
					//sacar fp:
					text.append("\tlw $fp, 4($sp)\n");
					//sacar ra:
					text.append("\tlw $ra, ($sp)\n");
					//terminar de reestablecer sp:
					text.append("\tadd $sp, $sp, 8\n");
					//regresar:
					text.append("\tjr $ra\n\n");
					continue;
				}//epilogadas
				
				 /*Las variables vivas ya debieron haber sido guardadas...*/
				if(instruction.operador.equalsIgnoreCase("goto")){
					//imprimir el salto
					text.append(String.format("\tb _%s\n", getLabel(instruction.res)));
					continue;
				}//goto

				if(instruction.operador.equalsIgnoreCase("call")){
					//imprimir el salto
					paramCount=0;
					text.append(String.format("\tjal _%s_%s\n",currentScope, instruction.arg1));
					//procesar el resultado:
					if(!instruction.res.isEmpty()){
						//obtener un registro para el temporal del retorno
						HashMap<String, String> reg =obtenReg(
							new Quadruple("",instruction.res, "", ""),
							 currentScope, block, i, true);
						//generar el mv
						text.append(String.format("\tmove %s, $v0\n", reg.get("y")));
						//actualizar el descriptor de acceso y registro
						if(instruction.res.matches(FE_TEMP)){
							ad=this.frontEndTemps.get(instruction.res).accessDescriptor;
						}else{
							ad=this.st.get(currentScope, instruction.res).symbol.accessDescriptor;
						}
						//el descriptor de acceso
						ad.add(reg.get("y"));
						//el descriptor de registro:
						regDescriptor.get(reg.get("y")).clear();
						regDescriptor.get(reg.get("y")).add(instruction.res);
					}
					continue;
				}//call
				//TODO: tener un buffer de params, para saber cuándo empezar a meter a la pila
				if(instruction.operador.equalsIgnoreCase("param")){
					//paramCount++;
					//if( paramCount > 4){/*generar push...*/}
					String location=getLocation(currentScope, instruction.arg1);
					text.append(String.format("\t%s $a%d, %s\n",
								    getLoadInstruction(location), paramCount++, location));					      continue;
				}//param
				
				if(instruction.operador.equalsIgnoreCase("return")){
					//determinar el registro:
					String location=getLocation(currentScope, instruction.arg1);
					text.append(String.format("\t%s $v0, %s\n",
								    getLoadInstruction(location), location));
					text.append(String.format("\tb _exit_%s\n", currentScope));
					continue;
				}//return
				if(instruction.operador.equalsIgnoreCase("glblExit")){
				//la etiqueta ya está generada...
					text.append(String.format("\tli $v0, 10\n\tsyscall\n\n"));
					continue;
				}

				/*Ahora, la hora de la verdad: las variables que ocupan registros!!*/
				if(instruction.operador.matches(OPERATOR)){
					//obtener los registros
					registros=obtenReg(instruction, currentScope, block, i, false);
					
					//ver si se necesitan instrucciones de carga:
					createLoad(currentScope, instruction.arg1, "y", registros);
					//el último no tiene por qué cargarse a registro si es entero
					if(instruction.arg2.matches(INTEGER_LITERAL)){
						registros.put("z", instruction.arg2);
					}else{
						createLoad(currentScope, instruction.arg2,"z",registros);
					}
						
					//generar la instrucción:
					generateInstruction(instruction, 
							    registros.get("x"), registros.get("y"), registros.get("z"));
					//actualizar descriptores para x:
					if(!registros.get("x").isEmpty() && !instruction.res.matches(CONSTANT)){
						//1. Rx sólo debe contener a x:
						regDescriptor.get(registros.get("x")).clear();
						regDescriptor.get(registros.get("x")).add(instruction.res);
						//2. x sólo debe estar en Rx
						//if(!instruction.res.isEmpty() &&!instruction.res.matches(NOT_REGISTRABLE)){
						if(instruction.res.matches(FE_TEMP)){
							ad=this.frontEndTemps.get(instruction.res).accessDescriptor;
						}else{
							ad=this.st.get(currentScope,instruction.res).symbol.accessDescriptor;
						}
						ad.clear();
						ad.add(registros.get("x"));
						//}
						//3.Eliminar Rx del descriptor de acceso de cualquier variable que no sea x
						for(Map.Entry var: st.table.entrySet()) {
							String key=var.getKey().toString();
							VariableSymbol value=(VariableSymbol)var.getValue();
							if(!key.equals(String.format("%s.%s", currentScope, instruction.res))
							   && value.accessDescriptor.contains(registros.get("x"))){
								value.accessDescriptor.remove(registros.get("x"));
							}
						}//eliminar el registro
					}//sii el registro de equis no está vacío, claro	
				}else if(instruction.operador.matches(COPY)){
					//obtener registros
					registros=obtenReg(instruction, currentScope, block, i, true);
					//determinar si y ocupa carga:
					createLoad(currentScope, instruction.arg1, "y", registros);
					//poner a x como una de las vars en el registro de y:
					this.regDescriptor.update(registros.get("y"), instruction.res);
					//actualizar para x
					//la única ubicación de x debe ser Ry:
					if(instruction.res.matches(FE_TEMP)){
						ad=this.frontEndTemps.get(instruction.res).accessDescriptor;
					}else{
						ad=this.st.get(currentScope,instruction.res).symbol.accessDescriptor;
					}
					ad.clear();
					ad.add(registros.get("y"));
					
				}//copia
		
				
			}
			
		}//por cada bloque
		writeCodeFile(filename);
	}//assemble

	private void createLoad(String currentScope, String arg, String namen, HashMap<String,String> registros){
		if(!arg.isEmpty() && !arg.matches(NOT_REGISTRABLE) && !arg.matches(INTEGER_LITERAL)){
			HashSet<String> ad=new HashSet<>();
			//tiene que ser una variable o algo va?
			String l=getLocation(currentScope, arg);
			if(arg.matches(FE_TEMP)){
				ad=this.frontEndTemps.get(arg).accessDescriptor;
			}else if (this.st.get(currentScope,arg).symbol != null){
				ad=this.st.get(currentScope,arg).symbol.accessDescriptor;
			}
			if(!ad.contains(registros.get(namen))){
				//no lo contiene, generar el load
				text.append(
				String.format("\t%s %s, %s\n", getLoadInstruction(l),
					registros.get(namen),
					l
				)
				);
				//ahora, actualizar el registro:
				regDescriptor.get(registros.get(namen)).clear();
				regDescriptor.get(registros.get(namen)).add(arg);
				//luego, actualizar el acceso:
				ad.add(registros.get(namen));
			}
		}else if(arg.matches(INTEGER_LITERAL)){
			text.append(
			String.format("\t%s %s, %s\n", getLoadInstruction(arg),
				registros.get(namen),
				arg
			)
			);
		}
		
	}
	private String getLocation(String currentScope, String symbol){
		//si es constante, sólo regresarlo
		if(symbol.matches(CONSTANT)) {
                    return symbol;
                }
		
		if(symbol.matches(FE_TEMP)){
			return this.frontEndTemps.get(symbol).accessDescriptor.iterator().next().toString();	
		}
	
                if (!this.st.get(currentScope, symbol).symbol.accessDescriptor.isEmpty()) {
                    return this.st.get(currentScope, symbol).symbol.accessDescriptor.iterator().next().toString();
                }
                return "0";
	}

	private String getLoadInstruction(String var){
		if(var.matches(CONSTANT)) {
                    return "li";
                }
                else if(var.matches(REGISTER)) {
                    return "move";
                }
                else if(var.matches(IN_STACK)) {
                    return "lw";
                }
		//si sale otra cosa, estamos mal!!
		return "ERROR";
	}
	
	private void generateInstruction(Quadruple instruction, String rx, String ry, String rz){
		String operador=instruction.operador;
		String machineOperator;
		if(operador.matches("if.*")){
			//sacar el operador relacional
			String rel=operador.split("_")[1];
			//obtener la correspondiente instrucción:
			machineOperator=BRANCH_OPERATIONS.get(rel);
			
			text.append(String.format("\t%s %s, %s, _%s\n", machineOperator, ry, rz, getLabel(instruction.res)));
		}else if(operador.matches("put")){
			String service=SYSTEM_SERVICES.get(String.format("put_%s", instruction.arg1));
			text.append(String.format("\tli $v0, %s\n", service));
			
			if(instruction.arg1.equalsIgnoreCase("string")){
				String message=String.format("_msg%d", dataMessages++);
				data.append(String.format("%s: .asciiz %s\n", message, instruction.arg2));
				text.append(String.format("\tla $a0, %s\n\tsyscall\n", message));
			}else{
				text.append(String.format("\tmove $a0, %s\n\tsyscall\n", rz));
			}
							
		}else if(operador.matches("get")){
			
			String service=SYSTEM_SERVICES.get(String.format("get_%s", instruction.arg1));
			text.append(String.format("\tli $v0, %s\n\tsyscall\n", service));
			text.append(String.format("\tmove %s, $v0\n", rx));
		}else if(operador.matches(THREEWAY_OPERATOR)){
			
			text.append(String.format("\t%s %s, %s, %s\n", operador, rx, ry, rz));
		}else if(operador.matches(TWINE_OPERATOR)){
			text.append(String.format("\t%s %s, %s\n", operador, rx, ry));
		}
	}

	private void writeCodeFile(String filename){
		File archivo=new File(filename);
		BufferedWriter out=null;
		try{
			out=new BufferedWriter(new FileWriter(archivo));
			out.write(data.toString());out.newLine();
			out.write(text.toString());out.newLine();
		}catch(FileNotFoundException fnfex){
			System.err.printf("No se encuentra el archivo %s", filename);
		}catch(IOException ioex){
			System.err.printf("No se pudo escribir el archivo %s", filename);
		}finally{
			try{
				out.close();	
			}catch(IOException fnx){
				System.err.printf("No se encuentra el archivo %s", filename);
			}
		}
	}
}

