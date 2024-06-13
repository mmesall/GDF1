import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IServiceMFPAI } from 'app/entities/service-mfpai/service-mfpai.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IAgent {
  id: number;
  matricule?: string | null;
  nomAgent?: string | null;
  prenom?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  sexe?: keyof typeof Sexe | null;
  telephone?: number | null;
  email?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  serviceMFPAI?: IServiceMFPAI | null;
}

export type NewAgent = Omit<IAgent, 'id'> & { id: null };
