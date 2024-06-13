import { IFormation } from 'app/entities/formation/formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';
import { CFP } from 'app/entities/enumerations/cfp.model';
import { LYCEE } from 'app/entities/enumerations/lycee.model';
import { DiplomeObtenu } from 'app/entities/enumerations/diplome-obtenu.model';

export interface IFormationContinue {
  id: number;
  nomFormationC?: string | null;
  duree?: string | null;
  admission?: keyof typeof Admission | null;
  diplomeRequis?: keyof typeof DiplomeRequis | null;
  niveauEtude?: keyof typeof NiveauEtude | null;
  filiere?: keyof typeof NomFiliere | null;
  serie?: keyof typeof NomSerie | null;
  cfp?: keyof typeof CFP | null;
  lycee?: keyof typeof LYCEE | null;
  ficheFormation?: string | null;
  ficheFormationContentType?: string | null;
  libellePC?: string | null;
  montantPriseEnCharge?: number | null;
  coutFormation?: number | null;
  detailPC?: string | null;
  nomDiplome?: keyof typeof DiplomeObtenu | null;
  autreDiplome?: string | null;
  nomDebouche?: string | null;
  formation?: IFormation | null;
}

export type NewFormationContinue = Omit<IFormationContinue, 'id'> & { id: null };
