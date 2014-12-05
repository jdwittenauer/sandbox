#include <stdio.h>
#include <string.h>

#define MAXLINES 5000			/* constant defining max number of lines to sort */

char *linePointer[MAXLINES];	/* pointer to text lines */

int ReadLines(char *linePointer[], int nLines);
void WriteLines(char *linePointer[], int nLines);
void QuickSort(char *linePointer[], int left, int right);

int main()
{
	int nLines;

	if ((nLines = ReadLines(linePointer, MAXLINES)) >= 0)
	{
		QuickSort(linePointer, 0, nLines - 1);
		WriteLines(linePointer, nLines);
		return 0;
	}
	else
	{
		printf("Error: Input is too big\n");
		return 1;
	}
}


#define MAXLENGTH 1000

char *Alloc(int);
int GetLine(char *a, int i);
void Swap(char *v[], int i, int j);

int ReadLines(char *linePointer[], int maxLines)
{
	int length, nLines;
	char *pointer, line[MAXLENGTH];

	nLines = 0;
	while((length = GetLine(line, MAXLENGTH)) > 0)
	{
		if (nLines >= maxLines || (pointer = Alloc(length)) == NULL)
		{
			return -1;
		}
		else
		{
			line[length - 1] = '\0';	/* delete newline */
			strcpy(pointer, line);
			linePointer[nLines++] = pointer;
		}
	}

	return nLines;
}

void WriteLines(char *linePointer[], int nLines)
{
	int i;

	for (i = 0; i < nLines; i++)
		printf("%s\n", linePointer[i]);
}

void QuickSort(char *v[], int left, int right)
{
	int i, last;

	if (left >= right)
		return;			/* less than two elements */

	Swap(v, left, (left + right) / 2);
	last = left;

	for (i = left + 1; i <= right; i++)
	{
		if (strcmp(v[i], v[left]) < 0)
			Swap(v, ++last, i);
	}

	Swap(v, left, last);
	QuickSort(v, left, last - 1);
	QuickSort(v, last + 1, right);
}

char *Alloc(int i)
{
	// not implemented
	return NULL;
}

int GetLine(char *a, int i)
{
	// not implemented
	return -1;
}

void Swap(char *v[], int i, int j)
{
	char *temp;
	temp = v[i];
	v[i] = v[j];
	v[j] = temp;
}
