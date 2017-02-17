/* Ilie Nicolae Vlad 311CB */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct celg {
	struct celg *urm, *pre;	 /* adresa urmatoarei celule respectiv celulei precedente*/
	void *info;				 /* adresa informatie */
} TCelulaG, *TLG , **ALG;

typedef struct {	/* perechi key value frecventa*/
	char* key;
	char* value;
	int freq;
} TCache;

typedef int (*TFHASH)(char*, int); /* functie hash */

typedef void (*TFAfi)(void*);		/* functie de afisare */

typedef struct {
	int M;						/* numarul de bucket-uri */
	TFHASH fh;					/* functia hash */
	TLG* v;						/* vectorul de liste */
} THash;

int GasesteCerinta(char *);			/* functie care codifica operatiile */

void AfiValue(void *, FILE *);			/* afiseaza informatia*/

void AfisareValori( TLG L, int, FILE *);	/* afiseaza toate valorile din lista*/

void print(THash *, FILE *);		/* afiseaza toate valorile din tabela*/

int compara( void *, void *);	/* functie de comparare a informatiei */

int cauta( TLG x, char*);	/* functie de cautare intr o lista */

int LungLista( TLG L);		/* functia care calculeaza lungimea listei */

int functieD(char*, int);	/* functia de dispersie */

THash* initTHash(int, TFHASH);		/* aloca memorie pentru tabela hash */

int Remove( THash*, char*);			/*scoatere element din tabela */

TLG alocaCel(TCache*);					/* aloca memorie pentru o celula */

int InsearareOrd(ALG, TCache*, TLG);	/* inserare ordonata in lista */

void DistrugeLista(ALG);				/* eliberare memorie lista */

void distrugeTHash(THash**);			/* eliberare memorie tabela hash */

THash* set(char*, char*, THash**, int);			/* adaugare in tabela */

int print_list(THash*, int, FILE*);			/* afiseaza valorile din lista cu indicele respectiv*/

char *get(THash*, char*);			/* intoarce campul value */

