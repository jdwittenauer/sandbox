#include <stdio.h>
#include <errno.h>
#include <string.h>

// variable declarations
extern int a, b;
extern int c;
extern float f;
extern int errno;

// struct declarations
struct Books
{
   char  title[50];
   char  author[50];
   char  subject[100];
   int   book_id;
};

// function declarations
int max(int num1, int num2);
void printValues();
void printMax();
void printArray();
void printPointers();
void printStruct();
void printErrors();

int main()
{
	printValues();
	printMax();
	printArray();
	printPointers();
	printStruct();
	printErrors();
	return 0;
}

int max(int num1, int num2) 
{
   /* local variable declaration */
   int result;
 
   if (num1 > num2)
      result = num1;
   else
      result = num2;
 
   return result; 
}

void printValues()
{
	/* variable definition */
	int a, b;
	int c;
	float f;

	/* actual initialization */
	a = 10;
	b = 20;

	c = a + b;
	printf("value of c : %d \n", c);

	f = 70.0/3.0;
	printf("value of f : %f \n", f);
}

void printMax()
{
	/* local variable definition */
   int a = 100;
   int b = 200;
   int ret;
 
   /* calling a function to get max value */
   ret = max(a, b);
 
   printf("Max value is : %d\n", ret);
}

void printArray()
{
   int n[10]; /* n is an array of 10 integers */
   int i,j;
 
   /* initialize elements of array n to 0 */         
   for (i = 0; i < 10; i++)
   {
      n[i] = i + 100; /* set element at location i to i + 100 */
   }
   
   /* output each array element's value */
   for (j = 0; j < 10; j++)
   {
      printf("Element[%d] = %d\n", j, n[j]);
   }
}

void printPointers()
{
   int  var = 20;   /* actual variable declaration */
   int  *ip;        /* pointer variable declaration */

   ip = &var;  /* store address of var in pointer variable*/

   printf("Address of var variable: %x\n", &var);

   /* address stored in pointer variable */
   printf("Address stored in ip variable: %x\n", ip);

   /* access the value using the pointer */
   printf("Value of *ip variable: %d\n", *ip);
}

void printStruct()
{
   struct Books Book1;        /* Declare Book1 of type Book */
   struct Books Book2;        /* Declare Book2 of type Book */
 
   /* book 1 specification */
   strcpy(Book1.title, "C Programming");
   strcpy(Book1.author, "Nuha Ali"); 
   strcpy(Book1.subject, "C Programming Tutorial");
   Book1.book_id = 6495407;

   /* book 2 specification */
   strcpy(Book2.title, "Telecom Billing");
   strcpy(Book2.author, "Zara Ali");
   strcpy(Book2.subject, "Telecom Billing Tutorial");
   Book2.book_id = 6495700;
 
   /* print Book1 info */
   printf("Book 1 title : %s\n", Book1.title);
   printf("Book 1 author : %s\n", Book1.author);
   printf("Book 1 subject : %s\n", Book1.subject);
   printf("Book 1 book_id : %d\n", Book1.book_id);

   /* print Book2 info */
   printf("Book 2 title : %s\n", Book2.title);
   printf("Book 2 author : %s\n", Book2.author);
   printf("Book 2 subject : %s\n", Book2.subject);
   printf("Book 2 book_id : %d\n", Book2.book_id);
}

void printErrors()
{
   FILE * pf;
   int errnum;
   pf = fopen ("unexist.txt", "rb");
   if (pf == NULL)
   {
      errnum = errno;
      fprintf(stderr, "Value of errno: %d\n", errno);
      perror("Error printed by perror");
      fprintf(stderr, "Error opening file: %s\n", strerror(errnum));
   }
   else
   {
      fclose (pf);
   }
}
