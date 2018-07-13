
//  課題1サーバー
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

int main(int argc, const char * argv[]) {
    // insert code here...
    int i;
    int fd1, fd2;
    struct sockaddr_in saddr;
    struct sockaddr_in caddr;
    
    int len;
    int ret;
    char buf[1024];
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
        if ( ( fd2 = accept( fd1, ( struct sockaddr * )&caddr, ( socklen_t * ) &len ) ) < 0 ) {
            perror( "accept" );
            exit( 1 );
        }
        fprintf( stderr, "Connection established: socket %d used.\n", fd2 );
        
        while( ( ret = read( fd2, buf, 1024 ) ) > 0 ) {
            fprintf( stderr, "read: %s\n", buf );
            for ( i=0; i<ret; i++ )
                if ( isupper( buf[i] ) )
                    buf[i] = tolower(buf[i]);
            
                else if (islower(buf[i]))
                    buf[i] = toupper( buf[i] );
            
            
            fprintf( stderr, "write: %s\n", buf );
            write( fd2, buf, strlen( buf )+1 );
            fsync( fd2 );
        }
        
        close( fd2 );
    }
    close(fd1);
    
    
    printf("Hello, World!\n");
    return 0;
}
