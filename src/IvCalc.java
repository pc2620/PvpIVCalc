import java.io.FileNotFoundException;
import java.util.*;
import java.util.Collections;
import java.lang.Object;
import java.util.Arrays;

public class IvCalc {
	
	public static double [] cpMultiplier = {0.094, 0.135137432, 0.16639787, 0.192650919, 0.21573247, 0.236572661, 0.25572005, 
			0.273530381, 0.29024988, 0.306057377, 0.3210876, 0.335445036, 0.34921268, 0.362457751, 0.37523559, 0.387592406, 
			0.39956728, 0.411193551, 0.42250001, 0.432926419, 0.44310755, 0.4530599578, 0.46279839, 0.472336083, 
			0.48168495, 0.4908558, 0.49985844, 0.508701765, 0.51739395, 0.525942511, 0.53435433, 0.542635767, 
			0.55079269, 0.558830576, 0.56675452, 0.574569153, 0.58227891, 0.589887917, 0.59740001, 0.604818814,
			0.61215729, 0.619399365, 0.62656713, 0.633644533, 0.64065295, 0.647576426, 0.65443563, 0.661214806, 
			0.667934, 0.674577537, 0.68116492, 0.687680648, 0.69414365, 0.700538673, 0.70688421, 0.713164996, 
			0.71939909, 0.725571552, 0.7317, 0.734741009, 0.73776948, 0.740785574, 0.74378943, 0.746781211, 
			0.74976104, 0.752729087, 0.75568551, 0.758630378, 0.76156384, 0.764486065, 0.76739717, 0.770297266,
			0.7731865, 0.776064962, 0.77893275, 0.781790055, 0.78463697, 0.787473578, 0.79030001};
	
	public static double [] cpMultiplier2 = cpMultiplier2();
	public static double [] cpMultiplier4 = cpMultiplier4();
	
	
	
	public static double [] cpMultiplier2() { //
		
		double [] cpMultiple = new double[cpMultiplier.length];
		
		int counter = 0;
		
		for(double i : cpMultiplier) {
			cpMultiple[counter] = i*i;	
			counter++;
		}
		return Arrays.copyOf(cpMultiple, cpMultiple.length);
	}
	
	public static double [] cpMultiplier4() { //
			
			double [] cpMultiple = new double[cpMultiplier.length];
			
			int counter = 0;
			
			for(double i : cpMultiplier2) {
				cpMultiple[counter] = i*i;	
				counter++;
			}
			
			return Arrays.copyOf(cpMultiple, cpMultiple.length);
		}
	//this code return index closest to possible level based off stat distribution
	//using a binary search
	public static int binSearch(double [] arr, double key){
		
		int i = 0; //
		int j = arr.length - 1; //
		
		
		
		while(i <= j) {
			
			int k = (i+j)/2;
			
			if(arr[k] < key) {
				
				i = k + 1;
				
			}
			else if(arr[k] > key) {
				j = k - 1;
				
			}
			else {
				return k;
			}
		}
		
		return i - 1; //returns closest possible level
	}
	
	//this block return specific iv for user's rank
	public static int getCp(int attack, int defense, int hp, double cpMultiple2x) {		
		 return (int)Math.floor(attack * Math.sqrt(defense*hp)* cpMultiple2x / 10);
	}
	//this method calls binary search function to determine pokemon's ...
	//level based of stats and whether or not its great/ultra/master's league
	public static int lvlCap(int attack, int defense, int hp, int leagueCp) {	
		return binSearch(cpMultiplier4, (double)(leagueCp+1)*(leagueCp+1)*100/((double)attack*(double)attack*(double)defense*(double)hp));
	}
	
	public static void pvpCalc(int cpLeague, String name, int myAttack, int myDefense, int myHP) throws FileNotFoundException {
		
		int [][] ivCombos = new int[16*16*16][3];
		
		//for loop to add all posible IV combinations
		
		//int i = 0,j = 0,k = 0;
		int x=0;
		int counterTotal = 0;
		//int countTo2=0;//counter for placing att,def,hp
		for(int i = 0;i<=15;i++) {		
			for(int j =0;j<=15;j++) {				
				for(int k = 0;k<=15;k++) {					
					ivCombos[counterTotal][0] = i;
					ivCombos[counterTotal][1] = j;
					ivCombos[counterTotal][2] = k; 
					counterTotal++;
				}
			}
		}
		
		double [][] allProducts = new double [16*16*16][5];		
		
		
		pokemonInfo pokeCheck = new pokemonInfo();
		
		pokeCheck.pokemonDataRequest(name);		
		
		//builds a full list for every possible combination for specified pokemon
		for(int i = 0; i < ivCombos.length;i++) {
			int attack = (int) Math.floor(pokeCheck.getAttack()+ivCombos[i][0]);
			int defense = (int) Math.floor(pokeCheck.getDefense()+ivCombos[i][1]);
			int hp = (int) Math.floor(pokeCheck.getHp()+ivCombos[i][2 ]);
			int lvl = lvlCap(attack, defense, hp, cpLeague);
			double statProduct = cpMultiplier2[lvl]*attack*defense*Math.floor(cpMultiplier[lvl]*hp);
			allProducts[i][0] = statProduct;
			allProducts[i][1] = lvl;
			allProducts[i][2] = ivCombos[i][0];	//attack stat at index i
			allProducts[i][3] = ivCombos[i][1]; //defense stat at index i
			allProducts[i][4] = ivCombos[i][2]; //hp stat at index i
		}
		
		//sorts products is ascending number
		//to sort a multidimensional array allProducts
		
		java.util.Arrays.sort(allProducts, new java.util.Comparator<double[]>() {

			@Override
			public int compare(double[] o1, double[] o2) {
				return Double.compare(o1[0], o2[0]);
			}
			
		});
	
		//this loop below finds rank for user's specific combination 
		int rank = 0;
		
		for (int i = 0; i < allProducts.length;i++) {
			if(allProducts[i][2] == myAttack && allProducts[i][3] == myDefense && allProducts[i][4] == myHP)
				rank = 4096 - i - 1;
		}
		
		
		int attack = (int) (Math.floor(pokeCheck.getAttack())+myAttack);
		int defense = (int) (Math.floor(pokeCheck.getDefense())+myDefense);
		int HP = (int) (Math.floor(pokeCheck.getHp())+myHP);
		int lvl = lvlCap(attack,defense, HP, cpLeague);
		double myStatProduct = cpMultiplier2[lvl]*attack*defense*Math.floor(cpMultiplier[lvl]*HP);
		double pokePerfection = (myStatProduct/allProducts[allProducts.length-1][0])*100;
		

		System.out.println("rank" + "\t" +"stat product" + "\t" + "attack" +"\t" +"defense" +"\t" +"hp"+ "\t" + "Level" +"\t"+ "CP"+ "\t" + "perfect%");
		
		System.out.println(rank + "\t" + Double.parseDouble(String.format("%.3f", myStatProduct))+ "\t" + myAttack + "\t"+ myDefense + "\t" 
		+myHP + "\t" + ((lvl/2.0)+1.0) + "\t" +getCp(attack,defense,HP, cpMultiplier2[lvl]) + "\t" + Double.parseDouble(String.format("%.3f", pokePerfection)));
		
		for (int i = 1; i <=10;i++) {
			double perfection = (allProducts[4096-i][0]/allProducts[allProducts.length-1][0])*100;
			System.out.println(i + "\t" + Double.parseDouble(String.format("%.3f", allProducts[4096-i][0]))+ "\t" + (int)allProducts[4096-i][2] + "\t"+ (int)allProducts[4096-i][3] + "\t" 
					+ (int)allProducts[4096-i][4] + "\t" + ((allProducts[4096-i][1]/2.0)+1.0) + "\t" + getCp((int)allProducts[4096-i][2]+pokeCheck.getAttack(),(int)allProducts[4096-i][3]+pokeCheck.getDefense(),(int)allProducts[4096-i][4]+pokeCheck.getHp(), cpMultiplier2[(int)((allProducts[4096-i][1]))]) + "\t" + Double.parseDouble(String.format("%.3f", perfection)));
		}
		
	}
	
}

