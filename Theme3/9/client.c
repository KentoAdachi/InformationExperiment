#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>


#define SOCK_NAME "./socket"

int main()
{
  struct sockaddr_un saddr;
  int soc;
  char buf[1024];

  if ( ( soc = socket( AF_UNIX, SOCK_STREAM, 0 ) ) < 0 ) {
    perror( "socket" );
    exit( 1 );
  }

  memset( (char *)&saddr, 0, sizeof( saddr ) );
  saddr.sun_family = AF_UNIX;
  strcpy( saddr.sun_path, SOCK_NAME );

  if ( connect( soc, ( struct sockaddr * )&saddr, ( socklen_t )sizeof( saddr ) ) < 0 ) {
    perror( "connect" );
    exit( 1 );
  }
  fprintf( stderr, "Connection established: socket %d used.\n", soc );

  while( fgets( buf, 1024, stdin ) ) {
    if ( buf[strlen(buf)-1] == '\n' ) buf[strlen(buf)-1] = '\0';
    write( soc, buf, strlen( buf )+1 );
    fsync( soc );
    read( soc, buf, 1024 );
    fprintf( stdout, "%s\n", buf );
  }

  close( soc );

  return 0;
}
