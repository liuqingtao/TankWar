import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT =600;
	Tank myTank = new Tank(50,50,this);
	Missile missile = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TankClient().aLunch();
	}
	
	
	public void aLunch(){
		setLocation(200,200);
		setSize(GAME_WIDTH,GAME_HEIGHT);
		setTitle("TankWar");
		this.setBackground(Color.GREEN); //设置窗口的背景色
		//添加关闭动作
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.exit(0);	
			}			
		}
		);
		//let tank move
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
		this.setResizable(false); //设置窗口不可改变大小
		setVisible(true);
	}
 
	Image offScreenImage = null;
	
	public void paint(Graphics g) {
		myTank.paint(g);
		if(missile != null) missile.draw(g);
	}
	
//double buffer
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics offScreen = offScreenImage.getGraphics();
		Color c = offScreen.getColor();
		offScreen.setColor(Color.GREEN);
		offScreen.fillRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
		offScreen.setColor(c);
		paint(offScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	class KeyMonitor extends KeyAdapter{
		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}		
		
	}
	
	class PaintThread implements Runnable{
		
		public void run() {
			try {
					while(true){
						repaint();
					Thread.sleep(10);
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
