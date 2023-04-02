package Exe.Ex4.geo;


/**
 * This class represents a 2D segment on the plane, 
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Segment2D implements GeoShapeable{
	
	private Point2D p1;
	private Point2D p2;
	public static final double LINE_EPS = 0.025;
	
	public Segment2D(Point2D p1,Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public boolean contains(Point2D ot) {
		double slope = (p1.y()-p2.y()) / (p1.x()-p2.x());
		double b = p1.y() - (slope*p1.x());
		// allow for error using EPS
		// check if ot is on the infinity line of segment
		// checks if its on the segment - limited and not infinite
		boolean isOnLine = (Math.abs(ot.y() - (slope * ot.x() + b)) <= LINE_EPS);
		isOnLine = isOnLine && ot.x() >= Math.min(p1.x(), p2.x()) && ot.x() <= Math.max(p1.x(), p2.x());
		isOnLine = isOnLine && ot.y() >= Math.min(p1.y(), p2.y()) && ot.y() <= Math.max(p1.y(), p2.y());

		return isOnLine;
	}

	@Override
	public double area() {
		return 0;
	}

	@Override
	public double perimeter() {
		return 2* p1.distance(p2);
	}

	@Override
	public void move(Point2D vec) {
		p1.move(vec);
		p2.move(vec);
	}

	@Override
	public GeoShapeable copy() {
		return new Segment2D(new Point2D(p1),new Point2D(p2));
	}

	@Override
	public void scale(Point2D center, double ratio) {
		p1.scale(center, ratio);
		p2.scale(center, ratio);
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		p1.rotate(center, angleDegrees);
		p2.rotate(center, angleDegrees);
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = new Point2D[2];
		arr[0] = p1;
		arr[1] = p2;
		return arr;
	}
	
	public String toString() {
		return p1 + "," + p2;
	}
	
}