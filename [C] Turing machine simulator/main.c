#include <stdio.h>
#include <stdlib.h>
#include  <string.h>

#define MAX_ALFABET 60
#define MAX_STARI 100000
#define MAX_LINE 10000

typedef struct {
	char* in;
	char* out;
	int* stare_Urmatoare;
	int* miscare_Cursor;		// 1 = R , 0 = H , -1 = L
	int stare_Finala;		// 1 - Stare finala , 0 - nu e stare finala
	int nr_Tranzitii;
	int valid;
} Stare, *TStare;


void Adauga_Stare(TStare x, int index, char inaux, int next_state, char outaux, char miscare)		// adauga o noua tranzitie
{
	int tranzitie_curenta = x[index].nr_Tranzitii;
	x[index].in[tranzitie_curenta] = inaux;
	x[index].out[tranzitie_curenta] = outaux;
	x[index].stare_Urmatoare[tranzitie_curenta] = next_state;
	if ( miscare == 'H')
		x[index].miscare_Cursor[tranzitie_curenta] = 0;
	if (miscare == 'R')
		x[index].miscare_Cursor[tranzitie_curenta] = 1;
	if ( miscare == 'L')
		x[index].miscare_Cursor[tranzitie_curenta] = -1;
	x[index].valid = 1;
	x[index].nr_Tranzitii++;
}


int main()
{
	//deschidem fisierele
	FILE* in1 = fopen("tm.in", "r");
	FILE* in = fopen("tape.in", "r");
	FILE* out = fopen("tape.out", "w");

	char line[MAX_LINE], aux_caracter, *token;
	int aux, i, NR_MAX_STARI, STARE_INILIATA, lungime_aux;

	fscanf(in1, "%d", &aux);		// citim numarul de stari numarul de stari

	NR_MAX_STARI = aux;
	lungime_aux = aux;

	for ( i = 0; i < lungime_aux; i++) {
		fscanf(in1, "%c%c%d", &aux_caracter, &aux_caracter, &aux);
		if ( aux > NR_MAX_STARI )
			NR_MAX_STARI = aux;
	}

	// Initializare stari
	TStare Stari = (TStare)calloc(NR_MAX_STARI + 1, sizeof(Stare));
	// Alocam pentru fiecare stare

	for (i = 0; i <= NR_MAX_STARI; i++) {
		Stari[i].in = (char*)calloc(MAX_ALFABET, sizeof(char));
		Stari[i].out = (char*)calloc(MAX_ALFABET, sizeof(char));
		Stari[i].stare_Urmatoare = (int*)calloc(MAX_ALFABET, sizeof(int));
		Stari[i].miscare_Cursor = (int*)calloc(MAX_ALFABET, sizeof(int));
		Stari[i].nr_Tranzitii = 0;
		Stari[i].stare_Finala = 0;
		Stari[i].valid = 0;
	}

	//Setare stari finale

	fscanf(in1, "%d", &aux);				//citim numarul de stari finale
	lungime_aux = aux;
	for (i = 0; i < lungime_aux; i++) {		
		fscanf(in1, "%c%c%d", &aux_caracter, &aux_caracter, &aux);
		Stari[aux].stare_Finala = 1;
		Stari[aux].valid = 1;
	}

	//Setare stare initiala

	fscanf(in1, "%c", &aux_caracter); 		//trecem pe linia urmatoare
	fscanf(in1, "%c", &aux_caracter);		//trecem de caracterul 'S'
	fscanf(in1, "%d", &STARE_INILIATA); 	// am setat starea initiala
	fscanf(in1, "%c", &aux_caracter);		//trecem pe linia urmatoare
	fscanf(in1, "%d", &aux); 				// numarul de tranzitii
	lungime_aux = aux;
	fscanf(in1, "%c", &aux_caracter);		//trecem pe linia urmatoare


	//Parsare tranzitii

	int index, next_stare;
	char index_aux[1000000], next_stare_aux[1000000];
	char inaux, outaux, miscare;
	for ( i = 0; i < lungime_aux; i++) {			
		fgets(line, sizeof(line), in1);
		token = strtok(line, " ");
		strcpy(index_aux, token + 1);
		index = atoi(index_aux);

		token = strtok(NULL, " ");
		inaux = token[0];
		token = strtok(NULL, " ");

		strcpy(next_stare_aux, token + 1);
		next_stare = atoi(next_stare_aux);

		token = strtok(NULL, " ");
		outaux = token[0];
		token = strtok(NULL, " ");
		miscare = token[0];
		Adauga_Stare(Stari, index, inaux, next_stare, outaux, miscare);
	}



	//Pregatim simularea

	char Banda_final[10000];					
	fgets(Banda_final, sizeof(Banda_final), in);
	int lungime = strlen(Banda_final);
	int banda = 1;
	int stare_Curenta = STARE_INILIATA;
	int Tranzitie_valida;

	for (i = lungime - 1; i < 10000; i++)		//umplem banda cu caractere vide
		Banda_final[i] = '#';

	for (;;) {									//simulare masina turing
		Tranzitie_valida = 0;
		if ( Stari[stare_Curenta].valid == 0) {
			fprintf(out, "Se agata!");
			return 0;
		}
		if ( Stari[stare_Curenta].stare_Finala )
			break;
		for ( i = 0; i <= Stari[stare_Curenta].nr_Tranzitii; i++) {
			if ( Stari[stare_Curenta].in[i] == Banda_final[banda] ) {
				Tranzitie_valida = 1;
				Banda_final[banda] = Stari[stare_Curenta].out[i];
				banda = banda + Stari[stare_Curenta].miscare_Cursor[i];
				stare_Curenta = Stari[stare_Curenta].stare_Urmatoare[i];
			}
		}
		if (Tranzitie_valida == 0) {
			fprintf(out, "Se agata!");
			return 0;
		}
	}

	fprintf(out, "%s", Banda_final);
	return 0;
}
