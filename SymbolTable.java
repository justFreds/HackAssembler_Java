/*
 * Manages symbols
 *      predefined variables
 *      user-dfined variables
 *      labels
 */

 import java.util.Hashtable;

public class SymbolTable {
    private int currentRegister;
    private final Hashtable<String, Integer> symbolTable;

    public SymbolTable() {  //CONSTRUCTOR
        currentRegister = 16;
        symbolTable = new Hashtable<String, Integer>(25);

        for(int i = 0; i < 16; i++) {   //init pre-defined vars
            final String key = "R" + i;
            symbolTable.put(key, i);
        }

        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
    }

//---METHODS---//

    public boolean addVariable(final String symbol) {
        if(!symbolTable.containsKey(symbol)) {
            symbolTable.put(symbol, currentRegister);
            currentRegister++;
            return true;    //add success
        }
        return false;       //add fail
    }

    public void putKeyValue(final String symbol, final int value) {
        symbolTable.put(symbol, value);
    }
    public int getKeyValue(final String symbol) {
        return symbolTable.get(symbol);
    }
    
    public boolean symbolTableContainsKey(final String symbol) {
        return symbolTable.containsKey(symbol);
    }    
}
