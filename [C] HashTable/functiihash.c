/* Ilie Nicolae Vlad 311CB */

#include "hash.h"


int functieD(char *key , int M)		/* functia de dispersie */
{
	int i = 0;
	int sum = 0;
	while ( key[i] )
	{
		sum += key[i];			/* suma codurilor ASCII a carac. */
		i++;
	}

	return sum % M;				/* restul la numarul de bucket-uri */
}

void AfiValue(void *ae, FILE *x)
{
	TCache p = *(TCache*)ae;		/*se face cast */
	fprintf(x, " (%s)", p.value);		/* se afiseaza in fisier */
}

void AfisareValori( TLG L, int i, FILE *x)
{
	if (L) {

		fprintf(x, "%d:", i ); 	/* se afiseaza indicele */
		AfiValue(L->info, x);	/* informatia primei celule*/
		TLG b = L->urm;
		while ( b != L) {
			AfiValue(b->info, x);	/*se parcurge lista si se afiseaza si elementele ramase */
			b = b->urm;
		}
		fprintf(x, "\n");			/* se trece pe randul urmator in fisierul de iesire */
	}

}

void print(THash * h, FILE *x)		/* se afiseaza informatiile din celulele tabelului hash */
{
	int i;
	for (i = 0; i < h->M; i++)
		AfisareValori(h->v[i], i, x);
}

int print_list( THash * h, int i, FILE *x)	/* afiseaza valorile din lista cu indicele respectiv*/
{
	if (i > h->M)
		return 0; /*indice invalid*/

	TLG p = h->v[i];

	if (p == NULL) {
		fprintf(x, "%d: VIDA\n", i); /* verificare lista vida*/
		return 0;
	}
	fprintf(x, "%d:", i);			/* afisare indice */
	fprintf(x, " (%s)", ((TCache*)p->info)->value); /* afisare primul element */

	TLG q = p->urm;
	while ( q != p) { 	/*se parcurge lista */
		fprintf(x, " (%s)", ((TCache*)q->info)->value);	/*afisare elemente*/
		q = q->urm;
	}
	fprintf(x, "\n");		/* trecem pe randul urmator in fisier */
	return 1;
}

int compara( void * x , void * y)	/* functia de comparare >0 daca y>x si invers altfel */
{
	TCache p = *(TCache*)x;			/* se fac cast-uri la informatia TCache */
	TCache q = *(TCache*)y;

	if ( p.freq == q.freq )			/* daca frecventele sunt egale se compara cele doua siruri*/
		return strcmp(p.key, q.key);	/* se returneaza ordinea lor alfabetica */

	return q.freq - p.freq ;
}

int cauta( TLG x, char *key)	/* functie de cautare intr o lista */
{
	if (x == NULL) return 1;	/* verificam daca lista e goala */

	if ( !strcmp( key , ((TCache*)x->info)->key ))		/* verificam daca lista e goala */
		return 0;						/* l am gasit elementul */

	TLG q = x->urm;
	while ( q != x ) {
		if ( !strcmp(key , ((TCache*)q->info)->key))		/* verificam daca e in lista */
			return 0;					/* l am gasit elementul */
		q = q->urm;
	}
	return 1;		/* nu am gasit elementul */
}

int LungLista( TLG L)	/* calculeaza lungimea listei */
{	int S = 0;

	if (L == NULL)		/* verificam daca e lista goala */
		return 0;

	TLG q = L->urm;		/* avem un element */
	S++;

	while (q != L) {
		S++;			/* le numaram pe restul */
		q = q->urm;
	}

	return S;
}

THash* initTHash(int N , TFHASH fdisp)		/* initializare tabela hash */
{
	THash *td;
	td = (THash*)malloc(sizeof(THash));		/* alocare spatiu pentru structura */

	if (!td) return NULL;	/* verificare */

	td->v = (TLG*)calloc(N, sizeof(TLG));	/* alocare bucket-uri */

	if (!td->v)	/* verificare */
	{
		free(td);
		return NULL;
	}

	td->M = N;		/* completare campuri tabela hash */
	td->fh = fdisp;
	return td;
}

int Remove( THash * hash, char* key)		/*scoatere element din tabela */
{
	int k = hash->fh(key, hash->M);
	TLG p = hash->v[k];

	if (p == NULL) return 1; 	/* verificare lista vida */

	if ( cauta(p, key) )
		return 0;			/* verificare daca elementul exista */

	if ( !strcmp(key, ((TCache*)p->info)->key) && hash->v[k] == p->urm) {		/* verificam daca lista are un singur element*/
		TCache *d = (TCache*)hash->v[k]->info;			/* deconectare si eliberare spatiu */
		free(d->key);
		free(d->value);
		free(d);
		free(hash->v[k]);
		hash->v[k] = NULL;
		return 1;
	}

	if ( !strcmp(key, ((TCache*)p->info)->key) ) {		/* verificam daca este primul element */
		/* deconectare si eliberare memorie */
		TLG aux = p;								/* celula ce va fi eliminata */
		TCache *d = (TCache*)aux->info;

		hash->v[k] = aux->urm;
		aux->pre->urm = aux->urm;
		aux->urm->pre = aux->pre;

		free(d->key);
		free(d->value);
		free(d);
		free(aux);
		return 1;
	}
	TLG q = p->urm;

	while (p != q) {

		if ( !strcmp(key, ((TCache*)q->info)->key) ) {		/* cautam elementul in lista */
			/* deconectare si eliberare memorie */
			TLG aux = q;								/* celula ce va fi eliminata */
			TCache *d = (TCache*)aux->info;

			aux->pre->urm = aux->urm;
			aux->urm->pre = aux->pre;


			free(d->key);
			free(d->value);
			free(d);
			free(aux);

			return 1;
		}

		q = q->urm;
	}
	return 1;
}

TLG alocaCel( TCache* x )	/* functie de alocare spatiu pentru o celula de lista */
{
	TLG new = (TLG)malloc(sizeof(TCelulaG));
	if (!new)
		return NULL;		/* verificare */

	new->info = malloc(sizeof(TCache));		/* alocare pentru informatie */
	if (!new->info)	/* verificare */
	{
		free(new);
		return NULL;
	}

	((TCache*)(new->info))->key = strdup(x->key);	/* copiere + alocare camp key */
	if ( !((TCache*)(new->info))->key ) { 	/* verificare */
		free(new->info);
		free(new);
		return NULL;
	}

	((TCache*)(new->info))->value = strdup(x->value);	/* copiere + alocare camp value */
	if ( !((TCache*)(new->info))->value  ) {		/* verificare */

		free(((TCache*)(new->info))->key);
		free(new->info);
		free(new);
		return NULL;
	}

	((TCache*)(new->info))->freq = x->freq;

	return new;
}

int InsearareOrd(ALG aL, TCache *d, TLG aux) /* functia de inserare ordonata */
{
	if ( (*aL) == NULL )  {			/* verificam daca lista este goala */
		(*aL) = aux;					/* inseram la inceput */
		aux->urm = aux->pre = aux;
		return 1;
	}

	if ( compara(d, (*aL)->info) < 0 ) {	/* verificam daca trebuie inserat inainte de primul element din lista */

		TLG p = (*aL);
		(*aL) = aux;

		aux->urm = p;
		aux->pre = p->pre;
		p->pre->urm = aux;
		p->pre = aux;
		return 1;
	}

	TLG p = (*aL);
	TLG q = p;
	/* parcurgem lista pana gasim primul element cu informatia "< 0"
				decat informatia de adauga */
	while (q->urm != p && compara(d, q->urm->info) > 0)
		q = q->urm;

	aux->pre = q;	/* inseram elementul dupa el */
	aux->urm = q->urm;
	q->urm->pre = aux;
	q->urm = aux;
	return 1;
}

void DistrugeLista( ALG aL)	/* functie de distrugere lista generica */
{
	int k = LungLista(*aL); /* se calculeaza lungimea listei */
	if (*aL == NULL)			/* verificare daca este lista vida */
		return ;
	while ( k > 0) {

		TLG q = *aL;	 /* adresa celulei eliminate */

		free( ((TCache*)q->info)->key );	/* eliberare camp key */
		free( ((TCache*)q->info)->value );	/* eliberare camp value */
		free(q->info);	/* elib.spatiul ocupat de element*/
		*aL = q->urm;	/* deconecteaza celula din lista */
		k--;
		free(q);		/* elibereaza spatiul ocupat de celula */
	}
}

void distrugeTHash( THash **hash)	/* eliberare memorie tabela hash */
{
	int k;
	TLG L;
	if ( !(*hash) )					/* verificare tabela vida */
		return ;
	for ( k = 0; k < (*hash)->M; k++ )	/* parcurgem bucket-urile */
	{
		L = (*hash)->v[k];
		if (!L)
			continue;				/* verificare bucket vid */
		else
			DistrugeLista(&L);		/* functie de distrugere lista generica */
	}
	free((*hash)->v);			/* eliberam si spatiul ocupat de structura */
	free(*hash);
	*hash = NULL;
}

THash* set( char *key , char * value , THash** hash , int freq)
{
	int k = (*hash)->fh(key, (*hash)->M);		/* calculam in ce bucket ne aflam */
	int LungimeLista = 0;
	int S = 0;
	int j;
	TLG aux;
	TCache *d;

	if ( cauta((*hash)->v[k], key) == 0)			/* se verifica daca elementul exista , daca exista tabela ramane nemodificata */
		return *hash;

	for ( j = 0; j < (*hash)->M ; j++) {
		S += LungLista((*hash)->v[j]);			/* se calculeaza numarul total de elemente din tabela */
	}

	LungimeLista = LungLista((*hash)->v[k]);	/* se calculeaza lungimea liste curente */
	if ( LungimeLista >= (*hash)->M )				/* daca lista este mai mare decat M atunci se elimina ultimul element */
		Remove((*hash), ((TCache*)(*hash)->v[k]->pre->info)->key);



	d = (TCache *)malloc(sizeof(TCache));		/* se aloca spatiu pentru info */
	d->key = strdup(key);						/* copiere + alocare camp key */
	d->value = strdup(value);					/* copiere + alocare camp value */
	d->freq = freq;

	aux = alocaCel(d);							/* se aloca spatiu pentru celula ce urmeaza a fi inserata */
	InsearareOrd( &(*hash)->v[k], d, aux );		/* se insereaza ordonat */


	if ( S > 2 * (*hash)->M ) {			/* se verifica daca numarul total de intrari din tabela este mai mare decat 2*M */

		THash *newHash;
		newHash = initTHash(2 * (*hash)->M, functieD);			/* se initializeaza o noua tabela hash cu 2*M liste */
		if (!newHash) {
			return *hash;				/* verificare */
		}
		TCache *g = (TCache*)malloc(sizeof(TCache));		/* alocare memorie variabila ajutatoare */

		for (j = 0; j < (*hash)->M ; j++) {

			int lungime = LungLista((*hash)->v[j]);			/* se calculeaza lungimea listei */
			TLG q = (*hash)->v[j];
			TLG p = q;

			while ( lungime > 0 ) {						/* se parcurge lista si se copiaza tot din vechiul Hash in noul Hash */
				p = q;
				g->key = strdup(((TCache*)q->info)->key);
				if (!g->key) {			/* verificare */
					free(g);
					return *hash;
				}
				g->value = strdup(((TCache*)q->info)->value);
				if (!g->value) {
					free(g->key);		/* verificare */
					free(g);
					return *hash;
				}

				g->freq = ((TCache*)q->info)->freq;		/* se copiaza si frecventa */
				q = q->urm;
				int u = (*hash)->fh(g->key, newHash->M);			/* se determina bucket ul in care trebuie inserat */

				InsearareOrd( &(newHash)->v[u], g, p);			/* se insereaza ordonat in noul hash */

				free(g->key);			/* eliberare memorie */
				free(g->value);

				lungime--;

			}

		}
		free((*hash)->v);	/* eliberare vechiul hash */
		free(*hash);
		free(g);			/* eliberare memorie pentru variabila ajutatoare*/
		*hash = newHash;			/* hash va indica la zona de memorie a noului tabel creat */

	}

	free(d->key);		/* eliberare memorie */
	free(d->value);
	free(d);
	return *hash;
}

char *get( THash * h, char * key)
{
	int k = h->fh(key, h->M);
	if ( cauta( h->v[k], key) )		/* verificam daca elementul  exista */
		return NULL;
	else {
		/* daca este prmimul element trebuie doar sa incrementam frecventa */
		if (!strcmp(key , ((TCache*)h->v[k]->info)->key) )
		{
			((TCache*)h->v[k]->info)->freq++;
			return ((TCache*)h->v[k]->info)->value;
		}

		TLG pt = h->v[k];
		TLG qt = pt->urm;
		TLG aux;			/* cautam celula in lista */
		while ( qt != pt ) {

			if ( !strcmp( key , ((TCache*)qt->info)->key ) )
				break;
			qt = qt->urm;

		}
		TCache *d = (TCache*)malloc(sizeof(TCache));		/* alocare spatiu pentru informatie */

		d->key = strdup(((TCache*)qt->info)->key);			/* copiere + alocare camp key */
		if ( !d->key ) {		/*verificare*/
			free(d);
			return NULL;
		}
		d->value = strdup(((TCache*)qt->info)->value);		/* copiere + alocare camp value */
		if ( !d->value ) {	/*verificare*/
			free(d->key);
			free(d);
			return NULL;
		}
		d->freq = ((TCache*)qt->info)->freq + 1;			/* incrementam frecventa */

		aux = alocaCel(d);									/* se aloca spatiu pentru celula noua */
		Remove(h, key);									/* se elimina celula */
		InsearareOrd(&(h->v[k]), d, aux);					/* se insereaza ordonat */

		free(d->key);					/* eliberare memorie */
		free(d->value);
		free(d);
		return ((TCache*)aux->info)->value;

	}
	return NULL;
}

int GasesteCerinta(char *x)			/* functie care codifica operatiile */
{
	if ( !strcmp(x, "set") ) return 1;

	if ( !strcmp(x, "get") ) return 2;

	if ( !strcmp(x, "remove") ) return 3;

	if ( !strcmp(x, "print") ) return 4;

	if ( !strcmp(x, "print_list") ) return 5;

	return 0; 						/* operatie care nu exista */
}