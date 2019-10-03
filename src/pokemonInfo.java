import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//this class is simply used to look for specified pokemon in file
//returns all data ivcalc class will need to process
public class pokemonInfo {
	
	private int dexNumber; 
	private String name;
	private int attack;
	private int defense;
	private int hp;
	
	//pull pokemon info to be calculated
	public void pokemonDataRequest(String name) throws FileNotFoundException{
		
		Scanner filename = new Scanner(new File("files/pokeData.txt"));
		//string to be split
		String data;
		
		
		
		while(filename.hasNextLine()) {
			
			data = filename.nextLine();
			
			//split string into an array
			
			String[] token = data.split(" "); //whitespaces being the delimiter based on our specific data set
			//System.out.println(token[4]);
			//if name is present then we have data to be calculated
			if(token[1].equalsIgnoreCase(name)) {
				this.dexNumber = Integer.parseInt(token[0]);
				this.name = token[1];
				this.attack = Integer.parseInt(token[3]);
				this.defense = Integer.parseInt(token[4]);
				this.hp = Integer.parseInt(token[5]);
				
				System.out.println(dexNumber+" " + name + " " + attack + " "+ defense + " " + hp);
				break;
				
			}
			
		}
		
		filename.close(); 
		
	}
	//getter methods for IvCalc class
	//name, attack, defense, hp are the only data we need
	
	public String getName() {
		return this.name;
	}
	
	public int getAttack() {
		return this.attack;
	}
	public int getDefense() {
		return this.defense;
	}
	public int getHp() {
		return this.hp;
	}

}
