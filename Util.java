package Plushie;

import java.awt.Color;
import java.io.File;

public class Util {
	
	public static double[] intArrayToDouble(int[] array)
	{
	    double[] result = new double[array.length];
	    for (int i=0; i<array.length; i++)
	    	result[i] = (double)array[i];
		return result;
	}
	
	//Not Complete
	public static void textToPatches(File f) {
		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2, 2, 2}, new double[]{1, 3, 3, 3, 4}, Color.black);
		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(new double[]{2, 3, 3, 3, 3}, new double[]{2, 4, 4, 4, 5}, Color.black);
		//DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2}, new double[]{1, 3, 4}, Color.black);
		Screen.patches[0] = Screen.DrawablePolygons[0];
		Screen.patches[1] = Screen.DrawablePolygons[1];			
	}


}
