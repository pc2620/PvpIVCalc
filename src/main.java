import java.io.FileNotFoundException;
import java.util.*;

public class main {
	public static void main(String args[]) throws FileNotFoundException{
		
		Scanner input = new Scanner(System.in);
		
		//System.out.println("What Pokemon would you like to check");
		
		IvCalc polo = new IvCalc();
		
		IvCalc.cpMultiplier2();
		IvCalc.cpMultiplier4();
		IvCalc.pvpCalc(1500, "Charizard" , 1, 11, 11);
		 
	}
}
