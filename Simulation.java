package Plushie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Simulation {
	
	 public final static int NUM_OF_CYCLES_INFALTE = 30;
	 public final static int NUM_OF_CYCLES_ADJUST = 10;
	 public final static double ALPHA = 0.02;
	 public final static double BETTA = 1;
	
	public static void inflate(Polygon3D[] poly3d,ShallowPolygon3D[] poly3dOrginal, int patchNum, int numOfPatches) {
		System.out.println(poly3d[0].x[0]);
		System.out.println(poly3dOrginal[0].x[0]);
		for (int i=0; i<NUM_OF_CYCLES_INFALTE; i++){
			internalPressure(poly3d, patchNum, numOfPatches);
			for (int j=0; j<NUM_OF_CYCLES_ADJUST; j++)
				adjustEdges(poly3d,poly3dOrginal, patchNum, numOfPatches);
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
			}
			if (sumOfAllareas > 0)
				sumOfAllDisplacment.mulScalar(1/sumOfAllareas);
			
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
	
	public static void adjustEdges(Polygon3D[] poly3d, ShallowPolygon3D[] poly3dOrginal, int patchNum, int numOfPatches) {

		for (int i=0; i<poly3d[patchNum].x.length; i++){ // for each vertex
			Vector sumOfAllDisplacment = new Vector(0,0,0);
			double sumOfAllareas = 0;
			double area;
			
			Set<Integer> neighbors = getPolyNeighborsIndices(poly3d, patchNum, numOfPatches,i);
			List<Vector> neighborVercties;
			List<Vector> neighborVerctiesOrg;
			Vector vi = new Vector(poly3d[patchNum].x[i],poly3d[patchNum].y[i],poly3d[patchNum].z[i]);
			Vector viOrg = new Vector(poly3dOrginal[patchNum].x[i],poly3dOrginal[patchNum].y[i],poly3dOrginal[patchNum].z[i]);
			Vector vj,vjOrg;
			Vector force;
			
			for (int neighbor : neighbors) {
				neighborVercties = getNeighborVercties(poly3d[patchNum],poly3d[neighbor],i);
				neighborVerctiesOrg = getShallowNeighborVercties(poly3dOrginal[patchNum],poly3dOrginal[neighbor],i);
				
				for (int j=0; j< Math.min(neighborVercties.size(),neighborVerctiesOrg.size()); j++){
					vj = neighborVercties.get(j);
					vjOrg = neighborVerctiesOrg.get(j);
					area = poly3d[neighbor].area;
				    sumOfAllareas += area;
				    force = getForce(vi,vj,viOrg,vjOrg);
				    sumOfAllDisplacment = sumOfAllDisplacment.addVector(force.mulScalar(BETTA * area));
				}
			    
			}
			if (sumOfAllareas > 0)
				sumOfAllDisplacment.mulScalar(1/sumOfAllareas);
			
			poly3d[patchNum].x[i] += sumOfAllDisplacment.x;
			poly3d[patchNum].y[i] += sumOfAllDisplacment.y;
			poly3d[patchNum].z[i] += sumOfAllDisplacment.z;
		}			
	}
	
	public static List<Vector> getNeighborVercties(Polygon3D current,Polygon3D neighbor,int vertexIndex){
		List<Vector> neighborVercties = new ArrayList<Vector>();
		for (int i=0; i<neighbor.x.length;i++){
			if ((current.x[vertexIndex] != neighbor.x[i])||
					(current.y[vertexIndex] != neighbor.y[i])||
					(current.z[vertexIndex] != neighbor.z[i])){
				neighborVercties.add(new Vector(neighbor.x[i],neighbor.y[i],neighbor.z[i]));
			}//if
		}//for
		return neighborVercties;
	}
	
	public static List<Vector> getShallowNeighborVercties(ShallowPolygon3D current,ShallowPolygon3D neighbor,int vertexIndex){
		List<Vector> neighborVercties = new ArrayList<Vector>();
		for (int i=0; i<neighbor.x.length;i++){
			if ((current.x[vertexIndex] != neighbor.x[i])||
					(current.y[vertexIndex] != neighbor.y[i])||
					(current.z[vertexIndex] != neighbor.z[i])){
				neighborVercties.add(new Vector(neighbor.x[i],neighbor.y[i],neighbor.z[i]));
			}//if
		}//for
		return neighborVercties;
	}
	
	public static Vector getForce(Vector vi,Vector vj,Vector viOrg,Vector vjOrg){
		Vector force = new Vector(0,0,0);
		double lij = Util.getDistance(viOrg,vjOrg);
		double currentDistance = Util.getDistance(vi,vj);
		if ((currentDistance >= lij)&&(currentDistance > 0)){
			force = vi.addVector(vj.mulScalar(-1));
			force = force.mulScalar(0.5*((currentDistance-lij)/currentDistance));
		}
		return force;
	}
	
	
	public static void inflateArray(Polygon3D[] poly3d, int numOfPatches) {
		ShallowPolygon3D[] poly3dOrginal =  new ShallowPolygon3D[poly3d.length];
		Util.deepArraycopy(poly3d,poly3dOrginal);
		for (int i=0; i<numOfPatches; i++)
			inflate(poly3d,poly3dOrginal, i, numOfPatches);
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
