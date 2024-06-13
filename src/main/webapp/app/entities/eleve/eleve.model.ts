import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

export interface IEleve {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  sexe?: keyof typeof Sexe | null;
  telephone?: number | null;
  adressePhysique?: string | null;
  regionResidence?: keyof typeof NomRegion | null;
  departResidence?: keyof typeof NomDepartement | null;
  niveauEtude?: keyof typeof NiveauEtude | null;
  cni?: number | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewEleve = Omit<IEleve, 'id'> & { id: null };
