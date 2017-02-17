/* Ilie Nicolae Vlad 311CB */
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>

typedef struct nod {
	char info, codificare;		// info - 0 pentru info vida si > 0 in rest
	struct nod *st, *frate;		// legaturi spre fiul din stanga si fratele sau
} TNod, *TArb, **AArb;


TArb InitA() ;

TArb Constr(char info, char codificare); 	// construieste un nod cu info si codificatie
void AddCodificare(TArb *a, char *codificare, char info, int lungime); 	// adauga codificatie
void AfisareArbore( TArb a );
void AfiseazaArbore(TArb a, FILE* x);	// afisare arbore
int Info(TArb a);		// verifica daca pe ramura respectiva este info
void del(char info, AArb a);		//elimina informatia
char CautaInfo(TArb a, char* codificare, int LiteraCurenta);	//returneaz informatia corespunzatoare codificarii altfel 0
void DistrArb(TArb *a);		//distruge arborele

typedef struct cel 			// structura pentru o celula de coada
{	struct cel* urm;
	void* info;
} TCel, *ACel;


typedef struct 		// structura pentru coada
{
	size_t dime;
	ACel ic, sc; // adresa prima si ultima din coada
} TCoada, *AQ;
/*- macrodefinitii - acces campuri -*/
#define IC(a) 	(((AQ)(a))->ic)
#define SC(a) 	(((AQ)(a))->sc)
#define DIMEQ(a) (((AQ)(a))->dime)

void* InitQ(size_t d); /* creeaza coada vida cu elemente de dimensiune d;
							  anumite implementari pot necesita si alti parametri */
int IntrQ(void* a, void* ae); /* adauga element la sfarsitul cozii */
int ExtrQ(void* a, void* ae); /* extrage primul element din coada la adresa ae */
int VidaQ( void *a)	;			// verificare coada vida
void DistrQ(void** a);				// distrugere coada
