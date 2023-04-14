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

class Circle2DTest {
	public static final double EPS = 0.1;

	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(3,7);

	double rad1 = 4,
			rad2 = 1;

	Circle2D c1 = new Circle2D(p1,rad1);
	Circle2D c2 = new Circle2D(p2,rad2);
	
	@BeforeEach
	public void setValues() {
		c1 = new Circle2D(p1,rad1);
		c2 = new Circle2D(p2,rad2);
	}
	
	@Test
	void testPerimeter() {
		assertEquals(25.1, c1.perimeter(), EPS);
		assertEquals(6.25, c2.perimeter(), EPS);
	}
	
	@Test
	void testArea() {
		assertEquals(50.25, c1.area(), EPS);
		assertEquals(Math.PI, c2.area(), EPS);
	}
	
	@Test
	void testRotate() {
		double oldX = p1.x(),
				oldY = p1.y();
		c1.rotate(p1, 90);
		// Point needs to stay still
		assertEquals(oldX, c1.getPoints()[0].x(), EPS);
		assertEquals(oldY, c1.getPoints()[0].y(), EPS);
		// Point needs to change
		Point2D tmpP = new Point2D(p1);
		Point2D zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		c1.rotate(zero, 10);
		assertEquals(tmpP.x(), c1.getPoints()[0].x(), EPS);
		assertEquals(tmpP.y(), c1.getPoints()[0].y(), EPS);
	}
	
	@Test
	void testMove() {
		Point2D vec = new Point2D(0,2);
		c1.move(vec);
		// x should stay the same, y should increase by 2
		assertEquals(p1.x(), c1.getPoints()[0].x(), EPS);
		assertEquals(p1.y() + 2, c1.getPoints()[0].y(), EPS);
	}
	
	@Test
	void testScale() {
		// - Scale down
		double oldRad = c1.getRadius();
		c1.scale(p1, 0.9);
		// Not changing - p1 is center of c1
		assertEquals(p1.x(), c1.getPoints()[0].x(), EPS);
		assertEquals(p1.y(), c1.getPoints()[0].y(), EPS);
		// radius check
		assertEquals(oldRad*0.9, c1.getRadius(), EPS);

		// Reset c1
		setValues();
		
		// - Scale up
		c1.scale(p1, 1.1);
		// Not changing - p1 is center of c1
		assertEquals(p1.x(), c1.getPoints()[0].x(), EPS);
		assertEquals(p1.y(), c1.getPoints()[0].y(), EPS);
		// radius check
		assertEquals(oldRad*1.1, c1.getRadius(), EPS);
	}
	
	@Test
	void testCopy() {
		Circle2D c3 = (Circle2D) c1.copy();
		// All points need to be the same
		assertEquals(c1.getPoints()[0].x(), c3.getPoints()[0].x(), EPS);
		assertEquals(c1.getPoints()[1].x(), c3.getPoints()[1].x(), EPS);
		assertEquals(c1.getPoints()[2].x(), c3.getPoints()[2].x(), EPS);
		assertEquals(c1.getPoints()[0].y(), c3.getPoints()[0].y(), EPS);
		assertEquals(c1.getPoints()[1].y(), c3.getPoints()[1].y(), EPS);
		assertEquals(c1.getPoints()[2].y(), c3.getPoints()[2].y(), EPS);
	}
	
	@Test
	void testContains() {
		Point2D pIn = new Point2D(3, 2);
		Point2D pOut = new Point2D(7, 2);
		assertEquals(true, c1.contains(pIn));
		assertEquals(false, c1.contains(pOut));
	}
}
