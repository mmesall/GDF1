import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 20859,
  login: 'V',
};

export const sampleWithPartialData: IUser = {
  id: 19854,
  login: 'qjG6x',
};

export const sampleWithFullData: IUser = {
  id: 16713,
  login: 'P@W',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
