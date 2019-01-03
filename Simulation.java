package Plushie;

import java.awt.Color;
import java.util.Arrays;

public class Simulation {

	
//	//TODO
//	public void inflate(Polygon3D poly3d) {
//	
//		poly3d =  Polygon3D();
//
//	}
//	
	
	public static Polygon3D from2Dto3D(Polygon2D polygons2d) {
		double[] z = new double[polygons2d.P.xpoints.length];
		Arrays.fill(z,0.0);
		Polygon3D result = new Polygon3D(Util.intArrayToDouble(polygons2d.P.xpoints),
							Util.intArrayToDouble(polygons2d.P.ypoints), z, polygons2d.c);
		return result;
	}
	
	public static Polygon3D[] arrayFrom2Dto3D(Polygon2D[] polygons2d, int numOfPatches) {
		Polygon3D[] result = new Polygon3D[numOfPatches]; 
		for (int i=0; i<numOfPatches; i++)
			result[i] = from2Dto3D(polygons2d[i]);
		return result;
	}
}
