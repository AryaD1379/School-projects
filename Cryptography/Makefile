CC = clang
CFLAGS = -Wall -Wextra  -Werror  -Wpedantic
LDFLAGS = -lgmp
OBJFILES = numtheory.o rsa.o randstate.o
TARGET: encrypt decrypt keygen

all: $(TARGET)

$(TARGET): $(OBJFILES)
	$(CC) $(CFLAGS) $(OBJFILES) -o $(TARGET) $(LDFLAGS)

encrypt: encrypt.o
	$(CC) $(CFLAGS) -o $@ $^ $(OBJFILES) $(LDFLAGS)
	
encrypt.o: $(OBJFILES) encrypt.c
	$(CC) $(CFLAGS) -c encrypt.c

decrypt: decrypt.o
	$(CC) $(CFLAGS) -o $@ $^ $(OBJFILES) $(LDFLAGS)

decrypt.o: $(OBJFILES) decrypt.c
	$(CC) $(CFLAGS) -c decrypt.c 

keygen: keygen.o
	$(CC) $(CFLAGS) -o $@ $^ $(OBJFILES) $(LDFLAGS)
        
keygen.o: $(OBJFILES) keygen.c
	$(CC) $(CFLAGS) -c keygen.c

numtheory.o: numtheory.c numtheory.h
	$(CC) $(CFLAGS) -c numtheory.c

randstate.o: randstate.c randstate.h
	$(CC) $(CFLAGS) -c randstate.c
        
rsa.o: rsa.c rsa.h
	$(CC) $(CFLAGS) -c rsa.c

clean:
	rm -f $(TARGET) *.o

format:
	clang-format -i -style=file *.c *.h
