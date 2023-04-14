// Samuel Lazareanu 53036281
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdbool.h>

#define MAX_COURSES_SEMESTER 10
#define MAX_COURSE_EXERCISES 13
#define FAILED_GRADE 60

typedef struct CourseInfo
{
	int courseNum;
	int grade;
} COURSE_INFO;

void fillCourses(COURSE_INFO data[], int size);
void sortCourses(COURSE_INFO data[], int size);
void printCourses(COURSE_INFO data[], int size);
void swapCourseCells(COURSE_INFO data[], int ind1, int ind2);
void swapIntCells(int data[], int ind1, int ind2);
int unite(COURSE_INFO dataA[], int sizeA, COURSE_INFO dataB[], int sizeB, int uniteCourse[]);
int intersect(COURSE_INFO dataA[], int sizeA, COURSE_INFO dataB[], int sizeB, int interCourse[]);
void printCNum(int data[], int size);
bool isNumInIntArray(int data[], int size, int num);
COURSE_INFO findMinGrade(COURSE_INFO data[], int size);
void fillExerciseGrades(int data[], int size);
void changeExerciseGrades(int data[], int size);
int findFailPass(int grades[], int size);
int reorderGrades(int grades[], int size, int limit);

void main() {
	// Samuel Lazareanu 53036281
	int numOfCoursesSemesterA,		// Number of courses taken in semester A
		numOfCoursesSemesterB,		// Number of courses taken in semester B
		numOfExercises,				// Number of exercises in the lowest grade course
		limitGrade,					// Limit grade to sort exercises
		belowLimitGrades,			// The number of grade below limit
		numOfIntersect,				// The number of courses in sem a and in sem b
		numOfUnite;					// the num of courses in sem a or b
	COURSE_INFO semesterACourses[MAX_COURSES_SEMESTER],		// The courses in semster A
		semesterBCourses[MAX_COURSES_SEMESTER],				// The courses in semester B
		minGradeA;											// Min grade semester A
	int unitedCourseNum[MAX_COURSES_SEMESTER * 2],			// An array of course numbers taken in at least one semester
		intersectCourseNum[MAX_COURSES_SEMESTER * 2],		// An array of course numbers taken in both semesters A+B
		exerciseGrades[MAX_COURSE_EXERCISES];				// An array of the exercise grade in the lowest grade course

	printf(" Welcome students!! \n");
	printf("and bye bye Pizzeria\n");

	// Getting the number of courses in semesters A, B from the user
	// Filling the array of the semester with courses
	// And sorting in from the smallest course number to the biggers
	printf("\nPlease enter number of courses in semester A: ");
	scanf("%d", &numOfCoursesSemesterA);
	fillCourses(semesterACourses, numOfCoursesSemesterA);
	sortCourses(semesterACourses, numOfCoursesSemesterA);

	printf("\nPlease enter number of courses in semester B: ");
	scanf("%d", &numOfCoursesSemesterB);
	fillCourses(semesterBCourses, numOfCoursesSemesterB);
	sortCourses(semesterBCourses, numOfCoursesSemesterB);

	// Print the courses
	printf("\n\nSorted courses of semester A: \n");
	printCourses(semesterACourses, numOfCoursesSemesterA);

	printf("\nSorted courses of semester B: \n");
	printCourses(semesterBCourses, numOfCoursesSemesterB);

	// Unite course numbers
	numOfUnite = unite(semesterACourses, numOfCoursesSemesterA, semesterBCourses, numOfCoursesSemesterB, unitedCourseNum);
	printf("\ncourses taken in semester A or semester B: ");
	printCNum(unitedCourseNum, numOfUnite);

	// Intesect course numbers
	numOfIntersect = intersect(semesterACourses, numOfCoursesSemesterA, semesterBCourses, numOfCoursesSemesterB, intersectCourseNum);
	printf("\ncourses taken in semester A and semester B: ");
	printCNum(intersectCourseNum, numOfIntersect);

	// Minimum grade
	minGradeA = findMinGrade(semesterACourses, numOfCoursesSemesterA);
	printf("\n\nMinimum grade in semester A is: %d in course #%d\n", minGradeA.grade, minGradeA.courseNum);

	// Excercise
	printf("\nHow many exercises were given in course #%d? ", minGradeA.courseNum);
	scanf("%d", &numOfExercises);
	printf("\nEnter exercises grades: ");
	fillExerciseGrades(exerciseGrades, numOfExercises);
	printf("\nExercise grades: ");
	changeExerciseGrades(exerciseGrades, numOfExercises);
	printCNum(exerciseGrades, numOfExercises);

	printf("\nIndex of Fail-Pass is: %d", findFailPass(exerciseGrades, numOfExercises));

	// Limit grade
	printf("\nPlease enter a limit grade: ");
	scanf("%d", &limitGrade);
	printf("\nAfter reordering grades, the grades smaller than %d are: ", limitGrade);
	belowLimitGrades = reorderGrades(exerciseGrades, numOfExercises, limitGrade);
	printCNum(exerciseGrades, belowLimitGrades);
}

// Fills the data array with courses
void fillCourses(COURSE_INFO data[], int size) {
	// Efficiency: n
	int i;

	for (i = 0; i < size; i++)
	{
		printf("\nEnter course number and grade: ");
		scanf("%d", &data[i].courseNum);
		scanf("%d", &data[i].grade);
	}
}

// Sorts the data array containing courses
void sortCourses(COURSE_INFO data[], int size) {
	// Efficiency: n^2
	int i, j;

	for (i = 0; i < size; i++) {
		for (j = i + 1; j < size; j++) {
			if (data[j].courseNum < data[i].courseNum)
				swapCourseCells(data, j, i);
		}
	}
}

// Prints the courses in the format required
void printCourses(COURSE_INFO data[], int size) {
	// Efficiency: n
	int i;

	printf("Course# Grade\n");
	printf("======= =====\n");
	for (i = 0; i < size; i++) {
		printf("%-8d%d\n", data[i].courseNum, data[i].grade);
	}
}

// Swaps 2 cells of an COURSE_INFO array
void swapCourseCells(COURSE_INFO data[], int ind1, int ind2) {
	// Efficiency: 1
	COURSE_INFO temp = data[ind1];
	data[ind1] = data[ind2];
	data[ind2] = temp;
}

// Swaps 2 cells of an Integer array
void swapIntCells(int data[], int ind1, int ind2) {
	// Efficiency: 1
	int temp = data[ind1];
	data[ind1] = data[ind2];
	data[ind2] = temp;
}

// Returns the numbers of courses taken in semester A OR semester B and sets them into uniteCourse array
int unite(COURSE_INFO dataA[], int sizeA, COURSE_INFO dataB[], int sizeB, int uniteCourse[]) {
	// Efficiency: logn
	int readA = 0, readB = 0,			// reading index of dataA and dataB
		write = 0,						// writing index of uniteCourse
		counter = 0;					// Counting the number of writen data into uniteCourse
	
	// Store courses by order until running out of one array
	while (readA < sizeA && readB < sizeB) {
		if (dataA[readA].courseNum < dataB[readB].courseNum) {
			uniteCourse[write] = dataA[readA].courseNum;
			write++;
			readA++;
			counter++;
		}
		else if (dataA[readA].courseNum > dataB[readB].courseNum) {
			uniteCourse[write] = dataB[readB].courseNum;
			write++;
			readB++;
			counter++;
		}
		else if (dataA[readA].courseNum == dataB[readB].courseNum) {
			uniteCourse[write] = dataA[readA].courseNum;
			write++;
			readA++;
			readB++;
			counter++;
		}
	}
	// Store remaining courses from sem A
	while (readA < sizeA) {
		uniteCourse[write] = dataA[readA].courseNum;
		write++;
		readA++;
		counter++;
	}
	// Store remaining courses from sem B
	while (readB < sizeB) {
		uniteCourse[write] = dataB[readB].courseNum;
		write++;
		readB++;
		counter++;
	}
	return counter;
}

// Returns the numbers of courses taken in semester A AND semester B and sets them into interCourse array
int intersect(COURSE_INFO dataA[], int sizeA, COURSE_INFO dataB[], int sizeB, int interCourse[]) {
	// Efficiency: logn
	int readA = 0, readB = 0,	// reading index of dataA and dataB
		write = 0,				// writing index of interCourse
		counter = 0;			// Counting the number of writen data into interCourse

	while (readA < sizeA && readB < sizeB) {
		if (dataA[readA].courseNum == dataB[readB].courseNum) {
			interCourse[write] = dataA[readA].courseNum;
			readA++;
			readB++;
			write++;
			counter++;
		}
		else if (dataA[readA].courseNum > dataB[readB].courseNum)
			readB++;
		else if (dataA[readA].courseNum < dataB[readB].courseNum)
			readA++;
	}
	return counter;
}

// Checks if Integer num is in array data - returns T if it is, F if not
bool isNumInIntArray(int data[], int size, int num) {
	// Efficiency: n
	int i;

	for (i = 0; i < size; i++) {
		if (data[i] == num)
			return true;
	}
	return false;
}

// Prints Integer array data
void printCNum(int data[], int size) {
	// Efficiency: n
	int i;
	for (i = 0; i < size; i++) {
		printf("%d ", data[i]);
	}
}

// Finds the course with the lowest grade in a COURSE_INFO arrat and returns the it
COURSE_INFO findMinGrade(COURSE_INFO data[], int size) {
	// Efficiency: n
	int i = 0,
		minInd = i;

	for (i = 0; i < size; i++) {
		if (data[i].grade < data[minInd].grade)
			minInd = i;
	}

	return data[minInd];
}

// Fills the exrecise grades array with data given by the user
void fillExerciseGrades(int data[], int size) {
	// Efficiency: n
	int i;

	for (i = 0; i < size; i++) {
		scanf("%d", &data[i]);
	}
}

// Changes the grades so the first cell is 30 and the last on is 100
void changeExerciseGrades(int data[], int size) {
	// Efficiency: 1
	data[0] = 30;
	data[size - 1] = 100;
}

// Finds and returns an index where a failed grade is followed by a passing grade. Returns -1 if not found
int findFailPass(int grades[], int size) {
	// Efficiency: n
	int i;

	for (i = 1; i < size; i++) {
		if (grades[i] >= FAILED_GRADE && grades[i - 1] < FAILED_GRADE)
			return i - 1;
	}
	return -1;
}

// Returns the number of grades below the limit given to the func and reorders the array so the grades below the limit are on the left
int reorderGrades(int grades[], int size, int limit) {
	// Efficiency: logn
	int left = 0, right = size - 1,		// reading index of grades
		counter = 0;					// counting the number of grades below the limit given

	while (left < right) {
		if (grades[left] >= limit) {
			if (grades[right] < limit) {
				swapIntCells(grades, left, right);
				left++;
				right--;
				counter++;
			}
			else
				right--;
		}
		else {
			left++;
			counter++;

		}
	}
	return counter;
}