import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	private int x,y;
	private boolean bL = false,bU = false,bR = false,bD = false;
	public static final int XSpeed =5;
	public static final int YSpeed = 5;
	public static final int WIDTH =30;
	public static final int HEIGHT =30;
	TankClient tc = null;
	
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
	Direction dir =Direction.STOP;//坦克的方向
	Direction ptdir = Direction.R;//坦克炮筒的方向
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x,int y,TankClient tc){
		this(x,y);
		this.tc =tc;
	}

	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT); //画出坦克
		g.setColor(Color.BLACK);
//根据方向画出炮筒
		switch(ptdir){
		case L:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y);
			break;
		case R:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
			break;
		case D:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y+HEIGHT);
			break;
		case LD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
			break;
		}
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
		if(dir != Direction.STOP) ptdir = dir;
		
		if(x < 0) x =0;
		if(y <30) y =30;
		if((x + Tank.WIDTH) > TankClient.GAME_WIDTH) x =TankClient.GAME_WIDTH - Tank.WIDTH;
		if((y + Tank.HEIGHT)>TankClient.GAME_HEIGHT) y =TankClient.GAME_HEIGHT - Tank.HEIGHT;
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
		case KeyEvent.VK_CONTROL:
			fire();
			break;
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
	
	public Missile fire(){
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.WIDTH/2 - Missile.WIDTH/2;
		Missile m = new Missile(x,y,ptdir,this.tc);
		tc.missiles.add(m);
		return m;
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
