
//  クイズクライアント
//  Created by 足立賢人 on 2018/06/21.
//  Copyright © 2018年 足立賢人. All rights reserved.
//
//

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>

//#define DEB

#define USER_STATE 0
#define PASS_STATE 1
#define QUIZ_STATE 2
#define ANSR_STATE 3
#define GETM_STATE 4
int soc_;
int state_ = -1;
int correct_ = 0;
int count_ = 0;
char buf_[1024];

int init_connection(void);
int send_message(char *);
int get_message(void);
int get_stat(void);
int user_state(void);
int pass_state(void);
int quiz_state(void);
int ansr_state(void);
int getm_state(void);
int (*next_state_)(void);




int main(int argc, const char * argv[]) {
    
    init_connection();
    printf("老人「ようこそ若き勇者よ」\n老人「疲れた時は\"QUIT\"と入力すればいつでも終了できるぞ」\n");
    next_state_ = user_state;
    while (state_ < 4) {
        (*next_state_)();
        get_stat();
        //break;
    }
    printf("\n張り紙の番号に電話をかけてみた\nプルルル...\n...ガチャ\n");
    printf("老人『お疲れ様、宝はそう！ここまで登ってくる過程で君の頭の中に蓄積さr』\n");
    printf("投げ捨てられた受話器は風切り音をたてながら雲海に吸い込まれていった\n");
    printf("\nゲームクリアおめでとう！\n通信を終了します\n");
    send_message("QUIT");
    close( soc_ );
    
//    printf("end\n");
    return 0;
}


//コネクションの確立
// 原則として次に入りたい状態を返す
int init_connection(){
    // insert code here...
    struct sockaddr_in saddr;
    
    //socket
    if ((soc_ = socket(AF_INET, SOCK_STREAM, 0))<0) {
        perror("socket");
        exit(1);
    }
    //connect
    memset((char *)&saddr, 0, sizeof(saddr));
    saddr.sin_family = AF_INET;
    saddr.sin_port = htons(34401);//本番用34401 ログ確認用10000
    saddr.sin_addr.s_addr = inet_addr("172.29.144.27");//localhost
    //172.29.144.26
    //172.29.144.100 ログ確認用
    
    if (connect(soc_, (struct sockaddr *)&saddr, (socklen_t)sizeof(saddr))< 0) {
        perror("connect");
        exit(1);
    }
    fprintf( stderr, "Connection established: socket %d used.\n", soc_ );
    return 0;
}
//メッセージをバッファに受け取る
int get_message(){
    read( soc_, buf_, 1024 );
#ifdef DEB
    printf(">%s\n",buf_);
#endif
    return 0;
}
//メッセージの書き込み
int send_message(char *message){
    if (strstr(message, "QUIT")) {
        write(soc_, "QUIT", strlen("QUIT")+1);
        return 0;
    }
#ifdef DEB
    printf("<%s\n",message);
#endif
    write( soc_,message, strlen(message)+1 );
    fsync( soc_ );//同期
    return 0;
}

int print_state(){
#ifdef DEB
    fprintf(stderr, "state: %d\n",state_);
#endif
    return state_;
}
int get_stat(){
    send_message("STAT");
    get_message();
    //printf("%s\n",buf_);
    return 0;
}

//ユーザ名受付状態
int user_state(){
    char message[1024];
    //state message
    state_ = USER_STATE;
    print_state();
    //input
    printf("名前を入力してください : ");
    fgets( buf_, 1024, stdin );
    if ( buf_[strlen(buf_)-1] == '\n' ) buf_[strlen(buf_)-1] = '\0';
    sprintf(message, "USER %s",buf_);
    send_message(message);
    get_message();
    if (strcmp(buf_, "OK") == 0) {
        next_state_ = pass_state;
    }else{
        printf("そのようなユーザーは存在しません\n");
    }
    //return
    
    return 0;
}
//パスワード

int pass_state(){
    char message[1024];
    //state message
    state_ = PASS_STATE;
    print_state();
    //input
    printf("パスワードを入力してください : ");
    fgets( buf_, 1024, stdin );
    if ( buf_[strlen(buf_)-1] == '\n' ) buf_[strlen(buf_)-1] = '\0';
    sprintf(message, "PASS %s",buf_);
    send_message(message);
    get_message();
    if (strcmp(buf_, "OK") == 0) {
        next_state_ = quiz_state;
        printf("認証成功!\n老人「では張り切って行ってみよう」\n");
    }else{
        printf("認証失敗\n");
        next_state_ = user_state;
    }
    //return
    
    return 0;
}
//クイズ出題を受け付ける状態
int quiz_state(){
    char message[1024];
    //state message
    state_ = QUIZ_STATE;
    print_state();
    //input
    printf("\n第%d問\n",++count_);
    sprintf(message, "QUIZ %d",correct_);
    send_message(message);
    get_message();
    if (strcmp(buf_, "NG\n") == 0) {
        printf("やり直し\n");
        state_ = 5;
    }else{
        next_state_ = ansr_state;
        printf("%s\n",buf_);
        
    }
    //return
    
    return 0;
}
//回答を受け付ける状態
int ansr_state(){
    char message[1024];
    //state message
    state_ = ANSR_STATE;
    print_state();
    //input
    printf("回答 : ");
    fgets( buf_, 1024, stdin );
    if ( buf_[strlen(buf_)-1] == '\n' ) buf_[strlen(buf_)-1] = '\0';
    sprintf(message, "ANSR %s",buf_);
    send_message(message);
    get_message();
    next_state_ = quiz_state;
    if (strcmp(buf_, "OK") == 0) {
        printf("正解です\n");
        correct_++;
        if (correct_ >= 5) {
            next_state_ = getm_state;
        }
    }else{
        printf("不正解です\n");
    }
    //return
    printf("現在の正解数 : %d\n",correct_);
    switch (correct_) {
        case 0:
            printf("塔は雲の上へと続いている\n");
            break;
        case 1:
            printf("軽快に螺旋階段を駆け上がる音が響く\n");
            break;
        case 2:
            printf("喉が乾いてきた\n");
            break;
        case 3:
            printf("空気が薄くなってきた\n");
            break;
        case 4:
            printf("終わりが見えてきた\n");
            break;
        case 5:
            printf("塔の屋上には1枚の張り紙があった\n");
        default:
            break;
    }
    
    return 0;
}
//秘密のメッセージ送信を受け付ける状態
int getm_state(){
    char message[1024];
    strcpy(message, "GET MESSAGE");
    state_ = GETM_STATE;
    print_state();
    
    printf("秘密のメッセージを受け取ります\n");
    send_message(message);
    get_message();
    if (strcmp(buf_, "NG") == 0) {
        printf("メッセージの受け取りに失敗しました\n");
    }else if(strcmp(buf_, "ERROR") == 0){
        printf("不正な入力を行いました\n");
    }else{
        printf("秘密のメッセージは %s です\n",buf_);
        
    }
    return 0;
}

