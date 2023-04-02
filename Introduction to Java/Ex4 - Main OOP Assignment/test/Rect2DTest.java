package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Rect2D;

class Rect2DTest {

	
	public static final double EPS = 0.1;
	
	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(5,1);
	Point2D p3 = new Point2D(5,6);
	Point2D p4 = new Point2D(1,6);
	
	
	Point2D cen= new Point2D(1,1);
	
	Rect2D r = new Rect2D(p1,p2,p3,p4);
	
	@BeforeEach
	public void setValues() {
		r = new Rect2D(p1,p2,p3,p4);
		cen= new Point2D(1,1);
		p1 = new Point2D(1,1);
		p2 = new Point2D(5,1);
		p3 = new Point2D(5,6);
		p4 = new Point2D(1,6);
	}
	
	@Test
	void testPerimeter() {
		double per = r.perimeter();
		System.out.println(per);
		assertEquals(18,per, EPS);
	}
	
	@Test
	void testArea() {
		double area  = r.area();
		System.out.println(area);
		assertEquals(20,area, EPS);
	}

	@Test
	void testRotate() {
		r.rotate(p3, 90);
		double oldX = p3.x(),
				oldY = p3.y();
		// Point needs to stay still
		assertEquals(oldX, r.getPoints()[2].x(), EPS);
		assertEquals(oldY, r.getPoints()[2].y(), EPS);
		// Point needs to change
		Point2D tmpP = new Point2D(p3);
		Point2D zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		r.rotate(zero, 10);
		assertEquals(tmpP.x(), r.getPoints()[2].x(), EPS);
		assertEquals(tmpP.y(), r.getPoints()[2].y(), EPS);
		
		// Reset Values
		setValues();
		
		r.rotate(p4, 45);
		oldX = p4.x();
		oldY = p4.y();
		// Point needs to stay still
		assertEquals(oldX, r.getPoints()[3].x(), EPS);
		assertEquals(oldY, r.getPoints()[3].y(), EPS);
		// Point needs to change
		tmpP = new Point2D(p4);
		zero = new Point2D(0,0);
		tmpP.rotate(zero, 10);
		r.rotate(zero, 10);
		assertEquals(tmpP.x(), r.getPoints()[3].x(), EPS);
		assertEquals(tmpP.y(), r.getPoints()[3].y(), EPS);
	}

	@Test
	void testMove() {
		r.move(p1);
		Point2D p = r.getPoints()[0];
		assertEquals(new Point2D(2,2),p);
	}
	
	@Test
	void testScale() {
		// - Scale down
		r.scale(p2, 0.9);
		assertEquals(1, p1.x(), EPS);
		assertEquals(1.0, p1.y(), EPS);

		// Reset t1
		setValues();
		
		// - Scale up
		r.scale(p2, 1.1);
		assertEquals(1, p1.x(), EPS);
		assertEquals(0.9, p1.y(), EPS);
	}
	
	@Test
	void testContains() {
		assertTrue(r.contains(new Point2D(4,4)));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
