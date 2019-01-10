package Algorithms;

import java.util.concurrent.TimeUnit;

import GameData.Fruit;
import GameData.Game;
import GameData.Player;
import Geom.Point3D;
import Maps.MyFrame;
import coords.MyCoords;
/**
 * This class made to calculate the shortest path from one pacman or more. 
 * after we have the shortest path it update every pacman's path to be as fast as possible.
 * and when all the paths are ready with "Start" function it call the thread to move the pacmans at the same time.
 * @author 1.Tal 
 * @author 2.Amitai
 * @author 3.Oranit
 *
 */
public class ShortestPathAlgo {
	private MyFrame MF;
	private Game game;

	/**
	 * constructor
	 * @param game to get the pList and fList
	 * @param MF to print the pacmans later 
	 */
	public ShortestPathAlgo(MyFrame MF) {
		this.getTheShortestPath(); // we want to split the run time of this algorithm so we call this function upon initialization.
		this.MF=MF;
	}
	
	/**
	 * This function creates the Shortest Path this the path class.
	 * @return the shortestPath is the best way to split the pacmans among the fruit.
	 */
	private void getTheShortestPath() { 
			Path temp = new Path(game.fList(),game.player()); //call the "path" class the create a new pacman path
			game.player().setPath(temp); // set the path of every pacman. we use it in pacmansPath after.
	}

	/**
	 * This function activate the shortest path.
	 *  it take every pacman to it's fruit "physically" by sending threads to the ShortestPathAlgoThread class.
	 */
	public void Start() { 
		for(int Index=0;Index<game.player().getPath().size();Index++) { //run on the path arraylist of every pacman
			fullGamePath(game.player(), game.player().getPath().get(Index).getFruit(),Index); //call the function
			game.player().getPath().get(Index).setTime();
		}
		MF.results();
	}
	
	/**
	 * function that move the player all the way to his fruit
	 * @param our player that move to the fruit
	 * @param fru the goal of the player
	 * @param Index use to get the run time of the fruit from the pacman path
	 */
	public void fullGamePath (Player pac, Fruit fru,int Index) { //this function take the path of every pacman and move the pucman to every fruit on uts path.
		double x,y,z; 
		MyCoords coords=new MyCoords();
		double runTime=pac.getPath().get(Index).getRunTime(); //runTime
		double dist=coords.distance3d(pac.getPoint(),fru.getPoint()); //distance between the pacman and each fruit.
		Point3D midvec=coords.vector3D(pac.getPoint(),fru.getPoint()); //the vector between pacman and fruit.
		if(runTime>0) { //check if the runTime is valid.  
			x=midvec.x()/runTime; //Divide the vector lat, lon, and alt distance by the runTime  to make a new distance unit.
			y=midvec.y()/runTime; 
			z=midvec.z()/runTime; 
			while(dist>=1) { //check if the distance is still bigger then the radius.
				midvec=new Point3D(x,y,z); //this new vector is the pacman next step on the way to the fruit.
				pac.setPoint(coords.add(pac.getPoint(), midvec)); //we move the pacman to the vector coords after convert its to a GPS point.
				dist=coords.distance3d(pac.getPoint(), fru.getPoint()); //take the new distance
				runTime=dist/20;//runtime -1 because we just make progress by one 1 second
				midvec=coords.vector3D(pac.getPoint(), fru.getPoint()); //make new vector
				x=midvec.x()/runTime; //Divide the vector lat, lon, and alt distance by the runTime  to make a new distance unit.
				y=midvec.y()/runTime;
				z=midvec.z()/runTime;				
				MF.repaint();
				runTime--;
			try {TimeUnit.MILLISECONDS.sleep(50);} 
			catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}