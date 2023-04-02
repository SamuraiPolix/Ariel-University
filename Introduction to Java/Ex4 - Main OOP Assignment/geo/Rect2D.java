package Exe.Ex4.geo;

/**
 * This class represents a 2D rectangle (NOT necessarily axis parallel - this shape can be rotated!)
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Rect2D implements GeoShapeable {
	// we decided to use an array
	// because it makes it easier to do things
	// on array and not 4 single points
      private Point2D[] _points;
      public static final int NUM_OF_POINTS = 4;
          
    // @param p1, p3 - 2 opposite points
	public Rect2D(Point2D p1,Point2D p3) {
		_points = new Point2D[NUM_OF_POINTS];
		_points[0] = new Point2D(p1);
		_points[2] = new Point2D(p3);
		_points[1] = new Point2D(p3.x(), p1.y());
		_points[3] = new Point2D(p1.x(), p3.y());
	}
	
	// @param p1,p2,p3,p4 - all 4 points
	public Rect2D(Point2D p1,Point2D p2, Point2D p3, Point2D p4) {
		_points = new Point2D[NUM_OF_POINTS];
		_points[0] = new Point2D(p1);
		_points[2] = new Point2D(p3);
		_points[1] = new Point2D(p2);
		_points[3] = new Point2D(p4);
	}
	
	// @param points - array of 4 points
	public Rect2D(Point2D[] points) {
		_points = new Point2D[NUM_OF_POINTS];
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			_points[i] = new Point2D(points[i]);
		}
	}
	
	@Override
	public boolean contains(Point2D ot) {
		boolean isInsideX = ot.x()>Math.min(_points[0].x(),_points[2].x()) && ot.x()< Math.max(_points[0].x(), _points[2].x());
		boolean isInsideY = ot.y()>Math.min(_points[0].y(),_points[2].y()) && ot.y()< Math.max(_points[0].y(), _points[2].y());
		return isInsideX && isInsideY ;
	}
	
	    
	@Override
	public double area() {
		return _points[0].distance(_points[1]) * _points[0].distance(_points[3]);
	}

	@Override
	public double perimeter() {
		return 2*_points[0].distance(_points[1]) + 2*_points[0].distance(_points[3]);
	}

	@Override
	public void move(Point2D vec) {
		for (int i = 0; i < NUM_OF_POINTS; i++) {
			_points[i].move(vec);
		}
	}

	@Override
	public GeoShapeable copy() {
		return new Rect2D(_points);
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
		
		return str;
	}
}
