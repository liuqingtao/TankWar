import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Boold {
	int x,y,w,h;
	boolean live =true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc;
	public int[][] pos ={{250,250}};
	int step =0;
	
	public Boold(){
		this.x =pos[step][0];
		this.y =pos[step][1];
		this.w = 10;
		this.h =10;
	}
	
	public void draw(Graphics g){
		if(!this.isLive()) return;
		Color c = g.getColor();
		g.setColor(Color.PINK);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		step ++;
		if(step<pos.length){
			x=pos[step][0];
			y=pos[step][1];
		}
		else 
		{
			step =0;
		}
	}

	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
	
}
