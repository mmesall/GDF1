import dayjs from 'dayjs/esm';
import { IFormation } from 'app/entities/formation/formation.model';
import { NomEtablissement } from 'app/entities/enumerations/nom-etablissement.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

export interface IConcours {
  id: number;
  nomConcours?: string | null;
  nomEtablissement?: keyof typeof NomEtablissement | null;
  niveauEtude?: keyof typeof NiveauEtude | null;
  dateOuverture?: dayjs.Dayjs | null;
  dateCloture?: dayjs.Dayjs | null;
  dateConcours?: dayjs.Dayjs | null;
  affiche?: string | null;
  afficheContentType?: string | null;
  formation?: IFormation | null;
}

export type NewConcours = Omit<IConcours, 'id'> & { id: null };
