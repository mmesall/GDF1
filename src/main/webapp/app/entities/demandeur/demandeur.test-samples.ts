import dayjs from 'dayjs/esm';

import { IDemandeur, NewDemandeur } from './demandeur.model';

export const sampleWithRequiredData: IDemandeur = {
  id: 5502,
};

export const sampleWithPartialData: IDemandeur = {
  id: 7491,
  dateNaiss: dayjs('2024-06-05'),
  lieuNaiss: 'du fait que zzzz',
  sexe: 'HOMME',
  email: 'Albane_Rodriguez75@hotmail.fr',
  profil: 'ETUDIANT',
};

export const sampleWithFullData: IDemandeur = {
  id: 18109,
  nom: 'hé',
  prenom: 'à raison de oh',
  dateNaiss: dayjs('2024-06-04'),
  lieuNaiss: 'après que après que',
  sexe: 'FEMME',
  telephone: 31861,
  email: 'Arcade.Renault90@yahoo.fr',
  profil: 'ETUDIANT',
};

export const sampleWithNewData: NewDemandeur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
