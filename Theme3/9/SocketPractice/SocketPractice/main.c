//
//  main.c
//  SocketPractice
//
//  Created by 足立賢人 on 2018/06/21.
//  Copyright © 2018年 足立賢人. All rights reserved.
//

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#define FILENAME "dic-w.txt"
#define NOT_FOUND "未登録です"
int main(int argc, const char * argv[]) {
    // insert code here...
    
    int fd1, fd2;
    struct sockaddr_in saddr;
    struct sockaddr_in caddr;
    
    int len;
    int ret;
    char buf[1024];
    
    //init dic
    FILE *fp;
    if ((fp = fopen(FILENAME, "r")) == NULL){
        perror("file");
        exit(1);
    }
    
    
    
    
    //ソケット作成
    if ((fd1 = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket");
        exit(1);
    }
    //bind
    memset((char *)&saddr, 0, sizeof(saddr));
    saddr.sin_family = AF_INET;
    saddr.sin_port = htons(12345);
    saddr.sin_addr.s_addr = INADDR_ANY;
    
    if (bind(fd1, (struct sockaddr *)&saddr, (socklen_t)sizeof(saddr))< 0) {
        perror("bind");
        exit(1);
    }
    
    //listen
    if (listen(fd1, 5)< 0) {
        perror("listen");
        exit(1);
    }
    
    //connect
    
    while( 1 ) {
        len = sizeof( caddr );
        if ( ( fd2 = (int)accept( fd1, ( struct sockaddr * )&caddr, ( socklen_t * ) &len ) ) < 0 ) {
            perror("accept");
            exit( 1 );
        }
        fprintf( stderr, "Connection established: socket %d used.\n", fd2 );
        
        while( ( ret = (int)read( fd2, buf, 1024 ) ) > 0 ) {
            rewind(fp);
            fprintf( stderr, "read: %s\n", buf );
            
            //use here for function
            char key[128];
            char dic[128];
            int flag = 1;
            while (fscanf(fp, "%s %s",key,dic) != EOF){
                
                if ( strcmp(key, buf)==0){
                    fprintf( stderr, "write: %s\n", dic );
                    write( fd2, dic, strlen( dic )+1 );
                    flag = 0;
                    break;
                }
            }
            if (flag) {
                fprintf( stderr, "write: " );
                fprintf( stderr, NOT_FOUND);
                fprintf( stderr, "\n " );
                
                write( fd2, NOT_FOUND, strlen(NOT_FOUND)+1 );
            }
            
            fsync( fd2 );
        }
        
        close( fd2 );
    }
    close(fd1);
    
    
    
    return 0;
}
