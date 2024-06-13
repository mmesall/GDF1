import dayjs from 'dayjs/esm';

import { IEtudiant, NewEtudiant } from './etudiant.model';

export const sampleWithRequiredData: IEtudiant = {
  id: 19816,
  carteEtudiant: 'en',
  nom: 'de sorte que',
  prenom: 'zzzz',
  cni: 15931,
};

export const sampleWithPartialData: IEtudiant = {
  id: 25527,
  carteEtudiant: 'deçà comme',
  nom: 'apprivoiser fabriquer ha ha',
  prenom: 'de peur de afin que',
  dateNaiss: dayjs('2024-06-04'),
  telephone: 9163,
  departResidence: 'FATICK',
  cni: 10052,
};

export const sampleWithFullData: IEtudiant = {
  id: 32564,
  carteEtudiant: 'du côté de responsable parfois',
  nom: 'tailler',
  prenom: 'pour que debout',
  dateNaiss: dayjs('2024-06-04'),
  lieuNaiss: 'après que partout',
  sexe: 'HOMME',
  telephone: 9606,
  adressePhysique: 'bzzz',
  regionResidence: 'THIES',
  departResidence: 'GOUDOMP',
  email: 'Azalee_Hubert@yahoo.fr',
  cni: 11910,
};

export const sampleWithNewData: NewEtudiant = {
  carteEtudiant: 'vu que paf ha',
  nom: 'puisque électorat',
  prenom: 'avant de ficher mal',
  cni: 9436,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
