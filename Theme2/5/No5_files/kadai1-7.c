#include <stdio.h>
int main(void)
{
  int ai[3]={0,1,2};

  printf("%p\n",ai);
  printf("%p\n",&ai);

  printf("%d\n",*ai);
  printf("%p\n",*(&ai));
  printf("%d\n",*(*(&ai)));
  
  return 0;
}
