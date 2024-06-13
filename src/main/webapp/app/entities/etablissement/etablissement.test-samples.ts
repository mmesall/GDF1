import { IEtablissement, NewEtablissement } from './etablissement.model';

export const sampleWithRequiredData: IEtablissement = {
  id: 29802,
  region: 'KOLDA',
  departement: 'KOUNGHEUR',
  statut: 'PRIVE',
};

export const sampleWithPartialData: IEtablissement = {
  id: 11570,
  nomEtablissement: 'CRFP_SAINT_LOUIS',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  region: 'LOUGA',
  departement: 'KOLDA',
  email: 'Francisque_Roger@gmail.com',
  statut: 'PUBLIC',
  filiere: 'HYDRAULIQUE',
  autreSerie: 'présidence',
  autreNomEtablissement: 'lors de',
};

export const sampleWithFullData: IEtablissement = {
  id: 16414,
  nomEtablissement: 'CFP_DIAKHAO',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  region: 'LOUGA',
  departement: 'GOUDIRY',
  email: 'Tatiana.Leclercq52@hotmail.fr',
  telephone: 18318,
  typeEtablissement: 'LYCEE_TECH',
  statut: 'PRIVE',
  autreRegion: 'pas mal planquer mince',
  autreDepartement: 'subito secouriste sauvage',
  cfp: 'ICCM',
  lycee: 'LTIM_KEDOUGOU',
  filiere: 'SPORT',
  serie: 'S3',
  autreFiliere: 'présidence quasi loin de',
  autreSerie: 'autrement adepte au défaut de',
  autreNomEtablissement: 'vers violer',
};

export const sampleWithNewData: NewEtablissement = {
  region: 'DIOURBEL',
  departement: 'BIGNONA',
  statut: 'PRIVE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
