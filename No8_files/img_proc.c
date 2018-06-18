/*            img_proc.c                 */
/* Usage:  time a.out file.ppm kaisuu    */
/* Output: file_heikin.pgm, file_laplasian.pgm */
/*         file_res.ppm                  */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef unsigned char UCHAR;

/* 画像サイズは変更不可 */
#define DIM1 960		/* 行数 */
#define DIM2 1280		/* 列数 */
#define DIM3    3

#define RED   0
#define GREEN 1
#define BLUE  2

#define WHITE 255
#define BLACK 0

#define ON 1
#define OFF 0

#define HALF_VAL 127            /* ラプラシアンフィルタリングの結果に加えるバイアス値 */

#define THRESH 7                /* 計算時間測定時の閾値は７で固定（結果の変化を見たければ変更してOK） */
#define N_REPEAT 50	        /* 繰返し回数．消費時間測定時は必ず50とする． */

void grayscale(UCHAR[][DIM2], UCHAR[][DIM2][DIM3], int, int);
void heikinka(UCHAR[][DIM2], UCHAR[][DIM2], int, int);
void copy(UCHAR[][DIM2], UCHAR[][DIM2], int, int);
void laplasian(UCHAR[][DIM2], UCHAR[][DIM2], int, int);
void zero_kousa(UCHAR[][DIM2][DIM3], UCHAR[][DIM2][DIM3], UCHAR[][DIM2], int, int, int);

void write_ppm_cip(UCHAR[][DIM2][DIM3], char *, int, int);
void write_pgm_cip(UCHAR[][DIM2], char *, int, int);
void read_ppm_cip(UCHAR[][DIM2][DIM3], char *);

UCHAR org[DIM1][DIM2][DIM3];	/* 入力画像 */
UCHAR res[DIM1][DIM2][DIM3];	/* 出力画像 */
UCHAR gray[DIM1][DIM2];	/* グレースケール画像，平滑化画像*/
UCHAR gray2[DIM1][DIM2];	/* 処理用グレースケール画像 */
UCHAR lap[DIM1][DIM2];	/* ラプラシアンの結果画像 */

int main(int argc, char *argv[])
{
	int i, j, g, r, k;
	int kaisuu;			/* 平滑化の繰り返し回数 */
	char in_fname[200];		/* 入力画像ファイル名 */
	char out_fname[200];		/* 出力画像ファイル名 */

	if (argc < 3)			/* 入力コマンドチェック */
	{
		printf("usage: a.out filename keisuu \n");
		exit(1);
	}
	else
	{
		strcpy(in_fname, argv[argc - 2]); /* 入力画像のファイル名 */
		kaisuu = atoi(argv[argc - 1]);	/* 平滑化の繰り返し回数 */
	}

	read_ppm_cip(org, in_fname);	/* 画像をファイルより読み込み */

	for (g = 0; g < DIM1; g++)		/* 処理用画像，ラプラシアン画像，出力画像を0に初期化(外周部を0にするため) */
		for (r = 0; r < DIM2; r++)
		{
			gray2[g][r] = 0;
			lap[g][r] = 0;
			for (k = 0; k < DIM3; k++)
				res[g][r][k] = 0;
		}

	/* フィルタリング(時間測定のため必ず50回行う．) */
	for (i = 0; i < N_REPEAT; i++)//ここを1回にしたところ処理時間は0.1秒=ここの中だけ考えればよい
	{
		grayscale(gray, org, DIM1, DIM2); /* 入力画像(カラー)からグレースケール画像作成 *///3.56-2.44

		//#ifdef REMOVE
		for (j = 0; j < kaisuu; j++)
		{
			heikinka(gray2, gray, DIM1, DIM2); /* 3×3の平均化フィルタによる平滑化 *///3.56-1.8
			copy(gray, gray2, DIM1, DIM2); /* 繰り返しのためにコピー */
		}

		laplasian(lap, gray, DIM1, DIM2); /* ラプラシアンの結果画像作成 *///3.56-2.88

		zero_kousa(res, org, lap, DIM1, DIM2, DIM3); /* 出力画像作成（ゼロ交差法） *///3.56-1.73
		//#endif
	}

	in_fname[strlen(in_fname) - 4] = '\0'; /* 入力画像ファイル名から.ppm除く */

	sprintf(out_fname, "%s_heikin.pgm", in_fname); /* （グレースケール画像）平滑化画像用出力画像ファイル名設定 */
	write_pgm_cip(gray, out_fname, DIM2, DIM1); /* （グレースケール画像）平滑化画像をファイルへ出力 */

	sprintf(out_fname, "%s_laplasian.pgm", in_fname); /* ラプラシアンの結果画像用ファイル名設定 */
	write_pgm_cip(lap, out_fname, DIM2, DIM1); /* ラプラシアンの結果画像をファイルへ出力 */

	sprintf(out_fname, "%s_res.ppm", in_fname); /* 出力画像ファイル名設定 */
	write_ppm_cip(res, out_fname, DIM2, DIM1); /* 出力画像をファイルへ出力 */

	return 0;
}

/* カラー画像からグレースケール画像作成 */
//ループ展開 30ms
//計算をdouble からfloat に変更
void grayscale(UCHAR gray[][DIM2], UCHAR data[][DIM2][DIM3], int n_gyou, int n_retu)
{
  short g, r;// ,k;
	short tmp;
	//int imax = DIM1 * DIM2*DIM3;
	//int i = 0;

	for (g = 0; g < n_gyou; g++)
		for (r = 0; r < n_retu; r++)
		{
		  //tmp = 0;
			tmp = data[g][r][0] + data[g][r][1] + data[g][r][2];//展開
			//for (k = 0; k < DIM3; k++)
			//{
			//	tmp += data[g][r][k];
			//}
			gray[g][r] = (UCHAR)((float)tmp /3 + 0.5f ); /* 平均値を四捨五入 */  
			//gray[g][r] = (UCHAR) ((tmp*2 + 3)/6);//何故か伸びた
		}
	//UCHAR *gr = gray[0];
	//UCHAR *da = data[0][0];
	//for (i = 0; i < imax; i+=3) {
	//	
	//	*gr = (UCHAR)((*da + *(da + 1) + *(da + 2)) / 3.0 + 0.5);
	//	da += 3;
	//	gr++;
	//}

}

/* 3×3の平均化フィルタによる平滑化 */
/* 外周部は処理しない。予め0を代入している。 */
//変更点　ループの展開、割り算の括りだし 8s程度
//この変更により、乗算は時間を食うことがわかった
void heikinka(UCHAR gray[][DIM2], UCHAR data[][DIM2], int n_gyou, int n_retu)
{
	int g, r;
	int tmp;

	for (g = 1; g < n_gyou - 1; g++)
		for (r = 1; r < n_retu - 1; r++)
		{
			tmp =
				data[g - 1][r - 1]
				+ data[g - 1][r]
				+ data[g - 1][r + 1]
				+ data[g][r - 1]
				+ data[g][r]
				+ data[g][r + 1]
				+ data[g + 1][r - 1]
				+ data[g + 1][r]
				+ data[g + 1][r + 1];

			
			//gray[g][r] = (UCHAR)((float)tmp/9 + 0.5f); /* 四捨五入 */
			gray[g][r] = (UCHAR)((tmp * 2 + 9)/18);
		}
}

/* グレースケール画像のコピー */
void copy(UCHAR gray[][DIM2], UCHAR data[][DIM2], int n_gyou, int n_retu)
{
	int g, r;

	for (g = 0; g < n_gyou; g++)
		for (r = 0; r < n_retu; r++)
			gray[g][r] = data[g][r];
}


/* ラプラシアンの結果画像作成 */
/* 外周部は処理しない。予め0を代入している。 */
//Winograd's Minimal Filtering Algorism
//im2colを使った実装(できたらやる)
void laplasian(UCHAR lap[][DIM2], UCHAR data[][DIM2], int n_gyou, int n_retu)
{
	int g, r;
	int tmp;


	for (g = 1; g < n_gyou - 1; g++)
		for (r = 1; r < n_retu - 1; r++)
		{
			tmp = -data[g - 1][r - 1] - data[g - 1][r] - data[g - 1][r + 1]
				- data[g][r - 1] + 8 * data[g][r] - data[g][r + 1]
				- data[g + 1][r - 1] - data[g + 1][r] - data[g + 1][r + 1];
			tmp += HALF_VAL; /* 正の値にするため127を足す */


			//lap[g][r] = min(tmp, 255);
			//lap[g][r] = max(tmp, 0);//ifを使わずに書いてみた
			//lap[g][r] = (tmp>255)? 255:(tmp < 0 ? 0:tmp);
			if (tmp > 255)
			  lap[g][r] = 255;
			else if (tmp < 0)
			  lap[g][r] = 0;
			else
			  lap[g][r] = tmp;
		}
}


/* 原画像のゼロ交差の画素を黄色にした出力画像作成 */
/* 最外周部２列（２行）は処理しない。予め0を代入している。 */
void zero_kousa(UCHAR res[][DIM2][DIM3], UCHAR org[][DIM2][DIM3], UCHAR data[][DIM2], int n_gyou, int n_retu, int col)
{
	int g, r, i, j;
	int flag_p; /* 値が（HALF_VAL + THRESH）より大きいかをチェックするフラグ */
	int flag_m; /* 値が（HALF_VAL - THRESH）より小さいかをチェックするフラグ */

	for (g = 2; g < n_gyou - 2; g++)
		for (r = 2; r < n_retu - 2; r++)
		{
			flag_p = flag_m = OFF;
			for (i = -2; i <= 2; i++)
				for (j = -2; j <= 2; j++)
				  if (data[g + i][r + j] < HALF_VAL - THRESH /*&&flag_m ==OFF*/)
						flag_m = ON;                /* （ラプラシアンの値 < -THRESH）の画素が近傍に１画素でもあるとON */
					else if (data[g + i][r + j] > HALF_VAL + THRESH)
						flag_p = ON;                /* （ラプラシアンの値 > THRESH）の画素が近傍に１画素でもあるとON */


			
			if (flag_p &&flag_m) /* 何れのフラグもONだとエッジとして黄色にする */
			{
				res[g][r][0] = WHITE;
				res[g][r][1] = WHITE;
				res[g][r][2] = BLACK;
			} else{
			                           /* そうでなければ原画像の色 */
			res[g][r][0] = org[g][r][0];
			res[g][r][1] = org[g][r][1];
			res[g][r][2] = org[g][r][2];
			}
			
		}
}



#define W_BYTE DIM1*DIM2*DIM3

/* CIP形式の画像データよりPPMファイル作成 */
void write_ppm_cip(UCHAR data_buf[][DIM2][DIM3], char *fname, int width, int height)
{
	FILE *fp;

	/* ファイルを開く */
	if ((fp = fopen(fname, "wb")) == NULL) {
		fprintf(stderr, "file(%s) can't open\n", fname);
		exit(1);
	}

	fprintf(fp, "P6\n");		/* カラー画像かつバイナリーデータの記号 */
	fprintf(fp, "%d %d\n", width, height); /* 画像の幅(列数)と高さ(行数) */
	fprintf(fp, "255\n");	/* 最大値 */

	{				/* 画像データをrepeat(+1)個に分割して書き込み */
		int i;
		int repeat = (DIM1*DIM2*DIM3) / (W_BYTE); /* 分割数 */
		int rest = DIM1 * DIM2*DIM3 - repeat * (W_BYTE); /* 余りデータ量 */
		UCHAR *pt = (UCHAR *)&data_buf[0][0][0]; /* 書き込むデータの位置を持つポインタ */

		for (i = 0; i < repeat; i++)
		{
			fwrite(pt, sizeof(UCHAR), W_BYTE, fp);
			pt += W_BYTE;
		}
		if (rest > 0)
			fwrite(pt, sizeof(UCHAR), rest, fp);
	}

	fclose(fp);			/* ファイルを閉じる */
}


/* CIP形式の画像データよりPGMファイル作成 */
void write_pgm_cip(UCHAR data_buf[][DIM2], char *fname, int width, int height)
{
	FILE *fp;

	/* ファイルを開く */
	if ((fp = fopen(fname, "wb")) == NULL) {
		fprintf(stderr, "file(%s) can't open\n", fname);
		exit(1);
	}

	fprintf(fp, "P5\n");		/* グレースケール画像かつバイナリーデータの記号 */
	fprintf(fp, "%d %d\n", width, height); /* 画像の幅(列数)と高さ(行数) */
	fprintf(fp, "255\n");	/* 最大値 */

	fwrite(&data_buf[0][0], sizeof(UCHAR), DIM1*DIM2, fp);

	fclose(fp);			/* ファイルを閉じる */
}


#define R_BYTE DIM1*DIM2*DIM3

/* ppmフォーマットの画像ファイルをCIP形式の画像メモリに読み込み */
void read_ppm_cip(UCHAR data_buf[][DIM2][DIM3], char *fname)
{
	FILE	*fp;
	char	str_buf[1024];
	char	magic_num[8];		/* マジックナンバー */
	int	max_val;		/* 画素値の最大値 */
	int width, height;
	int count_limit;

	/* ファイルを開く */
	if ((fp = fopen(fname, "rb")) == NULL) {
		fprintf(stderr, "file(%s) can't open.\n", fname);
		exit(1);
	}
	/* マジックナンバー読み込み */
	count_limit = 0;
	sprintf(str_buf, "#");
	while ((str_buf[0] == '#') || (str_buf[0] == '\n') || (str_buf[0] == '\t') || (str_buf[0] == ' '))
	{
		fgets(str_buf, 1024, fp);
		count_limit++;
		if (count_limit > 1000)
		{
			fprintf(stderr, "ERROR: Irregal file format.\n");
			exit(1);
		}
	}
	strcpy(magic_num, str_buf);
	magic_num[strlen(magic_num) - 1] = '\0'; /* \nを除く */
	if (strcmp(magic_num, "P6") != 0)
	{
		fprintf(stderr, "ERROR: magic number(%s) not match.\n", magic_num);
		exit(1);
	}

	/* 画像の幅(列数)と高さ(行数) */
	count_limit = 0;
	sprintf(str_buf, "#");
	while ((str_buf[0] == '#') || (str_buf[0] == '\n') || (str_buf[0] == '\t') || (str_buf[0] == ' '))
	{
		fgets(str_buf, 1024, fp);
		count_limit++;
		if (count_limit > 1000)
		{
			fprintf(stderr, "ERROR: Irregal file format.\n");
			exit(1);
		}
	}
	sscanf(str_buf, "%d %d", &width, &height);
	if ((height != DIM1) || (width != DIM2)) /* 画像のサイズが仮定した値と異なる場合はエラーで終了 */
	{
		fprintf(stderr, "ERROR: Dimension dosenot match.\n");
		exit(1);
	}

	/* 最大値 */
	count_limit = 0;
	sprintf(str_buf, "#");
	while ((str_buf[0] == '#') || (str_buf[0] == '\n') || (str_buf[0] == '\t') || (str_buf[0] == ' '))
	{
		fgets(str_buf, 1024, fp);
		count_limit++;
		if (count_limit > 1000)
		{
			fprintf(stderr, "ERROR: Irregal file format.\n");
			exit(1);
		}
	}
	sscanf(str_buf, "%d", &max_val);
	if (max_val > 255)
	{
		fprintf(stderr, "ERROR: Irregal max value.\n");
		exit(1);
	}

	/* 画像データをrepeat(+1)個に分割して読み込み */
	{
		int i;
		int repeat = (DIM1*DIM2*DIM3) / (R_BYTE); /* 分割数 */
		int rest = DIM1 * DIM2*DIM3 - repeat * (R_BYTE); /* 余りデータ量 */
		UCHAR *pt = (UCHAR *)&data_buf[0][0][0]; /* 読み込みデータの位置を持つポインタ */

		for (i = 0; i < repeat; i++)
		{
			fread(pt, sizeof(UCHAR), R_BYTE, fp);
			pt += R_BYTE;
		}
		if (rest > 0)
			fread(pt, sizeof(UCHAR), rest, fp);
	}

	/* ファイルを閉じる */
	fclose(fp);
}
