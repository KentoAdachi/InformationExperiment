#include <stdio.h>
int main(void)
{
  int ai[3]={1,2,3};
  int n;

  for(n=0;n<3;n++)
    printf("ai[%d]=%d\n",n,ai[n]);

  for(n=0;n<3;n++)
    printf("&ai[%d]=%p\n",n,&ai[n]);
  
  return 0;
}
