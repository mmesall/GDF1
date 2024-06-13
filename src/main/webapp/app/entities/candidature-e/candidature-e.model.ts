import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IFormationInitiale } from 'app/entities/formation-initiale/formation-initiale.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { Resultat } from 'app/entities/enumerations/resultat.model';

export interface ICandidatureE {
  id: number;
  offreFormation?: keyof typeof NomFiliere | null;
  dateDebutOffre?: dayjs.Dayjs | null;
  dateFinOffre?: dayjs.Dayjs | null;
  dateDepot?: dayjs.Dayjs | null;
  resultat?: keyof typeof Resultat | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  formationInitiale?: IFormationInitiale | null;
  etablissement?: IEtablissement | null;
}

export type NewCandidatureE = Omit<ICandidatureE, 'id'> & { id: null };
