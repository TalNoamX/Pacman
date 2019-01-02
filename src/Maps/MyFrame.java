package Maps;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;
import Geom.Point3D;
import Robot.*;
import Geom.*;
import GIS.*;
import Server.*;

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
	private BufferedImage playerImg; //An Icon for the pacman
	private BufferedImage FruitImg;// An Icon for the Fruit
	private BufferedImage pacmanImg;
	private BufferedImage ghostImg;
	//Menu Items
	private MenuItem loadCSV;
	private MenuItem run;
	//will tell us whether to add a pacman or a fruit when pressed
	private boolean playerButton = false;
	//ID for fruit and pamans in case the user is drawing it on the screen
	double height;
	double width;
	int imgheight;// The map image height
	int imgwidth;// The map image width
	private Play play;
	private boolean player;

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
		menu1.add(loadCSV);
		menu1.add(run);
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
				JButton open = new JButton();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("þþDocuments"));
				fileChooser.setDialogTitle("Load CSV");
				if(fileChooser.showOpenDialog(open)==JFileChooser.APPROVE_OPTION) {

				}
				if(fileChooser.getSelectedFile().getAbsolutePath()!=null) {
					if(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".csv")) {
						play = new Play(fileChooser.getSelectedFile().getAbsolutePath());
						play.setIDs(313340267,204324305,204397715);
						playerButton=true;
						repaint();
					}
					else JOptionPane.showMessageDialog(null, "Not a CSV file, Please try again");
				}
			}

		});
		run.addActionListener(new ActionListener() {	//Starting The Game.
			public void actionPerformed(ActionEvent e) {
				if(play!=null&&player) {
					play.start();
					int i=0;
					while(play.isRuning()) {
						i++;
	//					String info = play.getStatistics();
						play.rotate(36*i); 
						repaint();
				//	System.out.println(info);
			//			board_data = play.getBoard();
//						for(int a=0;a<board_data.size();a++) {
//							System.out.println(board_data.get(a));
//						}
					}
				}
				else JOptionPane.showMessageDialog(null, "No game was loaded yet.");
			}
		});
	}

	/**
	 *  Drawing the maps image, and painting the pacman and the fruits.
	 *  @param g Graphics object that will draw everything we need.
	 */
	public  void paint(Graphics g) {
		g.drawImage(myImage, 8,53, this.getWidth()-16,this.getHeight()-61,this);//Drawing the map image
		if(play!=null) {
			ArrayList<String> board = play.getBoard();
			for(String s: board) {
				String[] line = s.split(",");
				Point3D gps = new Point3D(Double.parseDouble(line[2]),Double.parseDouble(line[3]));
				Point3D pixelPoint = map.coords2pixels(gps); 
				int x = (int)(pixelPoint.x()*(width/imgwidth));//convert the point to pixel depends on the img size
				int y = (int)(pixelPoint.y()*(height/imgheight));
				if(s.charAt(0)=='M') g.drawImage(playerImg, x-16, y-16, playerImg.getWidth(), playerImg.getHeight(), this);
				else if(s.charAt(0)=='P') g.drawImage(pacmanImg, x, y, pacmanImg.getWidth(), pacmanImg.getHeight(), this);
				else if(s.charAt(0)=='F') g.drawImage(FruitImg, x, y, FruitImg.getWidth(), FruitImg.getHeight(), this);
				else if(s.charAt(0)=='G')g.drawImage(ghostImg, x, y, ghostImg.getWidth(), ghostImg.getHeight(), this);
				else if(s.charAt(0)=='B') {
					Point3D lBottom = map.coords2pixels(new Point3D(Double.parseDouble(line[2]),Double.parseDouble(line[3])));
					Point3D rTop = map.coords2pixels(new Point3D(Double.parseDouble(line[5]),Double.parseDouble(line[6])));
					Point3D lTop = new Point3D(lBottom.x(),rTop.y());
					int recWidth =(int)(map.distBetPixels(rTop, lTop));
					int recHeight =(int)(map.distBetPixels(lTop,lBottom));
					g.fillRect((int)lTop.x(),(int) lTop.y(), recWidth, recHeight);	
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {//get pacman and fruit with mouse click
		int x=(int)(e.getX()/(width/imgwidth));//derivative the coords with imag size so when we multiply it in paint it will be where it shuld be
		int y=(int)(e.getY()/(height/imgheight));
		Point3D p = new Point3D(x, y);
		Point3D gpsPoint = map.pixels2coords(p);//get the point as coords
		if(playerButton&&!play.isRuning()) {//if true than we add a pacman
			play.setInitLocation(gpsPoint.x(),gpsPoint.y());
			player = true;
		}
		if(play.isRuning()) {
			ArrayList<String> board = play.getBoard();
			for(String s: board) {
				if(s.charAt(0)=='M') {
					String[] line = s.split(",");
					Point3D Me = new Point3D(Double.parseDouble(line[2]),Double.parseDouble(line[3]));
					Me = map.coords2pixels(Me);
					double azimuth=map.AzimuthBetPixels(p, Me);
					play.rotate(azimuth);
				}
			}
		}
		repaint();
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
}
