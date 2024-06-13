import dayjs from 'dayjs/esm';

import { IFormationInitiale, NewFormationInitiale } from './formation-initiale.model';

export const sampleWithRequiredData: IFormationInitiale = {
  id: 25925,
};

export const sampleWithPartialData: IFormationInitiale = {
  id: 2439,
  diplomeRequis: 'AUTRES',
  filiere: 'BEAUTE_ESTHETIQUE',
  serie: 'F6',
  nomConcours: 'bien que parlementaire',
  dateCloture: dayjs('2024-06-05'),
  dateConcours: dayjs('2024-06-04'),
  nomDiplome: 'BEP',
  nomDebouche: 'en decà de tant pschitt',
};

export const sampleWithFullData: IFormationInitiale = {
  id: 2513,
  nomFormationI: 'extra',
  duree: 'tendre sans que',
  admission: 'PC',
  diplomeRequis: 'BEP',
  niveauEtude: 'TROISIEME',
  ficheFormation: '../fake-data/blob/hipster.png',
  ficheFormationContentType: 'unknown',
  filiere: 'MENUISERIE_BOIS',
  serie: 'S4',
  cfp: 'CFP_SAINT_MARC',
  lycee: 'LTSLL_DAKAR',
  nomConcours: 'aussi toc plouf',
  dateOuverture: dayjs('2024-06-04'),
  dateCloture: dayjs('2024-06-05'),
  dateConcours: dayjs('2024-06-04'),
  nomDiplome: 'AUTRES',
  nomDebouche: 'touriste',
};

export const sampleWithNewData: NewFormationInitiale = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
