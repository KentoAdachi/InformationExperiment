#include <stdio.h>
#include <stdlib.h>

#define N 3

int main(void)
{
  int *dai;

  dai=(int *)malloc(N*sizeof(int));
  dai[1]=123;
  printf("%d\n",dai[1]);

  free(dai);

  return 0;
}
