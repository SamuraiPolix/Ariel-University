package Exe.Ex2;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * This JUnit class represents a very simple unit testing for Ex2.
 * This class should be improved and generalized significantly.
 * Make sure you add documentations to this Tesing class.
 * @author boaz.ben-moshe
 *
 */

class Ex2Test {
	static double[] po1={2,0,3, -1,0},		// -x^3 + 3x^2 + 2
			po2 = {0.1,0,1, 0.1,3};			// 3x^4 + 0.1x^3 + x^2 + 0.1
	
	@Test
	void testF() {
		double fx0 = Ex2.f(po1, 0);
		double fx1 = Ex2.f(po1, 1);
		double fx2 = Ex2.f(po1, 2);
		assertEquals(2, fx0);
		assertEquals(4, fx1);
		assertEquals(6, fx2);

		// Check without mekadem
		double[] p1 = Ex2.getPolynomFromString("x^7 - 57.8x -8");
		assertEquals(4.4, Ex2.f(p1, 2), Ex2.EPS);
		
		// Check with {0}
		assertEquals(0, Ex2.f(Ex2.ZERO, 10), Ex2.EPS);
		
		// Check only with 'c'
		double[] p2 = {7};
		assertEquals(7, Ex2.f(p2, 68), Ex2.EPS);
		
		// Check for power>1
		double[] p3 = Ex2.getPolynomFromString("x^7 - 57.8x^3 + 636x");
		assertEquals(808169.6, Ex2.f(p3, 7), Ex2.EPS);
		
		// Check without mekadems at all && when the last one is -
		double[] p4 = Ex2.getPolynomFromString("x^9 + x^4 - x");
		assertEquals(-18012714.38788, Ex2.f(p4, -6.4), Ex2.EPS);
	}

	@Test
	void testRoot() {
		double x12 = Ex2.root(po1, 0, 10, Ex2.EPS);
		assertEquals(3.1958, x12, Ex2.EPS);
		
		/*
		 * No need to check:
		 * 1. we dont need to check x1=x2
		 * 	  because then our assumption that f(x1) * f(x2) <= 0 will be false
		 * 2. no need to check if constant - y=a!=0 for every x
		 * 	  because then our assumption that f(x1) * f(x2) <= 0 will be false
		 *    if a<0: a*a>0;	if a>0: a*a>0;		if a=0: covered in 1
		 */
		
		// {0} with 0 range - needs to be 0 because y=0 for every x
		double a12 = Ex2.root(Ex2.ZERO, 3, 3, Ex2.EPS);
		assertEquals(3, a12, Ex2.EPS);
		
		// {0} with range != 0, we have more then 1 possible answer - the one on the left is picked - here 1
		double b12 = Ex2.root(Ex2.ZERO, 1, 3, Ex2.EPS);
		assertEquals(1, b12, Ex2.EPS);			// we have many answers here. the "leftest" one is picked
		
		// check if eps is 10^-3, or 10^-6		- needs to be the same as b12
		double c12 = Ex2.root(Ex2.ZERO, 1, 3, Math.pow(10, -3));
		assertEquals(1, c12, Ex2.EPS);
		
		c12 = Ex2.root(Ex2.ZERO, 1, 3, Math.pow(10, -6));
		assertEquals(1, c12, Ex2.EPS);		
	}
	
	@Test
	void testRoot_rec() {		// same as testRoot but for root_rec
		double x12 = Ex2.root_rec(po1, 0, 10, Ex2.EPS);
		assertEquals(3.1958, x12, Ex2.EPS);

		/*
		 * No need to check:
		 * 1. we dont need to check x1=x2
		 * 	  because then our assumption that f(x1) * f(x2) <= 0 will be false
		 * 2. no need to check if constant - y=a!=0 for every x
		 * 	  because then our assumption that f(x1) * f(x2) <= 0 will be false
		 *    if a<0: a*a>0;	if a>0: a*a>0;		if a=0: covered in 1
		 */
		
		// {0} with 0 range - needs to be 0 because y=0 for every x
		double a12 = Ex2.root_rec(Ex2.ZERO, 3, 3, Ex2.EPS);
		assertEquals(3, a12, Ex2.EPS);
		
		// {0} with range != 0, we have more then 1 possible answer - the one on the left is picked - here 1
		double b12 = Ex2.root_rec(Ex2.ZERO, 1, 3, Ex2.EPS);
		assertEquals(1, b12, Ex2.EPS);			// we have many answers here. the "leftest" one is picked
				
		
		// check if eps is 10^-3, or 10^-6		- needs to be the same as b12
		double c12 = Ex2.root_rec(Ex2.ZERO, 1, 3, Math.pow(10, -3));
		assertEquals(1, c12, Ex2.EPS);
		c12 = Ex2.root(Ex2.ZERO, 1, 3, Math.pow(10, -6));
		assertEquals(1, c12, Ex2.EPS);	
	}
	
	@Test
	void testSameValue() {
		double[] p1 = {0.5, 6};				// 6x + 0.5
		double[] p2 = {1.5, 3};				// 3x + 1.5
		double x = Ex2.sameValue(p1, p2, 0, 3, Ex2.EPS);
		assertEquals(0.333, x, Ex2.EPS);
		
		/*
		 * No need to check:
		 * 1. 2 functions that dont have a same value - that never cut
		 * 		because we assume (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0
		 * 		our assumption means somewhere they "cut" each other - so there must be an answer
		 * 		but have 2 functions that dont cut, no matter what the range is, will give no answer
		 */
		
		// check if x is x1 or x2 - if they cut in one of the edges
		double x1 = Ex2.sameValue(p1, p2, 0.333, 3, Ex2.EPS);
		assertEquals(x1, 0.333, Ex2.EPS);
		
		// check {0} and another & having more then one answer - picked answer is the smallest
		double[] p3 = {0, 543, -34, 0, 0, 64, -9};		// -9x^6 + 64x^5 -34x^2 + 543x
		double x2 = Ex2.sameValue(Ex2.ZERO, p3, -10, 3, Ex2.EPS);
		assertEquals(x2, 0, Ex2.EPS);
		// assertEquals(x2, 7.12408, Ex2.EPS);		another possible answer
	}
	
	@Test
	void testAdd() {
		double[] p12 = Ex2.add(po1, po2);		// -x^3 + 3x^2 + 2	// 3x^4 + 0.1x^3 + x^2 + 0.1
		double[] minus1 = {-1};					// -1
		double[] pp2 = Ex2.mul(po2, minus1);	// 3x^4 + 0.1x^3 + x^2 + 0.1	// -1
		double[] p1 = Ex2.add(p12, pp2);		// 3x^4 - 0.9x^3 + 4x^2 + 2.1	// -3x^4 - 0.1x^3 - x^2 - 0.1
		assertEquals(Ex2.poly(po1), Ex2.poly(p1));
		double dd = Ex2.f(p12, 5);
		assertEquals(1864.6, dd, Ex2.EPS);
		
		// add same func to itself and check its the same as the func multiplied by 2
		double[] two = {2};	
		double[] pp = Ex2.mul(p12, two);		// multiply p12 by 2 = p12+p12
		double[] p12p12 = Ex2.add(p12, p12);	// p12 + p12
		assertEquals(Ex2.poly(pp), Ex2.poly(p12p12));	
		
		// add ZERO to a func
		double[] pp20 = Ex2.add(pp2, Ex2.ZERO);	
		assertEquals(Ex2.poly(pp2), Ex2.poly(pp20));	
		
		// add ZERO to itself
		double[] pp00 = Ex2.add(Ex2.ZERO, Ex2.ZERO);	
		assertEquals(Ex2.poly(Ex2.ZERO), Ex2.poly(pp00));	
	}

	@Test
	void testMulDoubleArrayDoubleArray() {
		double[] p12 = Ex2.add(po1, po2);		// -x^3 + 3x^2 + 2	// 3x^4 + 0.1x^3 + x^2 + 0.1
		double[] minus1 = {-1};					// -1
		double[] pp2 = Ex2.mul(po2, minus1);	// 3x^4 + 0.1x^3 + x^2 + 0.1	// -1
		double[] p1 = Ex2.add(p12, pp2);		// 3x^4 - 0.9x^3 + 4x^2 + 2.1	// -3x^4 - 0.1x^3 - x^2 - 0.1
		assertEquals(Ex2.poly(po1), Ex2.poly(p1));
		
		// From what I understand, I dont need to delete the empty cells at the end so checking multiply by 0 in not needed
		// therefore I dont really have test to do
	}
	@Test
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] dp1 = {2,6}; // 6x+2
		double[] dp2 = Ex2.derivative(p);
		assertEquals(dp1[0], dp2[0],Ex2.EPS);
		assertEquals(dp1[1], dp2[1],Ex2.EPS);
		assertEquals(dp1.length, dp2.length);
		
		// check for the same func
		double[] pp = {-1.1,2.3,3.1}; 		// 3.1X^2 + 2.3x - 1.1
		double[] dp = {2.3,6.2};			// 6.2x + 2.3
		double[] dpp = Ex2.derivative(pp);
		assertEquals(dp[0], dpp[0],Ex2.EPS);
		assertEquals(dp[1], dpp[1],Ex2.EPS);
		assertEquals(dp.length, dpp.length);
		
		// check without mekadmin
		double[] p2 = {0,-1,1}; 		// x^2 -x
		double[] dp3 = {-1,2}; 				// 2x -1
		double[] dp4 = Ex2.derivative(p2);
		assertEquals(dp3[0], dp4[0],Ex2.EPS);
		assertEquals(dp3[1], dp4[1],Ex2.EPS);
		assertEquals(dp3.length, dp4.length);
		
		// check ZERO
		double[] dp0 = {0}; 				// 0
		double[] dp00 = Ex2.derivative(Ex2.ZERO);
		assertEquals(dp0[0], dp00[0],Ex2.EPS);
		// assertEquals(dp0[1], dp00[1],Ex2.EPS);		- there is only one cell here
		assertEquals(dp0.length, dp00.length);
		
		// check without x's
		double[] six = {6}; 		// 6
		double[] dp6 = {0}; 		// 0
		double[] dp66 = Ex2.derivative(six);
		assertEquals(dp6[0], dp66[0],Ex2.EPS);
		// assertEquals(dp0[1], dp00[1],Ex2.EPS);		- there is only one cell here
		assertEquals(dp6.length, dp66.length);
		
	}
	@Test
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; 		// 3.1X^2 + 2.3x - 1.1
		String sp = Ex2.poly(p);
		double[] p1 = Ex2.getPolynomFromString(sp);
		boolean isSame = Ex2.equals(p1, p);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		
		// check with ZERO
		sp = Ex2.poly(Ex2.ZERO);				// 0
		p1 = Ex2.getPolynomFromString(sp);
		isSame = Ex2.equals(p1, Ex2.ZERO);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		
		// Check with minuses
		double[] p2 = {-7,-6.4,-9}; 		// -9X^2 - 6.4x - 7
		sp = Ex2.poly(p2);
		p1 = Ex2.getPolynomFromString(sp);
		isSame = Ex2.equals(p1, p2);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		
		// Check with single
		double[] p3 = {-4}; 		// -4
		sp = Ex2.poly(p3);
		p1 = Ex2.getPolynomFromString(sp);
		isSame = Ex2.equals(p1, p3);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		
		// check with x's only
		double[] p4 = {0, -1, 0, 0, 1, 0, 0, 0, 0, 1}; 		// x^9 + x^4 - x
		sp = Ex2.poly(p4);
		p1 = Ex2.getPolynomFromString(sp);
		isSame = Ex2.equals(p1, p4);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		
		// check with a single x
		double[] p5 = {0, 1}; 		// x
		sp = Ex2.poly(p5);
		p1 = Ex2.getPolynomFromString(sp);
		isSame = Ex2.equals(p1, p5);
		if(!isSame) {fail();}
		assertEquals(sp, Ex2.poly(p1));
	}
	
	@Test
	public void testArea() {
		double[] p1 = Ex2.getPolynomFromString("0.5x^3-3x-1");
		
		// check with one of the polynoms being 0 (p0)
		double ans = Ex2.area(p1, Ex2.ZERO, -3, 3, 3);
		double ans2 = Ex2.area(Ex2.ZERO, po1, 0, 5, 100);
		assertEquals(-6, ans);
		assertEquals(21.2453125, ans2, Ex2.EPS);
		
		// Check with one polynom under the x-line with {0} && big boxNumber
		double[] underX = {-2, 0, -3};		// -3x^2-2
		double ansUndX = Ex2.area(underX, Ex2.ZERO, 100, 200, 200);
		assertEquals(-7000193.75, ansUndX, Ex2.EPS);
		
		// Check with one polynom under the x-line and one above
		double[] aboveX = {9, 0, 7};		// 7x^2+9
		double ansAbvX = Ex2.area(aboveX, Ex2.ZERO, -77, 31, 50);
		assertEquals(1135434.067199999999998, ansAbvX, Ex2.EPS);
		
		// negative numOfBoxes
		double ansNegN = Ex2.area(p1, Ex2.ZERO, -3, 3, -10);
		assertEquals(0, ansNegN);	
		
		// range is 0: x1=x2
		double ansSameX = Ex2.area(p1, Ex2.ZERO, 3, 3, 20);
		assertEquals(0, ansSameX);	
		
		// polynom with itself
		double ansSamePol = Ex2.area(p1, p1, 3, 10, 20);
		assertEquals(0, ansSamePol);	

	}
}
