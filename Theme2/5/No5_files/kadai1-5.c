#include <stdio.h>

#define N 3

int main(void)
{
  int ai[N];

  printf("%d\n",sizeof(int)*N);
  printf("%d\n",sizeof(ai));
  printf("%d\n",sizeof(int [N]));
  
  return 0;
}
