#define _CRT_SECURE_NO_WARNINGS
#define TAX 1.17
#define MUSHROOM_TOPPING_PRICE 12
#define OLIVE_TOOPING_PRICE 10
#define BASIC_PIZZA_PRICE 60
#define REGULAR_DOUGH_PRICE 0
#define VEGAN_DOUGH_PRICE 5
#define WHOLE_WHEAT_DOUGH_PRICE 3
#define GLUTEN_FREE_DOUGH_PRICE 4
#define BASIC_PIZZA_LENGTH 40
#define BASIC_PIZZA_WIDTH 40
#include <stdio.h>
// Samuel Lazareanu 53036281

// Pizza struct - Including all the Info needed for the pizza
struct Pizza {
	int length,		// The length of the pizza
		width,		// The width of the pizza
		oliveTopping,	// The Olive toppings of the pizza
		mushroomTopping;		// The Mushroom toppings of the pizza
	char dough;			// The dough type of the pizza
};

void main() {
	// Samuel Lazareanu 53036281
	int counter,				// Counter - helper
		id,				// The ID number of the user
		delivery,		// 1 - Y, 0 - N
		numOfPizzas,	// The number of pizzas the user wants to order
		tensChange, fivesChange, twosChange, onesChange,	// The change the user needs to get - in tens, fives, twos and ones.
		doughTypePrice = 0;			// The price of the chosen dough type
	double totalPrice = 0,			// The total price of the pizza
		priceWithTax = 0,			// The total price with tax
		pizzaPrice = 0,				// The price of the current pizza
		usersPayment,				// The price the user pays (before change is given)
		change,						// The change the user needs to receive - if needed
		chosenToBasicPizzaRatio;					// The ratio of the chosen pizza size to the basic pizza size
	struct Pizza pizza;

	printf(" Welcome to MTA-Pizza! \n\n");
	printf("*****\n *** \n  *  \n\n");

	printf("Please enter your ID number: \n");
	scanf("%d", &id);

	printf("\nOur menu: \n");
	printf("********* \n");
	printf("Basic pizza: 60.00 NIS for 40x40 size pizza \n\n");
	printf("Toppings for basic size pizza: \n");
	printf("Olives: 10 NIS \n");
	printf("Mushrooms: 12 NIS \n\n");
	printf("Dough type for basic size pizza: \n");
	printf("Regular: 0 NIS \n");
	printf("Vegan: 5 NIS \n");
	printf("Whole wheat: 3 NIS \n");
	printf("Gluten free: 4 NIS \n\n");

	printf("How many pizzas would you like to order? ");
	scanf("%d", &numOfPizzas);

	// Asking for the amout of pizzas to order - operating accordingly
	if (numOfPizzas <= 0)
		printf("\nThank you and goodbye.");
	else {
		printf("\n");
		for (counter = 1; counter <= numOfPizzas; counter++)
		{
			printf("\n*************************************************\n");
			printf("Pizza #%d: \n\n", counter);

			// The size of the pizza
			printf("Please enter your pizza's length (cm): ");
			scanf("%d", &pizza.length);
			if ((pizza.length % 2 != 0) || (pizza.length < 10) || (pizza.length > 40)) {
				pizza.length = BASIC_PIZZA_LENGTH;
				printf("\nInvalid length! Basic length was chosen as a default.");
			}

			printf("\nPlease enter your pizza's width (cm): ");
			scanf("%d", &pizza.width);
			if ((pizza.width % 2 != 0) || (pizza.width < 10) || (pizza.width > 40)) {
				pizza.width = BASIC_PIZZA_WIDTH;
				printf("\nInvalid width! Basic width was chosen as a default.");
			}


			// The dough type of the pizza
			printf("\n\nPlease enter the pizza's dough type: \n");
			printf("r - for regular \n");
			printf("v - for vegan \n");
			printf("w - for whole wheat \n");
			printf("f - for gluten-free \n");
			scanf(" %c", &pizza.dough);
			switch (pizza.dough) {
			case 'r':
				doughTypePrice = REGULAR_DOUGH_PRICE;
				break;
			case 'v':
				doughTypePrice = VEGAN_DOUGH_PRICE;
				break;
			case 'w':
				doughTypePrice = WHOLE_WHEAT_DOUGH_PRICE;
				break;
			case 'f':
				doughTypePrice = GLUTEN_FREE_DOUGH_PRICE;
				break;
			default:
				printf("Invalid choice! Regular dough was chosen as a default. \n");
				pizza.dough = 'r';
				doughTypePrice = 0;
				break;
			}

			// Olive toppings
			printf("\nPlease choose the toppings:\n");
			printf("\nOlives (choose 0-3):\n");
			printf("0. None\n");
			printf("1. Whole pizza\n");
			printf("2. Half pizza \n");
			printf("3. Quarter pizza \n");
			scanf("%d", &pizza.oliveTopping);
			if ((pizza.oliveTopping < 0) || (pizza.oliveTopping > 3)) {
				printf("Invalid choice! Current topping not added. \n");
				pizza.oliveTopping = 0;
			}

			// Mushroom toppings
			printf("\nMushrooms (choose 0-3):\n");
			printf("0. None\n");
			printf("1. Whole pizza\n");
			printf("2. Half pizza \n");
			printf("3. Quarter pizza \n");
			scanf("%d", &pizza.mushroomTopping);
			if ((pizza.mushroomTopping < 0) || (pizza.mushroomTopping > 3)) {
				printf("Invalid choice! Current topping not added. \n");
				pizza.mushroomTopping = 0;
			}

			// For convenience - if the user chooses 3 for the toppings - changing it to 4 so I can devide 1 by it and get the ratio of toppin on the pizza
			if (pizza.oliveTopping == 3)
				pizza.oliveTopping = 4;
			if (pizza.mushroomTopping == 3)
				pizza.mushroomTopping = 4;

			// Maximum amount of toppings allowed
			if ((pizza.oliveTopping != 0) && (pizza.mushroomTopping != 0)) {
				if (((double)((1.0 / pizza.oliveTopping) + (1.0 / pizza.mushroomTopping)) > 1.0)) {
					printf("You have exceeded the maximum amount of toppings allowed on one pizza! Current topping not added.\n");
					pizza.mushroomTopping = 0;
				}
			}
			
			// Calculation the ratio of the chosen pizza size to basic pizza size - to use it in the calculations of the price
			chosenToBasicPizzaRatio = ((double)pizza.length * pizza.width) / (double)(BASIC_PIZZA_LENGTH * BASIC_PIZZA_WIDTH);			

			// Calculating the price of the pizza
			pizzaPrice += chosenToBasicPizzaRatio * BASIC_PIZZA_PRICE;
			if (pizza.oliveTopping != 0)
				pizzaPrice += chosenToBasicPizzaRatio * (1.0 / pizza.oliveTopping) * OLIVE_TOOPING_PRICE;
			if (pizza.mushroomTopping != 0)
				pizzaPrice += chosenToBasicPizzaRatio * (1.0 / pizza.mushroomTopping) * MUSHROOM_TOPPING_PRICE;
			pizzaPrice += chosenToBasicPizzaRatio * (double)doughTypePrice;

			// Printing the details of the current pizza
			printf("\nPizza #%d details:\n", counter);
			printf("*******************\n");
			printf("Pizza size: %dx%d\n", pizza.length, pizza.width);
			printf("Pizza price (without tax): %.2lf\n", pizzaPrice);
			totalPrice += pizzaPrice;
			pizzaPrice = 0;
		}
		// THE NEXT PART WON'T EXECUTE IF THE USER DIDN'T WANT TO BUY A PIZZA
		printf("\n*************************************************\n");
		// Delivery or not + Adding to price
		printf("Do you want delivery for the price of 15 NIS? Enter 1 for delivery or 0 for pick-up: ");
		scanf("%d", &delivery);
		if (delivery < 0 || delivery > 1) {
			delivery = 0;
			printf("Invalid choice! Pick-up was chosen as a default.");
		}
		totalPrice += 15 * (double)delivery;

		priceWithTax = (int)(totalPrice * TAX);

		// Order details
		printf("\n\nYour order details: \n");
		printf("******************* \n");
		printf("ID number: %09d \n", id);
		printf("Number of pizzas: %d \n", numOfPizzas);
		printf("Total price: %.2lf\n", totalPrice);
		printf("Total price with tax (rounded down): %d\n", (int)priceWithTax);

		printf("\nPlease enter your payment: ");
		scanf("%lf", &usersPayment);

		// Calculating and printing change
		change = usersPayment - priceWithTax;

		// If the user has a remaining payment to pay - it will keep asking for money
		while (change < 0) {
			printf("\nYour remaining balance is: %d", ((int)change * (-1)));
			printf("\nPlease enter your payment: ");
			scanf("%lf", &usersPayment);
			change += usersPayment;
		}
		
		// If the user payed too much - he needs a change
		if (change > 0.0) {
			printf("\nYour change is %d NIS using: \n", (int)change);

			// Calculatin the coins
			tensChange = (int)((int)change - ((int)change % 10)) / 10;
			change -= tensChange * 10.0;
			fivesChange = ((int)change / 5);
			change -= fivesChange * 5.0;
			twosChange = ((int)change / 2);
			change -= twosChange * 2.0;
			onesChange = ((int)change / 1);

			// Printing the change in coins
			if (tensChange != 0)
				printf("%d coin(s) of ten \n", tensChange);
			if (fivesChange != 0)
				printf("%d coin(s) of five \n", fivesChange);
			if (twosChange != 0)
				printf("%d coin(s) of two \n", twosChange);
			if (onesChange != 0)
				printf("%d coin(s) of one \n", onesChange);
		}

		printf("\nThank you for your order! \n");
	}
}