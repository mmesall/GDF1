import dayjs from 'dayjs/esm';

import { IAgent, NewAgent } from './agent.model';

export const sampleWithRequiredData: IAgent = {
  id: 5240,
  matricule: 'terne',
};

export const sampleWithPartialData: IAgent = {
  id: 17283,
  matricule: 'trop',
  lieuNaiss: 'délégation ha vouh',
  sexe: 'HOMME',
  telephone: 19632,
  email: 'Pelagie61@gmail.com',
};

export const sampleWithFullData: IAgent = {
  id: 5539,
  matricule: 'que',
  nomAgent: 'pourvu que',
  prenom: 'fort de façon à ce que',
  dateNaiss: dayjs('2024-06-04'),
  lieuNaiss: 'du moment que devant',
  sexe: 'HOMME',
  telephone: 22187,
  email: 'Achille80@hotmail.fr',
};

export const sampleWithNewData: NewAgent = {
  matricule: 'étonner',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
