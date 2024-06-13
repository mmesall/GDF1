import dayjs from 'dayjs/esm';

import { ICandidatureE, NewCandidatureE } from './candidature-e.model';

export const sampleWithRequiredData: ICandidatureE = {
  id: 24494,
};

export const sampleWithPartialData: ICandidatureE = {
  id: 23872,
  dateDebutOffre: dayjs('2024-06-04'),
  dateDepot: dayjs('2024-06-05'),
  resultat: 'APPROUVE',
};

export const sampleWithFullData: ICandidatureE = {
  id: 11490,
  offreFormation: 'SPORT',
  dateDebutOffre: dayjs('2024-06-05'),
  dateFinOffre: dayjs('2024-06-05'),
  dateDepot: dayjs('2024-06-05'),
  resultat: 'VALIDE',
};

export const sampleWithNewData: NewCandidatureE = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
