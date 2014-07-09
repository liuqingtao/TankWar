import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{


	public int x=50,y=50;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TankClient().aLunch();
	}
	
	public void aLunch(){
		setLocation(200,200);
		setSize(800,600);
		setTitle("TankWar");
		this.setBackground(Color.GREEN); //设置窗口的背景色
		//添加关闭动作
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.exit(0);	
			}			
		}
		);
		this.addKeyListener(new KeyAction());
		new Thread(new PaintThread()).start();
		this.setResizable(false); //设置窗口不可改变大小
		setVisible(true);
	}

	public void paint(Graphics g) {
//System.out.println("x="+x);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30); //画出坦克
		g.setColor(c);
	}
	
	class KeyAction extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key){
				case KeyEvent.VK_UP:
//System.out.println("UP");
					y = y-5;
					break;
				case KeyEvent.VK_DOWN:
					y = y+5;
					break;
				case KeyEvent.VK_LEFT:
					x = x-5;
					break;
				case KeyEvent.VK_RIGHT:
					x = x+5;
				
			}
		}
		
	}
	
	class PaintThread implements Runnable{
		
		public void run() {
			try {
					while(true){
						repaint();
					Thread.sleep(150);
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
