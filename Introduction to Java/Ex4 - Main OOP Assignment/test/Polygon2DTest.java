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

class Polygon2DTest {
	
	public static final double EPS = 0.1;

	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(3,7);
	Point2D p3 = new Point2D(5,2);
	Point2D p4 = new Point2D(9,4);
	Point2D p5 = new Point2D(6,8);
	Point2D p6 = new Point2D(4,3);
	
	Point2D[] pArr1, pArr2;
	
	Polygon2D poly1, poly2;
	
	@BeforeEach
	public void setValues() {
		pArr1 = new Point2D[6];
		pArr1[0] = p1;
		pArr1[1] = p2;
		pArr1[2] = p3;
		pArr1[3] = p4;
		pArr1[4] = p5;
		pArr1[5] = p6;
		
		pArr2 = new Point2D[5];
		pArr2[0] = p1;
		pArr2[1] = p2;
		pArr2[2] = p3;
		pArr2[3] = p4;
		pArr2[4] = p5;
		
		poly1 = new Polygon2D(pArr1, 6);
		poly2 = new Polygon2D(pArr1, 5);
	}
	
	@Test
	void testPerimeter() {
		assertEquals(30.2, poly1.perimeter(), EPS);
		assertEquals(29.8, poly2.perimeter(), EPS);
	}
	
	@Test
	void testArea() {
		assertEquals(6, poly1.area(), EPS);
		assertEquals(11.5, poly2.area(), EPS);
	}
	
	@Test
	void testRotate() {
		poly1.rotate(p3, 90);
		double oldX = p3.x(),
				oldY = p3.y();
		// Point needs to stay still
		assertEquals(oldX, poly1.getPoints()[2].x(), EPS);
		assertEquals(oldY, poly1.getPoints()[2].y(), EPS);
		// Point needs to change
		Point2D tmpP = new Point2D(p3);
		Point2D zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		poly1.rotate(zero, 10);
		assertEquals(tmpP.x(), poly1.getPoints()[2].x(), EPS);
		assertEquals(tmpP.y(), poly1.getPoints()[2].y(), EPS);
		
		// Reset Values
		setValues();
		
		poly2.rotate(p4, 45);
		oldX = p4.x();
		oldY = p4.y();
		// Point needs to stay still
		assertEquals(oldX, poly2.getPoints()[3].x(), EPS);
		assertEquals(oldY, poly2.getPoints()[3].y(), EPS);
		// Point needs to change
		tmpP = new Point2D(p4);
		zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		poly2.rotate(zero, 10);
		assertEquals(tmpP.x(), poly2.getPoints()[3].x(), EPS);
		assertEquals(tmpP.y(), poly2.getPoints()[3].y(), EPS);
	}
	
	@Test
	void testMove() {
		Point2D vec = new Point2D(0,2);
		poly1.move(vec);
		// x should stay the same, y should increase by 2
		assertEquals(p1.x(), poly1.getPoints()[0].x(), EPS);
		assertEquals(p1.y() + 2, poly1.getPoints()[0].y(), EPS);
		
		// Reset values
		setValues();
		
		vec = new Point2D(2,2);
		poly1.move(vec);
		// x should stay the same, y should increase by 2
		assertEquals(p1.x() + 2, poly1.getPoints()[0].x(), EPS);
		assertEquals(p1.y() + 2, poly1.getPoints()[0].y(), EPS);
	}
	
	@Test
	void testScale() {
		// - Scale down
		poly1.scale(p3, 0.9);
		// Changing point
		assertEquals(1.4, poly1.getPoints()[0].x(), EPS);
		assertEquals(1.1, poly1.getPoints()[0].y(), EPS);
		// Not changing - p3 is part of polly1
		assertEquals(p3.x(), poly1.getPoints()[2].x(), EPS);
		assertEquals(p3.y(), poly1.getPoints()[2].y(), EPS);

		// Reset t1
		setValues();
		
		// - Scale up
		poly1.scale(p3, 1.1);
		// Changing point
		assertEquals(0.6, poly1.getPoints()[0].x(), EPS);
		assertEquals(0.9, poly1.getPoints()[0].y(), EPS);
		// Not changing - p3 is part of poly1
		assertEquals(p3.x(), poly1.getPoints()[2].x(), EPS);
		assertEquals(p3.y(), poly1.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testCopy() {
		Polygon2D poly3 = (Polygon2D) poly1.copy();
		// All points need to be the same
		assertEquals(poly1.getPoints()[0].x(), poly3.getPoints()[0].x(), EPS);
		assertEquals(poly1.getPoints()[1].x(), poly3.getPoints()[1].x(), EPS);
		assertEquals(poly1.getPoints()[2].x(), poly3.getPoints()[2].x(), EPS);
		assertEquals(poly1.getPoints()[0].y(), poly3.getPoints()[0].y(), EPS);
		assertEquals(poly1.getPoints()[1].y(), poly3.getPoints()[1].y(), EPS);
		assertEquals(poly1.getPoints()[2].y(), poly3.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testContains() {
		Point2D pIn = new Point2D(3, 5);
		Point2D pOut = new Point2D(1, 6);
		assertEquals(true, poly1.contains(pIn));
		assertEquals(false, poly1.contains(pOut));
		
		pIn = new Point2D(6, 6);
		pOut = new Point2D(8, 8);
		assertEquals(true, poly2.contains(pIn));
		assertEquals(false, poly2.contains(pOut));
	}
}
