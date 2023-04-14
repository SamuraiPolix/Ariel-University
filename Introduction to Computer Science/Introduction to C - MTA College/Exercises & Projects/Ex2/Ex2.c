#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

void main() {
	int id,				// ID number a user will enter
		pizzaLen,		// The length of the pizza
		pizzaWid,		// The width of the pizza
		delivery;		// 1 - Y, 0 - N
	double oliveTop,	// The Olive toppings of the pizza
		mushTop,		// The Mushroom toppings of the pizza
		price = 0;			// The total price of the pizza
	

	// Targil 1
	printf(" Welcome to MTA-Pizza! \n\n");
	printf("*****\n *** \n  *  \n\n");

	printf("Please enter your ID number: \n");
	scanf("%i", &id);

	printf("\nOur menu: \n");
	printf("********* \n");
	printf("Basic pizza: 60.00 NIS for 40x40 size pizza \n");
	printf("Toppings for basic size pizza: \n");
	printf("Olives: 10 NIS \n");
	printf("Mushrooms: 12 NIS \n\n");

	// The size of the pizza
	printf("Please enter your pizza's length (cm): ");
	scanf("%d", &pizzaLen);
	printf("\nPlease enter your pizza's width (cm): ");
	scanf("%d", &pizzaWid);

	// Olive toppings
	printf("\n\nPlease choose the toppings:\n\n");
	printf("Olives - Enter 1 for the whole pizza\n");
	printf("0.5 for half\n");
	printf("0.25 for quarter\n");
	printf("or 0 for none: ");
	scanf("%lf", &oliveTop);

	// Mushroom toppings
	printf("\n\nMushrooms - Enter 1 for the whole pizza\n");
	printf("0.5 for half\n");
	printf("0.25 for quarter\n");
	printf("or 0 for none: ");
	scanf("%lf", &mushTop);

	// Delivery or not
	printf("\n\nDo you want delivery for the price of 15 NIS? Enter 1 for delivery or 0 for pick-up: ");
	scanf("%d", &delivery);

	// Calculation the price
	price += (((double)pizzaLen * pizzaWid) / (40.0 * 40)) * 60.0;
	price += (((double)pizzaLen * pizzaWid) * oliveTop / (40.0 * 40)) * 10.0;
	price += (((double)pizzaLen * pizzaWid) * mushTop / (40.0 * 40)) * 12.0;
	price += 15*(double)delivery;

	// Order details
	printf("\n\nYour order details: \n");
	printf("******************* \n");
	printf("ID number: %09d \n", id);
	printf("Pizza size: %dx%d\n", pizzaLen, pizzaWid);
	printf("Total price: %.2lf\n", price);
	printf("Total price with tax (rounded down): %d\n", (int)(price*1.17));
	printf("Thank you for your order! \n");


}