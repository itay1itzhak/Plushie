package Plushie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Screen extends JPanel implements KeyListener{
	double SleepTime = 1000/30, lastRefresh = 0;
	static double[] ViewFrom = new double[] {10, 10, 10};
	static double[] ViewTo = new double[] {1, 1, 1.5};
	static int NumberOfPolygons = 0, NumberOf3Polygon3Ds = 0, NumberOfPatches = 0;
	static Polygon2D[] DrawablePolygons = new Polygon2D[100];
	static Polygon3D[] Polygon3Ds = new Polygon3D[100];
	int[] NewOrder;
	boolean[] Keys = new boolean[8];
	
	static Polygon2D[] patches = new Polygon2D[2];
	static Polygon3D[] patches3d;
	
	
	public Screen()
	{
		addKeyListener(this);
		setFocusable(true);
		DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2, 2, 2}, new double[]{1, 3, 3, 3, 4}, Color.black);
		DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{2, 3, 3, 3, 3}, new double[]{2, 4, 4, 4, 5}, Color.blue);
		DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 3, 3, 3, 3}, new double[]{1, 2, 4, 4, 4, 5}, Color.pink);
		//DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2}, new double[]{1, 3, 4}, Color.black);
		patches[0] = DrawablePolygons[0];
		patches[1] = DrawablePolygons[1];
		patches[2] = DrawablePolygons[2];	
		NumberOfPatches = 3;
		
		patches3d = Simulation.arrayFrom2Dto3D(patches,NumberOfPatches);
		Polygon3Ds[NumberOf3Polygon3Ds-2] = patches3d[0];
		Polygon3Ds[NumberOf3Polygon3Ds-1] = patches3d[1];
		
		
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{0, 0, 0, 0}, Color.gray);
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{3, 3, 3, 3}, Color.gray);
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2},  new double[]{0, 0, 3, 3}, Color.gray);
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		//for(int i = -4; i < 5; i++)
		//	for(int j = -4; j < 5; j++)
		//		Polygon3Ds[NumberOf3Polygon3Ds] = new Polygon3D(new double[]{i, i, i + 1, i + 1}, new double[]{j, j + 1, j + 1, j},  new double[]{0, 0, 0, 	0}, Color.green	);
	}
	
	public void paintComponent(Graphics g)
	{
		Controls();
		
		g.clearRect(0, 0, 2000, 1200);

		for(int i = 0; i < NumberOf3Polygon3Ds; i++)
			Polygon3Ds[i].updatePolygon();
		
		setOrder();

		for(int i = 0; i < NumberOfPolygons; i++)
			DrawablePolygons[NewOrder[i]].drawPolygon(g);
		SleepAndRefresh();
	}
	
	void setOrder()
	{
		double[] k = new double[NumberOfPolygons];
		NewOrder = new int[NumberOfPolygons];
		
		for(int i = 0; i < NumberOfPolygons; i++)
		{
			k[i] = DrawablePolygons[i].AvgDist;	
			NewOrder[i] = i;
		}
		
	    double temp;
	    int tempr;	    
		for (int a = 0; a < k.length-1; a++)
			for (int b = 0; b < k.length-1; b++)
				if(k[b] < k[b + 1])
				{
					temp = k[b];
					tempr = NewOrder[b];
					NewOrder[b] = NewOrder[b + 1];
					k[b] = k[b + 1];
					   
					NewOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
	}
	
	void SleepAndRefresh()
	{
		while(true)
		{
			if(System.currentTimeMillis() - lastRefresh > SleepTime)
			{
				lastRefresh = System.currentTimeMillis();
				repaint();
				break;
			}
			else
			{
				try 
				{
					Thread.sleep((long)(System.currentTimeMillis() - lastRefresh));
				} 
				catch (Exception e) 
				{

				}
			}
		}
	}

	void Controls()
	{
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		if(Keys[4])
		{
			ViewFrom[0] += ViewVector.x;
			ViewFrom[1] += ViewVector.y;
			ViewFrom[2] += ViewVector.z;
			ViewTo[0] += ViewVector.x;
			ViewTo[1] += ViewVector.y;
			ViewTo[2] += ViewVector.z;
		}
		
		if(Keys[6])
		{
			ViewFrom[0] -= ViewVector.x;
			ViewFrom[1] -= ViewVector.y;
			ViewFrom[2] -= ViewVector.z;
			ViewTo[0] -= ViewVector.x;
			ViewTo[1] -= ViewVector.y;
			ViewTo[2] -= ViewVector.z;
		}
		
		Vector VerticalVector = new Vector(0, 0, 1);
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		
		if(Keys[5])
		{
			ViewFrom[0] += SideViewVector.x;
			ViewFrom[1] += SideViewVector.y;
			ViewFrom[2] += SideViewVector.z;
			ViewTo[0] += SideViewVector.x;
			ViewTo[1] += SideViewVector.y;
			ViewTo[2] += SideViewVector.z;
		}
		
		if(Keys[7])
		{
			ViewFrom[0] -= SideViewVector.x;
			ViewFrom[1] -= SideViewVector.y;
			ViewFrom[2] -= SideViewVector.z;
			ViewTo[0] -= SideViewVector.x;
			ViewTo[1] -= SideViewVector.y;
			ViewTo[2] -= SideViewVector.z;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			Keys[0] = true;
			ViewFrom[0] --;
		}
			
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			Keys[1] = true;
			ViewFrom[0] ++;
		}
			
		if(e.getKeyCode() == KeyEvent.VK_UP){
			Keys[2] = true;
			ViewFrom[1] --;
		}
			
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			Keys[3] = true;
			ViewFrom[0] ++;
		}
			
		if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[5] = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[6] = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			Keys[0] = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			Keys[1] = false;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			Keys[2] = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			Keys[3] = false;
		if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = false;
		if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[5] = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[6] = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = false;

	}

	public void keyTyped(KeyEvent e) {
	}
}