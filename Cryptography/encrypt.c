#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "rsa.h"
#define OPTIONS "i:o:n:vh"

void printHelp() {
    printf("SYNOPSIS\n   Encrypts data using RSA encryption.\n   Encrypted data is decrypted by "
           "the decrypt program.\n\n");
    printf("USAGE\n   ./encrypt [-hv] [-i infile] [-o outfile] -n pubkey\n\n");
    printf("OPTIONS\n   -h              Display program help and usage.\n");
    printf("   -h              Display program help and usage.\n");
    printf("   -v              Display verbose program output.\n");
    printf("   -i infile       Input file of data to encrypt (default: stdin).\n");
    printf("   -o outfile      Output file for encrypted data (default: stdout).\n");
    printf("   -n pbfile       Public key file (default: rsa.pub).");
}

int main(int argc, char **argv) {
    int opt = 0;
    FILE *in;
    FILE *out;
    FILE *pubKey;
    int openin = 0;
    int openout = 0;
    int opKey = 0;
    int verbose = 0;
    while ((opt = getopt(argc, argv, OPTIONS)) != -1) {
        switch (opt) {
        case 'i':
            in = fopen(optarg, "w+");
            openin = 1;
            break;
        case 'o':
            out = fopen(optarg, "w+");
            openout = 1;
            break;
        case 'n':
            pubKey = fopen(optarg, "r");
            opKey = 1;
            break;
        case 'v': verbose = 1; break;
        case 'h': printHelp(); break;
        }
    }
    if (!openin) {
        in = stdin;
    }
    if (!openout) {
        out = stdout;
    }
    if (!opKey) {
        pubKey = fopen("rsa.pub", "r");
    }
    char username[100];
    mpz_t n, e, s, user;
    mpz_inits(n, e, s, user, (void *) 0);
    rsa_read_pub(n, e, s, username, pubKey);
    mpz_set_str(user, username, 62);
    if (rsa_verify(user, s, e, n)) {
        printf("error");
    }
    if (verbose) {
        printf("user = %s\n", username);
        gmp_printf("s (%lu bits) = %Zu\n", mpz_sizeinbase(s, 2), s);
        gmp_printf("n (%lu bits) = %Zu\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("e (%lu bits) = %Zu\n", mpz_sizeinbase(e, 2), e);
    }
    rsa_encrypt_file(in, out, n, e);
    mpz_clears(n, e, s, user, (void *) 0);
}
