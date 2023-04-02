package Exe.EX3;

/**
 * This class implements the Map2D interface. You should change (implement) this
 * class as part of Ex3.
 */
public class MyMap2D implements Map2D {
	private int[][] _map;

	public MyMap2D(int w, int h) {
		init(w, h);
	}

	public MyMap2D(int size) {
		this(size, size);
	}

	public MyMap2D(int[][] data) {
		this(data.length, data[0].length);
		init(data);
	}

	@Override
	public void init(int w, int h) {
		_map = new int[w][h];

	}

	@Override
	public void init(int[][] arr) {
		init(arr.length, arr[0].length);
		for (int x = 0; x < this.getWidth() && x < arr.length; x++) {
			for (int y = 0; y < this.getHeight() && y < arr[0].length; y++) {
				this.setPixel(x, y, arr[x][y]);
			}
		}
	}

	@Override
	public int getWidth() {
		return _map.length;
	}

	@Override
	public int getHeight() {
		return _map[0].length;
	}

	@Override
	public int getPixel(int x, int y) {
		return _map[x][y];
	}

	@Override
	public int getPixel(Point2D p) {
		return this.getPixel(p.ix(), p.iy());
	}

	public void setPixel(int x, int y, int v) {
		_map[x][y] = v;
	}

	public void setPixel(Point2D p, int v) {
		setPixel(p.ix(), p.iy(), v);
	}

	public void setPixels(Point2D[] p, int v) {
		for (int i = 0; i < p.length; i++) {
			setPixel(p[i].ix(), p[i].iy(), v);
		}
	}

	private int[][] getMap() {
		return this._map;
	}

	@Override
	public void drawSegment(Point2D p1, Point2D p2, int v) {
		/**
		 * We wanted to use Bresenham’s Line Algorithm, but we didn't know if we are allowed to.
		 * We decided to use the equation of the straight line.
		 * in every iteration, we round the coordinates on the line to the closest integer -
		 * which gives us the best pixel to set.
		 * In other words: we imagine a straight line from p1 to p2
		 * for every x we put in the equation and find the matching y,
		 * we round it and get the center of the closest pixel. same concept for y.
		 */
		double dx = p1.ix()-p2.ix(),	// dif in x
				dy = p1.iy()-p2.iy();	// dif in y
		double m = 0;			// slope of the line
		if (dx != 0)		// so we dont divide by 0 by accident
			m = dy/dx;
		double b = p1.iy() - (m*p1.ix());		// b=y-mx for p1 or p2 (doesnt matter)
		double yLine = 0, xLine = 0;	// for yLine is the y on the line for curr x. same concept for xLine.
		
		// we pick which line to run on, x or y, according to the one with more step needed.
		if (Math.abs(dx) >= Math.abs(dy)) {
			// more steps are needed for x, so we run on x
			for (int x = Math.min(p1.ix(), p2.ix()); x <= Math.max(p1.ix(), p2.ix()); x++) {
		    	yLine = m*x + b;	// if m=0, yLine=b=y1
		        // Calculate the corresponding y value for the current x
		        // Set the pixel at the coordinates (x, y) to the desired color
		    	setPixel(x, (int)Math.round(yLine), v);
			}
		} else {
			// more steps are needed for y, so we run on y
			xLine = p1.ix();		// covers the case of m being 0 (vertical line) p1.x=p2.x
			for (int y = Math.min(p1.iy(), p2.iy()); y <= Math.max(p1.iy(), p2.iy()); y++) {
	            // Calculate the corresponding x value for the current y
			 	if (m != 0)
			 		xLine = (y - b) / m;
	            // Set the pixel at the coordinates (x, y) to the desired color
	            setPixel((int)Math.round(xLine), y, v);
	            }
		}
	}

	@Override
	public void drawRect(Point2D p1, Point2D p2, int col) {
		/*
		 * we run from the smallest x to the biggest, while running on the y's as well,
		 * coloring every pixel in the area.
		 */
		for (int x = Math.min(p1.ix(), p2.ix()); x <= Math.max(p1.ix(), p2.ix()); x++) {
			for (int y = Math.min(p1.iy(), p2.iy()); y <= Math.max(p1.iy(), p2.iy()); y++) {
				setPixel(x, y, col);
			}
		}
	}

	@Override
	public void drawCircle(Point2D p, double rad, int col) {
		/**
		 * We run through the map and color the pixels that are inside the circle -
		 * from the features of a circle we know every point in
		 * the circle is in a distance of less then, or, the radius from the middle
		 * so the only points with a distance of less then or equal to rad from p are
		 * points in/on the circle.
		 */
		for (int x = 0; x < _map.length; x++) {
			for (int y = 0; y < _map[0].length; y++) {
				// by subtracting EPS we make sure we don't miss points
				// (pressing a bit higher or lower on the pixel makes it sensitive)
				if (p.distance(new Point2D(x, y))-Point2D.EPS <= rad)
					setPixel(x, y, col);
			}
		}
	}

	@Override
	public int fill(Point2D p, int new_v) {
		// functia maatefet recursivit
		return fillRecur(p.ix(), p.iy(), getPixel(p), new_v);
	}

	@Override
	public int fill(int x, int y, int new_v) {
		// functia maatefet recursivit
		return fillRecur(x, y, getPixel(x, y), new_v);
	}

	@Override
	public Point2D[] shortestPath(Point2D p1, Point2D p2) {
		Point2D[] pArray = new Point2D[shortestPathDist(p1, p2) + 1];

		// if same point
		if (p1.close2equals(p2)) {
			pArray[0] = p1;
			return pArray;
		}
		if (pArray.length == 1) {
			// This means we got 0 from "shortestPathDist",
			// also its no the same point so we got stuck
			return null;
		}
		// if colors are different - we can't go there. NOTE: assuming they are the same
		if (getPixel(p1) != getPixel(p2))
			return null; // NOTE: this crashes the app but yours crushes as well so we left it that way.

		// find shortestPath
		// set new array of ints that indicates where we are allowed to cross and where not
		int[][] intArray = new int[_map.length][_map[0].length];
		initSPValues(intArray, p1); // by this point the color of p1,p2 is the same color for sure

		// find the path by setting radius num each step
		// this function return the radius so if its 0, a path was not found
		if (setSPValues(intArray, p1.ix(), p1.iy(), p2.ix(), p2.iy()) == 0)
			return null;

		// Trace back the route by finding the cells near you that have a radius smaller from source
		traceBackSP(intArray, pArray, p2.ix(), p2.iy());

		// print path
		printPointsArray(pArray);

		return pArray;
		// path is colored in Ex3 - because we don't have access to the color selected here
	}

	@Override
	public int shortestPathDist(Point2D p1, Point2D p2) {
		// if same point
		if (p1.close2equals(p2)) {
			return 1;
		}
		// if colors are different - we can't go there. NOTE: assuming they are the same
		if (getPixel(p1) != getPixel(p2))
			return 0;

		// find shortestPath
		// set new array of ints that indicates where we are allowed to cross and where not
		int[][] intArray = new int[_map.length][_map[0].length];
		
		initSPValues(intArray, p1); // by this point the color of p1,p2 is the same

		// find the path by setting radius num each step
		// this function return the radius so if its 0, a path was not found
		return setSPValues(intArray, p1.ix(), p1.iy(), p2.ix(), p2.iy());
	}

	@Override
	public void nextGenGol() {
		int numOfLive = 0;

		MyMap2D newMap = new MyMap2D(_map.length, _map[0].length);
		newMap.fill(WHITE); // init white map

		for (int x = 0; x < _map.length; x++) {
			for (int y = 0; y < _map[0].length; y++) {
				// changes map to black&white while running on it
				if (getPixel(x, y) != WHITE) {
					newMap.setPixel(x, y, BLACK);
				}
				// count num of living neighbors for curr cell
				numOfLive = numOfLiveNeighbors(x, y);
				// the state of every cell stays the same if it has 2 neighbors
				if (numOfLive != 2) {
					/**
					 * living cells with less then 2 neighbors - die
					 * NOTE: we check "not white" and not "black" because we are yet to change all
					 * the cells to black&white.
					 * while checking the state of every cell, we change the map.
					 * that way we save us doing another n^2 loop to change all colors to
					 * black&white.
					 */
					if (numOfLive < 2 && getPixel(x, y) != WHITE) {
						newMap.setPixel(x, y, WHITE);
					}
					// if cell is alive, it will stay alive. if not, it will be born
					if (numOfLive == 3 && getPixel(x, y) == WHITE) {
						newMap.setPixel(x, y, BLACK);
					}
					// living cells with more then 3 neighbors die
					if (numOfLive > 3 && getPixel(x, y) != WHITE) {
						newMap.setPixel(x, y, WHITE);
					}
				}
			}
		}
		// set map to the new one we made
		this._map = newMap.getMap();
	}

	@Override
	public void fill(int c) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				this.setPixel(x, y, c);
			}
		}
	}

	/*
	 * Helpers for MyMap2D TODO(after submitting): Put in a new class
	 */
	/**
	 * This function gets the coordinations of a point, a color that needs to be
	 * changed and the new color it runs recursively through its neighbors and
	 * changes their color to the new one if they have the old one Its used in a
	 * "functiat matefet" for _fill_ so it will be easier to hold the old color
	 * 
	 * @param x
	 * @param y
	 * @param old_v
	 * @param new_v
	 * @return
	 */
	private int fillRecur(int x, int y, int old_v, int new_v) {
		setPixel(x, y, new_v);

		int count = 1; // the number of points filled

		// check all neighbors
		// right
		if (y + 1 < _map.length && getPixel(x, y + 1) == old_v) {
			count += fillRecur(x, y + 1, old_v, new_v);
		}
		// left
		if (y - 1 >= 0 && getPixel(x, y - 1) == old_v) {
			count += fillRecur(x, y - 1, old_v, new_v);
		}
		// up
		if (x + 1 < _map[0].length && getPixel(x + 1, y) == old_v) {
			count += fillRecur(x + 1, y, old_v, new_v);
		}
		// down
		if (x - 1 >= 0 && getPixel(x - 1, y) == old_v) {
			count += fillRecur(x - 1, y, old_v, new_v);
		}
		return count;
	}

	/**
	 * initialize shortestPath values: -2 if not accessible, -1 if not visited yet
	 * (explained in boaz's video)
	 * 
	 * @param intArray
	 * @param p
	 */
	private void initSPValues(int[][] intArray, Point2D p) {
		// find the path by setting radius num each step - if we get blocked, return null.
		// values explained: -1: allowed to cross, haven't visited yet.
		//					 -2: not allowed, obstacle.
		for (int x = 0; x < _map.length; x++) {
			for (int y = 0; y < _map[0].length; y++) {
				if (getPixel(x, y) != getPixel(p))
					intArray[x][y] = -2;
				else
					intArray[x][y] = -1;
			}
		}
		intArray[p.ix()][p.iy()] = 0; // set source as first step in path
	}

	/**
	 * set shortestPath values recursively: 1,2,3,... according to the curr "radius"
	 * (explained in boaz's video)
	 * 
	 * @param intArray
	 * @param x1,      y1 - souce point
	 * @param x2,      y2 - destination point
	 * @return int - the distance of the shortest path
	 */
	private int setSPValues(int[][] intArray, int x1, int y1, int x2, int y2) {
		// find the path by setting radius num each step
		// this function return the radius so if its 0, a path was not found
		int radius = 0;
		boolean found = true; // true if at least one was found, otherwise, we are blocked and there is no
								// reason to keep looking
		while (intArray[x2][y2] == -1 && found) {
			found = false;
			for (int x = 0; x < intArray.length; x++) {
				for (int y = 0; y < intArray[0].length; y++) {
					if (intArray[x][y] == radius) {
						// set all neighbors to the curr radius + 1
						if (y + 1 < intArray[0].length && intArray[x][y + 1] == -1) {
							intArray[x][y + 1] = radius + 1;
							found = true;
						}
						if (y - 1 >= 0 && intArray[x][y - 1] == -1) {
							intArray[x][y - 1] = radius + 1;
							found = true;
						}
						if (x + 1 < intArray.length && intArray[x + 1][y] == -1) {
							intArray[x + 1][y] = radius + 1;
							found = true;
						}
						if (x - 1 >= 0 && intArray[x - 1][y] == -1) {
							intArray[x - 1][y] = radius + 1;
							found = true;
						}
					}
				}
			}
			radius++;
		}

		if (!found) { // we finished the while because we got to a radius where we are stuck
			return 0; // there is no way to get to the destination - we are blocked
		}

		return radius;
	}

	/**
	 * This function get a map, an array of points and the coordinates of our
	 * destination point [x,y]. It starts from the destination and traces back the
	 * route to the source, coloring the cells on the way. There may be a few
	 * possible routes. this chooses a random possible one (they are all the same in
	 * distance). The function returns an array of the points included in our
	 * selected route.
	 * 
	 * @param intArray - array of possible routes from source to destination
	 * @param pArray   - array of points from chosen path - to return
	 * @param x        - x of destinatio point [x,y]
	 * @param y        - y of destinatio point [x,y]
	 */
	private void traceBackSP(int[][] intArray, Point2D[] pArray, int x, int y) {
		// if this cell in intArray is null (its the 1st run) - add the destination
		if (pArray[intArray[x][y]] == null) {
			pArray[intArray[x][y]] = new Point2D(x, y);
		}
		// when arriving at the source, finish
		if (intArray[x][y] == 0) {
			return;
		}

		// above
		if (x + 1 < intArray.length && intArray[x + 1][y] == intArray[x][y] - 1) {
			pArray[intArray[x][y] - 1] = new Point2D(x + 1, y);
			traceBackSP(intArray, pArray, x + 1, y);
			return;
		}
		// below
		if (x - 1 >= 0 && intArray[x - 1][y] == intArray[x][y] - 1) {
			pArray[intArray[x][y] - 1] = new Point2D(x - 1, y);
			traceBackSP(intArray, pArray, x - 1, y);
			return;
		}
		// right
		if (y + 1 < intArray[0].length && intArray[x][y + 1] == intArray[x][y] - 1) {
			pArray[intArray[x][y] - 1] = new Point2D(x, y + 1);
			traceBackSP(intArray, pArray, x, y + 1);
			return;
		}
		// left
		if (y - 1 >= 0 && intArray[x][y - 1] == intArray[x][y] - 1) {
			pArray[intArray[x][y] - 1] = new Point2D(x, y - 1);
			traceBackSP(intArray, pArray, x, y - 1);
			return;
		}
	}

	/**
	 * This function get the coordinations of a point on the map and returns the
	 * number of living neighbors it got
	 * 
	 * @param x
	 * @param y
	 * @return number of living neighbors for this point [x,y] on map
	 */
	private int numOfLiveNeighbors(int x, int y) {
		int count = 0;
		// check all neighbors (including diagonal) - combinations of -1,0,1 for x,y
		for (int xi = -1; xi <= 1; xi++) {
			for (int yi = -1; yi <= 1; yi++) {
				// if both 0, we are on our point, not a neighbor
				if (xi != 0 || yi != 0) {
					// make sure we are not "leaving" the map
					if (y + yi >= 0 && y + yi < _map[0].length && x + xi >= 0 && x + xi < _map.length) {
						// if neighbor checked is alive (not white), add to counter
						if (getPixel(x + xi, y + yi) != WHITE)
							count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * This function gets an array of points and prints it as required
	 * 
	 * @param pArray - array of points creating a path.
	 */
	private void printPointsArray(Point2D[] pArray) {
		System.out.println("ShortestPath: " + pArray[0].toStringInt() + " --> "
				+ pArray[pArray.length - 1].toStringInt() + "  length: " + pArray.length);
		for (int i = 0; i < pArray.length; i++) {
			System.out.println(i + ") " + pArray[i].toStringInt());
		}
	}
}
