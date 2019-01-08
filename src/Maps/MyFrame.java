package Maps;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.mysql.jdbc.TimeUtil;


import Geom.Point3D;
import Robot.Play;
import GameData.Block;
import GameData.Fruit;
import GameData.Game;
import GameData.Ghost;
import GameData.Pacman;
import GameData.moveThePackman;

/**
 * A GUI class that presents the Game.
 * @author Oranit
 * @author Amitay
 * @author Tal
 *
 */
public class MyFrame extends JFrame implements MouseListener,ComponentListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage myImage; //The image

	private Map map; 
	private Game game;
	private BufferedImage playerImg; //An Icon for the pacman
	private BufferedImage FruitImg;// An Icon for the Fruit
	private BufferedImage pacmanImg;
	private BufferedImage ghostImg;
	//Menu Items
	private MenuItem loadCSV;
	private MenuItem run;
	private MenuItem ME;
	//will tell us whether to add a pacman or a fruit when pressed
	private boolean playerButton = false;
	//ID for fruit and pamans in case the user is drawing it on the screen
	double height;
	double width;
	int imgheight;// The map image height
	int imgwidth;// The map image width
	private Play play;
	private boolean player;
	private double azimuth=0;
	private boolean flag;
	

	/**
	 * 
	 * @param path - The image's path.
	 * @param leftTop - left top corner of the map.
	 * @param rightBottom - right bottom corner of the map.
	 * @param rightTop - right top corner of the map.
	 */
	public MyFrame(String path, Point3D leftTop, Point3D rightBottom, Point3D rightTop) {
		map = new Map(path, leftTop, rightBottom, rightTop);//Setting a new map
		myImage = map.getmyImage();// setting the map image
		game = new Game();
		setMenu();	//setting the menu bar with all the options
		imgwidth=myImage.getWidth();
		imgheight=myImage.getHeight();
		//adding the image to the frame
		JLabel label1 = new JLabel(new ImageIcon(myImage));
		add(label1);
		setActionListeners();//adding action listeners to the menu Items
		setImages();//setting the image of the pacman and the fruit
		this.addMouseListener(this); //adding mouselisteners
		this.addComponentListener(this);
	}

	/**
	 * Setting the Menu Items - 
	 * first will be "file" the contain run, load CSV, and save as kml.
	 * Second will be "draw images" that will make it possible to make game by drawing pacmans and fruits on the map
	 */
	private void setMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("File");  
		loadCSV = new MenuItem("Load csv");
		run = new MenuItem("Run");
		ME=new MenuItem("set player");
		menu1.add(loadCSV);
		menu1.add(run);
		menu1.add(ME);
		menuBar.add(menu1);
		this.setMenuBar(menuBar);
	}

	/**
	 * setting the Icons for pacmans and fruits.
	 */
	public void setImages() {
		try {
			File file = new File("data//pickachu.jpg");
			playerImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			File file = new File("data//strawberry.jpg");
			FruitImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			File file = new File("data//jigglypuff.jpg");
			pacmanImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			File file = new File("data//Gastly.jpg");
			ghostImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Setting all the needed ACtionListeners For the menuItem.
	 */
	public void setActionListeners() {
		loadCSV.addActionListener(new ActionListener() {	//Loading CSV game files usinf FileChooser 
			public void actionPerformed(ActionEvent e) {
				game.clear();
				JButton open = new JButton();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("þþDocuments"));
				fileChooser.setDialogTitle("Load CSV");
				if(fileChooser.showOpenDialog(open)==JFileChooser.APPROVE_OPTION) {

				}
				if(fileChooser.getSelectedFile().getAbsolutePath()!=null) {
					if(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".csv")) {
						play = new Play(fileChooser.getSelectedFile().getAbsolutePath());
						playerButton=true;
						game.setBoardData(play.getBoard());
						play.setIDs(313340267,204324305,204397715);
						repaint();
						
					}
					else JOptionPane.showMessageDialog(null, "Not a CSV file, Please try again");
				}
			}

		});
		run.addActionListener(new ActionListener() {	//Starting The Game.
			public void actionPerformed(ActionEvent e) {
			//	if(play!=null&&player) {
					play.start();
					playerButton=true;
				moveThePackman mtp=new moveThePackman(MyFrame.this);
				mtp.start();
			
			//	else JOptionPane.showMessageDialog(null, "No game was loaded yet.");
			}
		});
		
		
	}
public void packmanStep() {
	game.setBoardData(play.getBoard());
	play.rotate(azimuth);
	repaint();
}
	/**
	 *  Drawing the maps image, and painting the pacman and the fruits.
	 *  @param g Graphics object that will draw everything we need.
	 */
	public  void paint(Graphics g) {
Image image = createImage(5000,5000);
		Graphics g1=image.getGraphics();
		g1.drawImage(myImage, 8,53, this.getWidth()-16,this.getHeight()-61,this);//Drawing the map image
		if(play!=null) {
			game.setBoardData(play.getBoard());
			Point3D pixelPoint = map.coords2pixels(game.player().getPoint()); 
			int x = (int)(pixelPoint.x()*(width/imgwidth));//convert the point to pixel depends on the img size
			int y = (int)(pixelPoint.y()*(height/imgheight));
			g1.drawImage(playerImg, x-16, y-16, playerImg.getWidth(),  playerImg.getHeight(),this);
			if(game.pList()!=null) {
				for(Pacman p: game.pList()) {
					pixelPoint = map.coords2pixels(p.getPoint()); 
					x = (int)(pixelPoint.x()*(width/imgwidth));//convert the point to pixel depends on the img size
					y = (int)(pixelPoint.y()*(height/imgheight));
					g1.drawImage(pacmanImg, x, y, pacmanImg.getWidth(), pacmanImg.getHeight(), this);
				}
			}
			if(game.fList()!=null) {
				for(Fruit f: game.fList()) {
					pixelPoint = map.coords2pixels(f.getPoint()); 
					x = (int)(pixelPoint.x()*(width/imgwidth));//convert the point to pixel depends on the img size
					y = (int)(pixelPoint.y()*(height/imgheight));
					g1.drawImage(FruitImg, x ,y, FruitImg.getWidth(), FruitImg.getHeight(), this);
				}
			}
			if(game.gList()!=null) {
				for(Ghost gh: game.gList()) {
					pixelPoint = map.coords2pixels(gh.getPoint()); 
					x = (int)(pixelPoint.x()*(width/imgwidth));//convert the point to pixel depends on the img size
					y = (int)(pixelPoint.y()*(height/imgheight));
					g1.drawImage(ghostImg,x, y, ghostImg.getWidth(), ghostImg.getHeight(), this);
				}
			}
			if(game.bList()!=null) {
				for(Block b: game.bList()) {
					Point3D lBottom = map.coords2pixels(b.GetBottom());
					Point3D rTop = map.coords2pixels(b.GetTop());
					Point3D lTop = new Point3D(lBottom.x(),rTop.y());
					int recWidth = (int) Math.abs(rTop.x()-lTop.x());
					int recHeight =(int) Math.abs(lTop.y()-lBottom.y());
					g1.fillRect((int)lTop.x()-20,(int) lTop.y()+50, recWidth, recHeight);	
				}
			}
		}
		g.drawImage(image,0,0,this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {//get pacman and fruit with mouse click
		flag=true;
		int x=(int)(e.getX()/(width/imgwidth));//derivative the coords with imag size so when we multiply it in paint it will be where it shuld be
		int y=(int)(e.getY()/(height/imgheight));
		Point3D p = new Point3D(x, y);
		Point3D gpsPoint = map.pixels2coords(p);//get the point as coords
		if(playerButton&&play.isRuning()==false) {
			play.setInitLocation(gpsPoint.x(),gpsPoint.y());
			game.player().setPoint(gpsPoint);
			player = true;
			repaint();
		}
		else if(playerButton==true) {
			flag=false;
			Point3D Me = map.coords2pixels(game.player().getPoint());
			azimuth=map.AzimuthBetPixels(p, Me);
			azimuth=180+azimuth;
			repaint();
			
			
		}
		//azimuth
		
	}
	public void componentResized(ComponentEvent e) {
		width=e.getComponent().getWidth();
		height=e.getComponent().getHeight();
		repaint();
	}

	/**
	 * Prints the results of the game, the algorithm already know who's winning so it prints in the middle of the game.
	 */
	public void results() {

	}

	//Unneeded functions:
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}


	public static void main(String[] args) {
		MyFrame window = new MyFrame("data//Ariel1.png", new Point3D(32.10566,35.20238), new Point3D(32.10191,35.21237),new Point3D(32.10566,35.21241));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.pack();
	}
	public Play getPlay() {
		return play;
	}
}
