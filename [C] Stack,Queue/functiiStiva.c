/* Ilie Nicolae Vlad */
/* functiiStiva-L.c -- elementele stivei sunt memorate intr o lista */
#include "Biblioteca.h"

void* InitS(size_t d)
{
	AST a = (AST)malloc(sizeof(TStiva));  // initializare stiva
	a->dime = d;
	VF(a) = NULL;
	return (void*)a;
}

int Push(void* a, void *ae)
{
	ACelST aux;
	aux = (ACelST)calloc(1, sizeof(TCelST));		/* inserare in vf stiva */
	aux->info = (TSwitch *)malloc(sizeof(TSwitch));
	memcpy(aux->info, ae, DIME(a));
	aux->urm = VF(a);					/* legam de varf */
	VF(a) = aux;						/* actualizam varful */

	return 1;
}

int Pop( void *a, void * ae)
{
	if ( VidaS(a) )
		return 0;
	memcpy(ae, VF(a)->info, DIME(a));		// copiere informatie
	ACelST aux = VF(a);
	VF(a) = VF(a)->urm;			// actualizare varf
	free(aux->info);			//eliberare memorie
	free(aux);
	return 1;
}

int InserareBaza(void*a, void* ae)
{
	void* aux = InitS(sizeof(TSwitch)); //stiva auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch)); //variabila auxiliara
	while ( !VidaS(a) ) { // se muta tot din stiva principala in stiva auxiliar
		Pop(a, b);
		Push(aux, b);
	}
	Push(a, ae); // se pune elementul la baza
	while ( !VidaS(aux) ) {
		Pop(aux, b); // se muta toate elementele la loc
		Push(a, b);
	}
	free(b); // eliberare memorie
	DistrS(&aux);
	return 1;
}

int InserareOrd(void* a, void* ae) /* se pastreaza baza */
{
	void* aux = InitS(sizeof(TSwitch));			//stiva auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));		//variabila auxiliara
	while ( VF(a)->urm != NULL ) {
		Top(a, b);					// se extrage informatia din varf
		if ( fcmp(ae, b) > 0 ) {		// se pun toate elementele in stiva auxiliar
			Pop(a, b);
			Push(aux, b);
		}
		else break;
	}
	Push(a, ae);	// se intrduce elementul
	while ( !VidaS(aux) ) {
		Pop(aux, b);
		Push(a, b);  // se muta tot in stiva principala
	}
	free(b);		// eliberare memorie
	DistrS(&aux);
	return 1;
}

int Top( void *a, void * ae)
{
	if ( VidaS(a) )
		return 0;
	memcpy(ae, VF(a)->info, DIME(a)); // se copiaza informatia
	return 1;
}

int VidaS( void *a) //verificare stiva vida
{
	if ( VF(a) == NULL)
		return 1;
	return 0;
}

int UltimulElemStiva(void* a, void* b)
{
	if ( VidaS(a) )
		return 0;
	void *aux = InitS(sizeof(TSwitch));  //stiva auxiliar
	void *ae = (TSwitch*)malloc(sizeof(TSwitch)); // celula auxiliara
	while ( !VidaS(a) ) {
		Pop(a, ae);
		Push(aux, ae); //se pun toate elemntele in stiva auxiliara
	}
	Pop(aux, b); // se extrage primul element din stiva auxiliara
	while ( !VidaS(aux) ) {
		Pop(aux, ae);
		Push(a, ae); 	//se pune tot in stiva principala
	}
	free(ae);	// eliberare memorie
	DistrS(&aux);
	return 1;
}

void EliminaDinStiva(void* a, int id) //eliminare din stiva dupa id
{
	void* aux = InitS(sizeof(TSwitch));  	//stiva auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));	// variabila auxiliara
	TSwitch* c = (TSwitch*)calloc(1, sizeof(TSwitch));	//elemntul care trebuie sters
	c->Id = -1;	//id nepermis
	while ( !VidaS(a) ) {
		Top(a, b);		//se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) { 		//se compara
			Pop(a, (void*)c);		// se scoate daca este cazul
			break;
		}
		else {
			Pop(a, b);	// altfel se pune totul in stiva auxiliara
			Push(aux, b);
		}
	}
	while ( !VidaS(aux) ) {
		Pop(aux, b);	//se muta tot in stiva princpiala la loc
		Push(a, b);
	}

	if (c->Id == -1) { 	// daca nu a fost gasit, elib memorie
		free(c);
		free(b);
		DistrS(&aux);
		return ;
	}
	free(c->Nume); // daca a fost gasit, eliberare memorie
	free(c->Ip);
	free(c->Mod);
	free(c);
	free(b);
	DistrS(&aux);
	return ;
}

TSwitch* RemoveDinStiva(void* a, int id) // se scoate elemntul din stiva fara a elibera memoria lui
{
	void* aux = InitS(sizeof(TSwitch));	//stiva auxilara
	void* b = (TSwitch*)malloc(sizeof(TSwitch)); // variabil auxiliara
	TSwitch* c = (TSwitch*)malloc(sizeof(TSwitch)); // elementul care trebuie scos
	while ( !VidaS(a) ) {
		Top(a, b); 					//se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) {
			Pop(a, (void*)c); // se scoate daca este gasit
			break;
		}
		else {
			Pop(a, b);		//altfel se pune in stiva auxiliara
			Push(aux, b);
		}
	}
	while ( !VidaS(aux) ) {
		Pop(aux, b);				//se pune tot in stiva principala
		Push(a, b);
	}
	free(c);			//eliberare memorie
	free(b);
	DistrS(&aux);
	return c;
}

TSwitch* CautaInStiva(void* a, int id, int *B) 			// se cauta element
{
	void* aux = InitS(sizeof(TSwitch));				//stiva auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));		// variabila auxiliara
	TSwitch* c = (TSwitch*)malloc(sizeof(TSwitch));		// elemtul care este cautat
	c->Id = -1; // id nepermis
	while ( !VidaS(a) ) {
		Top(a, b);		// se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) {	//se compara
			Pop(a, (void*)c);	// se pune in stiva aux
			Push(aux, (void*)c);
			if ( VidaS(a) ) {*B = -1;}	// ne da pozitia elementului , baza sau nu
		}
		else {
			Pop(a, b);	//altfel se pune in stiva aux
			Push(aux, b);
		}
	}
	while ( !VidaS(aux) ) {
		Pop(aux, b);	//se muta tot in stiva principala
		Push(a, b);
	}
	// eliberare memorie
	if (c->Id == -1) {
		free(c);
		free(b);
		DistrS(&aux);
		return NULL;
	}
	free(b);
	DistrS(&aux);
	return c;
}

void afiseazaStivaV2(void *a, FILE* x) //
{
	if ( VidaS(a) )
		return;
	void *aux = InitS(sizeof(TSwitch));			//stiva auxiliara
	void *ae = (TSwitch*)malloc(sizeof(TSwitch));		// variabila auxiliara
	while ( !VidaS(a) ) {
		Pop(a, ae);		// se extrag elementele si se pun in stiva auxiliar
		Push(aux, ae);
		fprintf(x, "%d ", (*(TSwitch*)ae).Id);
		fprintf(x, "%s ", ((TSwitch*)ae)->Ip);	// se afiseaza
		fprintf(x, "%s\n", ((TSwitch*)ae)->Nume);
	}
	while ( !VidaS(aux) ) {
		Pop(aux, ae);
		Push(a, ae);	// se pun in stiva principala
	}
	free(ae);	// eliberare memorie auxiliara
	DistrS(&aux);
}

void DistrS(void** stiva)
{
	while ( VF(*stiva) ) { // se parcurge stiva
		ACelST aux = VF(*stiva);
		free( ((TSwitch*)(aux->info))->Nume);
		free( ((TSwitch*)(aux->info))->Ip);
		free( ((TSwitch*)(aux->info))->Mod);	// eliberare campuri
		free(aux->info);
		VF(*stiva) = aux->urm;
		free(aux);
	}
	free(VF(*stiva));
	free(*stiva);
	*stiva = NULL;
}

void DistrugeStive(TVSt* stive)
{
	int k;
	ACelST L;
	for (k = 0; k < stive->M; k++)	/* parcurgem stivele */
	{
		L = (ACelST)stive->v[k];
		if (!L)		/* verificare stiva vida */
			continue;
		else
			DistrS((void*)&L);		/* functie de distrugere stiva */
	}
	free(stive->v);		/* eliberam si spatiul ocupat de structura */
	free(stive);
}