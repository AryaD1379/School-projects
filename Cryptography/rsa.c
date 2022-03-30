#include "rsa.h"
#include "numtheory.h"
#include "randstate.h"
#include <stdlib.h>
#include <stdio.h>

void rsa_make_pub(mpz_t p, mpz_t q, mpz_t n, mpz_t e, uint64_t nbits, uint64_t iters) {
    uint64_t pbits = (random() % (((2 * nbits) / 4) + 1)) + (nbits / 4);
    uint64_t qbits = nbits - pbits;
    make_prime(p, pbits, iters);
    make_prime(q, qbits, iters);
    mpz_mul(n, p, q); // n = p * q
    mpz_t phi, gd;
    mpz_inits(phi, gd, (void *) 0);
    mpz_sub_ui(phi, p, 1); // phi = p-1
    mpz_sub_ui(q, q, 1); //q = q-1
    mpz_mul(phi, phi, q); //phi = p-1 * q-1
    mpz_add_ui(q, q, 1); // q-1+1
    do {
        mpz_urandomb(e, state, nbits);
        gcd(gd, phi, e);
    } while (mpz_get_ui(gd) != 1);
}

void rsa_write_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile) {
    gmp_fprintf(pbfile, "%Zx\n", n);
    gmp_fprintf(pbfile, "%Zx\n", e);
    gmp_fprintf(pbfile, "%Zx\n", s);
    gmp_fprintf(pbfile, "%s\n", username);
}

void rsa_read_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile) {
    gmp_fscanf(pbfile, "%Zx\n%Zx\n%Zx\n%s\n", n, e, s, username);
}

void rsa_make_priv(mpz_t d, mpz_t e, mpz_t p, mpz_t q) {
    mpz_sub_ui(d, q, 1); //d = q-1
    mpz_sub_ui(p, p, 1); // p = p-1
    mpz_mul(d, p, d); // d = p-1 * q-1
    mod_inverse(d, e, d);
    mpz_add_ui(p, p, 1); // p-1+1
}

void rsa_write_priv(mpz_t n, mpz_t d, FILE *pvfile) {
    gmp_fprintf(pvfile, "%Zx\n%Zx\n", n, d);
}

void rsa_read_priv(mpz_t n, mpz_t d, FILE *pvfile) {
    gmp_fscanf(pvfile, "%Zx\n%Zx\n", n, d);
}

void rsa_encrypt(mpz_t c, mpz_t m, mpz_t e, mpz_t n) {
    pow_mod(c, m, e, n);
}

void rsa_encrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t e) {
    int k = (mpz_sizeinbase(n, 2) - 1) / 8;
    uint8_t *ptr = (uint8_t *) calloc(k, sizeof(uint8_t));
    ptr[0] = 0xFF;
    int j = 0;
    for (int i = 1; i < k; i++) {
        j = gmp_fscanf(infile, "%Zx\n", ptr[i]);
    }
    mpz_t m, c;
    mpz_inits(m, c, (void *) 0);
    mpz_import(m, k, 1, 1, 1, 0, ptr);
    rsa_encrypt(c, m, e, n);
    gmp_fprintf(outfile, "%Zu\n", m);
    free(ptr);
    ptr = NULL;
}
void rsa_decrypt(mpz_t m, mpz_t c, mpz_t d, mpz_t n) {
    pow_mod(m, c, d, n);
}
void rsa_decrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t d) {
    int k = (mpz_sizeinbase(n, 2) - 1) / 8;
    mpz_t c, m;
    mpz_inits(c, m, (void *) 0);
    uint8_t *ptr = (uint8_t *) calloc(k, sizeof(uint8_t));
    size_t *j = 0;
    while (gmp_fscanf(infile, "%Zu\n", c) != EOF) {
        j++;
        rsa_decrypt(m, c, d, n);
        mpz_export(ptr, j, 1, 1, 1, 0, m);
        gmp_printf("%Zu\n%Zu\n%Zu\n", c, m, d);
    }
    for (size_t i = 1; i < *j; i++) {
        gmp_fprintf(outfile, "%d\n", ptr[i]);
    }
}
void rsa_sign(mpz_t s, mpz_t m, mpz_t d, mpz_t n) {
    pow_mod(s, m, d, n);
}
bool rsa_verify(mpz_t m, mpz_t s, mpz_t e, mpz_t n) {
    mpz_t t;
    mpz_init(t);
    pow_mod(t, s, e, n);
    if (mpz_cmp(t, m) == 0) {
        return true;
    }
    return false;
}
