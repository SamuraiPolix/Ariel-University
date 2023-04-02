package Exe.Ex4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Rect2D;

/**
 * This class represents a collection of GUI_Shape.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class ShapeCollection implements ShapeCollectionable{
	private ArrayList<GUI_Shapeable> _shapes;
	
	public ShapeCollection() {
		_shapes = new ArrayList<GUI_Shapeable>();
	}
	@Override
	public GUI_Shapeable get(int i) {
		return _shapes.get(i);
	}

	@Override
	public int size() {
		return _shapes.size();
	}

	@Override
	public GUI_Shapeable removeElementAt(int i) {
		shiftTags(i);
		return _shapes.remove(i);
		
	}
	
	// this shifts all the tags -1 to deal with a deleted shape
	// i is the index of the deleted shape
	private void shiftTags(int i) {
		for (int j = i; j < _shapes.size(); j++) {
			_shapes.get(j).setTag(_shapes.get(j).getTag()-1);
		}
	}

	@Override
	public void addAt(GUI_Shapeable s, int i) {
		_shapes.add(i, s);
	}
	@Override
	public void add(GUI_Shapeable s) {
		if(s!=null && s.getShape()!=null) {
			_shapes.add(s);
		}
	}
	@Override
	public ShapeCollectionable copy() {
		ShapeCollectionable newShapes = new ShapeCollection();
		for (int i = 0; i < size(); i++) {
			newShapes.add(_shapes.get(i));
		}
		return newShapes; // TODO ?!?!?!?!
	}

	@Override
	public void sort(Comparator<GUI_Shapeable> comp) {
		_shapes.sort(comp);
	}

	@Override
	public void removeAll() {
		_shapes.clear();
	}

	@Override
	public void save(String fileName) {
		try {
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file);
			fw.write(toString());
			fw.flush();
			fw.close();
		}
		catch(IOException ex) {
			System.out.print("Error writing file\n" + ex);
		}
	}
	
	

	@Override
	public void load(String fileName) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String str;
			String[] arrStr;
			// split every row into array of the values
			str = br.readLine();
			ArrayList<GUI_Shapeable> newShapes = new ArrayList<GUI_Shapeable>();
			GUIShape gs = new GUIShape();
			for(int i=0; str!=null; i++) {
				if (i == 25) {
					System.out.println("");
				}
				arrStr = str.split(",");
				System.out.println(i+") "+str);
				gs.init(arrStr);
				newShapes.add(new GUIShape(gs));
				str = br.readLine();
			}
			br.close();
			_shapes = newShapes;
		}
		catch(IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}
	@Override
	public Rect2D getBoundingBox() {
		if (_shapes.size() == 0)
			return null;
		int i = 0;
		double minX, minY, maxX, maxY;
		// set first values to first point
		minX = _shapes.get(i).getShape().getPoints()[0].x();
		maxX = _shapes.get(i).getShape().getPoints()[0].x();
		minY = _shapes.get(i).getShape().getPoints()[0].y();
		maxY = _shapes.get(i).getShape().getPoints()[0].y();
		Point2D[] pointsArr;
		// check if there are smaller or bigger
		for (i = 0; i <_shapes.size(); i++) {
			pointsArr = _shapes.get(i).getShape().getPoints();
			for (int j = 0; j < pointsArr.length; j++) {
				if (pointsArr[j].x() < minX) {
					minX = pointsArr[j].x();
				}
				if (pointsArr[j].y() < minY) {
					minY = pointsArr[j].y();
				}
				if (pointsArr[j].x() > maxX) {
					maxX = pointsArr[j].x();			
				}
				if (pointsArr[j].y() > maxY) {
					maxY = pointsArr[j].y();
				}
			}
		}
		return new Rect2D(new Point2D(minX,minY), new Point2D(maxX,maxY));
	}
	@Override
	public String toString() {
		String ans = "";
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			ans += s.toString()+"\n";
		}
		return ans;
	}
	

}
