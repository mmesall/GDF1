import dayjs from 'dayjs/esm';

import { IDossier, NewDossier } from './dossier.model';

export const sampleWithRequiredData: IDossier = {
  id: 21521,
  prenom: 'à moins de ha ha',
  nom: 'porte-parole clac',
  posteOccupe: 'éloigner du fait que chef de cuisine',
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'responsable conseil d’administration quasiment',
};

export const sampleWithPartialData: IDossier = {
  id: 10970,
  numDossier: 'coac coac',
  prenom: 'à moins de fonctionnaire',
  nom: 'turquoise du fait que',
  nomUtilisateur: 'affranchir hé',
  dateNaiss: dayjs('2024-06-05'),
  lieuNaiss: 'à peine au-devant turquoise',
  typePiece: 'CNI',
  numeroPiece: 26817,
  sexe: 'HOMME',
  adresseResidence: 'hier adorable',
  niveauFormation: 'BEP2',
  specialite: 'TOURISME',
  intituleDiplome: 'renaître terminer',
  diplome: '../fake-data/blob/hipster.png',
  diplomeContentType: 'unknown',
  lieuObtention: 'atténuer',
  cv: '../fake-data/blob/hipster.png',
  cvContentType: 'unknown',
  lettreMotivation: '../fake-data/blob/hipster.png',
  lettreMotivationContentType: 'unknown',
  autreSpecialite: 'magenta écarter bien que',
  nomCompetence: 'areu areu afin que',
  posteOccupe: 'presque confondre jeune',
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-04'),
  nomEntreprise: 'quand ? en plus de',
  mission: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDossier = {
  id: 16751,
  numDossier: 'admirablement vraisemblablement ha ha',
  prenom: 'dring conseil municipal',
  nom: 'membre à vie cocorico membre à vie',
  nomUtilisateur: 'malgré parce que mieux',
  dateNaiss: dayjs('2024-06-04'),
  lieuNaiss: 'de façon à croâ clac',
  regionNaiss: 'KAFFRINE',
  departementNaiss: 'FATICK',
  typePiece: 'PASSEPORT',
  numeroPiece: 5010,
  sexe: 'FEMME',
  regionResidence: 'KAOLACK',
  depResidence: 'MEDINA_YORO_FOULAH',
  adresseResidence: 'quand si reproduire',
  telephone1: 'avare',
  telephone2: 'dans la mesure où rallier membre à vie',
  email: 'Hincmar73@hotmail.fr',
  niveauFormation: 'TROISIEME',
  specialite: 'ENVIRONNEMENT',
  intituleDiplome: 'gratis terne',
  diplome: '../fake-data/blob/hipster.png',
  diplomeContentType: 'unknown',
  anneeObtention: dayjs('2024-06-05'),
  lieuObtention: 'attarder miam',
  cv: '../fake-data/blob/hipster.png',
  cvContentType: 'unknown',
  lettreMotivation: '../fake-data/blob/hipster.png',
  lettreMotivationContentType: 'unknown',
  profession: 'recta',
  autreSpecialite: 'naguère',
  nomCompetence: 'sympathique assurément du côté de',
  niveauCompetence: 'INTERMEDIAIRE',
  intituleExperience: 'de façon que',
  posteOccupe: 'après que',
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'renverser chef de cuisine cesser',
  mission: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDossier = {
  prenom: 'incorporer',
  nom: 'pour',
  posteOccupe: 'boum',
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'aïe',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
