import dayjs from 'dayjs/esm';

import { IExperience, NewExperience } from './experience.model';

export const sampleWithRequiredData: IExperience = {
  id: 26834,
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'limiter oh bien que',
  posteOccupe: 'rectorat d’autant que',
};

export const sampleWithPartialData: IExperience = {
  id: 32236,
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'pourvu que',
  posteOccupe: 'afin que au point que pschitt',
  mission: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IExperience = {
  id: 6787,
  dateDebut: dayjs('2024-06-04'),
  dateFin: dayjs('2024-06-04'),
  nomEntreprise: 'personnel',
  posteOccupe: 'devant avant',
  mission: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewExperience = {
  dateDebut: dayjs('2024-06-05'),
  dateFin: dayjs('2024-06-05'),
  nomEntreprise: 'expédier',
  posteOccupe: 'sitôt que tarder fort',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
