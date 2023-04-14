package Exe.EX3;

import java.awt.Color;

/**
 * This class is a simple "inter-layer" connecting (aka simplifing) the
 * StdDraw_Ex3 with the Map2D interface. Written for 101 java course it uses
 * simple static functions to allow a "Singleton-like" implementation. You
 * should change this class!
 *
 */
public class Ex3 {
	private static Map2D _map = null;
	private static Color _color = Color.blue;
	private static String _mode = "";
	public static final int WHITE = Color.WHITE.getRGB();
	public static final int BLACK = Color.BLACK.getRGB();
	private static Point2D tmpP;

	public static void main(String[] args) {
		int dim = 10; // init matrix (map) 10*10
		init(dim);
	}

	private static void init(int x) {
		StdDraw_Ex3.clear();
		_map = new MyMap2D(x);
		StdDraw_Ex3.setScale(-0.5, _map.getHeight() - 0.5);
		StdDraw_Ex3.enableDoubleBuffering();
		_map.fill(WHITE);
		drawArray(_map);
	}

	public static void drawGrid(Map2D map) {
		int w = map.getWidth();
		int h = map.getHeight();
		for (int i = 0; i < w; i++) {
			StdDraw_Ex3.line(i, 0, i, h);
		}
		for (int i = 0; i < h; i++) {
			StdDraw_Ex3.line(0, i, w, i);
		}
	}

	static public void drawArray(Map2D a) {
		StdDraw_Ex3.clear();
		StdDraw_Ex3.setPenColor(Color.gray);
		drawGrid(_map);
		for (int y = 0; y < a.getWidth(); y++) {
			for (int x = 0; x < a.getHeight(); x++) {
				int c = a.getPixel(x, y);
				StdDraw_Ex3.setPenColor(new Color(c));
				drawPixel(x, y);
			}
		}
		StdDraw_Ex3.show();
	}

	public static void actionPerformed(String p) {
		_mode = p;
		// Set color
		if (p.equals("Clear")) {
			_map.fill(WHITE);
		}
		if (p.equals("White")) {
			_color = Color.WHITE;
		}
		if (p.equals("Black")) {
			_color = Color.BLACK;
		}
		if (p.equals("Blue")) {
			_color = Color.BLUE;
		}
		if (p.equals("Red")) {
			_color = Color.RED;
		}
		if (p.equals("Yellow")) {
			_color = Color.YELLOW;
		}
		if (p.equals("Green")) {
			_color = Color.GREEN;
		}

		// Set map size
		if (p.equals("20x20")) {
			init(20);
		}
		if (p.equals("40x40")) {
			init(40);
		}
		if (p.equals("80x80")) {
			init(80);
		}
		if (p.equals("160x160")) {
			init(160);
		}

		drawArray(_map);
	}

	public static void mouseClicked(Point2D p) {
		System.out.println(p);
		int col = _color.getRGB();
		/*
		 **** For Segment, Rect & ShortestPath we need 2 points so: if a point was selected
		 * and we dont have one saved yet, we save it and wait for the second point to
		 * come, then we reset the temp point holder. 
		 **** For Circle: we need a point (the mid of the circle, which is selected first) and the radius which is selected later.
		 * so the first point selected is saved into a temp point holder, when we
		 * get the second point, we call drawCircle with the middle point and the
		 * distance between the mid point and the selected point for radius.
		 */
		// Point
		if (_mode.equals("Point")) {
			_map.setPixel(p, col);
		}
		// Segment
		if (_mode.equals("Segment")) {
			if (tmpP != null) {
				_map.drawSegment(tmpP, p, col);
				tmpP = null;
			} else {
				tmpP = p;
				return;
			}
		}
		// Rect
		if (_mode.equals("Rect")) {
			if (tmpP != null) {
				_map.drawRect(tmpP, p, col);
				tmpP = null;
			} else {
				tmpP = p;
				return;
			}
		}
		// Circle
		if (_mode.equals("Circle")) {
			if (tmpP != null) {
				_map.drawCircle(tmpP, p.distance(tmpP), col);
				tmpP = null;
			} else {
				tmpP = p;
				return;
			}
		}
		// Fill
		if (_mode.equals("Fill")) {
			_map.fill(p, col);
			_mode = "none";
		}
		// Gol
		if (_mode.equals("Gol")) {
			_map.nextGenGol();
		}
		// ShortestPath
		if (_mode.equals("ShortestPath")) {
			if (tmpP != null) {
				// we need to draw it here because a color is not given to Map2D.shortestPath.
				drawPointsArray(_map.shortestPath(tmpP, p), col);
				tmpP = null;
			} else {
				tmpP = p;
				return;
			}
		}
		drawArray(_map);
	}

	/**
	 * This functions draws a given array of points with the given color
	 * 
	 * @param shortestPath - array of points to draw
	 * @param col          - color to draw
	 */
	private static void drawPointsArray(Point2D[] shortestPath, int col) {
		for (int i = 0; i < shortestPath.length; i++) {
			_map.setPixel(shortestPath[i], col);
		}
	}

	static private void drawPixel(int x, int y) {
		StdDraw_Ex3.filledCircle(x, y, 0.3);
	}
}
