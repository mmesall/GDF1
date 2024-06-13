import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICandidatureE, NewCandidatureE } from '../candidature-e.model';

export type PartialUpdateCandidatureE = Partial<ICandidatureE> & Pick<ICandidatureE, 'id'>;

type RestOf<T extends ICandidatureE | NewCandidatureE> = Omit<T, 'dateDebutOffre' | 'dateFinOffre' | 'dateDepot'> & {
  dateDebutOffre?: string | null;
  dateFinOffre?: string | null;
  dateDepot?: string | null;
};

export type RestCandidatureE = RestOf<ICandidatureE>;

export type NewRestCandidatureE = RestOf<NewCandidatureE>;

export type PartialUpdateRestCandidatureE = RestOf<PartialUpdateCandidatureE>;

export type EntityResponseType = HttpResponse<ICandidatureE>;
export type EntityArrayResponseType = HttpResponse<ICandidatureE[]>;

@Injectable({ providedIn: 'root' })
export class CandidatureEService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/candidature-es');

  create(candidatureE: NewCandidatureE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureE);
    return this.http
      .post<RestCandidatureE>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(candidatureE: ICandidatureE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureE);
    return this.http
      .put<RestCandidatureE>(`${this.resourceUrl}/${this.getCandidatureEIdentifier(candidatureE)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(candidatureE: PartialUpdateCandidatureE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidatureE);
    return this.http
      .patch<RestCandidatureE>(`${this.resourceUrl}/${this.getCandidatureEIdentifier(candidatureE)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCandidatureE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCandidatureE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCandidatureEIdentifier(candidatureE: Pick<ICandidatureE, 'id'>): number {
    return candidatureE.id;
  }

  compareCandidatureE(o1: Pick<ICandidatureE, 'id'> | null, o2: Pick<ICandidatureE, 'id'> | null): boolean {
    return o1 && o2 ? this.getCandidatureEIdentifier(o1) === this.getCandidatureEIdentifier(o2) : o1 === o2;
  }

  addCandidatureEToCollectionIfMissing<Type extends Pick<ICandidatureE, 'id'>>(
    candidatureECollection: Type[],
    ...candidatureESToCheck: (Type | null | undefined)[]
  ): Type[] {
    const candidatureES: Type[] = candidatureESToCheck.filter(isPresent);
    if (candidatureES.length > 0) {
      const candidatureECollectionIdentifiers = candidatureECollection.map(candidatureEItem =>
        this.getCandidatureEIdentifier(candidatureEItem),
      );
      const candidatureESToAdd = candidatureES.filter(candidatureEItem => {
        const candidatureEIdentifier = this.getCandidatureEIdentifier(candidatureEItem);
        if (candidatureECollectionIdentifiers.includes(candidatureEIdentifier)) {
          return false;
        }
        candidatureECollectionIdentifiers.push(candidatureEIdentifier);
        return true;
      });
      return [...candidatureESToAdd, ...candidatureECollection];
    }
    return candidatureECollection;
  }

  protected convertDateFromClient<T extends ICandidatureE | NewCandidatureE | PartialUpdateCandidatureE>(candidatureE: T): RestOf<T> {
    return {
      ...candidatureE,
      dateDebutOffre: candidatureE.dateDebutOffre?.format(DATE_FORMAT) ?? null,
      dateFinOffre: candidatureE.dateFinOffre?.format(DATE_FORMAT) ?? null,
      dateDepot: candidatureE.dateDepot?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCandidatureE: RestCandidatureE): ICandidatureE {
    return {
      ...restCandidatureE,
      dateDebutOffre: restCandidatureE.dateDebutOffre ? dayjs(restCandidatureE.dateDebutOffre) : undefined,
      dateFinOffre: restCandidatureE.dateFinOffre ? dayjs(restCandidatureE.dateFinOffre) : undefined,
      dateDepot: restCandidatureE.dateDepot ? dayjs(restCandidatureE.dateDepot) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCandidatureE>): HttpResponse<ICandidatureE> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCandidatureE[]>): HttpResponse<ICandidatureE[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
