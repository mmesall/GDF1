import { IServiceMFPAI, NewServiceMFPAI } from './service-mfpai.model';

export const sampleWithRequiredData: IServiceMFPAI = {
  id: 2974,
  chefService: "exclure gens d'après",
};

export const sampleWithPartialData: IServiceMFPAI = {
  id: 13528,
  imageService: '../fake-data/blob/hipster.png',
  imageServiceContentType: 'unknown',
  chefService: 'de façon à spécialiste',
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IServiceMFPAI = {
  id: 26022,
  imageService: '../fake-data/blob/hipster.png',
  imageServiceContentType: 'unknown',
  nomService: 'guide cuicui vouh',
  chefService: 'comme commissionnaire capter',
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewServiceMFPAI = {
  chefService: 'tic-tac personnel professionnel secouriste',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
