#include <stdio.h>

int main(void)
{
  int i;
  
  i=511;
  printf("Value= %d\n",*(int *)(&i));

  return 0;
}
