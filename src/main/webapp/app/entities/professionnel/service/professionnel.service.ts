import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfessionnel, NewProfessionnel } from '../professionnel.model';

export type PartialUpdateProfessionnel = Partial<IProfessionnel> & Pick<IProfessionnel, 'id'>;

type RestOf<T extends IProfessionnel | NewProfessionnel> = Omit<T, 'dateNaiss'> & {
  dateNaiss?: string | null;
};

export type RestProfessionnel = RestOf<IProfessionnel>;

export type NewRestProfessionnel = RestOf<NewProfessionnel>;

export type PartialUpdateRestProfessionnel = RestOf<PartialUpdateProfessionnel>;

export type EntityResponseType = HttpResponse<IProfessionnel>;
export type EntityArrayResponseType = HttpResponse<IProfessionnel[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionnelService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/professionnels');

  create(professionnel: NewProfessionnel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionnel);
    return this.http
      .post<RestProfessionnel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(professionnel: IProfessionnel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionnel);
    return this.http
      .put<RestProfessionnel>(`${this.resourceUrl}/${this.getProfessionnelIdentifier(professionnel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(professionnel: PartialUpdateProfessionnel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionnel);
    return this.http
      .patch<RestProfessionnel>(`${this.resourceUrl}/${this.getProfessionnelIdentifier(professionnel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProfessionnel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProfessionnel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfessionnelIdentifier(professionnel: Pick<IProfessionnel, 'id'>): number {
    return professionnel.id;
  }

  compareProfessionnel(o1: Pick<IProfessionnel, 'id'> | null, o2: Pick<IProfessionnel, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfessionnelIdentifier(o1) === this.getProfessionnelIdentifier(o2) : o1 === o2;
  }

  addProfessionnelToCollectionIfMissing<Type extends Pick<IProfessionnel, 'id'>>(
    professionnelCollection: Type[],
    ...professionnelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const professionnels: Type[] = professionnelsToCheck.filter(isPresent);
    if (professionnels.length > 0) {
      const professionnelCollectionIdentifiers = professionnelCollection.map(professionnelItem =>
        this.getProfessionnelIdentifier(professionnelItem),
      );
      const professionnelsToAdd = professionnels.filter(professionnelItem => {
        const professionnelIdentifier = this.getProfessionnelIdentifier(professionnelItem);
        if (professionnelCollectionIdentifiers.includes(professionnelIdentifier)) {
          return false;
        }
        professionnelCollectionIdentifiers.push(professionnelIdentifier);
        return true;
      });
      return [...professionnelsToAdd, ...professionnelCollection];
    }
    return professionnelCollection;
  }

  protected convertDateFromClient<T extends IProfessionnel | NewProfessionnel | PartialUpdateProfessionnel>(professionnel: T): RestOf<T> {
    return {
      ...professionnel,
      dateNaiss: professionnel.dateNaiss?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restProfessionnel: RestProfessionnel): IProfessionnel {
    return {
      ...restProfessionnel,
      dateNaiss: restProfessionnel.dateNaiss ? dayjs(restProfessionnel.dateNaiss) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProfessionnel>): HttpResponse<IProfessionnel> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProfessionnel[]>): HttpResponse<IProfessionnel[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
