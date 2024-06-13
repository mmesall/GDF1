import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConcours, NewConcours } from '../concours.model';

export type PartialUpdateConcours = Partial<IConcours> & Pick<IConcours, 'id'>;

type RestOf<T extends IConcours | NewConcours> = Omit<T, 'dateOuverture' | 'dateCloture' | 'dateConcours'> & {
  dateOuverture?: string | null;
  dateCloture?: string | null;
  dateConcours?: string | null;
};

export type RestConcours = RestOf<IConcours>;

export type NewRestConcours = RestOf<NewConcours>;

export type PartialUpdateRestConcours = RestOf<PartialUpdateConcours>;

export type EntityResponseType = HttpResponse<IConcours>;
export type EntityArrayResponseType = HttpResponse<IConcours[]>;

@Injectable({ providedIn: 'root' })
export class ConcoursService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/concours');

  create(concours: NewConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .post<RestConcours>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(concours: IConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .put<RestConcours>(`${this.resourceUrl}/${this.getConcoursIdentifier(concours)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(concours: PartialUpdateConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .patch<RestConcours>(`${this.resourceUrl}/${this.getConcoursIdentifier(concours)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestConcours>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestConcours[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConcoursIdentifier(concours: Pick<IConcours, 'id'>): number {
    return concours.id;
  }

  compareConcours(o1: Pick<IConcours, 'id'> | null, o2: Pick<IConcours, 'id'> | null): boolean {
    return o1 && o2 ? this.getConcoursIdentifier(o1) === this.getConcoursIdentifier(o2) : o1 === o2;
  }

  addConcoursToCollectionIfMissing<Type extends Pick<IConcours, 'id'>>(
    concoursCollection: Type[],
    ...concoursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const concours: Type[] = concoursToCheck.filter(isPresent);
    if (concours.length > 0) {
      const concoursCollectionIdentifiers = concoursCollection.map(concoursItem => this.getConcoursIdentifier(concoursItem));
      const concoursToAdd = concours.filter(concoursItem => {
        const concoursIdentifier = this.getConcoursIdentifier(concoursItem);
        if (concoursCollectionIdentifiers.includes(concoursIdentifier)) {
          return false;
        }
        concoursCollectionIdentifiers.push(concoursIdentifier);
        return true;
      });
      return [...concoursToAdd, ...concoursCollection];
    }
    return concoursCollection;
  }

  protected convertDateFromClient<T extends IConcours | NewConcours | PartialUpdateConcours>(concours: T): RestOf<T> {
    return {
      ...concours,
      dateOuverture: concours.dateOuverture?.format(DATE_FORMAT) ?? null,
      dateCloture: concours.dateCloture?.format(DATE_FORMAT) ?? null,
      dateConcours: concours.dateConcours?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restConcours: RestConcours): IConcours {
    return {
      ...restConcours,
      dateOuverture: restConcours.dateOuverture ? dayjs(restConcours.dateOuverture) : undefined,
      dateCloture: restConcours.dateCloture ? dayjs(restConcours.dateCloture) : undefined,
      dateConcours: restConcours.dateConcours ? dayjs(restConcours.dateConcours) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestConcours>): HttpResponse<IConcours> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestConcours[]>): HttpResponse<IConcours[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
