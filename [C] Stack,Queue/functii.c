/* Ilie Nicola Vlad 311 CB */
#include "Biblioteca.h"

int fcmpQ(void* a, void* b)		// functia de comparare pentru coada
{
	TSwitch x = *(TSwitch*)a;
	TSwitch y = *(TSwitch*)b;
	return ConverIp(x.Ip) - ConverIp(y.Ip);
}

int fcmp(void* a, void* b)		// functia de comparare pentru stiva
{
	return (*(TSwitch*)a).Id - (*(TSwitch*)b).Id;
}

int GasesteCerinta(char *x) // codificarea cerintelor
{
	if ( !strcmp(x, "add") ) return 1;

	if ( !strcmp(x, "show") ) return 2;

	if ( !strcmp(x, "del") ) return 3;

	if ( !strcmp(x, "ipmin") ) return 4;

	if ( !strcmp(x, "set") ) return 5;

	return 0; // operatie nepremisa
}

void Afisare(TVSt* vector, void* coada, FILE* x)
{
	afiseazaCoadaV2(coada, x); 		// se afiseaza coada
	int n = vector->M, i;
	for (i = 0; i < n; i++) {			// se parcurg stivele si se afiseaza fiecare in parte
		fprintf(x, "%d:\n", i);
		afiseazaStivaV2(vector->v[i], x);

	}
	fprintf(x, "\n");
}

void IPMIN(TVSt* vector, void* coada, FILE* x)
{
	if ( VidaQ(coada) ) {
		fprintf(x, "ipmin=0\n");		// verificare coada vida
		return;
	}
	ACel aux = IC(coada);
	unsigned int a = ConverIp(((TSwitch*)(aux->info))->Ip);			// se converteste ip-ul si se afiseaza in fisier
	fprintf(x, "ipmin=%u\n", a);
}

int set(TVSt* vector, int id, char* functionare, int id_stiva, char* principal, void* coada)
{
	TSwitch* c = CautaInCoada(coada, id);	// se cauta elementul in coada
	if (c->Id != -1) {
		if ( !strcmp("SINGLE", functionare) ) {
			free(c);	// daca se gaseste in coada si modul de functionare este SINGLE nu se intampla nimic
			return 0;
		}
		else { // trebuie pus in stive
			RemoveDinCoada(coada, id); // am scos din coada switch ul
			add(vector, id, c->Nume, c->Ip, "STACK", id_stiva, principal, coada);	// se adauga switch-ul
			free(c->Nume);		// eliberare campuri
			free(c->Mod);
			free(c->Ip);
			free(c);
			return 0;
		}
	}
	free(c);
	int m = vector->M, i, stiva_curenta = -1; int base = 0; // base ne spune daca switchul este la baza sau nu
	TSwitch* d;
	for (i = 0; i < m; i++) {
		d = CautaInStiva(vector->v[i], id, &base); // se cauta in vectorul de stive switch-ul
		if (d == NULL)
			continue;		// daca nu gasim se trece la stiva urmatoare
		if (d->Id != -1) {
			stiva_curenta = i; break; // se gaseste si se opreste din cautare id-ul fiind unic
		}
	}
	if (d->Id != -1) { // a gasit ceva in stiva
		if ( !strcmp(functionare, "STACK") ) {
			int k = d->Id;
			RemoveDinStiva((void*)vector->v[stiva_curenta], k); 	// se scoate elementul din stiva
			add(vector, id, d->Nume, d->Ip, "STACK", id_stiva, principal, coada);		//se adauga in stiva coresp
			free(d->Nume);		// eliberare campuri
			free(d->Mod);
			free(d->Ip);
			free(d);
			return 0;
		}
		if ( !strcmp(functionare, "SINGLE") ) {
			RemoveDinStiva((void*)vector->v[stiva_curenta], id);			// se scoate elementul din stiva
			InserareOrdCoada(coada, d);			// se adauga elementul in coada
			free(d); 	// eliberare memorie
			return 0;
		}

	}
	else {
		free(d);
	}
	return 0;
}

int del( TVSt* vector, int id, void* coada)
{
	int n = vector->M, i; // n = nr de stive
	for (i = 0; i < n; i++) {
		EliminaDinStiva((void*)vector->v[i], id);
		// se scoate switch ul din stive si se elibereaza memoria
	}
	EliminaDinCoada(coada, id);		// se scoate switch ul din coada si se elibereaza memoria
	return 0;
}

int add(TVSt* vector, int id, char* denumire, char* Ip, char* functionare, int id_stiva, char* principal, void* coada)
{
	TSwitch* ae = (TSwitch*)malloc(sizeof(TSwitch));	// switch ul care va fi adaugat
	ae->Id = id;
	ae->Nume = strdup(denumire);			// alocare si scriere campuri
	ae->Ip = strdup(Ip);
	ae->Mod = strdup(functionare);

	if ( VidaS((void*)vector->v[id_stiva]) && !strcmp("STACK", functionare) ) {
		Push((void*)vector->v[id_stiva], ae);			// daca se pune in stiva vida = Push
		free(ae);		// eliberare
		return 1;
	}

	if ( !strcmp("BASE", principal) && !strcmp("STACK", functionare) ) {		// elementul trebuie inserat la baza
		void *baza = (TSwitch*)malloc(sizeof(TSwitch));			// variabila auxiliara
		UltimulElemStiva((void*)vector->v[id_stiva], baza);		//se scoate utlimul element din stiva aferenta
		InserareBaza((void*)vector->v[id_stiva], ae);			// insereaza la baza switch-ul
		InserareOrd((void*)vector->v[id_stiva], baza);			// se insereaza ordonat switch ul scos
		free(baza);		// eliberare memorie
		free(ae);
		return 1;
	}
	if ( !strcmp("NON_BASE", principal) && !strcmp("STACK", functionare) ) {
		InserareOrd((void*)vector->v[id_stiva], ae);		// daca nu trebuie inserat la baza se insereaza ordonat
		free(ae);
		return 1;
	}

	if ( !strcmp("SINGLE", functionare) ) {		// inserare in coada
		InserareOrdCoada(coada, ae);
		free(ae);
		return 1;
	}
	free(ae->Nume);		// eliberare campuri
	free(ae->Ip);
	free(ae->Mod);
	free(ae);			// eliberare
	return 1;

}

TVSt* InitTVSt(int nrel)		/* initializare tabela stive */
{
	TVSt* td;
	int i;
	td = (TVSt*)malloc(sizeof(TVSt));		/* alocare spatiu pentru structura */

	if (!td)	return NULL;		/* verificare */

	td->v = (AST*)calloc(nrel, sizeof(AST));	/* alocare stive */

	if (!td->v)		/* verificare */
	{
		free(td);
		return NULL;
	}

	td->M = nrel;	/* completare campuri tabela stive */
	for (i = 0; i < nrel; i++) {
		td->v[i] = InitS(sizeof(TSwitch));
	}
	return td;
}

unsigned int ConverIp(char* IP)		// functia de conversie IP
{
	unsigned int S = 0, s;
	char* p;
	char* sir = strdup(IP);
	p = strtok(sir, ".");
	int x = 256 * 256 * 256;
	while (p != NULL) {
		s = atoi(p);
		S += s * x;
		x = x / 256;
		p = strtok(NULL, ".");			// se sparge sirul
	}
	free(sir);		// eliberare memorie
	return S;
}