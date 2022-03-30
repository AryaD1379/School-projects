#include "numtheory.h"
#include <stdlib.h>
#include "randstate.h"
void gcd(mpz_t d, mpz_t a, mpz_t b) {
    mpz_t c;
    mpz_init(c);
    mpz_set(c, b); //c = b
    while (mpz_sgn(b) != 0) {
        mpz_set(d, b);
        mpz_mod(b, a, b);
        mpz_set(a, d);
    }
    mpz_set(b, c); // b = c
}

void mod_inverse(mpz_t i, mpz_t a, mpz_t n) {
    mpz_t r, rp, t, tp, q, copy;
    mpz_inits(r, rp, t, tp, q, copy, (void *) 0);
    mpz_set(r, n);
    mpz_set(rp, a);
    mpz_set_si(t, 0);
    mpz_set_si(tp, 1);
    while (mpz_sgn(rp) != 0) {
        mpz_fdiv_q(q, r, rp);
        mpz_set(copy, r); // copy = r
        mpz_set(r, rp); // r = rp
        mpz_mul(rp, q, rp); //rp = q * rp
        mpz_sub(rp, copy, rp); // rp = copy - rp
        mpz_set(copy, t);
        mpz_set(t, tp);
        mpz_mul(tp, q, tp); // tp = q*tp
        mpz_sub(tp, copy, tp); // tp = copy - tp
    }
    if (mpz_cmp_si(r, 1) > 0) {
        mpz_set_ui(i, 0);
        return;
    }
    if (mpz_sgn(t) < 0) {
        mpz_add(t, t, n);
    }
    mpz_set(i, t);
}

void pow_mod(mpz_t out, mpz_t base, mpz_t exponent, mpz_t modulus) {
    mpz_t v, p;
    mpz_inits(v, p, (void *) 0);
    mpz_set_ui(v, 1); // v = 1
    mpz_set(p, base); // p = base
    while (mpz_sgn(exponent) > 0) {
        mpz_mod_ui(out, exponent, 2); // r = exponent % two;
        if (mpz_sgn(out) != 0) { // r != 0
            mpz_mul(v, v, p); // v = v*p
            mpz_mod(v, v, modulus);
        }
        mpz_mul(p, p, p); // p = p * p
        mpz_mod(p, p, modulus); // p = p % modulus
        mpz_fdiv_q_ui(exponent, exponent, 2);
    }
    mpz_set(out, v);
}

bool is_prime(mpz_t n, uint64_t iters) {
    if (mpz_cmp_ui(n, 1) == 0) {
        return false;
    }
    if (mpz_cmp_ui(n, 2) == 0) {
        return true;
    }
    mpz_t y, j, s, a, r, two, test;
    mpz_inits(y, j, s, a, r, two, test, (void *) 0); //  s =0
    mpz_set(r, n); // r = n
    mpz_sub_ui(r, r, 1); // r = n-1
    mpz_set_ui(two, 2);
    mpz_mod(test, n, two); //test = n % 2
    if (mpz_cmp_ui(test, 0) == 0) {
        return false;
    }
    mpz_sub_ui(n, n, 1); // n = n-1
    mpz_mul(test, two, r); // test = 2 * r
    mpz_mod_ui(a, r, 2); // a = r%2
    while (mpz_cmp(test, n) != 0 && mpz_cmp_ui(a, 0) == 0) {
        mpz_add_ui(s, s, 1); // s += 1;
        mpz_fdiv_q(r, r, two);
        if (mpz_cmp_ui(s, 1) != 0) {
            mpz_mul_ui(two, two, 2); //two *= 2
        }
        mpz_mul(test, two, r); // test = 2 ^ s * r
        mpz_mod_ui(a, r, 2); // a = r % 2

        if (!mpz_cmp_ui(a, 0) && !mpz_cmp(test, n)) {
            return false;
        }
    }
    mpz_add_ui(n, n, 1); // n = n+1;
    for (uint64_t i = 1; i <= iters; i++) {
        mpz_sub_ui(n, n, 3); // n = n-3
        mpz_urandomm(a, state, n);
        mpz_add_ui(a, a, 2); // a = a + 2
        mpz_add_ui(n, n, 3); //n-3+3 = n
        pow_mod(y, a, r, n);
        mpz_sub_ui(n, n, 1); //n = n-1
        if (mpz_cmp_ui(y, 1) != 0 && mpz_cmp(y, n) != 0) {
            mpz_set_ui(j, 1);
            mpz_sub_ui(s, s, 1); // s = s-1
            // test = n-1
            mpz_set(test, n); //test = n-1
            mpz_add_ui(n, n, 1); // n-1+1
            while (mpz_cmp(j, s) <= 0 && mpz_cmp(y, test) != 0) {
                pow_mod(y, y, two, n);
                if (mpz_cmp_ui(y, 1) == 0) {
                    return false;
                }
                mpz_add_ui(j, j, 1);
            }
            if (mpz_cmp(y, test) != 0) {
                return false;
            }
        }
    }
    return true;
}

void make_prime(mpz_t p, uint64_t bits, uint64_t iters) {
    uint64_t seed = random();
    randstate_init(seed);
    do {
        mpz_urandomb(p, state, bits);
    } while (!is_prime(p, iters));
}
