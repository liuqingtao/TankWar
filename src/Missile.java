import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Missile {
	public static final int XSpeed =10;
	public static final int YSpeed =10;
	public static final int WIDTH =10;
	public static final int HEIGHT =10;

	private boolean good; //区分子弹是哪方坦克发出
	private int x,y;
	Direction dir;
	private TankClient tc;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x,int y, boolean good,Direction dir,TankClient tc){
		this(x,y,dir);
		this.tc = tc;
		this.good = good;
	}
	
	public Missile(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
	}

	public void draw(Graphics g){
			Color c = g.getColor();
			if(good) g.setColor(Color.DARK_GRAY);
			else g.setColor(Color.ORANGE);
			g.fillOval(x, y, WIDTH, HEIGHT); //画出子弹
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
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitWall(Wall w){
		if(this.getRect().intersects(w.getRect())){
			tc.missiles.remove(this);
			return true;
		}
		return false;
	}
	
	//判断子弹与坦克的碰撞
	public boolean hitTank(Tank t){
		if(this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
			
			if(t.isGood()){
				t.setLife(t.getLife() -20);
				if(t.getLife()<=0) t.setLive(false);
			}else
			{
				t.setLive(false);
			}
			Explode e = new Explode(x, y, this.tc);
			tc.explodes.add(e);
			tc.missiles.remove(this);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))) return true;
			
		}
		return false;
	}
}
