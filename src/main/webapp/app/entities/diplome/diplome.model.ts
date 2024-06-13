import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { Mention } from 'app/entities/enumerations/mention.model';

export interface IDiplome {
  id: number;
  intitule?: string | null;
  domaine?: keyof typeof NomFiliere | null;
  niveau?: keyof typeof NiveauEtude | null;
  mention?: keyof typeof Mention | null;
  anneeObtention?: string | null;
  etablissement?: string | null;
  document?: string | null;
  documentContentType?: string | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
  demandeur?: IDemandeur | null;
}

export type NewDiplome = Omit<IDiplome, 'id'> & { id: null };
