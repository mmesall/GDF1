export interface IServiceMFPAI {
  id: number;
  imageService?: string | null;
  imageServiceContentType?: string | null;
  nomService?: string | null;
  chefService?: string | null;
  description?: string | null;
}

export type NewServiceMFPAI = Omit<IServiceMFPAI, 'id'> & { id: null };
