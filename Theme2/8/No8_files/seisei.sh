
for r in `seq 0 127`    
do
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"][0] + data[g]["$r"][1] + data[g]["$r"][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+1][0] + data[g]["$r"+1][1] + data[g]["$((r+1))"][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+2][0] + data[g]["$r"+2][1] + data[g]["$r"+2][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+3][0] + data[g]["$r"+3][1] + data[g]["$r"+3][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+4][0] + data[g]["$r"+4][1] + data[g]["$r"+4][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+5][0] + data[g]["$r"+5][1] + data[g]["$r"+5][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+6][0] + data[g]["$r"+6][1] + data[g]["$r"+6][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+7][0] + data[g]["$r"+7][1] + data[g]["$r"+7][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+8][0] + data[g]["$r"+8][1] + data[g]["$r"+8][2])*2+3)/6);"
    echo "gray[g][r] = (UCHAR)(((data[g]["$r"+9][0] + data[g]["$r"+9][1] + data[g]["$r"+9][2])*2+3)/6);"


done
