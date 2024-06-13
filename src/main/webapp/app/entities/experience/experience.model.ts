import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';

export interface IExperience {
  id: number;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  nomEntreprise?: string | null;
  posteOccupe?: string | null;
  mission?: string | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
  demandeur?: IDemandeur | null;
}

export type NewExperience = Omit<IExperience, 'id'> & { id: null };
