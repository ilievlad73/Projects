/* Ilie Nicola Vlad 311 CB */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include <ctype.h>

/*- macrodefinitii - acces campuri -*/
#define VF(a)	(((AST)(a))->vf)
#define DIME(a)		(((AST)(a))->dime)

/*   STIVA   */


typedef struct celst		// structura celulei pentru stiva
{
	struct celst *urm;	/* adresa urmatoare celule */
	void* info;			/* adresa informatiei */
} TCelST, *ACelST;

typedef struct
{
	size_t dime;		/* dimensiune element */
	ACelST vf;			/* adresa celulei din varf */
} TStiva, *AST;

typedef struct 			// structura pentru switch
{
	int Id;
	char* Nume;
	char* Ip;
	char* Mod; /* s = mod stiva, i = mod individul */
} TSwitch, *TSW;

typedef struct {
	int M;			/* numarul de stive */
	AST* v;			/* vectorul de stive */
} TVSt;				// vectorul de stive


void* InitS(size_t d);		  /* initializeaza stiva cu elemente de dimensiune d; */
int Push(void* a, void* ae);  /* pune element in varful stivei */
int Pop (void* a, void* ae);  /* extrage elementul din varful stivei la adresa ae */
int Top (void* a, void* ae);  /* copiaza elementul din varful stivei la adresa ae */
int VidaS(void* a);           /* test stiva vida */
void DistrS (void** aa);      /* elibereaza intregul spatiu ocupat de stiva */
int InserareOrd(void* a, void* ae); /* insereaza ordonat in stiva */
int InserareBaza(void*a, void* ae); /* insereaza la baza stivei */
void EliminaDinStiva(void* a, int id); /* elimina element din stiva */
TSwitch* CautaInStiva(void* a, int id, int *B); /* returneaza elem daca l a gasit altel null */
TSwitch* RemoveDinStiva(void* a, int id); /* scoate elementul din stiva fara a elibera memorie */
void afiseazaStivaV2(void *a, FILE* x); /* afiseaza in fisier stiva */
int UltimulElemStiva(void* a, void* b); /* returneaza ultimul elem din stiva */
TVSt* InitTVSt(int nrel);
void DistrugeStive(TVSt* stive);
int fcmp(void* a, void* b); //functie de comparatie pentru elementele ce intra in stiva
int InserareBaza(void*a, void* ae); // insereaza un element la baza //


/* COADA */


typedef struct cel 			// structura pentru o celula de coada
{	struct cel* urm;
	void* info;
} TCel, *ACel;

typedef struct coada 		// structura pentru coada
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
int PrimQ(void* a, void* ae); /* copiaza primul element din coada la adresa ae */
int VidaQ(void* a);           /* test coada vida */
void DistrQ(void** aa);       /* elibereaza intregul spatiu ocupat de coada */
TSwitch* CautaInCoada(void*a , int id); /* returneaza elem gasit altfel NULL */
int InserareOrdCoada(void* a, void*ae); /* insereaza ordonat in coada */
TSwitch* RemoveDinCoada(void* a, int id);
/* scoate elem din coada fara a elibera spatiul de memorie */
void EliminaDinCoada(void* a, int id); /* elimina element din coada */
void afiseazaCoadaV2(void *a, FILE* x); /* afiseaza in fisier coada */

int add(TVSt* vector, int id, char* denumire, char* Ip, char* functionare, int id_stiva, char* tip, void* coada);
//adauga switch in retea
void IPMIN(TVSt* vector, void* coada, FILE* x); //scrie in fisier ip-ul minim din coada
int set(TVSt* vector, int id, char* functionare, int id_stiva, char* principal, void* coada);
//schimba modul de functionare al unui switch
unsigned int ConverIp(char* IP); //converteste de la IP
void Afisare(TVSt* vector, void* coada, FILE* x); // afiseaza toate switch-urile
int del( TVSt* vector, int id, void* coada); //scoate switch din retea
int GasesteCerinta(char *x); // codifica operatiile
int fcmpQ(void* a, void* b); // functie de comparatie pentru elementele ce intra in coada