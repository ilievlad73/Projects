/* Ilie Nicolae Vlad 311CB */

#include "hash.h"

int main(int argc , char const *argv[] )
{

	if (argc != 4)			/* numari incorect de parametrii */
	{

		printf("Numar incorect de parametrii\n");
		return 0;

	}

	FILE * in = fopen(argv[2], "r");		/* fisierele de date */
	FILE *out = fopen(argv[3], "w");

	char line[256];				 /* variabila auxiliara */
	int M = atoi(argv[1]); 	               /*  convertim la intreg */

	THash* hash = initTHash(M, functieD);
	if (!hash)
	{
		printf("Nu a reusit alocarea\n");	/* verificare */
		return 0;
	}


	while ( fgets(line, sizeof(line), in) )	/* citim linie cu linie */
	{
		char *operatie = strtok(line, " \n");	/* rupem linia in 3 */
		char *key = strtok(NULL, " \n");
		char *value = strtok(NULL, " \n");

		if (operatie) {

			int k =  GasesteCerinta(operatie);		/* gasim operatia */

			switch (k) {

			case 1:   /* adaugare */
			{
				hash = set(key, value, &hash, 0);
				M = hash->M;
				break;
			}
			case 2: /* get value */
			{
				char *cris = get(hash, key);
				if ( cris == NULL)
					fprintf(out, "NULL\n");
				else
					fprintf(out, "%s\n", cris);
				break;

			}
			case 3: 	/* stergem element din tabela */
			{
				Remove(hash, key);
				break;
			}
			case 4: /* afisare informatii tabela (value) */
			{
				print(hash, out);
				break;
			}
			case 5:		/* afisare informatii din lista i */
			{
				if ( key[0] == '-' ) /* verificam ca bucket-ul sa existe */
					break;
				int r = atoi(key);
				if ( r >= M )
					break;

				print_list(hash, r, out);
				break;
			}
			default:
				break;			/* nu corespunde niciunei operatii */
			}
		}
	}

	fclose(in);		/* inchidere fisiere */
	fclose(out);
	distrugeTHash(&hash);			/* eliberare memorie hash */

	return 0;
}

