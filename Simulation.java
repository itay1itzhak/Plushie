package Plushie;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Simulation {
	
	 public final static int NUM_OF_CYCLES = 30;
	 public final static double ALPHA = 0.02;
	 public final static double BETTA = 1;
	
	public static void inflate(Polygon3D[] poly3d, int patchNum, int numOfPatches) {
		for (int i=0; i<NUM_OF_CYCLES; i++){
			internalPressure(poly3d, patchNum, numOfPatches);
			adjustEdges(poly3d, patchNum, numOfPatches);
		}


	}
	
	public static void internalPressure(Polygon3D[] poly3d, int patchNum, int numOfPatches) {
		for (int i=0; i<poly3d[patchNum].x.length; i++){ // for each vertex
			Vector sumOfAllDisplacment = new Vector(0,0,0);
			double sumOfAllareas = 0;
			Vector normal;
			double area;
			Set<Integer> neighbors = getPolyNeighborsIndices(poly3d, patchNum, numOfPatches,i);
			
			for (int neighbor : neighbors) {
			    normal = poly3d[neighbor].normal;
			    area = poly3d[neighbor].area;
			    sumOfAllareas += area;
			    sumOfAllDisplacment = sumOfAllDisplacment.addVector(normal.mulScalar(ALPHA * area));
			    //System.out.println("neighbor: "+neighbor+" area: "+area);
			    //System.out.println("add to dis: "+(ALPHA * area));
			}
			if (sumOfAllareas > 0)
				sumOfAllDisplacment.mulScalar(1/sumOfAllareas);
			
			//System.out.println("dis x:"+sumOfAllDisplacment.x+" y:"+sumOfAllDisplacment.y+" z:"+sumOfAllDisplacment.z);
			
			poly3d[patchNum].x[i] += sumOfAllDisplacment.x;
			poly3d[patchNum].y[i] += sumOfAllDisplacment.y;
			poly3d[patchNum].z[i] += sumOfAllDisplacment.z;
		}			
	}
	
	public static Set<Integer> getPolyNeighborsIndices(Polygon3D[] poly3d, int patchNum, int numOfPatches,int currectVertex) {
		Set<Integer> neighbors = new HashSet<Integer>();
		double currentX = poly3d[patchNum].x[currectVertex];
		double currentY = poly3d[patchNum].y[currectVertex];
		double currentZ = poly3d[patchNum].z[currectVertex];
		
		for (int i=0; i<numOfPatches; i++){
			for (int j=0; j<poly3d[patchNum].x.length; j++){ // for each vertex
				if (isVertexInPatch(poly3d[i],j,currentX,currentY,currentZ))
					neighbors.add(i);	
			}	
		}
		return neighbors;
		
		
	}
	
	public static boolean isVertexInPatch(Polygon3D poly3d, int vertexIndex, double currentX, double currentY, double currentZ) {
		if ((poly3d.x[vertexIndex] == currentX)&&(poly3d.y[vertexIndex] == currentY)&&(poly3d.z[vertexIndex] == currentZ))
			return true;
		return false;
	}
	
	//TODO
	public static void adjustEdges(Polygon3D[] poly3d, int patchNum, int numOfPatches) {
		
		
		return;
		
		
		
		
//		for (int i=0; i<poly3d[patchNum].x.length; i++){
//			poly3d[patchNum].x[i] = poly3d[patchNum].x[i] + 0;
//			poly3d[patchNum].y[i] = 0;
//			poly3d[patchNum].z[i] = 0;
//		}			
	}
	
	public static void inflateArray(Polygon3D[] poly3d, int numOfPatches) {
		for (int i=0; i<numOfPatches; i++)
			inflate(poly3d, i, numOfPatches);
	}
	
	
	public static Polygon3D from2Dto3D(Polygon2D polygons2d) {
		double[] z = new double[polygons2d.P.xpoints.length];
		Arrays.fill(z,1);
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
