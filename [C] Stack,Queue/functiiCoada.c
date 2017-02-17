/* Ilie Nicola Vlad 311 CB */
/* functii coada */
#include "Biblioteca.h"

void* InitQ(size_t dim)
{
	AQ a = (AQ)malloc(sizeof(TCoada)); // se aloca coada
	a->dime = dim; 		// se campleteaza campurile
	IC(a) = NULL;
	SC(a) = NULL;
	return (void*)a;
}

int IntrQ(void *a, void *ae)
{
	ACel aux = (ACel)malloc(sizeof(TCel));		// se aloca celula
	aux->urm = NULL;							// se aloca informatia
	aux->info = (TSwitch*)malloc(sizeof(TSwitch));
	memcpy(aux->info, ae, DIMEQ(a));
	if ( IC(a) == SC(a) && IC(a) == NULL) { // coada goala
		IC(a) = aux;
		SC(a) = aux; // coada vida
		return 0;
	}

	SC(a)->urm = aux; // se actualizeaza sfarsitul
	SC(a) = aux;
	return 1;
}

void afiseazaCoadaV2(void *a, FILE* x)
{
	fprintf(x, "{");
	void *aux = InitQ(sizeof(TSwitch));	// coada auxiliara
	void *ae = (TSwitch*)malloc(sizeof(TSwitch));	//variabila auxiliara
	while ( !VidaQ(a) ) {
		ExtrQ(a, ae);		// se muta tot din coada principala in coada aux
		IntrQ(aux, ae);

	}
	while ( !VidaQ(aux) ) {
		ExtrQ(aux, ae);	// se extrage din coada aux se pun cea principala si se afiseaza
		IntrQ(a, ae);
		fprintf(x, "%d ", (*(TSwitch*)ae).Id);
	}
	fprintf(x, "}\n");
	free(ae);	// eliberare memorie
	DistrQ(&aux);
}

int InserareOrdCoada(void* a, void* ae)
{

	void* aux = InitQ(sizeof(TSwitch));			// coada aux
	void* b = (TSwitch*)malloc(sizeof(TSwitch));	// variabila auxiliara

	while ( !VidaQ(a) ) {
		PrimQ(a, b); // se extrage informatia
		if ( fcmpQ(ae, b) > 0 ) { // se compara
			ExtrQ(a, b);		//se pune in coada aux
			IntrQ(aux, b);
		}
		else break;
	}
	IntrQ(aux, ae); // se introduce elemetul in coada aux
	while ( !VidaQ(a) ) {
		ExtrQ(a, b);			// se pun elementele in coada aux
		IntrQ(aux, b);
	}
	while (!VidaQ(aux)) {
		ExtrQ(aux, b);	//se muta elementele in coada principala
		IntrQ(a, b);
	}
	free(b);				// eliberare memorie
	DistrQ(&aux);
	return 1;
}

int ExtrQ(void* a, void* ae)
{
	if ( VidaQ(a) ) {
		return 0;
	}

	memcpy(ae, IC(a)->info, DIMEQ(a)); // se copiaza memoria
	if ( IC(a) == SC(a) )		// coada cu un singur element
	{
		ACel aux = IC(a);
		free(aux->info);
		free(aux);
		IC(a) = NULL;
		SC(a) = NULL;
		return 1;
	}

	ACel aux = IC(a);
	IC(a) = IC(a)->urm; 	// se actualizeaza inceputul cozii
	free(aux->info);	// elibeare memorie
	free(aux);
	return 1;
}

int VidaQ( void *a)		// verificare coada vida
{
	if ( IC(a) == NULL)
		return 1;
	return 0;
}

int PrimQ(void* a, void* ae)	// se extrage informatia de la inceput
{
	if ( VidaQ(a) )
		return 0;
	memcpy(ae, IC(a)->info, DIMEQ(a)); // se copiaza informatia
	return 1;
}

void EliminaDinCoada(void* a, int id) 	// elimina elemntu din coada cu eliberarea memorie
{


	void *aux = InitQ(sizeof(TSwitch));				// coada auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));			// variabil auxiliara
	TSwitch *c = (TSwitch*)calloc(1, sizeof(TSwitch));			// elementul de eliminat
	c->Id = -1;		// id nepermis
	while ( !VidaQ(a) ) {
		PrimQ(a, b);			// se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) { /// daca este gasit se pune in c
			ExtrQ(a, (void*)c);
		}
		else {
			ExtrQ(a, b);		// altfel se pune in coada auxiliara
			IntrQ(aux, b);
		}
	}

	while ( !VidaQ(aux) ) {
		ExtrQ(aux, b);
		IntrQ(a, b);				// se muta elementele in coada princi[pala
	}
	if (c->Id == -1) {			// eliberare memorie daca nu este gasit
		free(c);
		free(b);
		DistrQ(&aux);
		return ;
	}
	free(c->Nume);			// eliberare si campuri in cazul in care este gasit
	free(c->Ip);
	free(c->Mod);
	free(c);
	free(b);
	DistrQ(&aux);
}

TSwitch* RemoveDinCoada(void* a, int id) {			// scoate element din coada


	void *aux = InitQ(sizeof(TSwitch));			// coada auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));		// variabil auxiliara
	TSwitch *c = (TSwitch*)calloc(1, sizeof(TSwitch));		// elementul de eliminat

	while ( !VidaQ(a) ) {
		PrimQ(a, b);		// se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) {
			ExtrQ(a, (void*)c);		/// daca este gasit se pune in c
		}
		else {
			ExtrQ(a, b);			// altfel se pune in coada auxiliara
			IntrQ(aux, b);
		}
	}

	while ( !VidaQ(aux) ) {
		ExtrQ(aux, b);		// se muta elementele in coada princi[pala
		IntrQ(a, b);
	}
	free(c);		// eliberare memorie
	free(b);
	DistrQ(&aux);
	return c;
}

TSwitch* CautaInCoada(void*a , int id)
{

	void *aux = InitQ(sizeof(TSwitch));			// coada auxiliara
	void* b = (TSwitch*)malloc(sizeof(TSwitch));			// variabil auxiliara
	TSwitch *c = (TSwitch*)calloc(1, sizeof(TSwitch));			// elementul cautat
	c->Id = -1;		// id nepermis
	while ( !VidaQ(a) ) {
		PrimQ(a, b);				// se extrage informatia
		if ( id == (*(TSwitch*)b).Id ) {
			ExtrQ(a, (void*)c);		//se extrage elementul in c
			IntrQ(aux, (void*)c);
		}
		else {
			ExtrQ(a, b);			// altfel se pune in coada auxiliara
			IntrQ(aux, b);
		}
	}

	while ( !VidaQ(aux) ) {
		ExtrQ(aux, b);		// se muta elementele in coada princi[pala
		IntrQ(a, b);
	}
	free(b);			// eliberare memorie
	DistrQ(&aux);
	return c;
}

void DistrQ(void** a)			// eliberare memorie campuri
{
	if (*a == NULL) return;
	while ( IC(*a) ) {			// se actualizeaza inceputul
		ACel aux = IC(*a);
		free( ((TSwitch*)(aux->info))->Nume);		//eliberare campuri
		free( ((TSwitch*)(aux->info))->Ip);
		free( ((TSwitch*)(aux->info))->Mod);
		free(aux->info);			// eliberare celula
		IC(*a) = aux->urm;			//se scoate celula
		free(aux);
	}
	free(*a);
	*a = NULL;
}