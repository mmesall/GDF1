import { IFormationContinue, NewFormationContinue } from './formation-continue.model';

export const sampleWithRequiredData: IFormationContinue = {
  id: 8816,
};

export const sampleWithPartialData: IFormationContinue = {
  id: 25348,
  nomFormationC: 'après que sauvage fort',
  niveauEtude: 'TITRE_PROFESSIONNEL',
  filiere: 'ENVIRONNEMENT',
  serie: 'F6',
  cfp: 'CFP_SALEMATA',
  lycee: 'LTID_DAKAR',
  ficheFormation: '../fake-data/blob/hipster.png',
  ficheFormationContentType: 'unknown',
  coutFormation: 1371.92,
  autreDiplome: 'aux alentours de paf',
  nomDebouche: 'efficace tsoin-tsoin areu areu',
};

export const sampleWithFullData: IFormationContinue = {
  id: 25508,
  nomFormationC: 'payer grrr',
  duree: 'assez via réjouir',
  admission: 'CONCOURS',
  diplomeRequis: 'MASTER',
  niveauEtude: 'AUTRES',
  filiere: 'MENUISERIE_BOIS',
  serie: 'S4',
  cfp: 'CFP_DAGANA',
  lycee: 'LTIM_KEDOUGOU',
  ficheFormation: '../fake-data/blob/hipster.png',
  ficheFormationContentType: 'unknown',
  libellePC: 'rédaction terne retracer',
  montantPriseEnCharge: 30759.49,
  coutFormation: 19291.43,
  detailPC: '../fake-data/blob/hipster.txt',
  nomDiplome: 'METP',
  autreDiplome: 'de façon à ce que déclarer',
  nomDebouche: 'snif super',
};

export const sampleWithNewData: NewFormationContinue = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
