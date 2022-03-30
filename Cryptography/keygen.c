#include "numtheory.h"
#include <unistd.h>
#include "rsa.h"
#include "randstate.h"
#define OPTIONS "b:i:n:d:s:vh"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/stat.h>
#include <string.h>
void printHelp() {
    printf("SYNOPSIS\n   Generates an RSA public/private key pair.\n\n");
    printf("USAGE\n   ./keygen [-hv] [-b bits] -n pbfile -d pvfile\n\n");
    printf("OPTIONS\n   -h              Display program help and usage.\n");
    printf("   -v              Display verbose program output.\n");
    printf("   -b bits         Minimum bits needed for public key n (default: 256).\n");
    printf("   -i confidence   Miller-Rabin iterations for testing primes (default: 50).\n");
    printf("   -n pbfile       Public key file (default: rsa.pub).\n");
    printf("   -d pvfile       Private key file (default: rsa.priv).\n");
    printf("   -s seed         Random seed for testing.\n");
}
int main(int argc, char **argv) {
    int opt = 0;
    FILE *pub;
    FILE *priv;
    int openPub = 0;
    int openPri = 0;
    int verbose = 0;
    uint64_t nbits = 256;
    uint64_t iters = 50;
    uint64_t seed = time(NULL);
    while ((opt = getopt(argc, argv, OPTIONS)) != -1) {
        switch (opt) {
        case 'b': nbits = atoi(optarg); break;
        case 'i': iters = atoi(optarg); break;
        case 'n':
            pub = fopen(optarg, "w+");
            openPub = 1;
            break;
        case 'd':
            priv = fopen(optarg, "w+");
            openPri = 1;
            break;
        case 's': seed = atoi(optarg); break;
        case 'v': verbose = 1; break;
        case 'h': printHelp(); break;
        }
    }
    if (!openPub) {
        pub = fopen("rsa.pub", "w+");
    }
    if (!openPri) {
        priv = fopen("rsa.priv", "w+");
    }
    if (fchmod(fileno(priv), S_IRUSR | S_IWUSR) < 0) {
        printHelp();
    }
    randstate_init(seed);
    mpz_t p, q, n, e, d, user, s, copyd;
    mpz_inits(p, q, n, e, d, user, s, copyd, (void *) 0);
    rsa_make_pub(p, q, n, e, nbits, iters);
    rsa_make_priv(d, e, p, q);
    mpz_set(copyd, d);
    char *username = getenv("USERNAME");
    mpz_set_str(user, username, 62);
    rsa_sign(s, user, d, n);
    mpz_set(d, copyd);
    rsa_write_pub(n, e, s, username, pub);
    rsa_write_priv(n, d, priv);
    if (verbose) {
        printf("user = %s\n", username);
        gmp_printf("s (%lu bits) = %Zu\n", mpz_sizeinbase(s, 2), s);
        gmp_printf("p (%lu bits) = %Zu\n", mpz_sizeinbase(p, 2), p);
        gmp_printf("q (%lu bits) = %Zu\n", mpz_sizeinbase(q, 2), q);
        gmp_printf("n (%lu bits) = %Zu\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("e (%lu bits) = %Zu\n", mpz_sizeinbase(e, 2), e);
        gmp_printf("d (%lu bits) = %Zu\n", mpz_sizeinbase(d, 2), d);
    }
}
