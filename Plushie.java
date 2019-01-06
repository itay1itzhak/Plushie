package Plushie;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;

public class Plushie extends JFrame{

	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	static JFrame F = new Plushie();
	Screen ScreenObject = new Screen();
	
	public Plushie()
	{
		add(ScreenObject); //calls paintComponent
		setUndecorated(true);
		setSize(ScreenSize);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		
	}
}