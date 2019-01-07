package Plushie;
import java.awt.Color;
import java.awt.Polygon;

public class ShallowPolygon3D {
	Color c;
	double[] x, y, z;
	int poly = 0;
	Vector normal;
	double area;

	public ShallowPolygon3D(double[] x, double[] y, double[] z, Color c) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		this.normal = getNormal(x,y,z);
		this.area = getTriangleArea(x,y,z);
	}


	double GetDist() {
		double total = 0;
		for (int i = 0; i < x.length; i++)
			total += GetDistanceToP(i);
		return total / x.length;
	}

	double GetDistanceToP(int i) {

		return Math.sqrt((Screen.ViewFrom[0] - x[i]) * (Screen.ViewFrom[0] - x[i])
				+ (Screen.ViewFrom[1] - y[i]) * (Screen.ViewFrom[1] - y[i])
				+ (Screen.ViewFrom[2] - z[i]) * (Screen.ViewFrom[2] - z[i]));
	}
	
	public static Vector getNormal(double[] x, double[] y, double[] z) {
		Vector U = new Vector(x[1]-x[0],y[1]-y[0],z[1]-z[0]);
		Vector V = new Vector(x[2]-x[0],y[2]-y[0],z[2]-z[0]);
		
		double Nx = U.y*V.z - U.z*V.y;
		double Ny = U.z*V.x - U.x*V.z;
		double Nz = U.x*V.y - U.y*V.x;
	
		return new Vector(Nx,Ny,Nz);
	}
	
	public static double getTriangleArea(double[] x, double[] y, double[] z) {
		Vector u = new Vector(x[1] - x[0],y[1] - y[0],z[1] - z[0]);
		Vector v = new Vector(x[2] - x[1],y[2] - y[1],z[2] - z[1]);
		Vector cross = u.CrossProduct(v);
		// tri area = 1/2 * | u x v |
		double absSq = (cross.x * cross.x) + (cross.y * cross.y) + (cross.z * cross.z);
		double area3D = Math.sqrt(absSq) / 2;
		return area3D;
	}

}