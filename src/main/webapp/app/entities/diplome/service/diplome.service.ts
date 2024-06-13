import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiplome, NewDiplome } from '../diplome.model';

export type PartialUpdateDiplome = Partial<IDiplome> & Pick<IDiplome, 'id'>;

export type EntityResponseType = HttpResponse<IDiplome>;
export type EntityArrayResponseType = HttpResponse<IDiplome[]>;

@Injectable({ providedIn: 'root' })
export class DiplomeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/diplomes');

  create(diplome: NewDiplome): Observable<EntityResponseType> {
    return this.http.post<IDiplome>(this.resourceUrl, diplome, { observe: 'response' });
  }

  update(diplome: IDiplome): Observable<EntityResponseType> {
    return this.http.put<IDiplome>(`${this.resourceUrl}/${this.getDiplomeIdentifier(diplome)}`, diplome, { observe: 'response' });
  }

  partialUpdate(diplome: PartialUpdateDiplome): Observable<EntityResponseType> {
    return this.http.patch<IDiplome>(`${this.resourceUrl}/${this.getDiplomeIdentifier(diplome)}`, diplome, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiplome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiplome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDiplomeIdentifier(diplome: Pick<IDiplome, 'id'>): number {
    return diplome.id;
  }

  compareDiplome(o1: Pick<IDiplome, 'id'> | null, o2: Pick<IDiplome, 'id'> | null): boolean {
    return o1 && o2 ? this.getDiplomeIdentifier(o1) === this.getDiplomeIdentifier(o2) : o1 === o2;
  }

  addDiplomeToCollectionIfMissing<Type extends Pick<IDiplome, 'id'>>(
    diplomeCollection: Type[],
    ...diplomesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const diplomes: Type[] = diplomesToCheck.filter(isPresent);
    if (diplomes.length > 0) {
      const diplomeCollectionIdentifiers = diplomeCollection.map(diplomeItem => this.getDiplomeIdentifier(diplomeItem));
      const diplomesToAdd = diplomes.filter(diplomeItem => {
        const diplomeIdentifier = this.getDiplomeIdentifier(diplomeItem);
        if (diplomeCollectionIdentifiers.includes(diplomeIdentifier)) {
          return false;
        }
        diplomeCollectionIdentifiers.push(diplomeIdentifier);
        return true;
      });
      return [...diplomesToAdd, ...diplomeCollection];
    }
    return diplomeCollection;
  }
}
