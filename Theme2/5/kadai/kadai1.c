#include<stdio.h>

struct hoge
{
	int a;
	unsigned char b;
	char c[2];
};

int main() {
	printf("kadai1\n");
	/*上から順番に
	ff
	ff
	ff
	ff

	ff
	
	80 1バイトの補数表現

	7f
	
	0
	*/
	struct hoge d = { -1,255,{-128,127} };

	/*
	d.bの(先頭)アドレスを格納
	*/
	unsigned char *pb = &d.b;
	/*
	79 ...y
	75 ...u
	61 ...a
	73 ...s
	61 ...a
	00 ...終端文字?
	*/
	char name[] = "yuasa";

	/*
	nameの先頭アドレスを格納
	*/
	char *pname = name;
	/*
	ff
	01
	*/
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

	
}