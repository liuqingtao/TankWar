import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	private int x,y;
	private boolean bL = false,bU = false,bR = false,bD = false;
	public static final int XSpeed =5;
	public static final int YSpeed = 5;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	Direction dir =Direction.STOP;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30); //画出坦克
		g.setColor(c);
		move();
	}
	
//	根据方向改变坦克位置
	public void move(){
		switch(dir){
			case L:
				x -= XSpeed;
				break;
			case LU:
				x -= XSpeed;
				y -= YSpeed;
				break;
			case U:
				y -= YSpeed;
				break;
			case RU:
				x += XSpeed;
				y -= YSpeed;
				break;
			case R:
				x += XSpeed;
				break;
			case RD:
				x += XSpeed;
				y += YSpeed;
				break;
			case D:
				y += YSpeed;
				break;
			case LD:
				x -= XSpeed;
				y += YSpeed;
				break;
			case STOP:
				break;
			}
//System.out.println(dir);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			
		}
		locateDirection();
	}
	
	public void KeyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			
		}
		locateDirection();
	}
//	根据按键判断方向
	void locateDirection(){
		if(bL && !bU && !bR && !bD) dir =Direction.L;
		else if(bL && bU && !bR && !bD) dir =Direction.LU;
		else if(!bL && bU && !bR && !bD) dir =Direction.U;
		else if(!bL && bU && bR && !bD) dir =Direction.RU;
		else if(!bL && !bU && bR && !bD) dir =Direction.R;
		else if(!bL && !bU && bR && bD) dir =Direction.RD;
		else if(!bL && !bU && !bR && bD) dir =Direction.D;
		else if(bL && !bU && !bR && bD) dir =Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir =Direction.STOP;
	}

}
