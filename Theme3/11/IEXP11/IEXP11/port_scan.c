#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#define PS_CONNECT 0   /* ポートに接続できた場合 */
#define PS_NOCONNECT 1 /* ポートに接続できなかった場合 */
#define PS_ERROR 2     /* 接続エラー */

/*!
 * @brief   タイムアウトを設定したソケットを作成する
 * @return  ソケットディスクリプタ
 * @note    受信設定をしないと正常にタイムアウトしない。（仕様？）
 */
static int
create_timeout_socket()
{
    int sock = 0;
    
    sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock < 0){
        perror("socket");
        return(-1);
    }
    
    /* 送信タイムアウトを設定する */
    struct timeval send_tv;
    send_tv.tv_sec  = 10;
    send_tv.tv_usec = 0;
    setsockopt(sock, SOL_SOCKET, SO_SNDTIMEO, &send_tv, sizeof(send_tv));
    
    /* 受信タイムアウトを設定する */
    struct timeval recv_tv;
    recv_tv.tv_sec = 10;
    recv_tv.tv_sec = 0;
    setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, &recv_tv, sizeof(recv_tv));
    
    return(sock);
}

/*!
 * @brief     接続できるか確認する
 * @param[in] sock      ソケットディスクリプタ
 * @param[in] dest      サーバ構造体
 * @param[in] dest_size サーバ構造体のサイズ
 * @return    接続状態を返す。
 */
static int
check_connect(int sock, struct sockaddr *dest, size_t dest_size)
{
    int rc = 0;
    
    errno = 0;
    rc = connect(sock, dest, dest_size);
    if(rc == 0){
        return(PS_CONNECT);
    }
    
    if(errno == ECONNREFUSED){
        return(PS_NOCONNECT);
    }
    
    if(errno != 0){
        perror("connect");
        return(PS_ERROR);
    }
    
    return(PS_NOCONNECT);
}

/*!
 * @brief     指定ポートに接続を試みる
 * @param[in] ipaddr    IPアドレス(xx.xx.xx.xx表記の文字列）
 * @param[in] port_num  ポート番号
 */
static int
connect_to_port(char *ipaddr, int n_port)
{
    struct sockaddr_in dest;
    int sock = 0;
    int rc = 0;
    
    sock = create_timeout_socket();
    if(sock < 0){
        return(PS_ERROR);
    }
    
    memset(&dest, 0, sizeof(dest));
    dest.sin_family = AF_INET;
    dest.sin_port   = htons(n_port);
    dest.sin_addr.s_addr = inet_addr(ipaddr);
    
    rc = check_connect(sock, (struct sockaddr *)&dest, sizeof(dest));
    
    close(sock);
    
    return(rc);
}

/*!
 * @brief     ポート番号に対応するサービス名を出力する
 * @param[in] port_num  ポート番号
 */
static void
print_portname(int port_num)
{
    struct servent *se = NULL;
    
    se = getservbyport(htons(port_num), "tcp");
    if(se == NULL){
        fprintf(stdout, "port=%5d, unknown\n", port_num);
    } else {
        fprintf(stdout, "port=%5d, service=%s\n", port_num, se->s_name);
    }
}

/*!
 * @brief     ポートスキャンを実行する
 * @param[in] ipaddr    IPアドレス(xx.xx.xx.xx表記の文字列）
 * @param[in] port_num  ポート番号
 */
static void
port_scan(char *ipaddr, int port_num)
{
    int rc = 0;
    printf("scan %d\n",port_num);
    rc = connect_to_port(ipaddr, port_num);
    
    /* エラー時には強制終了する */
    if(rc == PS_ERROR){
        exit(-1);
    }
    
    /* 接続できればポート情報を出力する */
    if(rc == PS_CONNECT){
        print_portname(port_num);
    }
}

/*!
 * @brief     IPアドレス表記を変換する
 * @param[in] ipaddr      IPアドレス(xx.xx.xx.xx表記の文字列）
 * @param[in] start_port  開始ポート番号
 * @param[in] end_port    終了ポート番号
 */
static void
ports_scan(char *ipaddr, int start_port, int end_port)
{
    int port_num = 0;
    
    
    for(port_num = start_port; port_num < end_port; port_num++){
        
        port_scan(ipaddr, port_num);
    }
}

/*!
 * @brief      IPアドレスの名前解決を行う。
 * @param[in]  ipaddr  IPアドレス（xx.xx.xx.xx表記の文字列）
 * @return     成功ならば0、失敗ならば-1を返す。
 */
static int
resolve_ipaddr(char *ipaddr)
{
    struct hostent *host = NULL;
    struct in_addr addr;
    
    /* 文字列表現のIPアドレスをバイナリ値に変換する */
    addr.s_addr = inet_addr(ipaddr);
    
    /* IPアドレスからホスト名を取得 */
    host = gethostbyaddr((const char *)&addr.s_addr,
                         sizeof(addr.s_addr), AF_INET);
    if(host == NULL){
        return(-1);
    }
    
    return(0);
}

/*!
 * @brief   main routine
 * @return  成功ならば0、失敗ならば-1を返す。
 */
int main(int argc, char *argv[])
{
    char adress[20] = "172.29.144.28";
    
    int rc = 0;
    
    
    
    /* 接続先を確認する */
    rc = resolve_ipaddr(adress);
    if(rc != 0){
        fprintf(stderr, "Error: Failed to connect to %s\n", adress);
        return(-1);
    }
    
    /* ポートは0番から5000番までを指定してみる */
    ports_scan(adress, 1024, 65535);
    
    return( 0 );
}

