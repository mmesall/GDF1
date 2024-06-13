export interface IBailleur {
  id: number;
  nomBailleur?: string | null;
  budgetPrevu?: number | null;
  budgetDepense?: number | null;
  budgetRestant?: number | null;
  nbrePC?: number | null;
}

export type NewBailleur = Omit<IBailleur, 'id'> & { id: null };
