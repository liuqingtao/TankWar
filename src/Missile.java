import java.awt.*;
import java.awt.event.*;

public class Missile {
	public static final int XSpeed =10;
	public static final int YSpeed =10;
	public static final int WIDTH =10;
	public static final int HEIGHT =10;

	private int x,y;
	Tank.Direction dir;
	private TankClient tc;
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x,int y, Tank.Direction dir,TankClient tc){
		this(x,y,dir);
		this.tc = tc;
	}
	
	public Missile(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
	}

	public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.DARK_GRAY);
			g.fillOval(x, y, WIDTH, HEIGHT); //»­³öÌ¹¿Ë
			g.setColor(c);
			
			move();
		
	}
	


	private void move() {
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
		}
	if(x <0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT){
		tc.missiles.remove(this);
	}
	
		
	}
	
	

}
