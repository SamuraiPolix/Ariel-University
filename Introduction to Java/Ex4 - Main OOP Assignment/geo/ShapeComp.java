package Exe.Ex4.geo;

import java.util.Comparator;

import Exe.Ex4.Ex4_Const;
import Exe.Ex4.GUI_Shapeable;

/**
 * This class represents a Comparator over GUI_Shapes - 
 * as a linear order over GUI_Shapes.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class ShapeComp implements Comparator<GUI_Shapeable>{
	//////////add your code below ///////////
	
	
	public static final Comparator<GUI_Shapeable> CompByToString = new ShapeComp(Ex4_Const.Sort_By_toString);
	
	private int _flag;
	public ShapeComp(int flag) {
		_flag = flag;
		
	}
	@Override
	public int compare(GUI_Shapeable o1, GUI_Shapeable o2) {
		// We didn't know if we can use switch case
		// we also could just return area1-area2
		// but it was required from us to return -1/0/1 and not other int's
		int ans=0;		// default: 0
		double tmp1, tmp2;
		if(_flag == Ex4_Const.Sort_By_toString) {
			return o1.toString().compareTo(o2.toString());
		}
		
		if(_flag == Ex4_Const.Sort_By_Anti_toString) {
			return o2.toString().compareTo(o1.toString());
		}
		if (_flag == Ex4_Const.Sort_By_Area) {
			tmp2 = o2.getShape().area();
			tmp1 = o1.getShape().area();
			if (tmp1 < tmp2)
				return -1;
			else if (tmp1 > tmp2)
				return 1;
		}
		else if (_flag == Ex4_Const.Sort_By_Anti_Area) {
			tmp2 = o2.getShape().area();
			tmp1 = o1.getShape().area();
			if (tmp1 < tmp2)
				return 1;
			else if (tmp1 > tmp2)
				return -1;
		}
		else if (_flag == Ex4_Const.Sort_By_Perimeter) {
			tmp2 = o2.getShape().perimeter();
			tmp1 = o1.getShape().perimeter();
			if (tmp1 < tmp2)
				return -1;
			else if (tmp1 > tmp2)
				return 1;
		}
		else if (_flag == Ex4_Const.Sort_By_Anti_Perimeter) {
			tmp2 = o2.getShape().perimeter();
			tmp1 = o1.getShape().perimeter();
			if (tmp1 < tmp2)
				return 1;
			else if (tmp1 > tmp2)
				return -1;
		}
		else if (_flag == Ex4_Const.Sort_By_Tag) {
			tmp2 = o2.getTag();
			tmp1 = o1.getTag();
			if (tmp1 < tmp2)
				return -1;
			else if (tmp1 > tmp2)
				return 1;
		}
		else if (_flag == Ex4_Const.Sort_By_Anti_Tag) {
			tmp2 = o2.getTag();
			tmp1 = o1.getTag();
			if (tmp1 < tmp2)
				return 1;
			else if (tmp1 > tmp2)
				return -1;
		}
		return ans;		// def: 0 if equals
	}

}
