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

public class ParserCodegen {

    public ArrayList<Quadruple> quads;
    public SequentialSymbolTable sst;
    
    private final static String BEGINNERS = "initFunction|call";
    private final static String DECLARERS = "function|glbl";
    private final static String ENDERS = "exit|glblExit";
    private final static String EQUAL = ":=";
    private final static String ERASABLES = "function";
    private final static String FINALIZER = "exit";
    private final static String FLOAT_LITERAL = "[0-9]+\\.[0-9]+";
    private final static String INITIALIZER = "initFunction";
    private final static String INSTRUCTIONS = "initFunction|exit|call";
    private final static String INTEGER_LITERAL = "[0-9]+";
    private final static String JUMPS = "if.*|goto";
    private final static String OPERATOR = "if.*|add|sub|neg|not|abs|mul|div|mod|rem|put|get";
    private final static String ORA_OPERATOR = "neg|not|rem|abs";
    private final static String ORAA_OPERATOR = "add|sub|mul|div";
    private final static String REGISTER = "\\$[atsf][0-9]+";
    private final static String STACK = "-?([0-9])?+\\(\\$[sf]p\\)";
    private final static String STRING_LITERAL = "\".*\"";
    private final static String TEMPS = "%t[0-9]+";
    
    private final static String CONSTANT = String.format("%s|%s|%s", INTEGER_LITERAL, FLOAT_LITERAL, STRING_LITERAL);
    private final static String NOT_VAR = String.format("integer|string|float|boolean|%s", CONSTANT);
    private final static String NON_REGISTER = String.format("integer|string|float|boolean|%s", STRING_LITERAL);
    
    public final static int WORD_LENGTH = 4;
    
    public HashMap<String, String> BRANCHERS;
    public HashMap<String, String> SYSCALLERS;
    
    private int dataMessages;
    private ArrayList<Block> basicBlocks;
    private StringBuilder data;
    private StringBuilder text;
    private HashMap<String, TempSymbol> frontEndTemps;
    private TempReg regDescriptor;

    public ParserCodegen(ArrayList<Quadruple> i, SequentialSymbolTable t, boolean dbg) {
        data = new StringBuilder("\t.data\n");
        text = new StringBuilder("\t.text\n");
        this.basicBlocks = new ArrayList<>();
        frontEndTemps = new HashMap<>();
        regDescriptor = new TempReg();
        dataMessages = 0;
        quads = i;
        sst = t;
        initMaps();
        this.quads = reorderCode(this.quads);
        findBasicBlocks(this.quads);
        getNextUse();
    }

    private void initMaps() {
        BRANCHERS = new HashMap<>();
        BRANCHERS.put("==", "beq");
        BRANCHERS.put("/=", "bneq");
        BRANCHERS.put("<", "blt");
        BRANCHERS.put(">", "bgt");
        BRANCHERS.put("<=", "ble");
        BRANCHERS.put(">=", "bge");

        SYSCALLERS = new HashMap<>();
        SYSCALLERS.put("put_integer", "1");
        SYSCALLERS.put("put_boolean", "1");
        SYSCALLERS.put("put_float", "2");
        SYSCALLERS.put("put_double", "3");
        SYSCALLERS.put("put_string", "4");
        SYSCALLERS.put("get_integer", "5");
        SYSCALLERS.put("get_boolean", "5");
        SYSCALLERS.put("get_float", "6");
        SYSCALLERS.put("get_double", "7");
        SYSCALLERS.put("get_string", "8");
        SYSCALLERS.put("sbrk", "9");
        SYSCALLERS.put("exit", "10");
    }

    private ArrayList<Quadruple> reorderCode(ArrayList<Quadruple> code) {
        ArrayList<Quadruple> reordered = new ArrayList<>();
        Stack<ArrayList<Quadruple>> declarativePart = new Stack<>();
        boolean stackMode = false;
        ArrayList<Quadruple> toStack = new ArrayList<>();
        declarativePart.push(new ArrayList<Quadruple>());
        for (Quadruple instruction : code) {
            if (reordered.contains(instruction)) {
                continue;
            }
            if (instruction.operador.matches(DECLARERS)) {
                stackMode = true;
                if (!toStack.isEmpty()) {
                    declarativePart.push(toStack);
                    toStack = new ArrayList<>();
                }
                if (!instruction.operador.matches(ERASABLES)) {
                    reordered.add(instruction);
                }
                continue;
            } else if (instruction.operador.matches(BEGINNERS)) {
                if (!toStack.isEmpty()) {
                    declarativePart.push(toStack);
                    toStack = new ArrayList<>();
                }
                stackMode = false;
                reordered.add(instruction);
                if (!declarativePart.isEmpty()) {
                    reordered.addAll(declarativePart.pop());
                    if (!declarativePart.isEmpty()) {
                        toStack = declarativePart.pop();
                    } else {
                        toStack = new ArrayList<>();
                    }
                }
                continue;
            } else if (instruction.operador.matches(ENDERS)) {
                stackMode = true;
                reordered.add(instruction);
                continue;
            } else if (stackMode) {
                toStack.add(instruction);
                continue;
            } else {
                reordered.add(instruction);
            }


        }
        int j = 0;
        for (Quadruple i : reordered) {
            if (i.operador.matches(JUMPS)) {
                if (!"".equals(i.res)) {
                    i.res = String.valueOf(Integer.parseInt(i.res)
                            - (code.indexOf(i) - reordered.indexOf(i)));
                } else {
                    i.res = String.valueOf(0 - (code.indexOf(i) - reordered.indexOf(i)));
                }
            }
        }

        return reordered;
    }

    private void findBasicBlocks(ArrayList<Quadruple> code) {
        HashSet<Integer> leaderSet = new HashSet<>();
        Integer iteration;
        Quadruple instruction;
        for (int i = 1; i < code.size(); i++) {
            instruction = code.get(i);
            iteration = new Integer(i);
            if (instruction.operador.matches(JUMPS)) {
                leaderSet.add(new Integer(instruction.res));
                if (i <= code.size() - 1) {
                    leaderSet.add(iteration + 1);
                }
            } else if (instruction.operador.matches(BEGINNERS)) {
                leaderSet.add(iteration);
            }
            if (instruction.res.matches(TEMPS)) {
                this.frontEndTemps.put(instruction.res, new TempSymbol(new VarInfo(false, UNUSED)));
            }
        }
        ArrayList<Integer> leaders = new ArrayList<>();
        leaders.addAll(leaderSet);
        Collections.sort(leaders);
        Integer leader;
        String label;
        int i;
        for (i = 0; i < leaders.size() - 1; i++) {
            leader = leaders.get(i).intValue();
            instruction = code.get(leader);
            label = (instruction.operador.matches("initFunction")) ? instruction.arg1 : String.format("%s%d", "label", i);
            this.basicBlocks.add(
                    new Block(label, leader, leaders.get(i + 1).intValue() - 1));
        }
        this.basicBlocks.add(new Block(String.format("%s%d", "label", i), leaders.get(i++), code.size() - 1));
    }

    private void setVarInfo(String var, String currentScope, boolean isAlive, int nextUse) {
        VariableSymbol sym;
        if (!var.isEmpty() && !var.matches(NOT_VAR)) {
            if (var.matches(TEMPS)) {
                this.frontEndTemps.put(var, new TempSymbol(new VarInfo(isAlive, nextUse)));
            } else {
                FindSymbol t = this.sst.get(currentScope, var);
                sym = (t == null) ? null : t.symbol;
                if (sym != null) {
                    sym.isAlive = isAlive;
                    sym.nextUse = nextUse;
                }
            }
        }
    }

    private VarInfo getVarInfo(String var, String currentScope) {
        VariableSymbol sym;
        if (!var.isEmpty() && !var.matches(NOT_VAR)) {
            if (var.matches(TEMPS)) {
                return this.frontEndTemps.get(var).info;
            } else {
                FindSymbol t = this.sst.get(currentScope, var);
                sym = (t == null) ? null : t.symbol;
                if (sym != null) {
                    return new VarInfo(sym.isAlive, sym.nextUse);
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    private void getNextUse() {
        Quadruple instruction;
        String currentScope = "";
        VarInfo temp;
        VariableSymbol var;
        HashMap<String, String> dirs = new HashMap<>(3);
        for (Block block : this.basicBlocks) {
            for (int i = block.finish; i >= block.start; i--) {
                instruction = quads.get(i);
                if (instruction.operador.matches(ENDERS)) {
                    currentScope = instruction.arg1;
                }
                if (instruction.operador.matches(INSTRUCTIONS)) {
                    continue;
                }
                if (!instruction.res.isEmpty()) {
                    dirs.put("res", instruction.res);
                }
                if (!instruction.arg1.isEmpty()) {
                    dirs.put("arg1", instruction.arg1);
                }
                if (!instruction.arg2.isEmpty()) {
                    dirs.put("arg2", instruction.arg2);
                }
                for (Map.Entry dir : dirs.entrySet()) {
                    if (dir.getValue().toString().matches(TEMPS)) {
                        temp = this.frontEndTemps.get(dir.getValue().toString()).info;
                        instruction.info.put(
                                dir.getKey().toString(),
                                temp);
                    } else {
                        FindSymbol t = this.sst.get(currentScope, dir.getValue().toString());
                        var = (t == null) ? null : t.symbol;
                        if (var != null) {
                            instruction.info.put(
                                    dir.getKey().toString(),
                                    new VarInfo(var.isAlive, var.nextUse));
                        }
                    }
                }
                setVarInfo(instruction.res, currentScope, false, UNUSED);
                setVarInfo(instruction.arg1, currentScope, true, i);
                setVarInfo(instruction.arg2, currentScope, true, i);

            }
        }
    }

    private HashMap<String, String> obtenReg(Quadruple I,
            String currentScope, Block block, int instruction, boolean isCopy) {
        HashMap<String, String> dirs = new HashMap<>();
        dirs.put("y", I.arg1);
        if (!isCopy) {
            dirs.put("z", I.arg2);
            dirs.put("x", I.res);
        }
        HashMap<String, String> retVal = new HashMap<>();
        String key, value, register;
        HashSet<String> accessDescriptor = new HashSet<>();
        HashSet<String> regdesc;
        int regScore;
        HashMap<String, Integer> scores = new HashMap<>();
        VarInfo v_info;
        boolean inReg;
        for (Map.Entry dir : dirs.entrySet()) {
            inReg = false;
            key = dir.getKey().toString();
            value = dir.getValue().toString();
            if (value.isEmpty()) {
                retVal.put(key, "");
                continue;
            }
            if (!value.matches(NON_REGISTER)
                    && !(value.matches(INTEGER_LITERAL) && this.sst.get(currentScope, value).symbol != null
                    && (key.equals("z") || key.equals("x")))) {
                if (value.matches(TEMPS)) {
                    accessDescriptor = this.frontEndTemps.get(value).hashUsageDescription;
                } else if (!value.matches(INTEGER_LITERAL)) {
                    accessDescriptor = this.sst.get(currentScope, value).symbol.accessDescriptor;
                }
                for (String place : accessDescriptor) {
                    inReg = false;
                    if (place.matches(REGISTER)) {
                        retVal.put(key, place);
                        inReg = true;
                        break;
                    }
                }
                if (inReg) {
                    continue;
                }
                LinkedHashSet<String> attempts = new LinkedHashSet<>();
                register = this.regDescriptor.getEmpty();
                attempts.add(register);
                while (register != null && retVal.containsValue(register)) {
                    register = this.regDescriptor.getEmpty(attempts);
                    attempts.add(register);
                }
                if (register != null) {
                    retVal.put(key, register);
                    continue;
                }
                boolean good = false;
                for (Map.Entry reg : this.regDescriptor.hashDescription.entrySet()) {
                    regScore = 0;
                    regdesc = (HashSet<String>) reg.getValue();
                    register = reg.getKey().toString();
                    for (String var : regdesc) {
                        if (var.matches(TEMPS)) {
                            accessDescriptor = this.frontEndTemps.get(var).hashUsageDescription;
                        } else {
                            accessDescriptor = this.sst.get(currentScope, var).symbol.accessDescriptor;
                        }
                        String ix = dirs.get("x");
                        v_info = getVarInfo(currentScope, var);
                        if (accessDescriptor.size() > 1) {
                            good = true;
                        } else if (var.equals(ix) && !(ix.equals(dirs.get("y"))
                                || ix.equals(dirs.get("z")))) {
                            good = true;
                        } else if (v_info != null && v_info.nextUse < instruction) {
                            good = true;
                        } else if (v_info != null && !var.matches(TEMPS)) {
                            store(currentScope, var);
                            regScore++;
                        }
                        if (good) {
                            break;
                        }
                        good = false;
                    }
                    scores.put(register, new Integer(regScore));
                }
                int lesser = Integer.MAX_VALUE;
                int current;
                String lesserReg = "";
                for (Map.Entry s : scores.entrySet()) {
                    current = ((Integer) s.getValue()).intValue();
                    if (current < lesser && !retVal.containsValue(s.getKey().toString())) {
                        lesserReg = s.getKey().toString();
                    }
                }
                retVal.put(key, lesserReg);
            } else {
                retVal.put(key, "");
            }
        }
        if (isCopy) {
            retVal.put("x", retVal.get("y"));
        }
        return retVal;
    }

    private void store(String var, String currentScope) {
        long address = this.sst.get(currentScope, var).symbol.address;
        text.append(String.format("\t sw %s, %d($fp)", var, address));
    }

    private String getLabel(String destino) {
        int destino_entero = Integer.parseInt(destino);
        for (Block b : this.basicBlocks) {
            if (b.start == destino_entero) {
                return b.label;
            }
        }
        return "ERROR";
    }

    public void assemble(String filename) {
        Quadruple instruction;
        int variable_space = 0;
        String currentScope = "";
        int paramCount = 0;
        HashSet<String> blockVariables;
        HashMap<String, String> registros;
        HashSet<String> ad;
        String mainProcedure = "";
        if (quads.get(0).operador.matches("glbl")) {
            mainProcedure = quads.get(0).arg1;
            text.append("\t.globl main\n");
        }
        for (Block block : this.basicBlocks) {
            blockVariables = new HashSet<>();
            if (block.label.equals(mainProcedure) || this.basicBlocks.size() == 1) {
                text.append("main:\n");
            }
            text.append(String.format("_%s:\n", block.label));
            for (int i = block.start; i <= block.finish; i++) {
                instruction = this.quads.get(i);
                if (i == block.finish) {
                    for (String blockVar : blockVariables) {
                        if (blockVar.matches(TEMPS)) {
                            HashSet<String> key;
                            for (String place : this.frontEndTemps.get(blockVar).hashUsageDescription) {
                                if ((key = this.regDescriptor.get(place)) != null) {
                                    key.remove(blockVar);
                                }
                            }
                        } else {
                            VariableSymbol info = this.sst.get(currentScope, blockVar).symbol;
                            if (info.isAlive) {
                                text.append(
                                        String.format("\t %s %s %d($fp)\n",
                                        "sw", info.address,
                                        getLocation(currentScope, blockVar)));
                                info.accessDescriptor.add(String.format("%d($fp)",
                                        info.address));
                            }
                        }
                    }
                }

                if (instruction.operador.matches(INITIALIZER)) {
                    variable_space = Integer.parseInt(instruction.arg2);
                    text.append(String.format("\tsub $sp, $sp, %d\n", 8 + variable_space));
                    text.append("\tsw $ra, ($sp)\n");
                    text.append("\tsw $fp, 4($sp)\n");
                    text.append(String.format("\tsub $fp, $sp, %d\n", variable_space));
                    text.append("\tmove $sp, $fp\n");
                    currentScope = instruction.arg1;
                    continue;
                }

                if (instruction.operador.matches(FINALIZER)) {
                    text.append(String.format("_exit_%s:\n", instruction.arg1));
                    text.append(String.format("\tadd $sp, $fp, %d\n", variable_space));
                    text.append("\tlw $fp, 4($sp)\n");
                    text.append("\tlw $ra, ($sp)\n");
                    text.append("\tadd $sp, $sp, 8\n");
                    text.append("\tjr $ra\n\n");
                    continue;
                }

                if (instruction.operador.equalsIgnoreCase("goto")) {
                    text.append(String.format("\tb _%s\n", getLabel(instruction.res)));
                    continue;
                }

                if (instruction.operador.equalsIgnoreCase("call")) {
                    paramCount = 0;
                    text.append(String.format("\tjal _%s_%s\n", currentScope, instruction.arg1));
                    if (!instruction.res.isEmpty()) {
                        HashMap<String, String> reg = obtenReg(
                                new Quadruple("", instruction.res, "", ""),
                                currentScope, block, i, true);
                        text.append(String.format("\tmove %s, $v0\n", reg.get("y")));
                        if (instruction.res.matches(TEMPS)) {
                            ad = this.frontEndTemps.get(instruction.res).hashUsageDescription;
                        } else {
                            ad = this.sst.get(currentScope, instruction.res).symbol.accessDescriptor;
                        }
                        ad.add(reg.get("y"));
                        regDescriptor.get(reg.get("y")).clear();
                        regDescriptor.get(reg.get("y")).add(instruction.res);
                    }
                    continue;
                }
                if (instruction.operador.equalsIgnoreCase("param")) {
                    String location = getLocation(currentScope, instruction.arg1);
                    text.append(String.format("\t%s $a%d, %s\n",
                            getLoadInstruction(location), paramCount++, location));
                    continue;
                }
                if (instruction.operador.equalsIgnoreCase("return")) {
                    String location = getLocation(currentScope, instruction.arg1);
                    text.append(String.format("\t%s $v0, %s\n",
                            getLoadInstruction(location), location));
                    text.append(String.format("\tb _exit_%s\n", currentScope));
                    continue;
                }
                if (instruction.operador.equalsIgnoreCase("glblExit")) {
                    text.append(String.format("\tli $v0, 10\n\tsyscall\n\n"));
                    continue;
                }
                if (instruction.operador.matches(OPERATOR)) {
                    registros = obtenReg(instruction, currentScope, block, i, false);
                    createLoad(currentScope, instruction.arg1, "y", registros);
                    if (instruction.arg2.matches(INTEGER_LITERAL)) {
                        registros.put("z", instruction.arg2);
                    } else {
                        createLoad(currentScope, instruction.arg2, "z", registros);
                    }
                    generateInstruction(instruction,
                            registros.get("x"), registros.get("y"), registros.get("z"));
                    if (!registros.get("x").isEmpty() && !instruction.res.matches(CONSTANT)) {
                        regDescriptor.get(registros.get("x")).clear();
                        regDescriptor.get(registros.get("x")).add(instruction.res);
                        if (instruction.res.matches(TEMPS)) {
                            ad = this.frontEndTemps.get(instruction.res).hashUsageDescription;
                        } else {
                            ad = this.sst.get(currentScope, instruction.res).symbol.accessDescriptor;
                        }
                        ad.clear();
                        ad.add(registros.get("x"));
                        for (Map.Entry var : sst.table.entrySet()) {
                            String key = var.getKey().toString();
                            VariableSymbol value = (VariableSymbol) var.getValue();
                            if (!key.equals(String.format("%s.%s", currentScope, instruction.res))
                                    && value.accessDescriptor.contains(registros.get("x"))) {
                                value.accessDescriptor.remove(registros.get("x"));
                            }
                        }
                    }
                } else if (instruction.operador.matches(EQUAL)) {
                    registros = obtenReg(instruction, currentScope, block, i, true);
                    createLoad(currentScope, instruction.arg1, "y", registros);
                    this.regDescriptor.update(registros.get("y"), instruction.res);

                    if (instruction.res.matches(TEMPS)) {
                        ad = this.frontEndTemps.get(instruction.res).hashUsageDescription;
                    } else {
                        ad = this.sst.get(currentScope, instruction.res).symbol.accessDescriptor;
                    }
                    ad.clear();
                    ad.add(registros.get("y"));

                }
            }
        }
        writeCodeFile(filename);
    }

    private void createLoad(String currentScope, String arg, String namen, HashMap<String, String> registros) {
        if (!arg.isEmpty() && !arg.matches(NON_REGISTER) && !arg.matches(INTEGER_LITERAL)) {
            HashSet<String> ad = new HashSet<>();
            String l = getLocation(currentScope, arg);
            if (arg.matches(TEMPS)) {
                ad = this.frontEndTemps.get(arg).hashUsageDescription;
            } else if (this.sst.get(currentScope, arg).symbol != null) {
                ad = this.sst.get(currentScope, arg).symbol.accessDescriptor;
            }
            if (!ad.contains(registros.get(namen))) {
                text.append(
                        String.format("\t%s %s, %s\n", getLoadInstruction(l),
                        registros.get(namen),
                        l));
                regDescriptor.get(registros.get(namen)).clear();
                regDescriptor.get(registros.get(namen)).add(arg);
                ad.add(registros.get(namen));
            }
        } else if (arg.matches(INTEGER_LITERAL)) {
            text.append(
                    String.format("\t%s %s, %s\n", getLoadInstruction(arg),
                    registros.get(namen),
                    arg));
        }

    }

    private String getLocation(String currentScope, String symbol) {
        if (symbol.matches(CONSTANT)) {
            return symbol;
        }

        if (symbol.matches(TEMPS)) {
            return this.frontEndTemps.get(symbol).hashUsageDescription.iterator().next().toString();
        }

        if (!this.sst.get(currentScope, symbol).symbol.accessDescriptor.isEmpty()) {
            return this.sst.get(currentScope, symbol).symbol.accessDescriptor.iterator().next().toString();
        }
        return "0";
    }

    private String getLoadInstruction(String var) {
        if (var.matches(CONSTANT)) {
            return "li";
        } else if (var.matches(REGISTER)) {
            return "move";
        } else if (var.matches(STACK)) {
            return "lw";
        }
        return "ERROR";
    }

    private void generateInstruction(Quadruple instruction, String rx, String ry, String rz) {
        String operador = instruction.operador;
        String machineOperator;
        if (operador.matches("if.*")) {
            String rel = operador.split("_")[1];
            machineOperator = BRANCHERS.get(rel);
            text.append(String.format("\t%s %s, %s, _%s\n", machineOperator, ry, rz, getLabel(instruction.res)));
        } else if (operador.matches("put")) {
            String service = SYSCALLERS.get(String.format("put_%s", instruction.arg1));
            text.append(String.format("\tli $v0, %s\n", service));

            if (instruction.arg1.equalsIgnoreCase("string")) {
                String message = String.format("_msg%d", dataMessages++);
                data.append(String.format("%s: .asciiz %s\n", message, instruction.arg2));
                text.append(String.format("\tla $a0, %s\n\tsyscall\n", message));
            } else {
                if (rz.matches("[0-9]+")) {
                    text.append(String.format("\tli $a0, %s\n\tsyscall\n", rz));
                } else {
                    text.append(String.format("\tmove $a0, %s\n\tsyscall\n", rz));
                }
            }
        } else if (operador.matches("get")) {
            String service = SYSCALLERS.get(String.format("get_%s", instruction.arg1));
            text.append(String.format("\tli $v0, %s\n\tsyscall\n", service));
            text.append(String.format("\tmove %s, $v0\n", rx));
        } else if (operador.matches(ORAA_OPERATOR)) {
            text.append(String.format("\t%s %s, %s, %s\n", operador, rx, ry, rz));
        } else if (operador.matches(ORA_OPERATOR)) {
            text.append(String.format("\t%s %s, %s\n", operador, rx, ry));
        }
    }

    private void writeCodeFile(String filename) {
        File archivo = new File(filename);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(archivo));
            out.write(data.toString());
            out.newLine();
            out.write(text.toString());
            out.newLine();
        } catch (FileNotFoundException ex) {
            System.err.printf("No se encuentra el archivo %s", filename);
        } catch (IOException ex) {
            System.err.printf("No se pudo escribir el archivo %s", filename);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                System.err.printf("No se encuentra el archivo %s", filename);
            }
        }
    }
}