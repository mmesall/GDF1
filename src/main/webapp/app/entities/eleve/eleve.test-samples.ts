import dayjs from 'dayjs/esm';

import { IEleve, NewEleve } from './eleve.model';

export const sampleWithRequiredData: IEleve = {
  id: 23254,
  nom: 'tellement propre mettre',
  prenom: 'au-dessous',
  niveauEtude: 'MASTER',
};

export const sampleWithPartialData: IEleve = {
  id: 27580,
  nom: 'hi soudain miaou',
  prenom: 'à cause de',
  lieuNaiss: 'commis',
  adressePhysique: 'antagoniste',
  departResidence: 'BAMBAEY',
  niveauEtude: 'TECHNICIENSPECIALISE',
  cni: 6617,
};

export const sampleWithFullData: IEleve = {
  id: 20539,
  nom: 'vouh patientèle',
  prenom: 'carrément',
  dateNaiss: dayjs('2024-06-05'),
  lieuNaiss: 'bang',
  sexe: 'HOMME',
  telephone: 76,
  adressePhysique: 'étant donné que pleurer',
  regionResidence: 'AUTRE',
  departResidence: 'GUEDIAWAYE',
  niveauEtude: 'EQUIVALENT_BAC',
  cni: 29936,
};

export const sampleWithNewData: NewEleve = {
  nom: 'rudement ouch',
  prenom: 'zzzz',
  niveauEtude: 'CAP',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
