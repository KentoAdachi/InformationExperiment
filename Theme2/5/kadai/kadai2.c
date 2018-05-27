#include<stdio.h>
int main() {
	short p[4] = { 1,-16,-256,-257 };
	short *p2 = (short *)((char*)((int*)p - 1) - 1) + sizeof(p) / sizeof(int) + 1;
	for (int n = 0; n < 3; n++)
	{
		printf("%d, %d\n", n, p2[n]);
	}

	for (int i = 0; i < sizeof(p); i++)
	{
		printf("%p %x\n", (char*)&p + i, *((char*)&p + i));
	}
	for (int i = 0; i < sizeof(p2); i++)
	{
		printf("%p %x\n", (char*)&p2 + i, *((char*)&p2 + i));
	}
}