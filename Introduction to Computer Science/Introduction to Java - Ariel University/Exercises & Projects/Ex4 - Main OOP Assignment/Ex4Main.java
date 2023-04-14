package Exe.Ex4;
import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;

import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Triangle2D;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.gui.Ex4;
/**
 * A very simple main class for basic code for Ex4
 * 
 * t2: output:
 * GUIShape,0,true,1,Circle2D,3.0,4.0, 2.0
 * GUIShape,255,false,2,Circle2D,6.0,8.0, 4.0
 * 
 * @author boaz.ben-moshe
 *
 */
public class Ex4Main {

	public static void main(String[] args) {
		 t1();
//		t5();
		//t2();
//		 t3(); // won't work "out of the box" - requires editing the code (save, load..)
	}
	// Minimal empty frame (no shapes)
	public static void t1() {
		Ex4 ex4 = Ex4.getInstance();
		ex4.show();
	} 
	// Two simple circles
	public static void t2() {
		Ex4 ex4 = Ex4.getInstance();
		ShapeCollectionable shapes = ex4.getShape_Collection();
		Point2D p1 = new Point2D(3,4);
		Point2D p2 = new Point2D(6,8);
		Circle2D c1 = new Circle2D(p1,2);
		Circle2D c2 = new Circle2D(p2,4);
		GUI_Shapeable gs1 = new GUIShape(c1, true, Color.black, 1);
		GUI_Shapeable gs2 = new GUIShape(c2, false, Color.blue, 2);
		shapes.add(gs1);
		shapes.add(gs2);
		//ex4.init(shapes);
		ex4.show();
		System.out.print(ex4.getInfo());
	}
		public static void t4() {
			Ex4 ex4 = Ex4.getInstance();
			ShapeCollectionable shapes = ex4.getShape_Collection();
			Point2D[] points = new Point2D[4];
			points[0] = new Point2D(2,2);
			points[1] = new Point2D(3,3);
			points[2] = new Point2D(4,3);
			points[3] = new Point2D(5,2);
			Circle2D c = new Circle2D(new Point2D(2,2),5);
			Rect2D r = new Rect2D(new Point2D(1,1),new Point2D(4,4));
			Triangle2D t = new Triangle2D(new Point2D(1,1),new Point2D(1,9),new Point2D(6,9));
			Polygon2D p = new Polygon2D(points,points.length);
			
			GUI_Shapeable c1 = new GUIShape (c,false,Color.black, 0);
			GUI_Shapeable r1 = new GUIShape (r,false,Color.blue, 2);
			GUI_Shapeable t1 = new GUIShape (t,false,Color.red, 3);
			GUI_Shapeable p1 = new GUIShape (p,false,Color.green, 1);
			shapes.add(c1);
			shapes.add(r1);
			shapes.add(t1);
			shapes.add(p1);
			shapes.add(new GUIShape(shapes.getBoundingBox(),false,Color.blue, 0));
			System.out.println("area: " + shapes.getBoundingBox().area());
			System.out.println("per: " + shapes.getBoundingBox().perimeter());
			shapes.getBoundingBox().rotate(shapes.getBoundingBox().getPoints()[2], 10);

			//ex4.init(shapes);
			ex4.show();
			System.out.print(ex4.getInfo());
		}
	// Loads a file from file a0.txt (Circles only).
	public static void t3() {
		Ex4 ex4 = Ex4.getInstance();
		ShapeCollectionable shapes = ex4.getShape_Collection();
		String file = "C:\\Users\\samla\\eclipse-workspace\\Ex4\\a0"; //make sure the file is your working directory.
		shapes.load(file);
		ex4.init(shapes);
		ex4.show();
	}
	
	public static void t5() {
		Ex4 ex4 = Ex4.getInstance();
		ShapeCollectionable shapes = ex4.getShape_Collection();
		
		Point2D p1 = new Point2D(1,1);
		Point2D p2 = new Point2D(3,7);
		Point2D p3 = new Point2D(5,2);
		Point2D p4 = new Point2D(9,4);
		Point2D p5 = new Point2D(6,8);
		Point2D p6 = new Point2D(4,3);
		
		Point2D[] pArr1, pArr2;
		
		Polygon2D poly1, poly2;
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
		
		
		GUI_Shapeable gs1 = new GUIShape(poly1, false, Color.black, 1);
		GUI_Shapeable gs2 = new GUIShape(poly2, false, Color.blue, 2);
//		shapes.add(gs1);
		shapes.add(gs2);
		//ex4.init(shapes);
		ex4.show();
		System.out.print(ex4.getInfo());
		
		
		
		
	}

}
