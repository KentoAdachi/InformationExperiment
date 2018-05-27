#include <stdio.h>
int main(void)
{
  int ai[3]={0,1,2};
  int n;

  for(n=0;n<3;n++)
    printf("%p %d\n",ai+n,*(ai+n));
  printf("\n");
  
  for(n=0;n<12;n++)
    printf("%p %d\n",(char *)ai+n,*((char *)ai+n));
  printf("\n");

  return 0;
}
