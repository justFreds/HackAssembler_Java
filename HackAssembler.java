/*
 * Main 
 *      Drives process
 *      Translates Hack Assembly To Machine Code
 */

import java.io.*;
import java.util.*;

public class HackAssembler {
    private final SymbolTable symbols;      //stores pre/user-defined variables
    private int currentLine;                //track current line
    private Parser parser;                  //parses

    //constructor
    public HackAssembler() {
        symbols = new SymbolTable();
        currentLine = 0;
    }

    //main
    public static void main(String[] args) {
        final String fileName = args[0];
        final HackAssembler assembler = new HackAssembler();

        assembler.noteLabels(fileName);
        assembler.translateHack(fileName);
    }

//--METHODS--//

//Scans thru fileName noting only labels
//Adds First Occurance only to SymbolTable
    private void noteLabels(final String fileName) {
        try {
            final BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
            boolean parseSuccess;
            String line;

            while((line = inputFile.readLine()) != null) {
                parser = new Parser();
                parseSuccess = parser.parseCommand(line);

                if(parseSuccess) {
                    if(line.trim().charAt(0) == '(') {  //CHECK FOR LABELS
                        final String symbolString = line.trim().substring(line.indexOf("(") + 1, line.lastIndexOf(")"));

                        if(!symbols.symbolTableContainsKey(symbolString)) {
                            symbols.putKeyValue(symbolString, currentLine);
                        }                        
                        currentLine--;  //LABEL DECLARATIONS DON'T COUNT
                    }
                    currentLine++;      //PARSE SUCCESSFUL COUNT
                }
            }

            inputFile.close();

        } catch (final IOException ioe) {
            System.out.println(ioe);
            return;
        }
    }

//TRANSLATES HackAssembly (.asm) File into MachineCode(.hack) file     
    private void translateHack(final String fileName) {
        try {
            //Swaps .asm to .hack
            final String output_fileName = fileName.substring(0, fileName.indexOf(".")) + ".hack";
            final BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
            final PrintWriter outputFile = new PrintWriter(output_fileName);

            currentLine = 0;    //reset currentline counter
            boolean parseSuccess;
            String line;

            while((line = inputFile.readLine()) != null) {
                parser = new Parser();
                parseSuccess = parser.parseCommand(line);

                if(parseSuccess && line.trim().charAt(0) != '(') {

                    if(parser.getAddress() == null) { //PARSE C-INSTR
                        final String computation = Code.getComputationCode(parser.getComputation());
                        final String destination = Code.getDestinationCode(parser.getDestination());
                        final String jump = Code.getJumpCode(parser.getJump());
                        outputFile.printf("111%s%s%s\n", computation, destination, jump);

                    }else {
                        final String parserAddress = parser.getAddress();

                        final Scanner sc = new Scanner(parserAddress);

                        if(sc.hasNextInt()) {   //check if parserAddress is an int
                            //convert to binary
                            final String addressBinary = Integer.toBinaryString(Integer.parseInt(parserAddress));
                            
                            //write binary as 16-bit
                            outputFile.println(pad_binary(addressBinary));

                        } else {
                            symbols.addVariable(parserAddress);
                            final String addressBinary = Integer.toBinaryString(symbols.getKeyValue(parserAddress));

                            outputFile.println(pad_binary(addressBinary));
                        }
                        sc.close();
                    }
                    currentLine++;
                }
            }
            inputFile.close();
            outputFile.close();

        } catch (final IOException ioe) {
            System.out.println(ioe);
            return;
        }

    }

//Pads Binary to retarin 16-bit format
    private String pad_binary(final String unpadded_binary) {
        String padded_binary = "";
        final int num_zeros = 16 - unpadded_binary.length();

        for(int i = 0; i < num_zeros; i++) {
            padded_binary += "0";
        }

        return padded_binary + unpadded_binary;
    }

}

