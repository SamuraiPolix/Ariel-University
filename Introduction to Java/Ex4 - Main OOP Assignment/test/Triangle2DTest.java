package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
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

class Triangle2DTest {
	
	public static final double EPS = 0.1;

	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(3,7);
	Point2D p3 = new Point2D(5,2);
	Point2D p4 = new Point2D(9,4);
	Point2D p5 = new Point2D(6,8);
	Point2D p6 = new Point2D(4,3);

	
	Triangle2D t1 = new Triangle2D(p1,p5,p3);
	Triangle2D t2 = new Triangle2D(p4,p2,p6);
	
	@BeforeEach
	public void setValues() {
		t1 = new Triangle2D(p1,p5,p3);
		t2 = new Triangle2D(p4,p2,p6);
	}
	
	
	@Test
	void testPerimeter() {
		assertEquals(18.8, t1.perimeter(), EPS);
		assertEquals(16.0, t2.perimeter(), EPS);
	}
	
	@Test
	void testArea() {
		assertEquals(11.5, t1.area(), EPS);
		assertEquals(10.5, t2.area(), EPS);
	}
	
	@Test
	void testRotate() {
		t1.rotate(p3, 90);
		double oldX = p3.x(),
				oldY = p3.y();
		// Point needs to stay still
		assertEquals(oldX, t1.getPoints()[2].x(), EPS);
		assertEquals(oldY, t1.getPoints()[2].y(), EPS);
		// Point needs to change
		Point2D tmpP = new Point2D(p3);
		Point2D zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		t1.rotate(zero, 10);
		assertEquals(tmpP.x(), t1.getPoints()[2].x(), EPS);
		assertEquals(tmpP.y(), t1.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testMove() {
		Point2D vec = new Point2D(0,2);
		t1.move(vec);
		// x should stay the same, y should increase by 2
		assertEquals(p1.x(), t1.getPoints()[0].x(), EPS);
		assertEquals(p1.y() + 2, t1.getPoints()[0].y(), EPS);
	}
	
	@Test
	void testScale() {
		// - Scale down
		t1.scale(p3, 0.9);
		// Changing point
		assertEquals(1.4, t1.getPoints()[0].x(), EPS);
		assertEquals(1.1, t1.getPoints()[0].y(), EPS);
		// Not changing - p3 is part of t1
		assertEquals(p3.x(), t1.getPoints()[2].x(), EPS);
		assertEquals(p3.y(), t1.getPoints()[2].y(), EPS);

		// Reset t1
		setValues();
		
		// - Scale up
		t1.scale(p3, 1.1);
		// Changing point
		assertEquals(0.6, t1.getPoints()[0].x(), EPS);
		assertEquals(0.9, t1.getPoints()[0].y(), EPS);
		// Not changing - p3 is part of t1
		assertEquals(p3.x(), t1.getPoints()[2].x(), EPS);
		assertEquals(p3.y(), t1.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testCopy() {
		Triangle2D t3 = (Triangle2D) t1.copy();
		// All points need to be the same
		assertEquals(t1.getPoints()[0].x(), t3.getPoints()[0].x(), EPS);
		assertEquals(t1.getPoints()[1].x(), t3.getPoints()[1].x(), EPS);
		assertEquals(t1.getPoints()[2].x(), t3.getPoints()[2].x(), EPS);
		assertEquals(t1.getPoints()[0].y(), t3.getPoints()[0].y(), EPS);
		assertEquals(t1.getPoints()[1].y(), t3.getPoints()[1].y(), EPS);
		assertEquals(t1.getPoints()[2].y(), t3.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testContains() {
		Point2D pIn = new Point2D(3, 2);
		Point2D pOut = new Point2D(7, 2);
		assertEquals(true, t1.contains(pIn));
		assertEquals(false, t1.contains(pOut));
	}
}
