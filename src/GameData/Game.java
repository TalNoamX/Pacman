package GameData;

import java.util.ArrayList;
/**
 * This class is a data structure for the game.
 * it keeps two ArrayLists, one for the fruits and one for the pacmans.
 * @author Tal
 *@author Oranit
 *@author Amitai
 */
public class Game {
	
	private Player playa;
	private ArrayList<Pacman> pList;
	private ArrayList<Fruit> fList;
	private ArrayList<Ghost> gList;
	private ArrayList<Block> bList;
	
	/**
	 * copy constructor
	 * @param game copy from this object
	 */
	public Game(Game game) {
		playa = new Player(game.player());
		pList = new ArrayList<Pacman>(game.pList());
		fList = new ArrayList<Fruit>(game.fList());
		bList = new ArrayList<Block>(game.bList());
		gList = new ArrayList<Ghost>(game.gList());
	}
	/**
	 * This function give the user the option to use the ArrayList methods
	 * @return ArrayList<Pacman>
	 */
	public ArrayList<Pacman> pList() {
		return pList;
	}
	/**
	 * This function give the user the option to use the ArrayList methods
	 * @return ArrayList<Fruit>
	 */
	public ArrayList<Fruit> fList() {
		return fList;
	}
	/**
	 * This function give the user the option to use the ArrayList methods
	 * @return ArrayList<Blocks>
	 */
	public ArrayList<Block> bList() {
		return bList;
	}
	/**
	 * This function give the user the option to use the ArrayList methods
	 * @return ArrayList<Ghost>
	 */
	public ArrayList<Ghost> gList() {
		return gList;
	}
	
	public Player player() {
		return playa;
	}
	public void setBoardData(ArrayList<String> board) {
		for(String line: board) {
			String[] data = line.split(",");
			if(line.startsWith("P")) {
				pList.add(new Pacman(data[1], data[2], data[3], data[4], data[5], data[6]));
			}
			else if(line.startsWith("M")) {
				playa = new Player(data[1], data[2], data[3], data[4], data[5]);
			}
			else if(line.startsWith("F")) {
				fList.add(new Fruit(data[1], data[2], data[3], data[4], data[5]));
			}
			else if(line.startsWith("G")) {
				gList.add(new Ghost(data[1], data[2], data[3], data[4], data[5], data[6]));
			}
			else if(line.startsWith("B")) {
				bList.add(new Block(data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
			}
		}
	}
	public void clear() {
		playa=null;
		pList.clear();
		fList.clear();
		bList.clear();
		gList.clear();
	}
}
