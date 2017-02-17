/* Ilie Nicolae Vlad 311CB */
#include "arb.h"
#define MAX 10000

int main(int argc , char const *argv[] )
{
	int i, j;
	TArb arb = InitA();				// initializare arbore
	FILE* incod = fopen(argv[1], "r");			// deschidere fisiere
	FILE* outarbore = fopen(argv[3], "w");
	FILE* intext = fopen(argv[2], "r");
	FILE* outtext = fopen(argv[4], "w");
	char line[256];			// variabila ajutatoare

	fgets(line, sizeof(line), incod);
	int M = atoi(line);

	for (i = 0; i < M; i++) {			//parcurgere codificari
		fgets(line, sizeof(line), incod);
		char* info = strtok(line, " \n");
		char* codificare = strtok(NULL, " \n");
		AddCodificare(&arb->st, codificare, info[0], 0);	// adaugare in arbore
	}
	AfiseazaArbore(arb, outarbore);

	char* separator, auxiliar;						//setam separatorul
	separator = (char*)malloc(2 * sizeof(char));
	auxiliar = fgetc(incod);
	separator[0] = auxiliar;
	separator[1] = 0;								// punem caracterul NULL la sfarsitul lui
	fgets(line, sizeof(line), incod);					//trecem la linia urmatoare


	fgets(line, sizeof(line), incod);					// setam numarul de operatii
	int NrOperatii = atoi(line);

	for (j = 0; j < NrOperatii; j++) {
		fgets(line, sizeof(line), incod);				//citire linia
		char* operatie = strtok(line, " \n");		//se afla operatie ce trebuie executata
		if ( !strcmp(operatie, "add") ) {
			char* info = strtok(NULL, " \n");
			char* codificare = strtok(NULL, " \n");
			del(info[0], &arb);						//se elimina din arbore informatia
			AddCodificare(&arb->st, codificare, info[0], 0);	//se adauga
			AfiseazaArbore(arb, outarbore);
		}
		if ( !strcmp(operatie, "delete") ) {
			char* info = strtok(NULL, " \n");
			del(info[0], &arb);				// se elimina din arbore informatia
			AfiseazaArbore(arb, outarbore);
		}
	}

	char text[MAX];				//variabila auxiliara pentru citirea randurilor
	while ( !feof(intext)  ) {		// cat timp nu este sfarsitul fisierului

		fgets(text, sizeof(text), intext);		// se citeste linia

		if ( !strcmp(text, "\n") && !feof(intext) ) {		//se verifica daca este \n sau sfarist
			fprintf(outtext, "\n");			//se afiseaza in fisier \n
		}
		else if ( !feof(intext)) {			//altfel se rupe textul dupa separator
			char* token;
			token = strtok(text, separator);
			while ( token != NULL ) {

				if (token[0] == ' ') {
					fprintf(outtext, " ");	//daca gasim ' ' il afisam si mutam token ul la caracterul urmator
					token++;
				}

				if ( CautaInfo(arb->st, token, 0) != 0)	// daca se gaseste informatia in arbore
					fprintf(outtext, "%c", CautaInfo(arb->st, token, 0));	// se afiseaza

				token = strtok(NULL, separator);
			}
			fprintf(outtext, "\n");		// dupa decodificarea liniei se afiseaza un rand nou in fisier
		}
	}

	free(separator);		//eliberare memorie
	DistrArb(&arb);
	fclose(incod);			// inchidere fisiere
	fclose(outarbore);
	fclose(intext);
	fclose(outtext);
	
	return 0;
}
