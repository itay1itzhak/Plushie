package Plushie;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Util {
	
	public static double[] intArrayToDouble(int[] array)
	{
	    double[] result = new double[array.length];
	    for (int i=0; i<array.length; i++)
	    	result[i] = (double)array[i];
		return result;
	}
	
	public static double getDistance(Vector vi,Vector vj){
		return Math.sqrt((vi.x-vj.x)*(vi.x-vj.x) + (vi.x-vj.y)*(vi.x-vj.y) + (vi.x-vj.z)*(vi.x-vj.z));
	}
	
	//Not Complete
	public static void textToPatches(String path) {
	    File file = new File(path); 
	    String str;
	    String[] data;
	    Screen.NumberOfPatches = 0;
	    double[] x,y;
	    
	    try {
			Scanner sc = new Scanner(file);
		    while (sc.hasNextLine()){
		    	str = sc.nextLine();
		    	data = str.split(";");
		    	x = processLine(data[0].split(","));
		    	y = processLine(data[1].split(","));
		    	Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(x,y, Color.green);
		    	Screen.NumberOfPatches++;
		    }
		    Screen.patches = new Polygon2D[Screen.NumberOfPatches];
		    for (int i=0; i<Screen.NumberOfPatches;i++)
		    	Screen.patches[i] = Screen.DrawablePolygons[i];
		      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		
//		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2, 2, 2}, new double[]{1, 3, 3, 3, 4}, Color.black);
//		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(new double[]{2, 3, 3, 3, 3}, new double[]{2, 4, 4, 4, 5}, Color.black);
//		//DrawablePolygons[NumberOfPolygons] = new Polygon2D(new double[]{1, 2, 2}, new double[]{1, 3, 4}, Color.black);
//		Screen.patches[0] = Screen.DrawablePolygons[0];
//		Screen.patches[1] = Screen.DrawablePolygons[1];			
	}
	
	
	public static double[] processLine(String[] strings) {
	    double[] doublearray = new double[strings.length];
	    int i = 0;
	    for(String str:strings){
	        doublearray[i] = (double)Double.parseDouble(str.trim());//Exception in this line
	        i++;
	    }
	    return doublearray;
	}
	
	public static void drawCube(){
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{0, 0, 0, 0}, Color.gray);
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{3, 3, 3, 3}, Color.gray);
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2},  new double[]{0, 0, 3, 3}, Color.gray);
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		Screen.Polygon3Ds[Screen.NumberOf3Polygon3Ds] = new Polygon3D(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
	}

}
