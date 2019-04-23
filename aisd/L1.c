#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int totalCompMTF=0; //counter to comparison through findMTF method
int totalCompTRANS=0; //counter to comparison through findTRANS method 

typedef struct Node {
	
	int value;
	struct Node* next;
	
} tNode; //defining the struct in a recursive manner


// function to write all elements of list - used to control initializing
void printList (tNode* head) {
	
	tNode* current = head;
	while(current != NULL) {
		printf("%d\n", current->value);
		current = current->next;
	}
	
}


		
//function to choose the maximum value from current list
int maxFunc(tNode* head) {
	
	int max=0;
	tNode *current=head;
	max=current->value; //initializing max as the first value of list
	while(current->next!=NULL) {
		totalCompMTF++;
		totalCompTRANS++;
		if(current->value>max) { //if current value of node is higher, it replaces the previous max
			totalCompMTF++;
			totalCompTRANS++;
			max=current->value;
		}
		current=current->next;
	}
	
	return max;
	
}
		
	
	
	

void insert(tNode** head, int val) {
	
	if(*head==NULL) { //case when list is empty
		*head=(tNode*)malloc(sizeof(tNode));
		(*head)->value=val;
		(*head)->next=NULL;
	}
	else { //case when list is not empty 
		tNode* current = *head;
		while(current->next!=NULL) {
			current=current->next;
		}
		current->next = (tNode*)malloc(sizeof(tNode));
		current->next->value=val;
		current->next->next=NULL;
	}
	
}




void delete(tNode** head, int deletedN) {
	
	tNode* current = *head;
	tNode *tmp;
		
	if (current->value==deletedN) { //case when the first list value is deleting
			totalCompMTF++;
			totalCompTRANS++;
			*head=current->next; 
	}
	else { //deleting from further places 
		while(current->next!=NULL) {	
			totalCompMTF++;	
			totalCompTRANS++;			
			if(current->next->value==deletedN) {
				totalCompMTF++;
				totalCompTRANS++;
				tmp=current->next;
				if(tmp->next==NULL) {
					totalCompMTF++;
					totalCompTRANS++;
					current->next=NULL;
					break;
				}
				else{
					current->next=tmp->next;
				}
			}
			current=current->next;
		}
	}
	
}



	

bool isEmpty(tNode* head) { //checking if list is empty or not
	
	if (head==NULL) {
		totalCompMTF++;
		totalCompTRANS++;
		return true;
	}
	else
		return false;
	
	
}






void findMTF(tNode **head, int searchedN) { //finding element on list and moving it to the beggining of list
	
	tNode* current = *head;
	tNode *tmp;
	int a=0;
		
	while(current->next!=NULL && a==0) {
		totalCompMTF++;
		if(current->next->value==searchedN) {
			totalCompMTF++;
			tmp=current->next->value;
			current->next->value=(*head)->value;
			(*head)->value=tmp;
			a=1;	
		}
		current=current->next;
	}
}





void findTRANS(tNode** head, int searchedN) { //finding element on list and moving it one place further 
	
	tNode* current = *head;
	tNode *tmp;
	int a=0;
		
	while((current->next!=NULL) && a==0) {		
		totalCompTRANS++;
		if(current->value==searchedN) {
			totalCompTRANS++;
			tmp=current->next->value;
			current->next->value=current->value;
			current->value=tmp;
			a=1;			
		}
		current=current->next;
	}
	
}

int main(void) {	
	
	tNode* head;
	head = malloc(sizeof(tNode));
	head=NULL; // initializing the list 
	
	
	//making table with numbers from 1 to 100 and initializing list with randoming ones
	int table[100];
	int tmp=0;
	
	
	while(tmp<100) {
		table[tmp]=tmp+1;
		tmp++;
	}
	
	tmp=0;
	
	srand(time(NULL));
	
	while(tmp<100) { //randoming indexes of table to mix values in table 
		int current=table[tmp];
		int randomindex=rand()%100;
		table[tmp]=table[randomindex];
		table[randomindex]=tmp;
		tmp++;
	}
		
	
	int counter=0;
	
	while(counter<100) {
		//insert(&head, table[rand()%100]);
		insert(&head, table[counter]);
		counter++;
	}
	
	//printList(head);
	
	while(isEmpty(head)==0) { //until the list will be empty
		totalCompMTF++;
		totalCompTRANS++;
		
		int i=1;
		
		while (i<101) { // checking if elements from 1 to 100 are on the list 
			totalCompMTF++;
			totalCompTRANS++;
			//findTRANS(&head, i);
			findMTF(&head, i);
			i++;
		}
		
		
		maxFunc(head); //choosing max from list 
		//printf("Max-> %d\n", maxFunc(head)); //writing it
		delete(&head, maxFunc(head)); //deleting it

		
	}
	
	printf("Is finally empty? %d\n", isEmpty(head)); //after 100 deletes writing if list is finally empty
	
	printf("Total compMTF --> %d\n", totalCompMTF);
	//printf("Total compTRANS --> %d\n", totalCompTRANS);
	
	free(head);
	
	return 0;
	
}
	
	
	
	
	
// ** - adres wskaźnika
// * - wskaźnik 