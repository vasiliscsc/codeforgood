import java.io.*;
import java.util.*;

/**
 * Saturated Reverse Polish Notation calculator implemented in Java
 * Part of Year 1 CM10227 Principles of Programming 1A course at University of Bath
 * As required by specifications, stack size is 23 entries
 * and it operates with 32-bit integers (between -2 147 483 648 and +2 147 483 647)
 * 
 * @author Daniil Demishin
 * Last updated 25 Nov 2016
 * 
 */

public class SRPN{
	
	List<Integer> stack = new Stack<Integer>(); //Now stack only accepts integers;
	/**
	 * Upon closer inspection, what looked like a random number generating function
	 * executed by entering symbol "r" on the keyboard turned out to be a call to
	 * a 100-element list of values which gives (callnumber%100)-th position in it 
	 */
    List<Integer> RandomNumbers = Arrays.asList(1804289383, 846930886, 1681692777, 1714636915, 1957747793, 424238335, 719885386, 1649760492, 596516649, 1189641421, 1025202362,
               1350490027, 783368690, 1102520059, 2044897763, 1967513926, 1365180540, 1540383426, 304089172, 1303455736, 35005211, 521595368,
               294702567, 1726956429, 336465782, 861021530, 278722862, 233665123, 2145174067, 468703135, 1101513929, 1801979802, 1315634022,
               635723058, 1369133069, 1125898167, 1059961393, 2089018456, 628175011, 1656478042, 1131176229, 1653377373, 859484421, 1914544919,
               608413784, 756898537, 1734575198, 1973594324, 149798315, 2038664370, 1129566413, 184803526, 412776091, 1424268980, 1911759956,
               749241873, 137806862, 42999170, 982906996, 135497281, 511702305, 2084420925, 1937477084, 1827336327, 572660336, 1159126505,
               805750846, 1632621729, 1100661313, 1433925857, 1141616124, 84353895, 939819582, 2001100545, 1998898814, 1548233367, 610515434,
               1585990364, 1374344043, 760313750, 1477171087, 356426808, 945117276, 1889947178, 1780695788, 709393584, 491705403, 1918502651,
               52392754, 1474612399, 2053999932, 1264095060, 1411549676, 1843993368, 943947739, 1984210012, 855636226, 1749698586, 1469348094,
               1956297539); //http://stackoverflow.com/questions/6520382/what-is-the-shortest-way-to-initialize-list-of-strings-in-java
    int RandNumPos = 0;
    int RandNum = RandomNumbers.get(RandNumPos);
    
    //public void SRPN(){
    //	System.out.println("SRPN initialised!");
	//}
    
    public boolean checkForInt(String s, int n){
		for(int i=0; i<=9; i++){
        	if (s.charAt(n) == Integer.toString(i).charAt(0)) {
        		return true;
        	}
		}
		return false;
	}
    
	public int integerDetected(char sign, String s, int n){
		int count = 0; // number of elements for the recognition
		StringBuilder untilNotInt = new StringBuilder(); // http://stackoverflow.com/questions/1654546/java-add-chars-to-a-string
		if (sign == '-'){
			untilNotInt.append(s.charAt(n-1));
		}
		if (s.charAt(n) == '0'){
			int extracount = 0; // number of integers between first 8 or 9 and first anything-but-int
			count++;
        	untilNotInt.append(s.charAt(n));
        	for(int x = n; x <= s.length()-2; x++){
        		if(checkIfIntNext(s, x, 7)){
        			count++;
        			untilNotInt.append(s.charAt(x+1));
        		}
        		else if (s.charAt(x+1) == '8' || s.charAt(x+1) == '9'){
        			for(int y = x; y <= s.length()-2; x++){
        				if(checkIfIntNext(s, y, 9)){
        					extracount++;
        				}
        				else{
        					if (stack.size()<=22){
        						if (Long.parseLong(untilNotInt.toString(), 8) > Integer.MAX_VALUE){
        							stack.add(Integer.MAX_VALUE);
        						}
        						else if (Long.parseLong(untilNotInt.toString(), 8) < Integer.MIN_VALUE){
        							stack.add(-2147483648);
        						}
        						else{
        							stack.add(Integer.parseInt(untilNotInt.toString(), 8));
        						}
        					}
        					else{
        						System.err.println("Stack overflow.");
        					}
        					return count+extracount;
        				}
        				y++;
        			}
        		}
        		else{ // if any non-integer is found, "cut the cord" and add to stack
        			if (stack.size()<=22){
						if (Long.parseLong(untilNotInt.toString(), 8) > Integer.MAX_VALUE){
							stack.add(Integer.MAX_VALUE);
						}
						else if (Long.parseLong(untilNotInt.toString(), 8) < Integer.MIN_VALUE){
							stack.add(-2147483648);
						}
						else{
							stack.add(Integer.parseInt(untilNotInt.toString(), 8));
						}
					}
					else{
						System.err.println("Stack overflow.");
					}
					return count+extracount;
        		}
        	}
        	if (stack.size()<=22){
				if (Long.parseLong(untilNotInt.toString(), 8) > Integer.MAX_VALUE){
					stack.add(Integer.MAX_VALUE);
				}
				else if (Long.parseLong(untilNotInt.toString(), 8) < Integer.MIN_VALUE){
					stack.add(-2147483648);
				}
				else{
					stack.add(Integer.parseInt(untilNotInt.toString(), 8));
				}
			}
			else{
			}
			return count+extracount;
		}
        else{ // first element is non-zero, so that decimal mode is on
        	count++;
        	untilNotInt.append(s.charAt(n));
        	for(int x = n; x <= s.length()-2; x++){
        		if(checkIfIntNext(s, x, 9)){
        			count++;
        			untilNotInt.append(s.charAt(x+1));
        		}
        		else{
        			if (stack.size()<=22){
        				if(Long.parseLong(untilNotInt.toString()) > Integer.MAX_VALUE){
        					stack.add(Integer.MAX_VALUE);
        				}
        				else if(Long.parseLong(untilNotInt.toString()) < Integer.MIN_VALUE){
        					stack.add(-2147483648);
        				}
        				else{
        					stack.add(Integer.parseInt(untilNotInt.toString()));
        				}
					}
					else{
						System.err.println("Stack overflow.");
					}
					return count;
        		}
        	}
        	if (stack.size()<=22){
        		if(Long.parseLong(untilNotInt.toString()) > Integer.MAX_VALUE){
					stack.add(Integer.MAX_VALUE);
				}
				else if(Long.parseLong(untilNotInt.toString()) < Integer.MIN_VALUE){
					stack.add(-2147483648);
				}
				else{
					stack.add(Integer.parseInt(untilNotInt.toString()));
				}
			}
			else{
				System.err.println("Stack overflow.");
			}
			return count; // in case the last few elements are all integers
		}
	}
	
	
	public boolean checkIfIntNext(String s, int x, int k){ // check if next symbol is integer no greater than k
		for(int j=0; j<=k; j++){
			if (x == s.length()-1){
				return false;
			}
			else if (s.charAt(x+1) == Integer.toString(j).charAt(0)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkForCmd(String s, int n){
		String cmds = "+-*/^%"; 
		for(int i=0; i<=cmds.length()-1; i++){
			if (s.charAt(n) == cmds.charAt(i)){
				return true;
			}
		}
		return false;
	}
	
	public int cmdExecution(String s, int n){
		if (stack.size() <= 1 && s.charAt(n) != '-'){
			System.err.println("Stack underflow");
		}
		else{
			if (s.charAt(n) == '+'){
				long sum = (long) stack.get(stack.size()-2) + (long) stack.get(stack.size()-1);
				checkForSaturation(sum);
			}
			else if (s.charAt(n) == '-'){//check fancy case of -Int.MIN
				if(checkIfIntNext(s, n, 9)){ // in this case this is not an operation, but an indicator of a negative value for the next integer
					int nIncr = integerDetected('-', s, (n+1)); // jump by length of number (and then +1 for the loop) => first element after it
					return nIncr;
				}
				else{
					if (stack.size() <= 1){
						System.err.println("Stack underflow.");
					}
					else{
						long diff = (long) stack.get(stack.size()-2) - (long) stack.get(stack.size()-1);
						checkForSaturation(diff);
					}
				}
			}
			else if (s.charAt(n) == '*'){
				long mult = (long) stack.get(stack.size()-2) * (long) stack.get(stack.size()-1);
				checkForSaturation(mult);
			}
			else if (s.charAt(n) == '/'){
				if (stack.get(stack.size()-1) == 0){
					System.err.println("Divide by 0.");
				}
				else{
					long frac = (long) stack.get(stack.size()-2) / (long) stack.get(stack.size()-1);
					checkForSaturation(frac);
				}
			}
			else if (s.charAt(n) == '^'){
				if (stack.get(stack.size()-1) <= 0){
					System.err.println("Negative power.");
				}
				else{
					int pow = (int) Math.pow(stack.get(stack.size()-2), stack.get(stack.size()-1));
					stack.set(stack.size()-2, pow); // change the value of the second to last element by raising it to the power of the top one 
					stack.remove(stack.size()-1); // and delete the top one
				}
			}
			else if (s.charAt(n) == '%'){
				if (stack.get(stack.size()-1) == 0){
					System.err.println("Divide by zero.");
				}
				else{
					long rem = (long) stack.get(stack.size()-2) % (long) stack.get(stack.size()-1);
					checkForSaturation(rem);
				}
			}
		}
		return(0);
	}
	
	public void checkForSaturation(long res){
		if(res > Integer.MAX_VALUE){
			stack.set(stack.size()-2, Integer.MAX_VALUE);
		}
		else if(res < Integer.MIN_VALUE){
			stack.set(stack.size()-2, -2147483648);
		}
		else{
			stack.set(stack.size()-2, (int) res);
		}
		stack.remove(stack.size()-1);
	}
		
	
	public void processCommand(String s) {
		for (int n = 0; n <= s.length()-1; n++){//actions will be made step-by-step for all characters in the string
			if (s.charAt(n) == '#'){
				n = s.length()-1;
			}
			else if (checkForInt(s, n)){
				n += integerDetected('+', s, n)-1;
			}
			else if (checkForCmd(s, n)){
				n+=cmdExecution(s, n);
			}
			else if (s.charAt(n) == 'd') {// procedure for display stack
				for (int i = 0; i <= stack.size()-1; i++){
	        		System.out.println(stack.get(i)); //prints all elements of the stack bottom to top
	        	}
	        }
			else if (s.charAt(n) == 'r') { // procedure for "random" number generation
				if (stack.size()<=22){
					stack.add(RandNum);
					if (RandNumPos<99){
		        		RandNumPos++;
		        	}
		        	else {
		        		RandNumPos = 0;
		        	}
		        	RandNum = RandomNumbers.get(RandNumPos);
				}
				else{
					System.err.println("Stack overflow.");
				}
			}	        
			else if (s.charAt(n) == '=') {// procedure for equal sign
	        	if (stack.size() != 0){
	        		System.out.println(stack.get(stack.size()-1)); //prints the top member of the stack
	        	}
	        	else{
	        		System.err.println("Stack empty.");
	        	}
	        }
			else if (s.charAt(n) == ' '){	}
			else{
				System.err.println("Unrecognised operator or operand \"" + s.charAt(n) + "\".");
			}
		}
	}//end of processCommand
    
    public static void main(String[] args){
        SRPN srpn = new SRPN();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            //Keep on accepting input from the command-line
            while(true) {
                String command = reader.readLine();
                
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null){
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }
                
                //Otherwise, (attempt to) process the character
                srpn.processCommand(command);          
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}
