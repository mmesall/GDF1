import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Profil } from 'app/entities/enumerations/profil.model';

export interface IDemandeur {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  sexe?: keyof typeof Sexe | null;
  telephone?: number | null;
  email?: string | null;
  profil?: keyof typeof Profil | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  dossier?: IDossier | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
}

export type NewDemandeur = Omit<IDemandeur, 'id'> & { id: null };
