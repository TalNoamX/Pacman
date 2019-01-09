package GameData;

import Maps.MyFrame;


public class moveThePackman extends Thread{
	MyFrame myframe;
	public moveThePackman(MyFrame myframe){
		this.myframe=myframe;
	}
public void run() {
	while(myframe.getPlay().isRuning()) {//our best score was 33
		myframe.packmanStep();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	String str= myframe.getPlay().getStatistics();
	System.out.println(str);
}
}
