import { IBailleur, NewBailleur } from './bailleur.model';

export const sampleWithRequiredData: IBailleur = {
  id: 28364,
  nomBailleur: 'pas mal',
};

export const sampleWithPartialData: IBailleur = {
  id: 7048,
  nomBailleur: 'considérer',
  nbrePC: 17071,
};

export const sampleWithFullData: IBailleur = {
  id: 13805,
  nomBailleur: 'fonctionnaire équipe de recherche extrêmement',
  budgetPrevu: 16852.32,
  budgetDepense: 18263.87,
  budgetRestant: 26286.31,
  nbrePC: 18909,
};

export const sampleWithNewData: NewBailleur = {
  nomBailleur: 'séduire après-demain vis-à-vie de',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
