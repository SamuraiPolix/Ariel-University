package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import Exe.Ex4.Ex4_Const;
import Exe.Ex4.GUIShape;
import Exe.Ex4.GUI_Shapeable;
import Exe.Ex4.ShapeCollection;
import Exe.Ex4.ShapeCollectionable;
import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.ShapeComp;
import Exe.Ex4.geo.Triangle2D;

class GUIShapeTest {
	
	public static final double EPS=0.1;

	final boolean  IS_FILLED = false;
	final Color COLOR  = Color.BLUE;
	
	
	public static Point2D[] getPoints() {
	Point2D[] points = new Point2D[4];
	points[0] = new Point2D(2,2);
	points[1] = new Point2D(3,3);
	points[2] = new Point2D(4,3);
	points[3] = new Point2D(5,2);
	return points;
	}

	ShapeCollectionable shapes = new ShapeCollection();
	Point2D[] points = getPoints();
	GeoShapeable c = new Circle2D(new Point2D(2,2),5);
	GeoShapeable r = new Rect2D(new Point2D(1,1),new Point2D(4,4));
	GeoShapeable t = new Triangle2D(new Point2D(1,1),new Point2D(1,9),new Point2D(6,9));
	GeoShapeable p = new Polygon2D(points,points.length);
	
	GUI_Shapeable c1 = new GUIShape (c,IS_FILLED,COLOR, 0);
	GUI_Shapeable r1 = new GUIShape (r,IS_FILLED,COLOR, 2);
	GUI_Shapeable t1 = new GUIShape (t,IS_FILLED,COLOR, 3);
	GUI_Shapeable p1 = new GUIShape (p,IS_FILLED,COLOR, 1);
	
	@Test
	void testCircle2D() {
		Circle2D c =new Circle2D(new Point2D(3,5),4);
		assertTrue(c.contains(new Point2D(6,4)));
		Point2D center = c.getPoints()[0];
		center.move(new Point2D(4,2));
		assertEquals(new Point2D(7,7),center);
		int area = (int)c.area();
		assertEquals(50,area);
		int perimeter = (int) c.perimeter();
		assertEquals(25,perimeter);
	}
	@Test
	void testRect2D() {
		Rect2D r =new Rect2D(new Point2D(2,3),new Point2D(5,6));
		assertFalse(r.contains(new Point2D(6,4)));
		int area = (int)r.area();
		assertEquals(9,area);
		int perimeter = (int) r.perimeter();
		assertEquals(12,perimeter);
	}
	@Test
	void testSegment2D() {
		Segment2D s = new Segment2D (new Point2D(2,2),new Point2D(5,5));
		assertFalse(s.contains(new Point2D(6,4)));
		int area = (int)s.area();
		assertEquals(0,area);
		int perimeter = (int) s.perimeter();
		int ans = (int) (2 * Math.sqrt(18));
		assertEquals(ans,perimeter);
	}
	@Test
	void testTriangle2D() {
		Triangle2D t =new Triangle2D(new Point2D(1,1),new Point2D(3,3),new Point2D(4,1));
		//assertEquals(true, t.contains(new Point2D(3,2)));
		assertEquals(false, t.contains(new Point2D(7,3)));
		double area = t.area();
		assertEquals(3,area, EPS);
		double perimeter = t.perimeter();
		assertEquals(8,perimeter, EPS);
	}
	@Test
	void testPolygon2D() {
		Point2D[] points = getPoints(); 
		Polygon2D p =new Polygon2D(points,points.length);
//		assertTrue(p.contains(new Point2D(4,2)));
		double area = p.area();
		assertEquals(2,area, EPS);
		double perimeter = p.perimeter();
		assertNotEquals(12,perimeter, EPS);
	}
	
	@Test
	void testMove() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		Rect2D boundingBox = shapes.getBoundingBox();
		double oldX = boundingBox.getPoints()[2].x();
		boundingBox.move(new Point2D(1,0));
		assertEquals(oldX+1, boundingBox.getPoints()[2].x(), EPS);
	}
	
	@Test
	void testRotate() {
		Segment2D seg = new Segment2D(new Point2D(1,1), new Point2D(3,1));
		shapes.add(new GUIShape(seg,false,Color.black,0));
		seg.rotate(new Point2D(1,1), 90);
		assertEquals(1, seg.getPoints()[0].x(), EPS);
	}
	
	@Test
	void testScale() {
		Segment2D seg = new Segment2D(new Point2D(1,1), new Point2D(3,1));
		shapes.add(new GUIShape(seg,false,Color.black,0));
		seg.scale(new Point2D(1,1), 0.9);
		assertEquals(2.8, seg.getPoints()[1].x(), EPS);
		seg.scale(new Point2D(1,1), 1.1);
		assertEquals(3, seg.getPoints()[1].x(), EPS);
	}
	
	@Test
	void testContains() {
		Rect2D rec = new Rect2D(new Point2D(1,1), new Point2D(3,2));
		assertEquals(true, rec.contains(new Point2D(1.5,1.5)));
	}
}
