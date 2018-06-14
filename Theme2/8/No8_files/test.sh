gcc -Wall -O2 img_proc.c
time ./a.out hana3.ppm 5
cmp hana3_res.ppm hana3_res_ans.ppm