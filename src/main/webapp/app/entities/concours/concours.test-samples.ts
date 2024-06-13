import dayjs from 'dayjs/esm';

import { IConcours, NewConcours } from './concours.model';

export const sampleWithRequiredData: IConcours = {
  id: 22610,
};

export const sampleWithPartialData: IConcours = {
  id: 3138,
  nomConcours: 'marcher',
  niveauEtude: 'TITRE_PROFESSIONNEL',
  dateOuverture: dayjs('2024-06-05'),
  affiche: '../fake-data/blob/hipster.png',
  afficheContentType: 'unknown',
};

export const sampleWithFullData: IConcours = {
  id: 11453,
  nomConcours: 'suffisamment',
  nomEtablissement: 'CFP_GUINGUINEO',
  niveauEtude: 'BT',
  dateOuverture: dayjs('2024-06-05'),
  dateCloture: dayjs('2024-06-05'),
  dateConcours: dayjs('2024-06-05'),
  affiche: '../fake-data/blob/hipster.png',
  afficheContentType: 'unknown',
};

export const sampleWithNewData: NewConcours = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
