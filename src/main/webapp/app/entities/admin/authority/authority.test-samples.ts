import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'bab5e561-0a47-4d75-bea9-2332404418d0',
};

export const sampleWithPartialData: IAuthority = {
  name: '134be8d5-db9b-4c81-996d-fe40af03d1e1',
};

export const sampleWithFullData: IAuthority = {
  name: 'e3e2bd16-876c-4865-b2ba-ba05160885d1',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
