package Exe.Ex2;
/** 
 * This class represents a set of functions on a polynom - represented as array of doubles.
 * In general, such an array {-1,2,3.1} represents the following polynom 3.1x^2+2x-1=0,
 * The index of the entry represents the power of x.
 * 
 * Your goal is to complete the functions below, see the marking: // *** add your code here ***
 *
 * @author boaz.benmoshe
 * 
 */
public class Ex2 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynom is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	
	/** Two polynoms are equal if and only if the have the same coefficients - up to an epsilon (aka EPS) value.
	 * @param p1 first polynom
	 * @param p2 second polynom
	 * @return true iff p1 represents the same polynom as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
		// *** add your code here ***
		int i = 0;		// counter to run through the array
		while (i < p1.length && i < p2.length && ans) {
			if (Math.abs(p1[i] - p2[i]) > EPS)
				ans = false;
			i++;
		}
		// Finish checking if one of the arrays is longer - can be still equal if the cells will be valued up to EPS
		if (p1.length > p2.length && ans) {
			i = p1.length - p2.length;
			ans = finishEqualCheck(p1, i);			// in order not to write it twice
		}
		else if (p1.length > p2.length && ans) {
			i = p2.length - p1.length;
			ans = finishEqualCheck(p2, i);
		}
		// **************************
		return ans;
	}
	
	/**
	 * Computes the f(x) value of the polynom at x.
	 * @param poly
	 * @param x
	 * @return f(x) - the polynom value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		// *** add your code here ***
		int i = 1;
		
		// if x is 0, all polynoms will reset except for the one without x --> x^0
		if (x == 0)
			return poly[0];
		ans += poly[0];
		while (i < poly.length) {
			ans += poly[i] * Math.pow(x, i);
			i++;
		}
		// **************************
		return ans;
	}
	/** 
	 * Computes a String representing the polynom.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynom represented as an array of doubles
	 * @return String representing the polynom: 
	 */
	public static String poly(double[] poly) {
		String ans = "";
		// *** add your code here ***
		int i = poly.length - 1;
		while (i >= 0) {
			// if we are after the biggest polynom, we need to add "+" before positive numbers
			if (i != poly.length - 1 && poly[i] > 0)
				ans += "+";
			
			if (poly[i] != 0) {
				ans += poly[i];		// adding the value
				
				// if the power is >= 2 then "x^" needs to be added
				// otherwise, if x=1, only "x" needs to be added
				// if its x^0, no x is added
				if (i >= 2)
					ans += "x^" + i;
				else if (i == 1)
					ans += "x";
			}
				
			i--;
		}
		// if all are 0, return 0 (0 is not added to string in the while loop)
		if (ans == "")
			ans = "0";
		// **************************
		return ans;
	}

	/**
	 * Given a polynom (p), a range [x1,x2] and an epsilon eps. 
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root(double[] p, double x1, double x2, double eps) {
		double x12 = (x1+x2)/2;
		// *** add your code here ***
		// check edges first - cause x1<=x<x2 - x might be one of the edges
		// This check makes it so that the if there are a few answers, including the edges, the left edge will be picked
		if (Math.abs(f(p, x1)) < eps)
			return x1;
		if (Math.abs(f(p, x2)) < eps)
			return x2;
		
		// check rest
		boolean ansFound = false;
		while (!ansFound) {
			// check if x12 (the mid of x1, x2) is the answer
			if (Math.abs(f(p, x12)) < eps) {
				ansFound = true;
			}
			else {
				// based on the value of f(a)*f(b) we can know if the function passes through 0 in the provided range [a,b]
				// if f(a)*f(b) < 0 then one of them is negative, the other positive so 0 is in between
				// otherwise, we need to check the other "half"
				if (f(p, x1) * f(p, x12) < 0)
					x2 = x12;
				else
					x1 = x12;
				x12 = (x1+x2)/2;		// set new mid for new range [x1,x2]
			}
		}
		// **************************
		return x12;
	}
	/** Given a polynom (p), a range [x1,x2] and an epsilon eps. 
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0. 
	 * This function should be implemented recursivly.
	 * @param p - the polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double x12 = (x1+x2)/2;
		// *** add your code here ***
		// check edges first - cause x1<=x<x2 - x might be one of the edges
		// This check makes it so that the if there are a few answers, including the edges, the left edge will be picked
		if (Math.abs(f(p, x1)) < eps)
			return x1;
		if (Math.abs(f(p, x2)) < eps)
			return x2;
				
		if (Math.abs(f(p,x12)) < eps)
			return x12;					// found, return the answer
		// based on the value of f(a)*f(b) we can know if the function passes through 0 in the provided range [a,b]
		// if f(a)*f(b) < 0 then one of them is negative, the other positive so 0 is in between
		// otherwise, we need to check the other "half"
		if (f(p, x1) * f(p, x12) < 0)
			x12 = root_rec(p, x1, x12, eps);
		else
			x12 = root_rec(p, x12, x2, eps);
		// **************************
		return x12;
	}
	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) -p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double x12 = (x1+x2)/2;
		// *** add your code here ***
		// check edges first. cause x1<=x<x2 - x might be one of the edges
		if (Math.abs(f(p1, x1) - f(p2, x1)) < eps) 
			return x1;
		if (Math.abs(f(p1, x2) - f(p2, x2)) < eps) 
			return x2;
		
		// check if not found in edges
		boolean ansFound = false;
		while (!ansFound) {
			// check if x12 (the mid of x1, x2) is the answer
			if (Math.abs(f(p1, x12) - f(p2, x12)) < eps) {
				ansFound = true;
			}
			else {
				// based on the value of (f(p1, x1) - f(p2, x1)) * (f(p1, x2) - f(p2, x2)) = F
				// we can know if the function cut each other between x1,x2
				// if F < 0 then they cut between
				// otherwise, we need to check the other "half"
				if ((f(p1, x1) - f(p2, x1)) * (f(p1, x12) - f(p2, x12)) < 0)
					x2 = x12;
				else
					x1 = x12;
				x12 = (x1+x2)/2;		// set new mid for new range [x1,x2]
			}
		}
		// **************************
		return x12;
	}
	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an integer representing the number of "boxes". 
	 * This function computes an approximation of the area between the polynoms within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfBoxes - a natural number representing the number of boxes between x1 and x2.
	 * @return the approximated area between the two polynoms within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[] p2, double x1, double x2, int numberOfBoxes) {
		double ans = 0;
		// *** add your code here ***
		double changeInX = Math.abs((x2 - x1) / numberOfBoxes);
		double y = 0;
		int i = 1;
		while (i <= numberOfBoxes) {
			x1 += changeInX / 2;		// go to the mid of the box
			y = f(p1, x1) - f(p2, x1);		// the "height" y of current box - we find the y of each polynom in the mid of the box
			ans += changeInX * y;			// add the region of current box to answer - width of box * height
			x1 += changeInX / 2;		// added twice to "hop" to between boxes. next run we add another half so a/2+a/2=a
			i++;
		}
		// **************************
		return ans;
	}
	
	/**
	 * This function computes the array representation of a polynom from a String
	 * representation. Note:given a polynom represented as a double array,  
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynom.
	 * @return
	 */
	public static double[] getPolynomFromString(String p) {
		// *** add your code here ***
		
		p = p.replaceAll("\s+","");			// remove spaces
		int i = 0;
		
		// add "+" before every "-" to split easily
		// AFTER FINISHING I REALIZED I COULD USE p.replaceAll("\\+","\\+-");
		p = addPlusBeforeMinus(p);
		
		// if the first one was -, a + will be added and it can mess later the split
		if (p.charAt(0) == '+') {
			p = p.substring(1);
		}
		
		// split string into string array
		String[] str = p.split("\\+");
		
		// search for the biggest power - cover the case of the string not being organized from biggest to lowest
		int maxPower = findMaxPower(str);
		
		
		// go through the string array we have and put it into a double array
		double[] poly = new double[maxPower + 1];			// the maxpower+1 shows us the length of the poly
		int indPwr;		// index of the power mekadem - where to put value in poly
		String indStr;	// same but before converting to int
		String valueStr;
		double value;
		i = 0;
		while (i < str.length) {
			indPwr = 0;
			value = 0;
			// we search for '^'. if found, power > 1, we save it. if not found and x is found then power=1, else power=0;
			if (str[i].indexOf('^') != -1)
				indStr = str[i].substring(str[i].indexOf('^')+1, str[i].length());
			else {
				if (str[i].indexOf('x') != -1 || str[i].indexOf('X') != -1)
					indStr = "1";
				else
					indStr = "0";
			}
			indPwr = Integer.parseInt(indStr);
			
			// find x, and get the number before it (take into consideration -1 and 1)
			// if found as x, great, else check with X - otherwise, value remains 0
			if (str[i].indexOf('x') != -1) {
				valueStr = str[i].substring(0, str[i].indexOf('x'));
				if (!valueStr.equals("") && !valueStr.equals("-")) 
					value = Double.parseDouble(valueStr);
				else if (valueStr.equals("-"))
					value = -1;
				else
					value = 1;
			}
				
			else{
				
				if (str[i].indexOf('X') != -1) {
					valueStr = str[i].substring(0, str[i].indexOf('X'));
					if (!valueStr.equals("") && !valueStr.equals("-")) 
						value = Double.parseDouble(valueStr);
					else if (valueStr.equals("-"))
						value = -1;
					else
						value = 1;
				}
				else
					value = Double.parseDouble(str[i]);
			}
				
			
			// add found value and power to poly
			poly[indPwr] = value;
			
			i++;
		}
		return poly;
		// **************************
	}
	
	/**
	 * This function computes the polynom which is the sum of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
		// *** add your code here ***
		int max = 0;			// the length of the longer array
		if (p1.length >= p2.length)
			max = p1.length;
		else
			max = p2.length;
		
		double[] newP = new double[max];

		int i = 0;
		while (i < p1.length && i < p2.length) {
			newP[i] = p1[i] + p2[i];
			i++;
		}
		
		// Finish both arrays
		while (i < p1.length) {
			newP[i] = p1[i];
			i++;
		}
		while (i < p2.length) {
			newP[i] = p2[i];
			i++;
		}
		return newP;
		// **************************
	}
	
	/**
	 * This function computes the polynom which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		// *** add your code here ***
		// creating a new array - this is the size because the new biggest polynom will be p1.length+p2.length-2, we need 1 more
		double[] newP = new double[p1.length + p2.length - 1];
		int i = 0, j = 0;
		while (i < p1.length) {
			while (j < p2.length) {
				// x^a * x^b = x^(a+b) --> so we put the value into a+b which is (i+j)
				// we multiply every cell in p1 by every cell in p2 --> p1[i] * p2[j]
				newP[i+j] += p1[i] * p2[j];
				j++;
			}
			i++;
			j = 0;
		}
		return newP;
		// **************************
	}
	
	/**
	 * This function computes the derivative polynom of po.
	 * @param po
	 * @return
	 */
	public static double[] derivative (double[] po) {
		// *** add your code here ***
		// The derivative of p(x) = ax^n is p'(x) = a*n*x^(n-1)
		// So the power of the derivative is the power of the polynom minus 1
		// and the value of the polynom before x is multiplied by the power of the polynom
		if (po.length == 1)
			return ZERO; 		// means the polynom has a number without power - after deriviative its 0
		double[] ans = new double[po.length - 1];	// we need 1 less because 'c' will be 0
		int i = 0;
		while (i < po.length - 1) {
			ans[i] = po[i+1] * (i+1);
			i++;
		}
		return ans;
		// **************************
	}
	
	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * Note: this function only works for a set of points containing three points, else returns null.
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
	 * Note: you can assume xx[0]!=xx[1]!=xx[2]
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		double [] ans = new double[3];
		if(xx!=null && yy!=null && xx.length==3 && yy.length==3) {
			// *** add your code here ***
			// we have 3 points by using the "n+1 point principle" - we know the biggest power polynom will be 2
			// We will substitute, every set of x,y in: y = ax^2 + bx + c, giving us 3 functions to work with
			// f1: ax1^2 + bx1 * c = y1 ; f2: ax2^2 + bx2 * c = y2 ; f3: ax3^2 + bx3 * c = y3
			/* After playing around with the function we get to:
			 * a = (x1(y3−y2)+x2(y1−y3)+x3(y2−y1)) / (x1−x2)(x1−x3)(x2−x3)
			 * b = (y2−y1) / (x2−x1) −a(x1+x2)
			 * c = y1 - ax1^2 - bx1
			 * explained more here: https://math.stackexchange.com/a/680695
			 */
			// find a
			ans[2] = (xx[0] * (yy[2] - yy[1]) + xx[1] * (yy[0] - yy[2]) + xx[2] * (yy[1] - yy[0]))
					/ ((xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]));
			// find b using a
			ans[1] = (yy[1] - yy[0]) / (xx[1] - xx[0]) - ans[2] * (xx[0] + xx[1]);
			// find c using a and b
			ans[0] = yy[0] - (ans[2] * Math.pow(xx[0], 2)) - (ans[1] * xx[0]);
			// **************************
		}
		return ans;
	}
	///////////////////// Private /////////////////////
	// you can add any additional functions (private) below

	/**
	 * Runs through an array, from a given index, and checks if the value of every remaining cell is up to EPS
	 * @param poly
	 * @param index
	 * @return true if p from index i represents ZERO.
	 */
	private static boolean finishEqualCheck(double[] p, int i) {
		while (i < p.length) {
			if (p[i] > EPS)
				return false;
			i++;
		}
		return true;
	}
	

	/**
	 * This function adds '+' before every '-' in string array

	 * @param p - a String array representing polynom.
	 * @return p - modified string
	 */
	private static String addPlusBeforeMinus(String p) {
		int i = 0;
		String temp;
		while (i < p.length()) {
			if (p.charAt(i) == '-') {
				temp = p.substring(i, p.length());		// save everything after the - (included)
				p = p.substring(0, i) + "+" + temp;		// set p to everything before the minus, with + and the rest
				i++;		// to skip the "-" after moving it 1 forward
			}
			i++;
		}
		return p;
	}
	
	// 
	/**
	 * This function searches for the biggest power in a given string array representing polynom
	 *
	 * @param p - a String array representing polynom.
	 * @return maxPower
	 */
	private static int findMaxPower(String[] str) {
		int i = 0;
		String temp;
		int maxPower = 0;
		while (i < str.length) {
			if (str[i].indexOf('^') != -1)
				temp = str[i].substring(str[i].indexOf('^')+1, str[i].length());	// find the power
			else {
				if (str[i].indexOf('x') != -1 || str[i].indexOf('X') != -1)
					temp = "1";
				else
					temp = "0";
			}
			if (Integer.parseInt(temp) > maxPower)
					maxPower = Integer.parseInt(temp);		// if the power found is bigger then the current max
			i++;
		}
		return maxPower;
	}
			
}
