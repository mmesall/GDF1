import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { TypePiece } from 'app/entities/enumerations/type-piece.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NIVEAUCOMP } from 'app/entities/enumerations/niveaucomp.model';

export interface IDossier {
  id: number;
  numDossier?: string | null;
  prenom?: string | null;
  nom?: string | null;
  nomUtilisateur?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  regionNaiss?: keyof typeof NomRegion | null;
  departementNaiss?: keyof typeof NomDepartement | null;
  typePiece?: keyof typeof TypePiece | null;
  numeroPiece?: number | null;
  sexe?: keyof typeof Sexe | null;
  regionResidence?: keyof typeof NomRegion | null;
  depResidence?: keyof typeof NomDepartement | null;
  adresseResidence?: string | null;
  telephone1?: string | null;
  telephone2?: string | null;
  email?: string | null;
  niveauFormation?: keyof typeof NiveauEtude | null;
  specialite?: keyof typeof NomFiliere | null;
  intituleDiplome?: string | null;
  diplome?: string | null;
  diplomeContentType?: string | null;
  anneeObtention?: dayjs.Dayjs | null;
  lieuObtention?: string | null;
  cv?: string | null;
  cvContentType?: string | null;
  lettreMotivation?: string | null;
  lettreMotivationContentType?: string | null;
  profession?: string | null;
  autreSpecialite?: string | null;
  nomCompetence?: string | null;
  niveauCompetence?: keyof typeof NIVEAUCOMP | null;
  intituleExperience?: string | null;
  posteOccupe?: string | null;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  nomEntreprise?: string | null;
  mission?: string | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
}

export type NewDossier = Omit<IDossier, 'id'> & { id: null };
