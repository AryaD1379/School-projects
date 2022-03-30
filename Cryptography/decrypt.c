#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "rsa.h"
#define OPTIONS "i:o:n:vh"

void printHelp() {
}
int main(int argc, char **argv) {
    int opt = 0;
    FILE *in;
    FILE *out;
    FILE *priKey;
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
            priKey = fopen(optarg, "r");
            if (priKey == NULL) {
                printf("ERROR");
                break;
            }
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
        priKey = fopen("rsa.priv", "r");
        if (priKey == NULL) {
            printf("ERROR");
        }
    }
    mpz_t n, d;
    mpz_inits(n, d, (void *) 0);
    rsa_read_priv(n, d, priKey);
    if (verbose) {
        gmp_printf("n (%lu bits) = %Zu\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("d (%lu bits) = %Zu\n", mpz_sizeinbase(d, 2), d);
    }
    rsa_decrypt_file(in, out, n, d);
    fclose(in);
    fclose(out);
    fclose(priKey);
    mpz_clears(n, d, (void *) 0);
}
