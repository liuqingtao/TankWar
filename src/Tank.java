import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.*;


public class Tank {
	private int x,y,oldX,oldY;
	private boolean bL = false,bU = false,bR = false,bD = false;
	public static final int XSpeed =5;
	public static final int YSpeed = 5;
	public static final int WIDTH =50;
	public static final int HEIGHT =50;
	private static final int BaseLife =100; //坦克的初始生命
	
	private static Toolkit tk =Toolkit.getDefaultToolkit();
	
	private static Image[] tankImgs = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	static{
		tankImgs = new Image[]{
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif"))
		};
		
		
		 imgs.put("L", tankImgs[0]);
		 imgs.put("LU", tankImgs[1]);
		 imgs.put("U", tankImgs[2]);
		 imgs.put("RU", tankImgs[3]);
		 imgs.put("R", tankImgs[4]);
		 imgs.put("RD", tankImgs[5]);
		 imgs.put("D", tankImgs[6]);
		 imgs.put("LD", tankImgs[7]);
		 
	
	};
	
	private static Random r = new Random();
	private boolean good;
	private int life =100; //坦克的生命值
	
	public BooldBar bb = new BooldBar();
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	TankClient tc = null;
	int step = r.nextInt(12) +13; //敌军坦克移动相应频数后转方向
	private boolean live =true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

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
	
	
	Direction dir =Direction.STOP;//坦克的方向
	Direction ptdir = Direction.R;//坦克炮筒的方向
	
	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.oldX =x;
		this.oldY =y;
		this.good = good;
	}
	
	public Tank(int x,int y, boolean good,Direction dir,TankClient tc){
		this(x,y,good);
		this.tc =tc;
		this.dir = dir;
	}

	public void paint(Graphics g){
		Color c =g.getColor();
		if(!live){
			if(!good) {tc.tanks.remove(this);}
			
		return;
		}
		
		g.setColor(Color.BLACK);
		if(good) bb.draw(g);
//根据方向画出炮筒
		switch(ptdir){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		g.setColor(c);
		move();
		
	}
	
//	根据方向改变坦克位置
	public void move(){
		oldX =x;
		oldY =y;
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
		if(!good){
			Direction[] dirs = Direction.values();
			if(step == 0){
				step = r.nextInt(12) +3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step --;
			if(r.nextInt(40)>38) this.fire();
		}
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
		case KeyEvent.VK_F2:
			rebirth();
			
		}
		locateDirection();
	}
	
	public void KeyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_A:
			superFire();
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
	
	//坦克打出炮弹
	public Missile fire(){
		if(!live) return null;
		
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.WIDTH/2 - Missile.WIDTH/2;
		Missile m = new Missile(x,y,good,ptdir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.WIDTH/2 - Missile.WIDTH/2;
		Missile m = new Missile(x,y,good,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	//每个方向打出一发炮弹
	public void superFire(){
		Direction[] dirs = Direction.values();
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
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

	private void stay(){
		x =oldX;
		y=oldY;
	}
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	//判断坦克是否撞墙
	public boolean collidesWithWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			stay();
			return true;
		}
		return false;
	}
	
	//判断坦克之间不能互相相撞
	public boolean collidesWithTank(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			if(this != t){
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}		
			}	
		} 
		return false;
	}
	
	public boolean eatBoold(Boold bd){
		if(this.isGood() && this.isLive() && bd.isLive() && this.getRect().intersects(bd.getRect())){
			this.life =BaseLife;
			bd.setLive(false);
			return true;
		}
		return false;
	}
	
	private class BooldBar{
		public void  draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			g.fillRect(x, y-10, WIDTH*life/100, 10);
			g.setColor(c);
		}
	}
	
	public void rebirth(){
		if(this.isGood()) {
			this.setLive(true);
			this.life =BaseLife;
		}
	}
}
