read NAME
cd $NAME

gcc -o client k2_client.c
gcc -o server k2_server.c

open ./server
open ./client
