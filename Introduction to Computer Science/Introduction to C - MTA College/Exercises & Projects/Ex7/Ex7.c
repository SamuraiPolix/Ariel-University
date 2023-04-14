// Samuel Lazareanu 53036281
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define SIZE 10
#define MAX_INT_SIZE 10
#define FUNC_COUNT 9
#define EXIT 0

// Add your recursive functions declarations here
bool isEven(int num, int dig);
int howManyPaths(int x, int y);
int biggestLowPower(int x, int num);
int partSum(int num);
void intToStr(int num, char s[]);
void fillMaxPrefixesArray(int numbers[], int n, int maxPrefixesArray[]);
void getMinToStart(int numbers[], int n);
void combineArrays(int sortedArr1[], int size1,
	int sortedArr2[], int size2);
int countSmaller(int arr[], int start, int end, int num);


// Testing functions
int readArray(int data[], int maxSize);
void printArray(int data[], int size);
void bubbleSort(int arr[], int size);
void swap(int arr[], int i, int j);
void checkQ1();
void checkQ2();
void checkQ3();
void checkQ4();
void checkQ5();
void checkQ6();
void checkQ7();
void checkQ8();
void checkQ9();

/*********** main - don't make any changes here !!! ***************************/
void main()
{
	int funcNum;
	bool exit = false;

	while (!exit)
	{
		printf("Enter the number of function you want to check (1-%d) or %d to exit the program: ", FUNC_COUNT, EXIT);
		scanf("%d", &funcNum);

		switch (funcNum)
		{
		case 1:
			checkQ1();
			break;
		case 2:
			checkQ2();
			break;
		case 3:
			checkQ3();
			break;
		case 4:
			checkQ4();
			break;
		case 5:
			checkQ5();
			break;
		case 6:
			checkQ6();
			break;
		case 7:
			checkQ7();
			break;
		case 8:
			checkQ8();
			break;
		case 9:
			checkQ9();
			break;
		case EXIT:
			exit = true;
			break;
		default:
			printf("Invalid choice.\n");
		}
		printf("\n");
	}

}

/***************** Testing functions - don't make any changes here!!! ***************/
void checkQ1()
{
	int num, dig;

	printf("Enter a positive integer: ");
	scanf("%d", &num);
	printf("Enter a digit: ");
	scanf("%d", &dig);
	if (isEven(num, dig))
	{
		printf("%d appears even number of times in %d\n", dig, num);
	}
	else
	{
		printf("%d appears odd number of times in %d\n", dig, num);
	}
}

void checkQ2()
{
	int x, y;

	printf("Enter two non-negative integers: ");
	scanf("%d%d", &x, &y);
	printf("There are %d paths from (0,0) to (%d,%d)\n", howManyPaths(x, y), x, y);
}

void checkQ3()
{
	int x, num;

	printf("Enter two positive integers: ");
	scanf("%d%d", &x, &num);
	printf("The biggest power of %d which is smaller than %d is %d\n", x, num, biggestLowPower(x, num));
}

void checkQ4()
{
	int num;

	printf("Enter a positive integer: ");
	scanf("%d", &num);
	printf("The partial sum of %d digits is %d\n", num, partSum(num));
}

void checkQ5()
{
	int num;
	char string[MAX_INT_SIZE + 1];

	printf("Enter a positive integer: ");
	scanf("%d", &num);
	intToStr(num, string);
	printf("The string representing the integer %d is %s\n", num, string);
}

void checkQ6()
{
	int numbers[SIZE];
	int maxPrefixes[SIZE];
	int size;

	size = readArray(numbers, SIZE);
	fillMaxPrefixesArray(numbers, size, maxPrefixes);
	printf("Max prefixes array: ");
	printArray(maxPrefixes, size);
}

void checkQ7()
{
	int numbers[SIZE];
	int size;

	size = readArray(numbers, SIZE);
	getMinToStart(numbers, size);
	printf("The minimal number is: %d\n", numbers[0]);

	// check if all other numbers are still inb array
	bubbleSort(numbers, size);
	printf("The sorted array: ");
	printArray(numbers, size);
}

void checkQ8()
{
	int arr1[SIZE], arr2[2 * SIZE];
	int size1, size2;

	size1 = readArray(arr1, SIZE);
	size2 = readArray(arr2, 2 * SIZE - size1);

	// sort arrays
	bubbleSort(arr1, size1);
	bubbleSort(arr2, size2);

	combineArrays(arr1, size1, arr2, size2);
	printf("The combined sorted array: ");
	printArray(arr2, size1 + size2);
}

void checkQ9()
{
	int numbers[SIZE];
	int size, num;

	// read numbers from user (assumption: numbers are different from each other)
	size = readArray(numbers, SIZE);
	bubbleSort(numbers, size);

	printf("Please enter an integer: ");
	scanf("%d", &num);

	printf("There are %d numbers in array that are smaller than %d\n", countSmaller(numbers, 0, size - 1, num), num);
}

// This function reads a series of integers from user into data array. 
// The function will first ask the user to enter the number of integers he wishes
// to enter to array. If number is bigger than maxSize, then only the first masSize
// numbers will be read.
// The fucntion returns the arrays logical size (number of elements inserted into array).
int readArray(int data[], int maxSize)
{
	int count;
	int i;	// number of elements inserted into array

	printf("How many numbers would you like to enter to array ? (no more than %d) ", maxSize);
	scanf("%d", &count);
	if (count > maxSize)
	{
		count = maxSize;
	}
	printf("Please enter %d integers: ", count);

	for (i = 0; i < count; i++)
	{
		scanf("%d", &data[i]);	// read current input number		
	}

	return count;
}

// This function prints the first size elements of data array (integers).
void printArray(int data[], int size)
{
	int i;

	for (i = 0; i < size; i++)
	{
		printf("%d ", data[i]);
	}
	printf("\n");
}

// This functions sorts arr in increasing order using bubble sort algorithm
void bubbleSort(int arr[], int size)
{
	int i, j;

	for (i = 0; i < size - 1; i++)
		for (j = 0; j < size - i - 1; j++)
			if (arr[j] > arr[j + 1])
				swap(arr, j, j + 1);
}

void swap(int arr[], int i, int j)
{
	int tmp = arr[i];
	arr[i] = arr[j];
	arr[j] = tmp;
}

/******************************* Recursive functions **************************/
// Add recursive functions implementation here

// This func receives a number and a digit
// Returns T/F if the number of times digit is in num is even
bool isEven(int num, int dig) {
	// int counter = 0;		// counting the amount of times dig is in num
	bool even;

	if (num < 10) {
		if (num == dig)		// num contains only one digit and its 'dig' - uneven times - return false
			even = false;
		else    // if (num != dig)
			even = true;
	}
	else {
		// The magician checks for a smaller input - num / 10;
		even = isEven(num / 10, dig);

		// We check the last dig (num % 10)
		if (num % 10 == dig)		// if our digit is 'dig'
			even = !even;		// Flips the value. T -> F, F -> T, because the dig was found again
	}
	return even;
}

// This func receives 2 int numbers
// Returns the num of paths to its coordinates (x,y) from (0,0)
int howManyPaths(int x, int y) {
	int numOfPaths = 0;		// Couting the number of paths to (x,y) from (0,0)

	if (x == 0 && y == 0) {
		numOfPaths = 1;
	}
	else {
		// Keeps checking with a smaller input (x-1 or y-1) until both x and y reach 0
		if (x > 0)
			numOfPaths += howManyPaths(x - 1, y);
		if (y > 0)
			numOfPaths += howManyPaths(x, y - 1);
	}
	return numOfPaths;
}

// This func receives 2 natural numbers: x, num
// Returns the max power on x that will give a smaller/equal number to num
// Assuming x >= 2
int biggestLowPower(int x, int num) {
	int y = 0;

	if (num < x) {	// num is smaller then x so no int power except 0 will give a smaller num then num, every num in the power of 0 is 1
		y = 1;
	}
	else if (num == x) {
		y = x;	// every num in the power of 1 is itself - so num*1=x^1=x
	}
	else {
		y = x * biggestLowPower(x, num / x);
	}
	return y;
}

// This func receives a natural positive int
// Returns the sum of its digits without the first right digit
int partSum(int num) {
	int sum = 0;

	if (num < 10) {
		// 0 if num has 1 digit
		// We could do sum = num, but then if the user enters a one digit num, the value returned won't be 0 like required
		sum = 0;
	}
	else if (num < 100) {
		// Makes sure the first digit doesnt get lost because of the first 'if'
		// without this 'else if', the func will lose the first digit because it will return 0 and not the value of it
		sum += (num / 10 % 10);
	}
	else {
		// Making the num smaller by div 10 before calling the func so the digit from the right is lost
		// We are still giving the func a "smaller value" because the num /= 10 happens everytime before the call
		num /= 10;
		sum += partSum(num);
		sum += num % 10;
	}
	return sum;
}

// This func receives a positive int and a string, and puts the num is s
void intToStr(int num, char s[]) {
	int i = 0;		// counter to go through the array

	if (num < 10) {
		// If the num has 1 digit - put it in the first cell and '\0' in the second;
		s[0] = '0' + (num % 10);
		s[1] = '\0';
	}
	else {
		intToStr(num / 10, s);
		// In every run - finds the last cell ('\0')
		while (s[i] != '\0') {
			i++;
		}
		// Puts the current dig in the last cell and '\0' right after
		if (s[i] == '\0') {
			s[i] = '0' + (num % 10);
			s[i + 1] = '\0';
		}
	}
}

// This func receives an array of natural numbers, the size of it and an output array
// The func puts the number from 'numbers' sorted from low to max in 'maxPrefixesArray'
// Assuming the size of the array (n) is a positive int (n > 0) + maxPrefixesArray has enough space for the numbers
void fillMaxPrefixesArray(int numbers[], int n, int maxPrefixesArray[]) {
	if (n == 1) {
		maxPrefixesArray[0] = numbers[0];
	}
	else {
		fillMaxPrefixesArray(numbers, n - 1, maxPrefixesArray);
		if (numbers[n - 1] < maxPrefixesArray[n - 2])
			maxPrefixesArray[n - 1] = maxPrefixesArray[n - 2];
		else {	// if (numbers[n - 1] >= maxPrefixesArray[n - 2])
			maxPrefixesArray[n - 1] = numbers[n - 1];
		}
	}
}

// This func receives an array of int numbers and the size of it
// The func put the smallest number from the array into the first cell [0]
// Assuming the size of the array (n) is a positive int (n > 0)
void getMinToStart(int numbers[], int n) {
	int temp;		// Used to swap cells - temp saving the value of one cell

	if (n == 1)		
		return;			// Do nothing - the smallest is already in [0] cause there is only one num inside
	else {
		getMinToStart(numbers, n - 1);
		if (numbers[n - 1] < numbers[0]) {
			// A smaller num is found - swap cells
			temp = numbers[0];
			numbers[0] = numbers[n - 1];
			numbers[n - 1] = temp;
		}
	}
}

// This func receives 2 sorted arrays of int numbers and their sizes
// The func combines the arrays into the second array, sorted from the smallest to the biggest num
// Assuming the arrays are sorted, the size of each array is 0 or bigger, and there is enough space in the second array for both arrays
void combineArrays(int sortedArr1[], int size1,
	int sortedArr2[], int size2) {
	int i;

	if (size1 == 0)		// covers if (size1 + size2 == 0) as well
		return;
	else if (size2 == 0) {
		// Copy arr1 to arr2
		for (i = 0; i < size1; i++) {		// size2 > size1 so its ok to run until size1
			sortedArr2[i] = sortedArr1[i];
		}
	}
	else {		// size1 != 0, size2 != 0, size1 + size2 > 0
		if (sortedArr1[size1 - 1] >= sortedArr2[size2 - 1]) {	// if size1,size2 == 0 is checked before
			sortedArr2[size1 + size2 - 1] = sortedArr1[size1 - 1];
			combineArrays(sortedArr1, size1 - 1, sortedArr2, size2);
		}
		else { // if (sortedArr1[size1 - 1] < sortedArr2[size2 - 1])
			sortedArr2[size1 + size2 - 1] = sortedArr2[size2 - 1];
			combineArrays(sortedArr1, size1, sortedArr2, size2 - 1);
		}
	}
}


// This func receives a sorted array of different integers, starting index, ending index and an int num
// The func returns the number of numbers smaller then 'num' from index 'start' to 'end' in the given array
int countSmaller(int arr[], int start, int end, int num) {
	int times;		// The number of numbers smaller then 'num' from index 'start' to 'end' in the given array

	if (start == end) {		// Only one num is in the range
		// if this only number is smaller then num, "return" 1, else 0
		if (arr[start] < num)
			times = 1;
		else
			times = 0;
	}
	else {
		// The magician checks for a smaller input (end - 1)
		times = countSmaller(arr, start, end - 1, num);
		// We check with the last cell - end
		if (arr[end] < num)
			times++;
	}
	return times;
}