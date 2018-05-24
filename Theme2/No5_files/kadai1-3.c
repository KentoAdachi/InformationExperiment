#include <stdio.h>

int main(void)
{
  int i;
  
  i=511;
  {
    int j;
    for(j=0;j<sizeof(int);j++)
      printf("%p %x\n",((char *)&i+j),*((char *)&i+j));
  }
  
  return 0;
}
