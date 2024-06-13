import dayjs from 'dayjs/esm';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { IFormationContinue } from 'app/entities/formation-continue/formation-continue.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { Resultat } from 'app/entities/enumerations/resultat.model';

export interface ICandidatureP {
  id: number;
  offreFormation?: keyof typeof NomFiliere | null;
  dateDebutOffre?: dayjs.Dayjs | null;
  dateFinOffre?: dayjs.Dayjs | null;
  dateDepot?: dayjs.Dayjs | null;
  resultat?: keyof typeof Resultat | null;
  professionnel?: IProfessionnel | null;
  formationContinue?: IFormationContinue | null;
  etablissement?: IEtablissement | null;
}

export type NewCandidatureP = Omit<ICandidatureP, 'id'> & { id: null };
