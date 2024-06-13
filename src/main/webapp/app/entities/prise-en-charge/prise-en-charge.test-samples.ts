import { IPriseEnCharge, NewPriseEnCharge } from './prise-en-charge.model';

export const sampleWithRequiredData: IPriseEnCharge = {
  id: 16995,
};

export const sampleWithPartialData: IPriseEnCharge = {
  id: 7264,
  libelle: 'y déclencher miam',
  montantPC: 7041.83,
};

export const sampleWithFullData: IPriseEnCharge = {
  id: 1051,
  libelle: 'impressionner super',
  montantPC: 18046.9,
};

export const sampleWithNewData: NewPriseEnCharge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
