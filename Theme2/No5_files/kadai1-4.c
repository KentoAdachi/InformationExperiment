#include <stdio.h>

int main(void)
{
  int i;
  int *pi;

  i=-1;

  pi=&i;

  printf("%d\n",*pi);
  
  {
    int j;

    
    for(j=0;j<sizeof(int *);j++)
      printf("%p %02x\n",((char *)&pi+j),*((char *)&pi+j));
    printf("\n");

    for(j=0;j<sizeof(int);j++)
      printf("%p %02x\n",((char *)&i+j),*((char *)&i+j));
    printf("\n");
  }
  
  return 0;
}
