import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;	//���ڿ��
	public static final int GAME_HEIGHT =600;	//���ڸ߶�
	Tank myTank = new Tank(350,300,true,Direction.STOP,this); //����̹��
	
	List<Explode> explodes = new ArrayList<Explode>();	//��ը��List
	List<Missile> missiles = new ArrayList<Missile>();	//�ӵ�List
	List<Tank> tanks = new ArrayList<Tank>();	//�з�̹��List
	Wall wall = new Wall(300,100,20,300,this);	//ǽ
	Boold bd = new Boold();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TankClient().aLunch();
	}
	
	//����������
	public void aLunch(){
		setLocation(200,200);
		setSize(GAME_WIDTH,GAME_HEIGHT);
		setTitle("TankWar");
		
		//�������ļ��ж�ȡ̹������
	
		for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("initTankCount"));i++){
			tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
		}
		
		this.setBackground(Color.BLACK); //���ô��ڵı���ɫ
		
		//��ӹرն���
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.exit(0);	
			}			
		}
		);
		
		//let tank move
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
		this.setResizable(false); //���ô��ڲ��ɸı��С
		setVisible(true);
	}
 
	Image offScreenImage = null;
	
	//�����ӵ�
	public void paint(Graphics g) {
		Color c =g.getColor();
		g.setColor(Color.BLUE);
		g.drawString("sissile count:"+missiles.size(), 10, 50);
		g.drawString("Tank's life:" + myTank.getLife(), 10, 70);
		g.setColor(c);
		for(int i=0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(wall);
			m.draw(g);
			
		}

		//������ը��
		for(int i=0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		//����̹��
		myTank.paint(g);
		myTank.eatBoold(bd);
		bd.draw(g);
		
		if(tanks.size()<=0){
			for(int i=0;i<10;i++){
				tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
			}
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			t.collidesWithWall(wall);
			t.collidesWithTank(tanks);
			t.paint(g);
			
		}
		wall.draw(g);
	}
	
//double buffer
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics offScreen = offScreenImage.getGraphics();
		Color c = offScreen.getColor();
		offScreen.setColor(Color.BLACK);
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
					Thread.sleep(100);
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
