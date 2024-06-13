import dayjs from 'dayjs/esm';

import { ICandidatureP, NewCandidatureP } from './candidature-p.model';

export const sampleWithRequiredData: ICandidatureP = {
  id: 19888,
};

export const sampleWithPartialData: ICandidatureP = {
  id: 4044,
  dateDepot: dayjs('2024-06-05'),
};

export const sampleWithFullData: ICandidatureP = {
  id: 18169,
  offreFormation: 'DEVELOPPEMENT_TERRITORIAL',
  dateDebutOffre: dayjs('2024-06-05'),
  dateFinOffre: dayjs('2024-06-05'),
  dateDepot: dayjs('2024-06-05'),
  resultat: 'REJETE',
};

export const sampleWithNewData: NewCandidatureP = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
