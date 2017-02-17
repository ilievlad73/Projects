/* Ilie Nicolae Vlad 311CB */
#include "arb.h"

TArb InitA()         /* initializare arbore */
{
	TArb aux = Constr(0, 0); // radacina
	return aux;
}

TArb Constr(char info, char codificare)
{
	TArb aux = (TArb)calloc(1, sizeof(TNod));	// se aloca spatiu pentru un nod
	if (!aux) return NULL;
	aux->info = info;			// se completeaza campurile structurii
	aux->codificare = codificare;
	return aux;
}

void AddCodificare(TArb *a, char *codificare, char info, int LiteraCurenta)	// radacina este construita se va insera in arb->st
{
	if ( *a == NULL && LiteraCurenta  + 1 == strlen(codificare) ) {		// daca am ajuns la ultimul caracter din codificatie
		*a = Constr(info, codificare[LiteraCurenta]);							// Construim nod si punem codificatia si info
	}
	if ( *a != NULL && LiteraCurenta + 1 == strlen(codificare) && (*a)->codificare == codificare[LiteraCurenta] ) {		//	daca am ajuns la ultimul caracter din codificatie dar nu este Nod NULL
		(*a)->info = info;														// doar setam informatia in nodul respectiv
	}
	if ( *a == NULL && LiteraCurenta != strlen(codificare) ) {			// daca nu am ajuns la finalul codificarii
		*a = Constr(0, codificare[LiteraCurenta]);							// / Construim nod si punem codificatia si mergem mai departe in arbore
		AddCodificare(&(*a)->st, codificare, info, LiteraCurenta + 1);
	}
	if ( *a != NULL && (*a)->codificare == codificare[LiteraCurenta] && LiteraCurenta != strlen(codificare) ) {		// daca nu am ajuns la finalul codificarii si caracterele codif sunt identice
		AddCodificare(&(*a)->st, codificare, info, LiteraCurenta + 1);															// mergem mai departe in arbore
	}
	if ( *a != NULL && (*a)->codificare != codificare[LiteraCurenta] && LiteraCurenta != strlen(codificare)) {		// daca nu am ajuns la finalul codificarii si caracterele codif sunt diferite
		if ( (*a)->frate == NULL && LiteraCurenta + 1 == strlen(codificare) ) {														// daca fratele este NULL
			(*a)->frate = Constr(info, codificare[LiteraCurenta]);						// Construim nod si punem codificat
		}
		else if ( (*a)->frate == NULL) {														// daca fratele este NULL
			(*a)->frate = Constr(0, codificare[LiteraCurenta]);						// Construim nod si punem codificatia
			AddCodificare(&(*a)->frate->st, codificare, info, LiteraCurenta + 1);			// Mergem mai departe in arbore pe ramura respectiva
		}
		else if ( (*a)->frate->codificare == codificare[LiteraCurenta] && LiteraCurenta != strlen(codificare) ) {			// daca fratele nu este NULL si caracterele coincid
			AddCodificare(&(*a)->frate->st, codificare, info, LiteraCurenta + 1);										// mergem mai departe pe ramura respectiva
		}
		else AddCodificare(&(*a)->frate, codificare, info, LiteraCurenta);							// in ultimul caz trecem la fratele urmator
	}
	if ( *a != NULL && (*a)->codificare != codificare[LiteraCurenta] && LiteraCurenta <= strlen(codificare)) { // avansam in frati
		AddCodificare(&(*a)->frate, codificare, info, LiteraCurenta);
	}
}

char CautaInfo(TArb a, char* codificare, int LiteraCurenta)				// returneaza informatia corespunzatoare codificari, altfel 0
{
	if ( a == NULL || LiteraCurenta + 1 > strlen(codificare)) return 0;				// daca am ajuns la un nod null sau am depasit codificarea

	if (  LiteraCurenta + 1 == strlen(codificare) && a->codificare == codificare[LiteraCurenta] && a->info != 0)
		return a->info;													// daca am ajuns la info valid si lungimea codificarii este egala cu lungimea curenta
	else if ( a->codificare == codificare[LiteraCurenta] )			// daca coincid codificarile mergem in arbore mai departe pe fiul stang
		return CautaInfo(a->st, codificare, LiteraCurenta + 1);
	else if ( a->codificare != codificare[LiteraCurenta] )			// daca nu coincid codificarile mergem in arbore pe fiul frate
		return CautaInfo(a->frate, codificare, LiteraCurenta);
	else
		return 0;
}

void del(char info, AArb a)
{
	if (*a == NULL) return;
	if ( (*a)->info == info) {
		(*a)->info = 0;				// se seteaza informatia pe 0
	}
	else {
		del(info, &(*a)->st);
		if ( (*a)->st != NULL ) {		// cand ne intoarcem din recursivitate se verifica :

			if ( (*a)->st->frate == NULL && (*a)->st->st == NULL && (*a)->st->info == 0) { // daca este frunza marginala
				free((*a)->st);
				(*a)->st = NULL;
			}
			else if (  Info((*a)->st->frate) != 0 && Info((*a)->st->st)  == 0 && (*a)->st->info == 0) { // daca info este 0
				TArb aux = (*a)->st;							// si pe ramura din nu stange exista informatie valida si pe cealalta cea a fratelui exista
				(*a)->st = (*a)->st->frate;						// se elimina nodul din stanga
				free(aux);
			}
		}
		del(info, &(*a)->frate);
		if ( (*a)->frate != NULL ) {

			if ( (*a)->frate->frate == NULL && (*a)->frate->st == NULL && (*a)->frate->info == 0) {	// daca este frunza marginala
				free((*a)->frate);
				(*a)->frate = NULL;
			}
			else if ( Info((*a)->frate->frate) != 0 && Info((*a)->frate->st)  == 0 && (*a)->frate->info == 0 ) { // daca info este 0
				TArb aux = (*a)->frate;						// si pe ramura din nu stange exista informatie valida si pe cealalta cea a fratelui exista
				(*a)->frate = (*a)->frate->frate;			// se elimina frate , practic se trece la fratele urmator
				free(aux);
			}
		}

	}
}

int Info(TArb a) 		// functia imi spune daca este informatie valida pe ramura respectiv - 0 nu exista informatie
{
	if ( a == NULL) return 0;
	if ( a->info != 0 )					// daca e dif de 0 info adunam si apelam recursiv cele 2 ramuri
		return 1 + Info(a->st) + Info(a->frate);
	return Info(a->st) + Info(a->frate);
}

int VidaQ( void *a)		// verificare coada vida
{
	if ( IC(a) == NULL)
		return 1;
	return 0;
}

void* InitQ(size_t dim)
{
	AQ a = (AQ)calloc(1, sizeof(TCoada)); // se aloca coada
	if (!a) return NULL;
	a->dime = dim; 		// se campleteaza campurile
	IC(a) = NULL;
	SC(a) = NULL;
	return (void*)a;
}

int IntrQ(void *a, void *ae)
{
	ACel aux = (ACel)calloc(1, sizeof(TCel));		// se aloca celula
	if (!aux) return 0;
	aux->urm = NULL;							// se aloca informatia
	aux->info = (TArb*)calloc(1, sizeof(TArb));
	if (!aux->info) {
		free(aux);
		return 0;
	}
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

void DistrQ(void** a)			// eliberare memorie campuri
{
	if (*a == NULL) return;
	while ( IC(*a) ) {			// se actualizeaza inceputul
		ACel aux = IC(*a);
		free(aux->info);		// eliberare info
		IC(*a) = aux->urm;		//se scoate celula
		free(aux);				// eliberare celula
	}
	free(*a);
	*a = NULL;
}

void AfiseazaArbore( TArb a, FILE* x)
{
	if (a == NULL) return;
	void* traverseaza = (TArb*)calloc(1, sizeof(TArb));	// variabila cu care vom prelucra nodurile
	void* coada = InitQ(sizeof(TArb*));					// coada auxiliara
	TArb spatiu = (TArb)calloc(1, sizeof(TNod));			// variabila pentru a plasa \n
	spatiu->codificare = ' ';
	spatiu->info = 0;							// codificare nepermisa
	IntrQ(coada, &a);									// se introduce in coada radacina si variabila pentru \n
	IntrQ(coada, &spatiu);

	while ( !VidaQ(coada) ) {

		ExtrQ(coada, traverseaza);						//se extrage primul nod
		if ((*(TArb*)traverseaza)->codificare == ' ') {	// verificam daca trebuie pus \n
			fprintf(x, "\n");
			if (!VidaQ(coada))							// daca e coada vida il punem la loc pentru urmatorul nivel
				IntrQ(coada, &spatiu);
		}
		else {											// altfel afisam informatia din nodul respectiv
			if ( (*(TArb*)traverseaza)->codificare == 0) //radacina
				fprintf(x, "(:)");
			else if ( (*(TArb*)traverseaza)->info != 0)
				fprintf(x, "(%c:%c)", (*(TArb*)traverseaza)->codificare, (*(TArb*)traverseaza)->info);
			else if ( (*(TArb*)traverseaza)->codificare != 0)
				fprintf(x, "(%c:)", (*(TArb*)traverseaza)->codificare);

			// parcurgem toti fii si ii introducem in coada
			if ( (*(TArb*)traverseaza)->st != NULL ) {
				IntrQ(coada, &(*(TArb*)traverseaza)->st);
				TArb aux = (*(TArb*)traverseaza)->st->frate;
				while ( aux != NULL ) {
					IntrQ(coada, &aux);
					aux = aux->frate;
				}
			}
		}
	}
	DistrQ(&coada);
	free(spatiu);
	free(traverseaza);
}

void distruge(TArb r) 	/* functie auxiliara - distruge toate nodurile */
{
	if (!r) return;
	distruge (r->st);     /* distruge subarborele stang */
	distruge (r->frate);     /* distruge subarborele drept */
	free (r);             /* distruge nodul radacina */
}

void DistrArb(TArb *a) /* distruge toate nodurile arborelui de la adresa a */
{
	if (!(*a)) return;       /* arbore deja vid */
	distruge (*a);           /* distruge toate nodurile */
	*a = NULL;               /* arborele este vid */
}
