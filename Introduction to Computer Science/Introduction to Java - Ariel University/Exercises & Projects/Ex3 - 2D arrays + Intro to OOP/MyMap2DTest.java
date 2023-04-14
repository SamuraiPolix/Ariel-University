package Exe.EX3;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyMap2DTest {

	Map2D testMap;
	Map2D testMap1;

	Point2D p1 = new Point2D (4,4);
	Point2D p2 = new Point2D (6,7);

	@BeforeEach
	public void setMap() {
		testMap = new MyMap2D(5);
		testMap1= new MyMap2D (20);
		testMap.fill(Map2D.WHITE);
		testMap1.fill(Map2D.WHITE);
	}
	@Test
	void testSizeOfMap() {
		assertEquals(5,testMap.getWidth());
		assertEquals(5,testMap.getHeight());
		assertEquals(20, testMap.getWidth());
		assertEquals(20, testMap.getHeight());
	}
	@Test
	void testSegment() {
		testMap.drawSegment(new Point2D(1,1) , new Point2D(4,4), Color.GREEN.getRGB());
		assertEquals(Color.GREEN.getRGB(),testMap.getPixel(3,3));
	}
	@Test
	void testCircle() {
		testMap.drawCircle(new Point2D(1,1), 3, Ex3.BLACK);
		assertEquals(Ex3.BLACK,testMap.getPixel(2, 2));
	}
	@Test
	void testFill() {
		testMap1.drawRect(p1, p2, Color.BLUE.getRGB());
		int count = testMap1.fill(new Point2D(6,6), Color.GREEN.getRGB());
		assertTrue(testMap1.getPixel(new Point2D(5,5))==Color.GREEN.getRGB());
		assertEquals(12, count);		// rect is 3x4
	}
	@Test
	void testShortestPathDist() {
		testMap1.drawRect(p1, p2,Color.GREEN.getRGB() );
		int radius =  testMap1.shortestPathDist(new Point2D(3,4), new Point2D(4,8));
		assertTrue(radius<9);
	}
	@Test
	void testGameOfLife() {
		// check map is changed to black & white
		testMap1.drawSegment(new Point2D(2,2), new Point2D(4,2), Color.GREEN.getRGB());
		testMap1.nextGenGol();
		assertFalse(Color.GREEN.getRGB() == testMap1.getPixel(3,1));
		assertEquals(Ex3.BLACK,testMap1.getPixel(3,1));
		
		testMap1.fill(Ex3.WHITE);	// reset map
		
		//** a living cell with less than 2 neighbors should die
		testMap1.setPixel(3, 3, Ex3.BLACK);		// no neighbors
		testMap1.nextGenGol();
		assertEquals(Ex3.WHITE, testMap1.getPixel(3, 3));	// should be dead
		
		testMap1.fill(Ex3.WHITE);	// reset map

		//** a living cell with 2 or 3, neighbors should stay alive
		testMap1.drawSegment(new Point2D(2,3), new Point2D(4,3), Ex3.BLACK);
		testMap1.nextGenGol();
		// mid - stays alive, the rest die
		assertEquals(Ex3.WHITE, testMap1.getPixel(2, 3));	// should be dead
		assertEquals(Ex3.WHITE, testMap1.getPixel(4, 3));	// should be dead
		assertEquals(Ex3.BLACK, testMap1.getPixel(3, 3));	// should remain alive
		
		testMap1.fill(Ex3.WHITE);	// reset map

		//** a living cell with more than 3 neighbors should die
		testMap1.drawRect(new Point2D(1,1), new Point2D(3,3), Ex3.BLACK);
		testMap1.nextGenGol();
		// center should be dead, the rest alive
		assertEquals(Ex3.WHITE, testMap1.getPixel(2, 2));	// should be dead
		assertEquals(Ex3.BLACK, testMap1.getPixel(3,3));	// should remain alive

		//* a dead cell should be born if it has exactly 3 neighbors
		assertEquals(Ex3.BLACK, testMap1.getPixel(0, 2));	// should get born
		}
	@Test
	void testPoint() {
		testMap.setPixel(p1, Ex3.BLACK);
		assertEquals(Ex3.BLACK, testMap.getPixel(p1));
	}

}


