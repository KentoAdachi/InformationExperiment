echo DCIP
gcc -Wall -O2 -DCIP -lm kadai3-1.c
/usr/bin/time -p ./a.out
echo DCSQ
gcc -Wall -O2 -DCSQ -lm kadai3-1.c
/usr/bin/time -p ./a.out