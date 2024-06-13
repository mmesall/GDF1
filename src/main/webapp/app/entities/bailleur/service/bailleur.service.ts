import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBailleur, NewBailleur } from '../bailleur.model';

export type PartialUpdateBailleur = Partial<IBailleur> & Pick<IBailleur, 'id'>;

export type EntityResponseType = HttpResponse<IBailleur>;
export type EntityArrayResponseType = HttpResponse<IBailleur[]>;

@Injectable({ providedIn: 'root' })
export class BailleurService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bailleurs');

  create(bailleur: NewBailleur): Observable<EntityResponseType> {
    return this.http.post<IBailleur>(this.resourceUrl, bailleur, { observe: 'response' });
  }

  update(bailleur: IBailleur): Observable<EntityResponseType> {
    return this.http.put<IBailleur>(`${this.resourceUrl}/${this.getBailleurIdentifier(bailleur)}`, bailleur, { observe: 'response' });
  }

  partialUpdate(bailleur: PartialUpdateBailleur): Observable<EntityResponseType> {
    return this.http.patch<IBailleur>(`${this.resourceUrl}/${this.getBailleurIdentifier(bailleur)}`, bailleur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBailleur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBailleur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBailleurIdentifier(bailleur: Pick<IBailleur, 'id'>): number {
    return bailleur.id;
  }

  compareBailleur(o1: Pick<IBailleur, 'id'> | null, o2: Pick<IBailleur, 'id'> | null): boolean {
    return o1 && o2 ? this.getBailleurIdentifier(o1) === this.getBailleurIdentifier(o2) : o1 === o2;
  }

  addBailleurToCollectionIfMissing<Type extends Pick<IBailleur, 'id'>>(
    bailleurCollection: Type[],
    ...bailleursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bailleurs: Type[] = bailleursToCheck.filter(isPresent);
    if (bailleurs.length > 0) {
      const bailleurCollectionIdentifiers = bailleurCollection.map(bailleurItem => this.getBailleurIdentifier(bailleurItem));
      const bailleursToAdd = bailleurs.filter(bailleurItem => {
        const bailleurIdentifier = this.getBailleurIdentifier(bailleurItem);
        if (bailleurCollectionIdentifiers.includes(bailleurIdentifier)) {
          return false;
        }
        bailleurCollectionIdentifiers.push(bailleurIdentifier);
        return true;
      });
      return [...bailleursToAdd, ...bailleurCollection];
    }
    return bailleurCollection;
  }
}
