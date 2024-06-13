import dayjs from 'dayjs/esm';
import { IFormation } from 'app/entities/formation/formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';
import { CFP } from 'app/entities/enumerations/cfp.model';
import { LYCEE } from 'app/entities/enumerations/lycee.model';
import { DiplomeObtenu } from 'app/entities/enumerations/diplome-obtenu.model';

export interface IFormationInitiale {
  id: number;
  nomFormationI?: string | null;
  duree?: string | null;
  admission?: keyof typeof Admission | null;
  diplomeRequis?: keyof typeof DiplomeRequis | null;
  niveauEtude?: keyof typeof NiveauEtude | null;
  ficheFormation?: string | null;
  ficheFormationContentType?: string | null;
  filiere?: keyof typeof NomFiliere | null;
  serie?: keyof typeof NomSerie | null;
  cfp?: keyof typeof CFP | null;
  lycee?: keyof typeof LYCEE | null;
  nomConcours?: string | null;
  dateOuverture?: dayjs.Dayjs | null;
  dateCloture?: dayjs.Dayjs | null;
  dateConcours?: dayjs.Dayjs | null;
  nomDiplome?: keyof typeof DiplomeObtenu | null;
  nomDebouche?: string | null;
  formation?: IFormation | null;
}

export type NewFormationInitiale = Omit<IFormationInitiale, 'id'> & { id: null };
