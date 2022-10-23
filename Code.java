/*
 * TRANSLATE FIELD TO VALUE
 */

 import java.util.*;

public class Code {
    //table to store specs for DESTINATION, JUMP, COMPUTATION
    private static Hashtable <String, String> destination_table = new Hashtable<String, String>(8);
    private static Hashtable <String, String> jump_table = new Hashtable<String, String>(8);
    private static Hashtable <String, String> computation_table = new Hashtable<String, String>(28);

//---METHODS---

//SETTERS
//store destination
    private static void storeDestinationTable() {        
        destination_table.put("null", "000");
        destination_table.put("M", "001");      //RAM

        destination_table.put("D","010");       //D-Reg
        destination_table.put("MD", "011");

        destination_table.put("A", "100");      //A-Reg
        destination_table.put("AM", "101");
        destination_table.put("AD","110");
        destination_table.put("AMD", "111");
    }
    
//store jump
    private static void storeJumpTable() {
        jump_table.put("null", "000");

        jump_table.put("JGT", "001");	// jump if greater than zero
        jump_table.put("JEQ", "010");	// jump if equal to zero
        jump_table.put("JGE", "011");	// jump is greater than or equal to zero

        jump_table.put("JLT", "100");	// jump if less than zero
        jump_table.put("JNE", "101");	// jump if not equal to zero
        jump_table.put("JLE", "110");	// jump if less than or equal to zero

        jump_table.put("JMP", "111");	// unconditional jump        
    }

//store computaiton
    private static void storeComputationTable() {
        computation_table.put("0", "0101010");
        computation_table.put("1", "0111111");
        computation_table.put("-1", "0111010");

        computation_table.put("D", "0001100");  
        computation_table.put("A", "0110000");
        computation_table.put("!D", "0001101");
        computation_table.put("!A", "0110001");
        computation_table.put("-D", "0001111");
        computation_table.put("-A", "0110011");
        computation_table.put("D+1", "0011111");
        computation_table.put("A+1", "0110111");
        computation_table.put("D-1", "0001110");
        computation_table.put("A-1", "0110010");
        computation_table.put("D+A", "0000010");
        computation_table.put("D-A", "0010011");
        computation_table.put("A-D", "0000111");
        computation_table.put("D&A", "0000000");
        computation_table.put("D|A", "0010101");

        computation_table.put("M", "1110000");
        computation_table.put("!M", "1110001");
        computation_table.put("-M", "1110011");
        computation_table.put("M+1", "1110111");
        computation_table.put("M-1", "1110010");
        computation_table.put("D+M", "1000010");
        computation_table.put("D-M", "1010011");
        computation_table.put("M-D", "1000111");
        computation_table.put("D&M", "1000000");
        computation_table.put("D|M", "1010101");
    }

//---GETTER FUNCTIONS---//
    public static String getDestinationCode(final String key) {
        storeDestinationTable();
        return destination_table.get(key);
    }
    public static String getJumpCode(final String key) {
        storeJumpTable();
        return jump_table.get(key);
    }
    public static String getComputationCode(final String key) {
        storeComputationTable();
        return computation_table.get(key);
    }

}
