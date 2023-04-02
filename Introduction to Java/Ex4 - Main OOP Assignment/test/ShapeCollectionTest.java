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

class ShapeCollectionTest {
	
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
	void testSortToString() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_toString);
		shapes.sort(sComp);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
		assertTrue(shapes.get(3).getShape() instanceof Triangle2D);
	}
	
	@Test
	void testSortAntiToString() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_toString);
		shapes.sort(sComp);
		assertTrue(shapes.get(3).getShape() instanceof Circle2D);
		assertTrue(shapes.get(2).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(1).getShape() instanceof Rect2D);
		assertTrue(shapes.get(0).getShape() instanceof Triangle2D);
	}
	
	@Test
	void testSortArea() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Area);
		shapes.sort(sComp);
		assertTrue(shapes.get(3).getShape() instanceof Circle2D);
		assertTrue(shapes.get(2).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(1).getShape() instanceof Rect2D);
		assertTrue(shapes.get(0).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testSortAntiArea() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
				
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Area);
		shapes.sort(sComp);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
		assertTrue(shapes.get(3).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testSortPerimeter() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Perimeter);
		shapes.sort(sComp);
		assertTrue(shapes.get(3).getShape() instanceof Circle2D);
		assertTrue(shapes.get(2).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(1).getShape() instanceof Rect2D);
		assertTrue(shapes.get(0).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testSortAntiPerimeter() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Perimeter);
		shapes.sort(sComp);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
		assertTrue(shapes.get(3).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testSortTag() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Tag);
		shapes.sort(sComp);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(3).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
		assertTrue(shapes.get(1).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testSortAntiTag() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Tag);
		shapes.sort(sComp);
		assertTrue(shapes.get(3).getShape() instanceof Circle2D);
		assertTrue(shapes.get(0).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(1).getShape() instanceof Rect2D);
		assertTrue(shapes.get(2).getShape() instanceof Polygon2D);
	}
	
	@Test
	void testBoundingBox() {
		shapes.add(c1);
		shapes.add(r1);
		shapes.add(t1);
		shapes.add(p1);
		
		assertEquals(120, shapes.getBoundingBox().area(), EPS);
		assertEquals(44, shapes.getBoundingBox().perimeter(), EPS);
	}
}
