import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeur, NewDemandeur } from '../demandeur.model';

export type PartialUpdateDemandeur = Partial<IDemandeur> & Pick<IDemandeur, 'id'>;

type RestOf<T extends IDemandeur | NewDemandeur> = Omit<T, 'dateNaiss'> & {
  dateNaiss?: string | null;
};

export type RestDemandeur = RestOf<IDemandeur>;

export type NewRestDemandeur = RestOf<NewDemandeur>;

export type PartialUpdateRestDemandeur = RestOf<PartialUpdateDemandeur>;

export type EntityResponseType = HttpResponse<IDemandeur>;
export type EntityArrayResponseType = HttpResponse<IDemandeur[]>;

@Injectable({ providedIn: 'root' })
export class DemandeurService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demandeurs');

  create(demandeur: NewDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .post<RestDemandeur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(demandeur: IDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .put<RestDemandeur>(`${this.resourceUrl}/${this.getDemandeurIdentifier(demandeur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(demandeur: PartialUpdateDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .patch<RestDemandeur>(`${this.resourceUrl}/${this.getDemandeurIdentifier(demandeur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDemandeur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDemandeur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemandeurIdentifier(demandeur: Pick<IDemandeur, 'id'>): number {
    return demandeur.id;
  }

  compareDemandeur(o1: Pick<IDemandeur, 'id'> | null, o2: Pick<IDemandeur, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemandeurIdentifier(o1) === this.getDemandeurIdentifier(o2) : o1 === o2;
  }

  addDemandeurToCollectionIfMissing<Type extends Pick<IDemandeur, 'id'>>(
    demandeurCollection: Type[],
    ...demandeursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demandeurs: Type[] = demandeursToCheck.filter(isPresent);
    if (demandeurs.length > 0) {
      const demandeurCollectionIdentifiers = demandeurCollection.map(demandeurItem => this.getDemandeurIdentifier(demandeurItem));
      const demandeursToAdd = demandeurs.filter(demandeurItem => {
        const demandeurIdentifier = this.getDemandeurIdentifier(demandeurItem);
        if (demandeurCollectionIdentifiers.includes(demandeurIdentifier)) {
          return false;
        }
        demandeurCollectionIdentifiers.push(demandeurIdentifier);
        return true;
      });
      return [...demandeursToAdd, ...demandeurCollection];
    }
    return demandeurCollection;
  }

  protected convertDateFromClient<T extends IDemandeur | NewDemandeur | PartialUpdateDemandeur>(demandeur: T): RestOf<T> {
    return {
      ...demandeur,
      dateNaiss: demandeur.dateNaiss?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDemandeur: RestDemandeur): IDemandeur {
    return {
      ...restDemandeur,
      dateNaiss: restDemandeur.dateNaiss ? dayjs(restDemandeur.dateNaiss) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDemandeur>): HttpResponse<IDemandeur> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDemandeur[]>): HttpResponse<IDemandeur[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
