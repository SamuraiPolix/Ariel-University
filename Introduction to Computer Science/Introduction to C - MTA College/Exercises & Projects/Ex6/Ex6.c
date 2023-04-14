// Samuel Lazareanu 53036281
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define MAX_COURSES_SEMESTER 10
#define MAX_COURSE_EXERCISES 13
#define FAILED_GRADE 60
#define MAX_FIRST_NAME_LEN 10
#define MAX_LAST_NAME_LEN 10
#define MAX_NAME_LEN 21
#define MAX_FULL_NAME_LEN 29
#define STUDENTS_IN_GROUP 6
#define NUM_OF_GROUPS 3


typedef struct CourseInfo
{
	int courseNum;
	int grade;
} COURSE_INFO;

typedef struct Student
{
	char name[MAX_NAME_LEN];
	int identity;
	int nofCourses; //number of courses taken in semesterA
	COURSE_INFO course_info[MAX_COURSES_SEMESTER];
} STUDENT;

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
void setStudentData(STUDENT data[][STUDENTS_IN_GROUP], int rows, int cols);
void getStringUntilChar(char data[], char endChar);
int getStudentNames(STUDENT stuData[][STUDENTS_IN_GROUP],
	int rows, int cols, int courseNum, char stuNames[][MAX_FULL_NAME_LEN]);
bool isCourseTaken(COURSE_INFO data[], int size, int courseNum);
void printDoubleString(char data[][MAX_FULL_NAME_LEN], int rows);

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
	STUDENT student;
	STUDENT groups[STUDENTS_IN_GROUP][NUM_OF_GROUPS];
	char stuNames[STUDENTS_IN_GROUP*NUM_OF_GROUPS][MAX_NAME_LEN];
	int courseNum,
		studentsInCourse;

	printf("********************\n");
	printf("* Welcome Students *\n");
	printf("********************\n\n");

	
	setStudentData(groups, NUM_OF_GROUPS, STUDENTS_IN_GROUP);

	printf("Enter a course number: ");
	scanf("%d", &courseNum);

	studentsInCourse = getStudentNames(groups, NUM_OF_GROUPS, STUDENTS_IN_GROUP, courseNum, stuNames);

	printDoubleString(stuNames, studentsInCourse);



	/*

	// Getting the number of courses in semesters A, B from the user
	// Filling the array of the semester with courses
	// And sorting in from the smallest course number to the biggers
	printf("\nEnter number of courses in semester A: ");
	scanf("%d", &numOfCoursesSemesterA);
	fillCourses(semesterACourses, numOfCoursesSemesterA);
	sortCourses(semesterACourses, numOfCoursesSemesterA);

	printf("\nEnter number of courses in semester B: ");
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
	*/
}

// Collects and sets all the data for all students in all groups
void setStudentData(STUDENT data[][STUDENTS_IN_GROUP], int rows, int cols) {
	// Efficiency: n^2
	int iRow = 0, iCol = 0;
	char group = 'A',
		firstName[MAX_FIRST_NAME_LEN],
		lastName[MAX_LAST_NAME_LEN];

	for (iRow = 0; iRow < rows; iRow++) {
		printf("Enter students data for GROUP %c:\n\n", group);
		printf("--------------------------------\n");
		for (iCol = 0; iCol < cols; iCol++) {
			printf("\nEnter student first name and last name (seperated by spaces): ");
			scanf("%s %s", firstName, lastName);
			strcpy(data[iRow][iCol].name, firstName);
			strcat(data[iRow][iCol].name, " ");
			strcat(data[iRow][iCol].name, lastName);

			printf("\nEnter students ID: ");
			scanf("%d", &data[iRow][iCol].identity);
			printf("\n\nEnter number of courses taken in semester A: ");
			scanf("%d", &data[iRow][iCol].nofCourses);

			fillCourses(data[iRow][iCol].course_info, data[iRow][iCol].nofCourses);
		}
		group++;		// A -> B -> C
	}
	
}

// Gets string from user until a selected char is entered
void getStringUntilChar(char data[], char endChar) {
	// Efficiency: n
	int i = 0;
	char currChar;

	scanf("%c", &currChar);
	while (currChar != endChar) {
		data[i] = currChar;
		i++;
		scanf("%c", &currChar);
	}
}

// Fills the data array with courses
void fillCourses(COURSE_INFO data[], int size) {
	// Efficiency: n
	int i;

	for (i = 0; i < size; i++)
	{
		printf("\nEnter Course number and grade: ");
		scanf("%d", &data[i].courseNum);
		scanf("%d", &data[i].grade);
	}
}

int getStudentNames (STUDENT stuData[][STUDENTS_IN_GROUP],
	int rows, int cols, int courseNum, char stuNames[][MAX_FULL_NAME_LEN]) {
	// Efficiency: n^3 (for -> for -> if including n efficient func)
	int iRow, iCol, iRowNames = 0;
	char groupChar = 'A',
		group[2] = { groupChar, '\0' };

	for (iRow = 0; iRow < rows; iRow++) {
		for (iCol = 0; iCol < cols; iCol++) {
			if (isCourseTaken(stuData[iRow][iCol].course_info, stuData[iRow][iCol].nofCourses, courseNum)) {
				strcpy(stuNames[iRowNames], "Group");
				strcat(stuNames[iRowNames], group);
				strcat(stuNames[iRowNames], " ");
				strcat(stuNames[iRowNames], stuData[iRow][iCol].name);
			}
		}
		group[0]++;
	}
}

bool isCourseTaken(COURSE_INFO data[], int size, int courseNum) {
	// Efficiency: n
	int i;

	for (i = 0; i < size; i++) {
		if (data[i].courseNum == courseNum)
			return true;
	}
	return false;
}

void printDoubleString(char data[][MAX_FULL_NAME_LEN], int rows) {
	int i;

	for (i = 0; i < rows; i++) {
		printf("%s", data[i]);
	}
}
// --------------------------------------------------------------

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