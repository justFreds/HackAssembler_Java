/*
 * Unpacks string containing instructions into fields
 * Specified by Hack Language
 */


public class Parser {
    private String destination;         //DESTNIATION INSTR
    private String computation;         //COMPUTATION INSTR
    private String jump;                //JUMP INSTR
    private String address;             //16-BIT ADDRESS

    public Parser() {
        destination = "null";
        jump = "null";
    }



//---METHODS---//

//PARSES A INSTRUCTION
    public boolean parseCommand(String line) {
        line = line.trim(); //remove leading/trailing whitespace

        if(!line.isEmpty()) {

            if(line.charAt(0) != '/') {

                if(line.contains("@")) { //A-INSTR
                    address = line.split("@")[1].trim();    //LABEL || VARIABLE || NUMBER 

                } else { //C-INSTR

                    if(line.contains("=")) { //DESTINATION
                        final String[] fields = line.split("=");
                        destination = fields[0];

                        if(fields[1].contains(";")) {   //JUMP
                            split_jump(fields[1]);

                        } else {
                            computation = fields[1].split("/")[0].trim(); //remove comments/whitespace
                        }

                    } else if(line.contains("+") || line.contains("-")) { //contain computation field; may contain jump
                        
                        if(line.contains(";")) {
                            split_jump(line);

                        } else {                           
                            computation = line.split("/")[0].trim();
                        }

                    } else if (line.contains(";")) {
                        split_jump(line);

                    } else {
                        jump = line.split("/")[0].trim();
                    }
                }
                return true;    //SUCCESS PARSE
            }
        }
        return false;   //FAILED PARSE
    }

//CHECKS FOR JUMP FIELD
    private void split_jump(final String str) {        
        final String[] parts = str.split(";");
        computation = parts[0].trim();
        jump = parts[1].split("/")[0].trim();
    }

//---GETTER FUNCTIONS---//
//ACCESS PRIVATE VARIABLES IN CLASS
    public String getComputation() {
        return computation;
    }
    public String getDestination() {
        return destination;
    }
    public String getJump() {
        return jump;
    }
    public String getAddress() {
        return address;
    }
}