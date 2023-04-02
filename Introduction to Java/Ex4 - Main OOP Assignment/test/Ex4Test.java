package Exe.Ex4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
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
import Exe.Ex4.gui.Ex4;

class Ex4Test {
	Ex4 ex4 = Ex4.getInstance();
	ShapeCollectionable shapes = ex4.getShape_Collection();
	String file = "C:\\Users\\samla\\eclipse-workspace\\Ex4\\a2"; //make sure the file is your working directory.
	
	@Test
	void testLoadSave() {
		shapes.load(file);
		ex4.init(shapes);
		assertTrue(shapes.get(24).getShape() instanceof Rect2D);
		assertEquals(25, shapes.size());
	}
}
