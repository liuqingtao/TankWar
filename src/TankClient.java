import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30); //����̹��
		g.setColor(c);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TankClient().aLunch();
	}
	
	public void aLunch(){
		setLocation(200,200);
		setSize(800,600);
		setTitle("TankWar");
		this.setBackground(Color.GRAY); //���ô��ڵı���ɫ
		//��ӹرն���
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.exit(0);	
			}			
		}
		);
		this.setResizable(false); //���ô��ڲ��ɸı��С
		setVisible(true);
	}

}
