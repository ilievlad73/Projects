/* Ilie Nicolae Vlad */
/* main.c */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "Biblioteca.h"


int main(int argc , char const *argv[] )
{

	if ( argc != 3 )
	{
		printf("Numar incorect de parametrii\n");
		return 0;
	}
	FILE * in = fopen(argv[1], "r");
	FILE *out = fopen(argv[2], "w");

	void* coada = InitQ(sizeof(TSwitch));		// initializare coada
	char line[256]; 	// variabila auxiliara
	fgets(line, sizeof(line), in);		// se citeste marimea vectorului de stive
	int M = atoi(line);					// se converteste la intreg
	TVSt* stive = InitTVSt(M);			// alocare vector de stive

	while ( fgets(line, sizeof(line), in) ) {			// se parcurge fisierul

		char* operatie = strtok(line, " \n");

		if ( operatie ) {
			int indice = GasesteCerinta(operatie);
			switch ( indice ) {

			case 1: //add
			{
				char* id = strtok(NULL, " \n");
				int id_intreg = atoi(id);
				char* nume = strtok(NULL, " \n");
				char* Ip = strtok(NULL, " \n");
				char* functionare = strtok(NULL, " \n");

				if ( !strcmp("SINGLE", functionare) ) {
					add(stive, id_intreg, nume, Ip, "SINGLE", 0, "NON_BASE", coada);

				}
				if ( !strcmp("STACK", functionare) ) {
					char* id_stiva = strtok(NULL, " \n");
					int id_stiva_real = atoi(id_stiva);
					char* mod = strtok(NULL, " \n");
					add(stive, id_intreg, nume, Ip, "STACK", id_stiva_real, mod, coada);
				}
				break;
			}
			case 2: //show
			{
				Afisare(stive, coada, out);
				break;
			}
			case 3: //del
			{
				char* id = strtok(NULL, " \n");
				int id_real = atoi(id);
				del(stive, id_real, coada);
				break;
			}
			case 4:  //ipmin
			{
				IPMIN(stive, coada, out);
				break;
			}
			case 5: //set
			{
				char* id = strtok(NULL, " \n");
				int id_real = atoi(id);
				char* mod = strtok(NULL, " \n");

				if (!strcmp(mod, "SINGLE"))
					set(stive, id_real, "SINGLE", 0, "Nu_conteaza", coada);

				if (!strcmp(mod, "STACK")) {
					char* stiva_viitoare = strtok(NULL, " \n");
					int stiva_viitoare_reala = atoi(stiva_viitoare);
					char* mod = strtok(NULL, " \n");
					set(stive, id_real, "STACK", stiva_viitoare_reala, mod, coada);
				}
				break;
			}
			default: break;
			}
		}
	}

	DistrQ(&coada); //eliberare memorie si inchidere fisiere
	DistrugeStive(stive);
	fclose(in);
	fclose(out);
	
	return 0;
}
















