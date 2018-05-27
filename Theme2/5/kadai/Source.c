#include<stdio.h>

struct hoge
{
	int a;
	unsigned char b;
	char c[2];
};

int main() {
	printf("kadai1\n");
	struct hoge d = { -1,255,{-128,127} };

	unsigned char *pb = &d.b;
	
	char name[] = "yuasa";

	char *pname = name;
	
	short e[2] = { -1,1 };



	for (int i = 0; i < sizeof(d); i++)
	{
		printf("%p %x\n", (char *)&d.a + i, *((char *)&d.a + i));
	}
	printf("\n");

	for (int i = 0; i < sizeof(pb); i++)
	{
		printf("%p %x\n", (char *)&pb + i, *((char *)&pb + i));
	}
	printf("\n");
	for (int i = 0; i < sizeof(name); i++)
	{
		printf("%p %x\n", (char *)&name + i, *((char *)&name + i));
	}
	printf("\n");
	for (int i = 0; i < sizeof(pname); i++)
	{
		printf("%p %x\n", (char *)&pname + i, *((char *)&pname + i));
	}
	printf("\n");
	for (int i = 0; i < sizeof(e); i++)
	{
		printf("%p %x\n", (char *)&e + i, *((char *)&e + i));
	}
	printf("\n");
	int a = 7;
	for (int  i = 0; i < sizeof(a); i++)
	{
		//printf("%p %x\n", ((char *)&a + i), *((char *)&a + i));
	}
	
}