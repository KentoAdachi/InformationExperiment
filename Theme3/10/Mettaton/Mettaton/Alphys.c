
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

#define DEB

#define USER_STATE 0
#define PASS_STATE 1
#define QUIZ_STATE 2
#define ANSR_STATE 3
#define GETM_STATE 4
#define USER_ID "bp16001"
#define PASSWORD "10061pb"
#define FILENAME "resource/uniq.txt"

int soc_;
int state_ = -1;
int correct_ = 0;
int count_ = 0;
char buf_[1024];
int ans=1;
int N = 0;
FILE *fp;

char quiz[1024];
char qlist[1024][1024];
int alist[1024];


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


int init_quiz_list(){
    char buf[1024];
    int a;
    
    while (fscanf(fp, "%[^,],%d",buf,&a) != EOF) {
        
        strcpy(qlist[N],buf);
        alist[N] = a;
        N++;
        
    }
    printf("init ok\n");
    return 0;
}


int main(int argc, const char * argv[]) {
    
    
    
    
    ans = 0;
    
    fp = fopen(FILENAME, "r");
    if (fp == NULL) {
        exit(1);
    }
    correct_ = 0;
    count_ = 0;
    state_ =USER_STATE;
    init_connection();
    next_state_ = user_state;
    
    
    init_quiz_list();
    
    while (state_ < GETM_STATE && count_ <= 200) {
        (*next_state_)();
        //            get_stat();
    }
    send_message("QUIT");
    close( soc_ );
    printf("close\n");
    fclose(fp);
    
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
    saddr.sin_addr.s_addr = inet_addr("172.29.144.26");//localhost
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
    sprintf(message, "USER %s",USER_ID);
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
    sprintf(message, "PASS %s",PASSWORD);
    send_message(message);
    get_message();
    if (strcmp(buf_, "OK") == 0) {
        next_state_ = quiz_state;
        printf("認証成功!\n");
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
    printf("第%d問\n",++count_);
    sprintf(message, "QUIZ %d",correct_);
    send_message(message);
    //get_message();
    read(soc_, quiz, 1024);
#ifdef DEB
    printf(">%s\n",quiz);
#endif
    
    if (strcmp(quiz, "NG\n") == 0) {
        printf("やり直し\n");
    }else{
        next_state_ = ansr_state;
    }
    //return
    
    return 0;
}
//回答を生成する
int generate_answer(){
    int i;
    for (i = 0; i < N; i++) {
//        printf("%s %s\n",quiz,qlist[i]);
        if (strstr(qlist[i], quiz) != NULL) {
            printf("hit\n");
            return alist[i];
        }
    }
    return 1;
}
//回答を受け付ける状態
int ansr_state(){
    char message[1024];
    //state message
    state_ = ANSR_STATE;
    print_state();
    //input
    
    sprintf(message, "ANSR %d",generate_answer());
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
        //        printf("不正解です\n");
    }
    //return
    //    printf("現在の正解数 : %d\n",correct_);
    
    return 0;
}
//秘密のメッセージ送信を受け付ける状態
int getm_state(){
    state_ = GETM_STATE;
    print_state();
    
    printf("秘密のメッセージを受け取ります\n");
    send_message("GET MESSAGE");
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
//csvを元に回答を作成する
int search_answer(char *quiz){
    
    
    return 0;
}
//認証と回答を自動化する
//回答を蓄積する

