package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Segment2D;


class Segment2DTest {

	
	
public static final double EPS = 0.1;
	
	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(5,1);
	Point2D p3 = new Point2D(5,6);
	Point2D p4 = new Point2D(1,6);
	
	public Point2D cen= new Point2D(1,1);
	
	Segment2D s = new Segment2D(p1,p2);
	
	@BeforeEach
	public void setValues() {
		p1 = new Point2D(1,1);
		p2 = new Point2D(5,1);
		p3 = new Point2D(5,6);
		p4 = new Point2D(1,6);
		cen= new Point2D(1,1);
		s = new Segment2D(p1,p2);
	}
	
	
	@Test
	void testPerimeter() {
		assertEquals(8,s.perimeter(), EPS);
	}
	
	@Test
	void testArea() {
		assertEquals(0,s.area(), EPS);
	}

	@Test
	void testRotate() {
		s.rotate(cen, 90);
	}

	@Test
	void testMove() {
		s.move(p3);
		assertEquals(new Point2D(6,7),p1);
	}
	
	@Test
	void testScale() {
		// - Scale down
		s.scale(p2, 0.9);
		assertEquals(1.4, p1.x(), EPS);
		assertEquals(1.0, p1.y(), EPS);

		// Reset t1
		setValues();
		
		// - Scale up
		s.scale(p2, 1.1);
		assertEquals(0.6, p1.x(), EPS);
		assertEquals(0.9, p1.y(), EPS);
	}
	
	@Test
	void testCopy() {
		Segment2D s2 = (Segment2D) s.copy();
		// All points need to be the same
		assertEquals(s.getPoints()[0].x(), s2.getPoints()[0].x(), EPS);
		assertEquals(s.getPoints()[1].x(), s2.getPoints()[1].x(), EPS);
		assertEquals(s.getPoints()[0].y(), s2.getPoints()[0].y(), EPS);
		assertEquals(s.getPoints()[1].y(), s2.getPoints()[1].y(), EPS);
	}
	
	@Test
	void testContains() {
		assertTrue(s.contains(new Point2D(3,1)));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
