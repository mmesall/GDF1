import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { TypeFormation } from 'app/entities/enumerations/type-formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';

export interface IFormation {
  id: number;
  nomFormation?: string | null;
  imageFormation?: string | null;
  imageFormationContentType?: string | null;
  typeFormation?: keyof typeof TypeFormation | null;
  duree?: string | null;
  admission?: keyof typeof Admission | null;
  diplomeRequis?: keyof typeof DiplomeRequis | null;
  ficheFormation?: string | null;
  ficheFormationContentType?: string | null;
  etablissements?: IEtablissement[] | null;
}

export type NewFormation = Omit<IFormation, 'id'> & { id: null };
