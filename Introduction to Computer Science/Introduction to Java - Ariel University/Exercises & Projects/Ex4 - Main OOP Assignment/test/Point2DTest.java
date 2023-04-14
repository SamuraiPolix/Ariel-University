package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;

class Point2DTest {
	
	
	public static final double EPS = 0.1;

	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(3,7);
	Point2D p3 = new Point2D(5,2);
	
	@BeforeEach
	public void setValues() {
		p1 = new Point2D(1,1);
		p2 = new Point2D(3,7);
		p3 = new Point2D(5,2);
	}

	@Test
	void testRotate() {
		Point2D tmpP = new Point2D(p1);
		p1.rotate(p1, 90);
		// should stay the same
		assertEquals(tmpP, p1);
	}

	@Test
	void testMove() {
		p2.move(p3);
		assertEquals(new Point2D(8,9),p2);
	}
	
	@Test
	void testScale() {
		// - Scale down
		p1.scale(p2, 0.9);
		// Changing point
		assertEquals(1.2, p1.x(), EPS);
		assertEquals(1.6, p1.y(), EPS);

		// Reset t1
		setValues();
		
		// - Scale up
		p1.scale(p2, 1.1);
		// Changing point
		assertEquals(0.8, p1.x(), EPS);
		assertEquals(0.4, p1.y(), EPS);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
