#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

int cmpCounter=0;
int moveCounter=0;

void swap(int *x,int *y)
{
    int temp;
    temp = *x;
    *x = *y;
    *y = temp;
}



//SELECT SORT

void selectSortASC(int arrayF[100], int arraySizeF) {
	int a, b, position, tmpSwapper;
	for(a=0; a<(arraySizeF-1); a++) {
		position=a;
		for(b=a+1; b<arraySizeF; b++) {
			if(arrayF[b]<arrayF[position]) {
				cmpCounter++;				
				position=b;
				moveCounter++;
			}
		}
		if(position!=a) {
			cmpCounter++;
			tmpSwapper=arrayF[a];
			arrayF[a]=arrayF[position];
			arrayF[position]=tmpSwapper;
			moveCounter++;
		}
	}
}

void selectSortDESC (int arrayF[100], int arraySizeF) {
	int a, b, position, tmpSwapper;
	for(a=0; a<(arraySizeF-1); a++) {
		position=a;
		for(b=a+1; b<arraySizeF; b++) {
			if(arrayF[b]>arrayF[position]) {
				cmpCounter++;
				position=b;
				moveCounter++;
			}
		}
		if(position!=a) {
			cmpCounter++;
			tmpSwapper=arrayF[a];
			arrayF[a]=arrayF[position];
			arrayF[position]=tmpSwapper;
			moveCounter++;
		}
	}
}






//INSERT SORT


void insertionSortASC(int arrayF[100], int arraySizeF) {
	int a, b, tmpSwapper;
	for(a=1; a<arraySizeF; a++) {
		b=a;
		moveCounter++;
		tmpSwapper=arrayF[b];
		moveCounter++;
		while(b>0 && (arrayF[b-1] > tmpSwapper)) {
			cmpCounter++;
			arrayF[b]=arrayF[b-1];
			moveCounter++;
			b--;
		}
		arrayF[b]=tmpSwapper;
		moveCounter++;
	}
}


void insertionSortDESC(int arrayF[100], int arraySizeF) {
	int a, b, tmpSwapper;
	for(a=1; a<arraySizeF; a++) {
		b=a;
		moveCounter++;
		tmpSwapper=arrayF[b];
		moveCounter++;
		while(b>0 && (arrayF[b-1] < tmpSwapper)) {
			cmpCounter++;
			arrayF[b]=arrayF[b-1];
			moveCounter++;
			b--;
		}
		arrayF[b]=tmpSwapper;
		moveCounter++;
	}
}









//HEAP SORT


void heapifyASC(int arr[100], int n, int i)
{
   // Find largest among root, left child and right child
   int largest = i;
   int l = 2*i + 1;
   int r = 2*i + 2;

   if (l < n && arr[l] > arr[largest])
     largest = l;
 
	cmpCounter++;

   if (r < n && arr[r] > arr[largest])
     largest = r;
 
	cmpCounter++;

   // Swap and continue heapifying if root is not largest
   if (largest != i)
   {
	 int tmp=arr[i];
	 arr[i]=arr[largest];
	 arr[largest]=tmp;
	 moveCounter++;
	 
     heapifyASC(arr, n, largest);
   }
   cmpCounter++;
}


// main function to do heap sort
void heapSortASC(int arr[100], int n)
{
   // Build max heap
   for (int i = n / 2 - 1; i >= 0; i--)
     heapifyASC(arr, n, i);

   // Heap sort
   for (int i=n-1; i>=0; i--)
   {
	 int tmp=arr[0];
	 arr[0]=arr[i];
	 arr[i]=tmp;
	 moveCounter++;
     
     // Heapify root element to get highest element at root again
     heapifyASC(arr, i, 0);
   }
}


void heapifyDESC(int arr[100], int n, int i)
{
   // Find largest among root, left child and right child
   int largest = i;
   int l = 2*i + 1;
   int r = 2*i + 2;

   if (l < n && arr[l] < arr[largest])
     largest = l;
 
	cmpCounter++;

   if (r < n && arr[r] < arr[largest])
     largest = r;
 
 
	cmpCounter++;

   // Swap and continue heapifying if root is not largest
   if (largest != i)
   {
	 
	 int tmp=arr[i];
	 arr[i]=arr[largest];
	 arr[largest]=tmp;
	 moveCounter++;
	 
     //swap(arr[i], arr[largest]);
     heapifyDESC(arr, n, largest);
   }
   cmpCounter++;
}


void heapSortDESC(int arr[100], int n)
{
   // Build max heap
   for (int i = n / 2 - 1; i >= 0; i--)
     heapifyDESC(arr, n, i);

   // Heap sort
   for (int i=n-1; i>=0; i--)
   {
	 int tmp=arr[0];
	 arr[0]=arr[i];
	 arr[i]=tmp;
	 moveCounter++;
     
     // Heapify root element to get highest element at root again
     heapifyDESC(arr, i, 0);
   }
}








//QUICK SORT


int partitionASC(int arr[], int low, int high) 
{ 
    int pivot = arr[high];    // pivot 
    int i = (low - 1);  // Index of smaller element 
  
    for (int j = low; j <= high- 1; j++) 
    { 
        // If current element is smaller than or 
        // equal to pivot 
        if (arr[j] <= pivot) 
        {
            i++;    // increment index of smaller element 
            swap(&arr[i], &arr[j]); 
			moveCounter++;
        }
		cmpCounter++;
    } 
    swap(&arr[i + 1], &arr[high]); 
	moveCounter++;
    return (i + 1); 
} 
  
/* The main function that implements QuickSort 
 arr[] --> Array to be sorted, 
  low  --> Starting index, 
  high  --> Ending index */
void quickSortASC(int arr[], int low, int high) 
{ 
    if (low < high) 
    { 
        /* pi is partitioning index, arr[p] is now 
           at right place */
        int pi = partitionASC(arr, low, high); 
  
        // Separately sort elements before 
        // partition and after partition 
        quickSortASC(arr, low, pi - 1); 
        quickSortASC(arr, pi + 1, high); 
    } 
	cmpCounter++;
} 
 


int partitionDESC(int arr[], int low, int high) 
{ 
    int pivot = arr[high]; 
    int i = (low - 1);  
  
    for (int j = low; j <= high- 1; j++) 
    { 
        if (arr[j] >= pivot) 
        { 
            i++;  
            swap(&arr[i], &arr[j]); 
			moveCounter++;
        }
		cmpCounter++;
    } 
    swap(&arr[i + 1], &arr[high]); 
	moveCounter++;
    return (i + 1); 
} 
  
void quickSortDESC(int arr[], int low, int high) 
{ 
    if (low < high) 
    { 
        int pi = partitionDESC(arr, low, high); 
  
        quickSortDESC(arr, low, pi - 1); 
        quickSortDESC(arr, pi + 1, high); 
    } 
	cmpCounter++;
} 
	

		


int main(int argc,char* argv[]) {
	
	
	if(argc!=3) {
		printf("You should give 2 arguments - type and order. Try once again.");
		exit(1);
	}
	
	int arraySize, array[100], ctr;
	clock_t start, end;
    double time;
	
	printf("Give number of el: \n");
	scanf("%d", &arraySize);
	printf("\n");
	printf("Give numbers: \n");
	
	for(ctr=0; ctr<arraySize; ctr++) {
		scanf("%d", &array[ctr]);
	}
	
	printf("\n");
	printf("Sorted ones:");
	
	start=clock();
	
	if(!(strcmp(argv[1], "select"))) {
		if(!(strcmp(argv[2], "asc"))) {
			selectSortASC(array, arraySize);
		}
		else if (!(strcmp(argv[2],"desc"))) {
			selectSortDESC(array, arraySize);
		}
	}
	else if(!(strcmp(argv[1], "insert"))) {
		if(!(strcmp(argv[2], "asc"))) {
			insertionSortASC(array, arraySize);
		}
		else if (!(strcmp(argv[2],"desc"))) {
			insertionSortDESC(array, arraySize);
		}
	}
	else if(!(strcmp(argv[1], "heap"))) {
		if(!(strcmp(argv[2], "asc"))) {
			heapSortASC(array, arraySize);
		}
		else if (!(strcmp(argv[2],"desc"))) {
			heapSortDESC(array, arraySize);
		}
	}
	else if(!(strcmp(argv[1], "quick"))) {
		if(!(strcmp(argv[2], "asc"))) {
			quickSortASC(array, 0, arraySize-1);
		}
		else if (!(strcmp(argv[2],"desc"))) {
			quickSortDESC(array, 0, arraySize-1);
		}
	}
	
	if(!(strcmp(argv[2], "asc"))) {
		for(ctr=0; ctr<arraySize-1; ctr++) {
			if(array[ctr]>array[ctr+1]) {
				printf("Error in sorting algorithm");
			}
			else {
				for(ctr=0; ctr<arraySize; ctr++) {
					printf("\n");
					printf("%d", array[ctr]);
				}
			}
		}
	}
	else if(!(strcmp(argv[2], "desc"))) {
		for(ctr=0; ctr<arraySize-1; ctr++) {
			if(array[ctr]<array[ctr+1]) {
				printf("Error in sorting algorithm");
			}
			else {
				for(ctr=0; ctr<arraySize; ctr++) {
					printf("\n");
					printf("%d", array[ctr]);
				}
			}
		}
	}
	
	end = clock();
	time=((double) (end-start))/CLOCKS_PER_SEC;
	
	printf("\n");
	printf("\n");
	
	//perror("Number of compares: %d\n");
	fprintf(stderr, "Number of compares: %d\n", cmpCounter);
	fprintf(stderr, "Number of swaps: %d\n", moveCounter);
	fprintf(stderr, "Time of algorithm: %f\n", time);
	
	printf("\n");
}
		
		
		
