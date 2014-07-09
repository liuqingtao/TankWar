import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TankClient().aLunch();
	}
	
	public void aLunch(){
		setLocation(200,200);
		setSize(800,600);
		setTitle("TankWar");
		//添加关闭动作
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);	
			}			
		}
		);
		this.setResizable(false); //设置窗口不可改变大小
		setVisible(true);
	}

}
