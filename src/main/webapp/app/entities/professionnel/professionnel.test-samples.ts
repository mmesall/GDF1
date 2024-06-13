import dayjs from 'dayjs/esm';

import { IProfessionnel, NewProfessionnel } from './professionnel.model';

export const sampleWithRequiredData: IProfessionnel = {
  id: 6609,
  profession: 'souvenir sauter secouriste',
  nom: 'sur de peur que',
  prenom: 'beaucoup d’autant que',
  cni: 2773,
};

export const sampleWithPartialData: IProfessionnel = {
  id: 207,
  profession: 'hors de hors',
  nom: 'au-devant',
  prenom: 'vis-à-vie de hirsute auprès de',
  lieuNaiss: 'vroum actionnaire',
  sexe: 'FEMME',
  telephone: 879,
  adressePhysique: 'à bas de hebdomadaire chef',
  cni: 11320,
};

export const sampleWithFullData: IProfessionnel = {
  id: 17693,
  profession: "à l'entour de",
  nom: 'hé lorsque au-dedans de',
  prenom: 'commis',
  dateNaiss: dayjs('2024-06-04'),
  lieuNaiss: 'éclaircir pour',
  sexe: 'FEMME',
  telephone: 15061,
  adressePhysique: 'hebdomadaire envers conseil d’administration',
  regionResidence: 'DAKAR',
  departResidence: 'KEBEMERE',
  email: 'Anicette97@gmail.com',
  cni: 26544,
};

export const sampleWithNewData: NewProfessionnel = {
  profession: "compenser à l'insu de moyennant",
  nom: 'ressortir',
  prenom: 'groin groin vraiment prétendre',
  cni: 3122,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
