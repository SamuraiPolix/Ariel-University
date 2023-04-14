package Exe.Ex4.geo;

/**
 * This class represents a 2D Triangle in the plane.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Triangle2D implements GeoShapeable{
	// we decided to use an array
	// because it makes it easier to do things
	// on array and not 3 single points
          private Point2D[] _points;
          public static final int NUM_OF_POINTS = 3;
	
	
	
	public Triangle2D(Point2D p1,Point2D p2,Point2D p3) {
		_points = new Point2D[NUM_OF_POINTS];
		_points[0] = new Point2D(p1);
		_points[1] = new Point2D(p2);
		_points[2] = new Point2D (p3);
	}
	
	public Triangle2D(Point2D[] points) {
		_points = new Point2D[NUM_OF_POINTS];
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			_points[i] = new Point2D(points[i]);
		}
	}
	
	
	@Override
	public boolean contains(Point2D ot) {
		// https://www.youtube.com/watch?v=HYAgJN3x4GA
		//  a,b are scalars 
			
		double s1 = _points[2].y() - _points[0].y();
		double s2 = _points[2].x() - _points[0].x();
		double s3 = _points[1].y() - _points[0].y();
		double s4 = ot.y() - _points[0].y();

		double a = (_points[0].x() * s1 + s4 * s2 - ot.x() * s1) / (s3 * s2 - (_points[1].x()-_points[0].x()) * s1);
		double b = (s4- a * s3) / s1;
		boolean isInsideTriangle = (a >= 0 && b >= 0 && (a + b) <= 1);
		return isInsideTriangle;
	}
	
	@Override
	public double area () {
		//using Heron's formula
		// lengths of triangles' sides 
		double a = _points[0].distance(_points[1]);
		double b = _points[0].distance(_points[2]);
		double c = _points[1].distance(_points[2]);
		
		double s = (a+b+c)/2;
		double temp =(s*(s-a)*(s-b)*(s-c));
		double area1 = Math.sqrt(temp);
		
		return area1;
	}

	@Override
	public double perimeter() {
		
		return _points[0].distance(_points[1])+
				  _points[0].distance(_points[2])+
				  _points[1].distance(_points[2]);
	}

	@Override
	public void move(Point2D vec) {
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			_points[i].move(vec);
		}
	}

	@Override
	public GeoShapeable copy() {
		return new Triangle2D(_points);
	}

	@Override
	public void scale(Point2D center, double ratio) {
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			this._points[i].scale(center, ratio);
		}
		
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			_points[i].rotate(center, angleDegrees);
		}
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] points = new Point2D[NUM_OF_POINTS];
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			points[i] = new Point2D(_points[i]);
		}
		return points;
	}
	
	public String toString() {
		String str = "";
		int i;
		for (i = 0; i < NUM_OF_POINTS - 1; i++) {
			str += _points[i] + ",";
		}
		str += _points[i];
		
		return str;	}
	
}
