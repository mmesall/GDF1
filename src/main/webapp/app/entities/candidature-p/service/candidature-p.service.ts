import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICandidatureP, NewCandidatureP } from '../candidature-p.model';

export type PartialUpdateCandidatureP = Partial<ICandidatureP> & Pick<ICandidatureP, 'id'>;

type RestOf<T extends ICandidatureP | NewCandidatureP> = Omit<T, 'dateDebutOffre' | 'dateFinOffre' | 'dateDepot'> & {
  dateDebutOffre?: string | null;
  dateFinOffre?: string | null;
  dateDepot?: string | null;
};

export type RestCandidatureP = RestOf<ICandidatureP>;

export type NewRestCandidatureP = RestOf<NewCandidatureP>;

export type PartialUpdateRestCandidatureP = RestOf<PartialUpdateCandidatureP>;

export type EntityResponseType = HttpResponse<ICandidatureP>;
export type EntityArrayResponseType = HttpResponse<ICandidatureP[]>;

@Injectable({ providedIn: 'root' })
export class CandidaturePService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/candidature-ps');

  create(candidatureP: NewCandidatureP): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureP);
    return this.http
      .post<RestCandidatureP>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(candidatureP: ICandidatureP): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureP);
    return this.http
      .put<RestCandidatureP>(`${this.resourceUrl}/${this.getCandidaturePIdentifier(candidatureP)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(candidatureP: PartialUpdateCandidatureP): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureP);
    return this.http
      .patch<RestCandidatureP>(`${this.resourceUrl}/${this.getCandidaturePIdentifier(candidatureP)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCandidatureP>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCandidatureP[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCandidaturePIdentifier(candidatureP: Pick<ICandidatureP, 'id'>): number {
    return candidatureP.id;
  }

  compareCandidatureP(o1: Pick<ICandidatureP, 'id'> | null, o2: Pick<ICandidatureP, 'id'> | null): boolean {
    return o1 && o2 ? this.getCandidaturePIdentifier(o1) === this.getCandidaturePIdentifier(o2) : o1 === o2;
  }

  addCandidaturePToCollectionIfMissing<Type extends Pick<ICandidatureP, 'id'>>(
    candidaturePCollection: Type[],
    ...candidaturePSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const candidaturePS: Type[] = candidaturePSToCheck.filter(isPresent);
    if (candidaturePS.length > 0) {
      const candidaturePCollectionIdentifiers = candidaturePCollection.map(candidaturePItem =>
        this.getCandidaturePIdentifier(candidaturePItem),
      );
      const candidaturePSToAdd = candidaturePS.filter(candidaturePItem => {
        const candidaturePIdentifier = this.getCandidaturePIdentifier(candidaturePItem);
        if (candidaturePCollectionIdentifiers.includes(candidaturePIdentifier)) {
          return false;
        }
        candidaturePCollectionIdentifiers.push(candidaturePIdentifier);
        return true;
      });
      return [...candidaturePSToAdd, ...candidaturePCollection];
    }
    return candidaturePCollection;
  }

  protected convertDateFromClient<T extends ICandidatureP | NewCandidatureP | PartialUpdateCandidatureP>(candidatureP: T): RestOf<T> {
    return {
      ...candidatureP,
      dateDebutOffre: candidatureP.dateDebutOffre?.format(DATE_FORMAT) ?? null,
      dateFinOffre: candidatureP.dateFinOffre?.format(DATE_FORMAT) ?? null,
      dateDepot: candidatureP.dateDepot?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCandidatureP: RestCandidatureP): ICandidatureP {
    return {
      ...restCandidatureP,
      dateDebutOffre: restCandidatureP.dateDebutOffre ? dayjs(restCandidatureP.dateDebutOffre) : undefined,
      dateFinOffre: restCandidatureP.dateFinOffre ? dayjs(restCandidatureP.dateFinOffre) : undefined,
      dateDepot: restCandidatureP.dateDepot ? dayjs(restCandidatureP.dateDepot) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCandidatureP>): HttpResponse<ICandidatureP> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCandidatureP[]>): HttpResponse<ICandidatureP[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
