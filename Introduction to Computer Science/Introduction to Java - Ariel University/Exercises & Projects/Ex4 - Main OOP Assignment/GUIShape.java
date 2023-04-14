package Exe.Ex4;
/**
 * This class implements the GUI_shape.
 * Ex4: you should implement this class!
 * @author I2CS
 */
import java.awt.Color;
import java.util.ArrayList;

import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.Triangle2D;


public class GUIShape implements GUI_Shapeable{
	private GeoShapeable _g = null;
	private boolean _fill;
	private Color _color;
	private int _tag;
	private boolean _isSelected;
	
	public GUIShape(GeoShapeable g, boolean f, Color c, int t) {
		_g = null;
		if (g!=null) {_g = g.copy();}
		_fill= f;
		_color = c;
		_tag = t;
		_isSelected = false;
	}
	public GUIShape(GUIShape ot) {
		this(ot._g, ot._fill, ot._color, ot._tag);
	}
	// empty constructor for loading file
	public GUIShape() {
	}
	
	@Override
	public GeoShapeable getShape() {
		return _g;
	}

	@Override
	public boolean isFilled() {
		return _fill;
	}

	@Override
	public void setFilled(boolean filled) {
		_fill = filled;
	}

	@Override
	public Color getColor() {
		return _color;
	}

	@Override
	public void setColor(Color cl) {
		_color = cl;
	}

	@Override
	public int getTag() {
		return _tag;
	}

	@Override
	public void setTag(int tag) {
		_tag = tag;
	}

	@Override
	public GUI_Shapeable copy() {
		GUI_Shapeable cp = new GUIShape(this);
		return cp;
	}
	@Override
	public String toString() {
		// calculate RGB: 
		// https://www.rapidtables.com/web/color/RGB_Color.html#:~:text=RGB%20color%20space,-RGB%20color%20space&text=The%20red%2C%20green%20and%20blue%20use%208%20bits%20each%2C%20which,*256%3D16777216%20possible%20colors.&text=Each%20pixel%20in%20the%20LED,LEDs%20(light%20emitting%20diodes).
		int color = (_color.getRed()*65536)+(_color.getGreen()*256)+_color.getBlue();
		String str = "";
		str += "GUIShape,";
		str += color + ",";
		str += isFilled() + ",";
		str += getTag() + ",";
		str += getShape().getClass().getSimpleName() + ",";
		str += getShape();
		return str;
	}
	
	// Initializes values from loaded file
	public void init(String[] ww) {
		_color = new Color(Integer.parseInt(ww[1]));
		_fill = ww[2].equals("true");
		_tag = Integer.parseInt(ww[3]);
		if(ww[4].equals("Circle2D")) {
			Point2D p = new Point2D(Double.parseDouble(ww[5]), Double.parseDouble(ww[6]));
			_g = new Circle2D(p, Double.parseDouble(ww[7]));
		}
		if(ww[4].equals("Rect2D")) {
			// we take every 2 values from the string arr and create a point from it
			Point2D[] p = new Point2D[Rect2D.NUM_OF_POINTS];
			int j = 5;
			for (int i = 0; i < p.length; i++) {
				p[i] = new Point2D(Double.parseDouble(ww[j]), Double.parseDouble(ww[j+1]));
				j+=2;
			}
			_g = new Rect2D(p);
		}
		if(ww[4].equals("Triangle2D")) {
			// we take every 2 values from the string arr and create a point from it
			Point2D[] p = new Point2D[Triangle2D.NUM_OF_POINTS];
			int j = 5;
			for (int i = 0; i < p.length; i++) {
				p[i] = new Point2D(Double.parseDouble(ww[j]), Double.parseDouble(ww[j+1]));
				j+=2;
			}
			_g = new Triangle2D(p);
		}
		if(ww[4].equals("Segment2D")) {
			Point2D p1 = new Point2D(Double.parseDouble(ww[5]), Double.parseDouble(ww[6]));
			Point2D p2 = new Point2D(Double.parseDouble(ww[7]), Double.parseDouble(ww[8]));

			_g = new Segment2D(p1, p2);
		}
		if(ww[4].equals("Polygon2D")) {
			ArrayList<Point2D> p = new ArrayList<Point2D>();
			for (int i = 5; i < ww.length; i+=2) {
				p.add(new Point2D(Double.parseDouble(ww[i]), Double.parseDouble(ww[i+1])));
			}
			_g = new Polygon2D(p);
		}
	}
	@Override
	public boolean isSelected() {
		return this._isSelected;
	}
	@Override
	public void setSelected(boolean s) {
		this._isSelected = s;
	}
	@Override
	public void setShape(GeoShapeable g) {
		_g = g.copy();
	}
}
