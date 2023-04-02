package Exe.Ex4.gui;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

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

// ID's: 315144972, 53036281
/**
 * 
 * This class is a simple "inter-layer" connecting (aka simplifying) the
 * StdDraw with the Map class.
 * Written for 101 java course it uses simple static functions to allow a 
 * "Singleton-like" implementation.
 * @author boaz.benmoshe
 *
 */
public class Ex4 implements Ex4_GUI{
	private  ShapeCollectionable _shapes = new ShapeCollection();
	private  GUI_Shapeable _gs;
	private  Color _color = Color.blue;
	private  boolean _fill = false;
	private  String _mode = "";
	private  Point2D _p1;
	private  Point2D _p2;
	private  static Ex4 _winEx4 = null;
	// Array for creating polygon
	private  ArrayList<Point2D> _polyPoints = new ArrayList<Point2D>();

	private Ex4() {
		init(null);
	}
	public void init(ShapeCollectionable s) {
		if(s==null) {_shapes = new ShapeCollection();}
		else {_shapes = s.copy();}
		GUI_Shapeable _gs = null;
		Polygon2D _pp = null;
		_color = Color.blue;
		_fill = false;
		_mode = "";
		Point2D _p1 = null;
	}
	public void show(double d) {
		StdDraw_Ex4.setScale(0,d);
		StdDraw_Ex4.show();
		drawShapes();
	}
	public static Ex4 getInstance() {
		if(_winEx4 ==null) {
			_winEx4 = new Ex4();
		}
		return _winEx4;
	}

	public void drawShapes() {
		StdDraw_Ex4.clear();
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable sh = _shapes.get(i);

			drawShape(sh);
		}
		if(_gs!=null) {drawShape(_gs);}
		StdDraw_Ex4.show();
	}
	private static void drawShape(GUI_Shapeable g) {
		StdDraw_Ex4.setPenColor(g.getColor());
		if(g.isSelected()) {StdDraw_Ex4.setPenColor(Color.gray);}
		GeoShapeable gs = g.getShape();
		boolean isFill = g.isFilled();
		// CIRCLE
		if(gs instanceof Circle2D) {
			Circle2D c = (Circle2D)gs;
			Point2D cen = c.getPoints()[0];
			double rad = c.getRadius();
			if(isFill) {
				StdDraw_Ex4.filledCircle(cen.x(), cen.y(), rad);
			}
			else { 
				StdDraw_Ex4.circle(cen.x(), cen.y(), rad);
			}
		}
		// RECT
		if(gs instanceof Rect2D) {
			Rect2D r = (Rect2D)gs;
			Point2D[] arr =r.getPoints();

			double[] arrX = new double[arr.length];
			double[] arrY = new double[arr.length];
			for (int i = 0; i < arr.length; i++) {
				arrX[i] = arr[i].x();
				arrY[i] = arr[i].y();
			}
			/*
			 * We used StdDraw_Ex4.filledRectangle at first
			 * but then we figured it can't draw a tilted rectangle.
			 * so after debugging for ages we came up with using
			 * StdDraw_Ex4.filledPolygon.
			 * that way we also fixed a bug you have in your jar file:
			 * saved tilted rect2d won't load properly from file. 
			 */
			if(isFill) {
				StdDraw_Ex4.filledPolygon(arrX, arrY);
				// StdDraw_Ex4.filledRectangle(xCen, yCen, halfWidth, halfHeight);
			}
			else { 
				StdDraw_Ex4.polygon(arrX, arrY);
				// StdDraw_Ex4.rectangle(xCen, yCen, halfWidth, halfHeight);
			}
		}
		// SEGMENT
		if(gs instanceof Segment2D) {
			Segment2D s =(Segment2D) gs;
			Point2D[] arr = s.getPoints();
			double x0 =arr[0].x();
			double y0 =arr[0].y();
			double x1 =arr[1].x();
			double y1 =arr[1].y();

			StdDraw_Ex4.line(x0,y0,x1,y1);
		}
		// TRIANGLE
		if(gs instanceof Triangle2D) {

			Triangle2D t = (Triangle2D)gs;
			Point2D[] arr =t.getPoints();

			double[] arrX = new double[arr.length];
			double[] arrY  = new double[arr.length];

			for (int i = 0; i < arrX.length; i++) {
				arrX[i] = arr[i].x();
			}
			for (int i = 0; i < arrY.length; i++) {
				arrY[i] = arr[i].y();
			}


			if(isFill) {
				StdDraw_Ex4.filledPolygon(arrX, arrY);
			}
			else { 
				StdDraw_Ex4.polygon(arrX, arrY);
			}
		}
		// POLYGON
		if(gs instanceof Polygon2D) {
			Polygon2D po =(Polygon2D) gs;
			Point2D[] arr = po.getPoints();

			double[] arrX = new double[arr.length];
			double[] arrY  = new double[arr.length];

			for (int i = 0; i < arrX.length; i++) {
				arrX[i] = arr[i].x();
			}
			for (int i = 0; i < arrY.length; i++) {
				arrY[i] = arr[i].y();
			}

			if(isFill) {
				StdDraw_Ex4.filledPolygon(arrX, arrY);
			}
			else { 
				StdDraw_Ex4.polygon(arrX, arrY);
			}
		}
	}
	private void setColor(Color c) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			if(s.isSelected()) {
				s.setColor(c);
			}
		}
	}
	private void setFill() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			if(s.isSelected()) {
				s.setFilled(_fill);
			}
		}
	}

	public void actionPerformed(String p) {
		_mode = p;
		// ----------FILE----------
		if(p.equals("Clear")) {_shapes.removeAll();}
		if(p.equals("Save")) {
			// https://www.codejava.net/java-se/swing/show-save-file-dialog-using-jfilechooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to save");
			
			int userSelection = fileChooser.showSaveDialog(fileChooser);
			
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				_shapes.save(fileToSave.getAbsolutePath());
			}
		}
		if(p.equals("Load")) {
			// https://www.codejava.net/java-se/swing/show-save-file-dialog-using-jfilechooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to load");
			
			int userSelection = fileChooser.showOpenDialog(fileChooser);
			
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToLoad = fileChooser.getSelectedFile();
				System.out.println("Load file: " + fileToLoad.getAbsolutePath());
				_shapes.load(fileToLoad.getAbsolutePath());
			}
		}
		// ----------COLOR----------
		if(p.equals("Blue")) {_color = Color.BLUE; setColor(_color);}
		if(p.equals("Red")) {_color = Color.RED; setColor(_color);}
		if(p.equals("Green")) {_color = Color.GREEN; setColor(_color);}
		if(p.equals("White")) {_color = Color.WHITE; setColor(_color);}
		if(p.equals("Black")) {_color = Color.BLACK; setColor(_color);}
		if(p.equals("Yellow")) {_color = Color.YELLOW; setColor(_color);}
		if(p.equals("Fill")) {_fill = true; setFill();}
		if(p.equals("Empty")) {_fill = false; setFill();}
		// ----------SELECT----------
		// SELECT - ALL
		if(p.equals("All")) 
		{ 
			for (int i = 0; i < _shapes.size(); i++)
			{
				_shapes.get(i).setSelected(true);
			}
		}
		// SELECT - ANTI
		if(p.equals("Anti")) 
		{
			selectAnti();
		}
		// SELECT - NONE
		if(_mode.equals("None")) {
			deselectAll();
		}
		// SELECT - Info
		if(_mode.equals("Info")) {
			System.out.println(getInfo());
//			_mode = "";
		}
		// ----------EDIT----------
		// EDIT - REMOVE
		if(_mode.equals("Remove")) {
			removeSelected();
		}
		
		// ----------SORT----------
		// SORT - ByArea
		if (_mode.equals("ByArea")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Area);
			_shapes.sort(sComp);
		}
		// SORT - ByAntiArea
		if (_mode.equals("ByAntiArea")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Area);
			_shapes.sort(sComp);
		}
		// SORT - ByPerimeter
		if (_mode.equals("ByPerimeter")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Perimeter);
			_shapes.sort(sComp);
		}
		// SORT - ByAntiPerimeter
		if (_mode.equals("ByAntiPerimeter")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Perimeter);
			_shapes.sort(sComp);
		}
		// SORT - ByToString
		if (_mode.equals("ByToString")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_toString);
			_shapes.sort(sComp);
		}
		// SORT - ByAntiToString
		if (_mode.equals("ByAntiToString")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_toString);
			_shapes.sort(sComp);
		}
		// SORT - ByTag
		if (_mode.equals("ByTag")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Tag);
			_shapes.sort(sComp);
		}
		// SORT - ByAntiTag
		if (_mode.equals("ByAntiTag")) {
			ShapeComp sComp = new ShapeComp(Exe.Ex4.Ex4_Const.Sort_By_Anti_Tag);
			_shapes.sort(sComp);
		}
		
		drawShapes();
	}


	public void mouseClicked(Point2D p) {
		// your program prints here "Mode: _mode + the address of p".
		// we figured its a planted bug because you don't have toString for Point2D.	
		System.out.println("Mode: "+_mode+"  "+p);
		// CIRCLE
		if(_mode.equals("Circle")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(getLastTag() + 1);
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		// RECT
		if(_mode.equals("Rect")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(getLastTag() + 1);
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		// SEGMENT
		if(_mode.equals("Segment")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(getLastTag() + 1);
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		// TRIANGLE
		if(_mode.equals("Triangle")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else if(_p2 ==null) {
				_p2 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(getLastTag() + 1);
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
				_p2 = null;
			}
		}
		// POLYGON
		if(_mode.equals("Polygon")) {
			if (_p1 == null) {
				_p1 = new Point2D(p);
			}
			_polyPoints.add(new Point2D (p));
		}
		// EDIT - MOVE
		if(_mode.equals("Move")) {
			if(_p1==null) {_p1 = new Point2D(p);}
			else {
				_p1 = new Point2D(p.x()-_p1.x(), p.y()-_p1.y());
				move();
				_p1 = null;
			}
		}
		// EDIT - ROTATE
		if(_mode.equals("Rotate")) {
			if(_p1==null) {_p1 = new Point2D(p);}
			else {
//				_p1 = new Point2D(p.x()-_p1.x(), p.y()-_p1.y());
				rotate(Math.atan2(p.y()-_p1.y(), p.x()-_p1.x()));
				_p1 = null;
			}
		}
		// EDIT - SCALE (90% or 100%)
		if(_mode.contains("Scale_")) {
			// get the ratio - 90 or 110 which will be 0.9 or 1.1 later
			double ratio = Double.parseDouble(_mode.substring(_mode.indexOf('_') + 1, _mode.length()-1));
			ratio /= 100;		// divide percentage by 100 to get multiplier
			_p1 = new Point2D(p);
			scale(ratio);
			_p1 = null;
		}
		// EDIT - COPY
		if(_mode.equals("Copy")) {
			if(_p1==null) {_p1 = new Point2D(p);}
			else {
				_p1 = new Point2D(p.x()-_p1.x(), p.y()-_p1.y());
				copy();
				_p1 = null;
			}
		}
		// SELECT - POINT
		if(_mode.equals("Point")) {
			select(p);
		}

		drawShapes();
	}

	/**
	 * Selects all the shapes that contain the given point
	 * @param p
	 */
	private void select(Point2D p) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(g!=null && g.contains(p)) {
				s.setSelected(!s.isSelected());
			}

		}
	}
	
	/**
	 * This func inverts the selection.
	 * selected will be deselected and not selected will be selected
	 * @param p
	 */
	private void selectAnti() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			if(s.getShape() != null) {
				s.setSelected(!s.isSelected());
			}
		}
	}
	
	/**
	 * Move the selected shapes by desired vec (_p1)
	 */
	private void move() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.move(_p1);
			}
		}
	}
	
	/**
	 * Rotate the selected shapes by desired degree (_p1)
	 */
	private void rotate(double angleDegrees) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.rotate(_p1, angleDegrees);
			}
		}
	}
	
	/**
	 * Scale the selected shapes by the ratio given
	 * @param ratio
	 */
	private void scale(double ratio) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.scale(_p1, ratio);
			}
		}
	}
	
	/**
	 * Copy all the selected shapes to the desired location (_p1)
	 * they will be set as unselected and their color will be _color
	 */
	private void copy() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				// copy the curr shape (g and move it to desired place)
				GeoShapeable newG = g.copy();
				newG.move(_p1);
				// set new gui_shapeable for newG
				GUI_Shapeable newS = new GUIShape(newG,s.isFilled(), _color, 0);
				newS.setSelected(false);	// new copied are not selected
				// add shape to collection of shapes
				_shapes.add(newS);
			}
		}
	}
	
	/**
	 * Deselects all the shapes in the collection
	 */
	private void deselectAll() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				// deselect
				s.setSelected(false);
				// return its original color
				StdDraw_Ex4.setPenColor(s.getColor());
			}
		}
	}
	
	/**
	 * This func removes the selected shapes
	 */
	private void removeSelected() {
		for(int i=_shapes.size() - 1;i >= 0;i--) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				_shapes.removeElementAt(i);			
			}
		}
	}
	
	private int getLastTag() {
		// if its the first shape
		if (_shapes.size() == 0)
			return -1;
		return _shapes.get(_shapes.size() - 1).getTag();
	}

	public void mouseRightClicked(Point2D p) {
		_gs = null;
		GeoShapeable polyShape = new Polygon2D(_polyPoints);
		GUI_Shapeable gs = new GUIShape(polyShape,_fill, _color, 0);
		gs.setTag(getLastTag() + 1);
		_shapes.add(gs);
		drawShapes();
		// reset values
		_polyPoints.clear();	// finished polygon - clear
		_p1 = null;
		
		
		// TEST BOUNDING BOX
//		gs = new GUIShape(_shapes.getBoundingBox(), false, Color.red, 0);
//		_shapes.add(gs);
//		drawShapes();		
		// TEST AREA
//		System.out.println(polyShape.area());

	}
	public void mouseMoved(MouseEvent e) {
		if(_p1!=null) {
			double x1 = StdDraw_Ex4.mouseX();
			double y1 = StdDraw_Ex4.mouseY();
			GeoShapeable gs = null;
			//	System.out.println("M: "+x1+","+y1);
			Point2D p = new Point2D(x1,y1);
			if(_mode.equals("Circle")) {
				double r = _p1.distance(p);
				gs = new Circle2D(_p1,r);
			}
			if(_mode.equals("Rect")) {
				gs = new Rect2D(_p1,p);
			}
			if(_mode.equals("Segment")) {
				gs = new Segment2D(_p1,p);
			}
			if(_mode.equals("Triangle")) {
				if(_p2 ==null) {
					gs = new Triangle2D(_p1,p,p);
				}
				else {
					gs = new Triangle2D(_p1,_p2,p);
				}
			}
			if(_mode.equals("Polygon")) {
				if (_polyPoints.size() == 1)
					gs = new Segment2D(_polyPoints.get(0),p);
				else	// show curr polygon with all points
					gs = new Polygon2D(_polyPoints, p);
			}
			_gs = new GUIShape(gs,false, Color.pink, 0);
			drawShapes();
		}
	}
	@Override
	public ShapeCollectionable getShape_Collection() {
		return this._shapes;
	}
	@Override
	public void show() {show(Ex4_Const.DIM_SIZE); }
	@Override
	public String getInfo() {
		return _shapes.toString();
	}
}
