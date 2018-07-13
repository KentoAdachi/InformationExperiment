
//  課題1クライアント
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

int portnum;

int main(int argc, const char * argv[]) {
    // insert code here...
//    portnum = 34493;
    portnum = 34493;
    //STAT :::状態番号:正解数:不正解数:ログイン回数
    
        
    struct sockaddr_in saddr;
    int soc;
    char buf[1024];
    //socket
    if ((soc = socket(AF_INET, SOCK_STREAM, 0))<0) {
        perror("socket");
        exit(1);
    }
    //connect
    memset((char *)&saddr, 0, sizeof(saddr));
    saddr.sin_family = AF_INET;
    saddr.sin_port = htons(portnum);
//    saddr.sin_addr.s_addr = inet_addr("172.29.144.27");//localhost
    saddr.sin_addr.s_addr = inet_addr("172.29.144.27");
    
    if (connect(soc, (struct sockaddr *)&saddr, (socklen_t)sizeof(saddr))< 0) {
        perror("connect");
//        exit(1);
        
    }
    fprintf( stderr, "Connection established: socket %d used.\n", soc );
        printf("%d\n",portnum);
    while( fgets( buf, 1024, stdin ) ) {
        if ( buf[strlen(buf)-1] == '\n' ) buf[strlen(buf)-1] = '\0';
        write( soc, buf, strlen( buf )+1 );
        fsync( soc );
        read( soc, buf, 1024 );
        fprintf( stdout, "%s\n", buf );
    }
    
    close( soc );
    
    
    printf("Hello, World!\n");
    return 0;
}
