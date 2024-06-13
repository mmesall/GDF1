import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormationInitiale, NewFormationInitiale } from '../formation-initiale.model';

export type PartialUpdateFormationInitiale = Partial<IFormationInitiale> & Pick<IFormationInitiale, 'id'>;

type RestOf<T extends IFormationInitiale | NewFormationInitiale> = Omit<T, 'dateOuverture' | 'dateCloture' | 'dateConcours'> & {
  dateOuverture?: string | null;
  dateCloture?: string | null;
  dateConcours?: string | null;
};

export type RestFormationInitiale = RestOf<IFormationInitiale>;

export type NewRestFormationInitiale = RestOf<NewFormationInitiale>;

export type PartialUpdateRestFormationInitiale = RestOf<PartialUpdateFormationInitiale>;

export type EntityResponseType = HttpResponse<IFormationInitiale>;
export type EntityArrayResponseType = HttpResponse<IFormationInitiale[]>;

@Injectable({ providedIn: 'root' })
export class FormationInitialeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/formation-initiales');

  create(formationInitiale: NewFormationInitiale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formationInitiale);
    return this.http
      .post<RestFormationInitiale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(formationInitiale: IFormationInitiale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formationInitiale);
    return this.http
      .put<RestFormationInitiale>(`${this.resourceUrl}/${this.getFormationInitialeIdentifier(formationInitiale)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(formationInitiale: PartialUpdateFormationInitiale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formationInitiale);
    return this.http
      .patch<RestFormationInitiale>(`${this.resourceUrl}/${this.getFormationInitialeIdentifier(formationInitiale)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFormationInitiale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFormationInitiale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFormationInitialeIdentifier(formationInitiale: Pick<IFormationInitiale, 'id'>): number {
    return formationInitiale.id;
  }

  compareFormationInitiale(o1: Pick<IFormationInitiale, 'id'> | null, o2: Pick<IFormationInitiale, 'id'> | null): boolean {
    return o1 && o2 ? this.getFormationInitialeIdentifier(o1) === this.getFormationInitialeIdentifier(o2) : o1 === o2;
  }

  addFormationInitialeToCollectionIfMissing<Type extends Pick<IFormationInitiale, 'id'>>(
    formationInitialeCollection: Type[],
    ...formationInitialesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const formationInitiales: Type[] = formationInitialesToCheck.filter(isPresent);
    if (formationInitiales.length > 0) {
      const formationInitialeCollectionIdentifiers = formationInitialeCollection.map(formationInitialeItem =>
        this.getFormationInitialeIdentifier(formationInitialeItem),
      );
      const formationInitialesToAdd = formationInitiales.filter(formationInitialeItem => {
        const formationInitialeIdentifier = this.getFormationInitialeIdentifier(formationInitialeItem);
        if (formationInitialeCollectionIdentifiers.includes(formationInitialeIdentifier)) {
          return false;
        }
        formationInitialeCollectionIdentifiers.push(formationInitialeIdentifier);
        return true;
      });
      return [...formationInitialesToAdd, ...formationInitialeCollection];
    }
    return formationInitialeCollection;
  }

  protected convertDateFromClient<T extends IFormationInitiale | NewFormationInitiale | PartialUpdateFormationInitiale>(
    formationInitiale: T,
  ): RestOf<T> {
    return {
      ...formationInitiale,
      dateOuverture: formationInitiale.dateOuverture?.format(DATE_FORMAT) ?? null,
      dateCloture: formationInitiale.dateCloture?.format(DATE_FORMAT) ?? null,
      dateConcours: formationInitiale.dateConcours?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFormationInitiale: RestFormationInitiale): IFormationInitiale {
    return {
      ...restFormationInitiale,
      dateOuverture: restFormationInitiale.dateOuverture ? dayjs(restFormationInitiale.dateOuverture) : undefined,
      dateCloture: restFormationInitiale.dateCloture ? dayjs(restFormationInitiale.dateCloture) : undefined,
      dateConcours: restFormationInitiale.dateConcours ? dayjs(restFormationInitiale.dateConcours) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFormationInitiale>): HttpResponse<IFormationInitiale> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFormationInitiale[]>): HttpResponse<IFormationInitiale[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
