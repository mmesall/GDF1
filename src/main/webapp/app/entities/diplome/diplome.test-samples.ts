import { IDiplome, NewDiplome } from './diplome.model';

export const sampleWithRequiredData: IDiplome = {
  id: 19960,
  domaine: 'AGRI_ELEVAGE',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithPartialData: IDiplome = {
  id: 17945,
  domaine: 'ARTISANAT',
  niveau: 'BT3',
  anneeObtention: 'mairie adepte jeune',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithFullData: IDiplome = {
  id: 18910,
  intitule: 'à condition que',
  domaine: 'ELEVAGE',
  niveau: 'BEP2',
  mention: 'ASSEZ_BIEN',
  anneeObtention: 'égoïste gai',
  etablissement: 'blême',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
};

export const sampleWithNewData: NewDiplome = {
  domaine: 'SANTE_BIOLOGIE_CHIMIE',
  document: '../fake-data/blob/hipster.png',
  documentContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
