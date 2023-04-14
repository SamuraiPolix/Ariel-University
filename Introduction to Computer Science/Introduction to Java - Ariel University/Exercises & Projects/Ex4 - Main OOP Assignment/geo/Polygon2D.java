package Exe.Ex4.geo;

import java.lang.reflect.Executable;
import java.util.ArrayList;

import Exe.Ex4.GUI_Shapeable;

/**
 * This class represents a 2D polygon, as in https://en.wikipedia.org/wiki/Polygon
 * This polygon can be assumed to be simple in terms of area and contains.
 * 
 * You should update this class!
 * @author boaz.benmoshe
 *
 */
public class Polygon2D implements GeoShapeable{
	
	Point2D[] _points;
	public static final double EPS = 0.1;
	
	public Polygon2D(ArrayList<Point2D> arr) {
		_points = new Point2D[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			_points[i] = new Point2D(arr.get(i));
		}
	}
	
	public Polygon2D(Point2D[] arr, int size) {
		_points = new Point2D[size];
		for (int i = 0; i < size; i++) {
			_points[i] = new Point2D(arr[i]);
		}
	}
	
	public Polygon2D(ArrayList<Point2D> arr, Point2D p) {
		_points = new Point2D[arr.size() + 1];
		int i;
		for (i = 0; i < _points.length - 1; i++) {
			_points[i] = new Point2D(arr.get(i));
		}
		_points[i] = p;
	}
	
	@Override
	public boolean contains(Point2D ot) { 
		/* Using ray casting algorithm
		 * General explanation:
		 * for each segment (2 points) of the polygon
		 * we check if ot's y value is in the range of those 2 point's y values
		 * then, if it is, we calculate the linear equation of the segment
		 * we set the value of ot's y and find the xOnLine
		 * if its bigger than ot's x, we count it in
		 * so we count all the lines that cut our imaginary horizontal line from ot.
		 * **NOTE: we could change > to < and it will work the same,
		 * 			it will just mean we go in the opposite direction (left and not right)
		 * https://en.wikipedia.org/wiki/Point_in_polygon
		 */
		int count = 0;
		int i = 0;
		double slope, b, xOnLine;
		for (i = 0; i < _points.length - 1; i++) {
			// check if ot is in the range of y's of the current 2 points
			if (ot.y() > Math.min(_points[i].y(), _points[i+1].y())
					&& ot.y() < Math.max(_points[i].y(), _points[i+1].y())) {
				// calculate linear equation (m and b)
				slope = (_points[i].y() - _points[i + 1].y())
						/ (_points[i].x() - _points[i + 1].x());
				b = _points[i].y() - (slope * _points[i].x());
				// calculate the value of x, on the current line related to ot's y
				xOnLine = (ot.y() - b) / slope;
				// if xOnLine is to the right of ot's x, it means the line is on the right
				// so we count++ because our imaginary line crosses it.
				if (xOnLine > ot.x()) {
					count++;
				}
			}
		}
		// check last segment - last point with first one
		if (ot.y() > Math.min(_points[i].y(), _points[0].y())
				&& ot.y() < Math.max(_points[i].y(), _points[0].y())) {
			slope = (_points[i].y() - _points[0].y()) / (_points[i].x() - _points[0].x());
			b = _points[i].y() - (slope * _points[i].x());
			xOnLine = (ot.y() - b) / slope;
			if (xOnLine > ot.x()) {
				count++;
			}
		}
		return count % 2 == 1;	// T if contains, F if not
	}

	@Override
	public double area() {
		double area = 0;
		int i;
		for (i = 0; i < _points.length - 1; i++) {
			area += (_points[i].x() * _points[i+1].y()) - (_points[i].y() * _points[i+1].x());
		}
		area += (_points[i].x() * _points[0].y()) - (_points[i].y() * _points[0].x());
		return 0.5 * Math.abs(area);
	}

	@Override
	public double perimeter() {
		double perim = 0;
		int i = 0;
		// add distance between point and the next one
		for (i = 0; i < _points.length - 1; i++) {
			perim += _points[i].distance(_points[i+1]);
		}
		// close the polygon - the last with the first
		perim += _points[i].distance(_points[0]);
		return perim;
	}

	@Override
	public void move(Point2D vec) {
		// move every point in vec
		for (int i = 0; i < _points.length; i++) {
			_points[i].move(vec);
		}
	}

	@Override
	public GeoShapeable copy() {
		Point2D[] points = new Point2D[_points.length];
		for (int i = 0; i < _points.length; i++) {
			points[i] = new Point2D (_points[i]);
		}
		return new Polygon2D(points, points.length);
	}

	@Override
	public void scale(Point2D center, double ratio) {
		for (int i = 0; i < _points.length; i++) {
			this._points[i].scale(center, ratio);
		}
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		for (int i = 0; i < _points.length;i++) {
			_points[i].rotate(center, angleDegrees);
		}
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = new Point2D[_points.length];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = _points[i];
		}
		return arr;
	}
	
	public String toString() {
		String str = "";
		int i;
		for (i = 0; i < _points.length - 1; i++) {
			str += _points[i] + ",";
		}
		str += _points[i];
		return str;
		
	}
	
}
