package Plushie;
import java.awt.Color;
import java.awt.Polygon;

public class Polygon3D {
	Color c;
	double[] x, y, z;
	int poly = 0;
	Vector normal;
	double area;

	public Polygon3D(double[] x, double[] y, double[] z, Color c) {
		Screen.NumberOf3Polygon3Ds++;
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		this.normal = getNormal(x,y,z);
		this.area = getTriangleArea(x,y,z);
		createPolygon();
	}

	void createPolygon() {
		poly = Screen.NumberOfPolygons;
		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new Polygon2D(new double[] {}, new double[] {}, c);
		updatePolygon();
	}

	void updatePolygon() {
		double dx = -50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0],
				Screen.ViewTo[1], Screen.ViewTo[2]);
		double dy = -50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0],
				Screen.ViewTo[1], Screen.ViewTo[2]);
		double[] newX = new double[x.length];
		double[] newY = new double[x.length];

		for (int i = 0; i < x.length; i++) {
			newX[i] = dx + Plushie.ScreenSize.getWidth() / 2
					+ 50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = dy + Plushie.ScreenSize.getHeight() / 2
					+ 50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
		}

		Screen.DrawablePolygons[poly] = new Polygon2D(newX, newY, c);
		Screen.DrawablePolygons[poly].AvgDist = GetDist();
		Screen.NumberOfPolygons--;
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
		
		System.out.println("noraml:"+Nx+" "+Ny+" "+Nz);
		return new Vector(Nx,Ny,Nz);
	}
	
	//TODO - This function is not good! fix pls
	public static double getTriangleArea(double[] x, double[] y, double[] z) {
		Vector u = new Vector(x[1] - x[0],y[1] - y[0],z[1] - z[0]);
		Vector v = new Vector(x[2] - x[1],y[2] - y[1],z[2] - z[1]);
		Vector cross = u.CrossProduct(v);
		// tri area = 1/2 * | u x v |
		double absSq = (cross.x * cross.x) + (cross.y * cross.y) + (cross.z * cross.z);
		double area3D = Math.sqrt(absSq) / 2;
		System.out.println(area3D);
		return area3D;
	}

}