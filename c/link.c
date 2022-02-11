#include "stdio.h"
#include "stdlib.h"

typedef struct link
{
	struct link* last;
	int val;
	struct link* next;
} link;

typedef struct list
{
	link* first;
	link* current;
	link* last;
} list;

link* initLink(void)
{
	link* lnk = (link*)malloc(sizeof(link));
	lnk->last = NULL;
	lnk->val = 0;
	lnk->next = NULL;
}

list* initList(void)
{
	list* lst = (list*)malloc(sizeof(list));
	lst->first = NULL;
	lst->current = lst->first;
	lst->last = NULL;
}

void insertNum(list* lst, int num)
{
	// Create the link 
	link* newLink = initLink();
	newLink->val = num;
	
	// Add this link to the chain
	if(lst->current == NULL)
	{
		lst->first = newLink;
	}
	else
	{
		lst->current->next = newLink;
		newLink->last = lst->current;
	}
	// Increment the current link	
	lst->current = newLink;
}

void insertLink(list* lst, link* lnk)
{
	// Add the link to the chain
	lst->current->next = lnk;
	lnk->last = lst->current;

	// Increment the current link
	lst->current = lnk;
}

link* findNum(list* lst, int num)
{
	link* cur = lst->first;

	if(cur != NULL)
	{
		while(cur->val != num)
		{	
			if(cur->next == NULL)
			{
				break;
			}
			else
			{
				cur = cur->next;
			}
		}
	}
	return cur;
}

void deleteNum(list* lst, int num)
{
	link* lnk = findNum(lst,num);
	
	// Remove the link
	
	link* llnk = lnk->last;
	link* nlnk = lnk->next;
	
	llnk->next = nlnk;
	nlnk->last = llnk;

	free(lnk);
}

void deleteList(list* lst)
{
	link* lnk = lst->first;
	link* next = lnk->next;
	while(lnk == NULL)
	{
		free(lnk);
		lnk = next;
		next = lnk->next;
	}

	free(lst);
}

	
int main(void)
{
	list* lst = initList();
	insertNum(lst, 0);
	insertNum(lst, 9);
	insertNum(lst, 25);
	insertNum(lst, 36);

	link* lnk = findNum(lst,25);
	if(lnk == NULL)
		printf("%d\n",lnk->val);
	deleteNum(lst,9);
	
	link* cur = lst->first;
	while(cur)
	{
		printf("%d\n",cur->val);
		cur = cur->next;
	}
	
	return 0;
}
