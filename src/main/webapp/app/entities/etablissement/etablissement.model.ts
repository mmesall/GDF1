import { IFormation } from 'app/entities/formation/formation.model';
import { NomEtablissement } from 'app/entities/enumerations/nom-etablissement.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { TypeEtablissement } from 'app/entities/enumerations/type-etablissement.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { CFP } from 'app/entities/enumerations/cfp.model';
import { LYCEE } from 'app/entities/enumerations/lycee.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';

export interface IEtablissement {
  id: number;
  nomEtablissement?: keyof typeof NomEtablissement | null;
  photo?: string | null;
  photoContentType?: string | null;
  region?: keyof typeof NomRegion | null;
  departement?: keyof typeof NomDepartement | null;
  email?: string | null;
  telephone?: number | null;
  typeEtablissement?: keyof typeof TypeEtablissement | null;
  statut?: keyof typeof StatutEtab | null;
  autreRegion?: string | null;
  autreDepartement?: string | null;
  cfp?: keyof typeof CFP | null;
  lycee?: keyof typeof LYCEE | null;
  filiere?: keyof typeof NomFiliere | null;
  serie?: keyof typeof NomSerie | null;
  autreFiliere?: string | null;
  autreSerie?: string | null;
  autreNomEtablissement?: string | null;
  formations?: IFormation[] | null;
}

export type NewEtablissement = Omit<IEtablissement, 'id'> & { id: null };
