import { IFormation, NewFormation } from './formation.model';

export const sampleWithRequiredData: IFormation = {
  id: 2825,
};

export const sampleWithPartialData: IFormation = {
  id: 21641,
  imageFormation: '../fake-data/blob/hipster.png',
  imageFormationContentType: 'unknown',
  typeFormation: 'INITIALE',
  duree: 'tic-tac aïe admirablement',
};

export const sampleWithFullData: IFormation = {
  id: 19631,
  nomFormation: 'tchou tchouu',
  imageFormation: '../fake-data/blob/hipster.png',
  imageFormationContentType: 'unknown',
  typeFormation: 'CONTINUE',
  duree: 'voir surmonter',
  admission: 'CONCOURS',
  diplomeRequis: 'BT',
  ficheFormation: '../fake-data/blob/hipster.png',
  ficheFormationContentType: 'unknown',
};

export const sampleWithNewData: NewFormation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
