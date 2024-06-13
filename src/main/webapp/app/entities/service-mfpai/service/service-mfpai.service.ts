import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServiceMFPAI, NewServiceMFPAI } from '../service-mfpai.model';

export type PartialUpdateServiceMFPAI = Partial<IServiceMFPAI> & Pick<IServiceMFPAI, 'id'>;

export type EntityResponseType = HttpResponse<IServiceMFPAI>;
export type EntityArrayResponseType = HttpResponse<IServiceMFPAI[]>;

@Injectable({ providedIn: 'root' })
export class ServiceMFPAIService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/service-mfpais');

  create(serviceMFPAI: NewServiceMFPAI): Observable<EntityResponseType> {
    return this.http.post<IServiceMFPAI>(this.resourceUrl, serviceMFPAI, { observe: 'response' });
  }

  update(serviceMFPAI: IServiceMFPAI): Observable<EntityResponseType> {
    return this.http.put<IServiceMFPAI>(`${this.resourceUrl}/${this.getServiceMFPAIIdentifier(serviceMFPAI)}`, serviceMFPAI, {
      observe: 'response',
    });
  }

  partialUpdate(serviceMFPAI: PartialUpdateServiceMFPAI): Observable<EntityResponseType> {
    return this.http.patch<IServiceMFPAI>(`${this.resourceUrl}/${this.getServiceMFPAIIdentifier(serviceMFPAI)}`, serviceMFPAI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceMFPAI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceMFPAI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServiceMFPAIIdentifier(serviceMFPAI: Pick<IServiceMFPAI, 'id'>): number {
    return serviceMFPAI.id;
  }

  compareServiceMFPAI(o1: Pick<IServiceMFPAI, 'id'> | null, o2: Pick<IServiceMFPAI, 'id'> | null): boolean {
    return o1 && o2 ? this.getServiceMFPAIIdentifier(o1) === this.getServiceMFPAIIdentifier(o2) : o1 === o2;
  }

  addServiceMFPAIToCollectionIfMissing<Type extends Pick<IServiceMFPAI, 'id'>>(
    serviceMFPAICollection: Type[],
    ...serviceMFPAISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const serviceMFPAIS: Type[] = serviceMFPAISToCheck.filter(isPresent);
    if (serviceMFPAIS.length > 0) {
      const serviceMFPAICollectionIdentifiers = serviceMFPAICollection.map(serviceMFPAIItem =>
        this.getServiceMFPAIIdentifier(serviceMFPAIItem),
      );
      const serviceMFPAISToAdd = serviceMFPAIS.filter(serviceMFPAIItem => {
        const serviceMFPAIIdentifier = this.getServiceMFPAIIdentifier(serviceMFPAIItem);
        if (serviceMFPAICollectionIdentifiers.includes(serviceMFPAIIdentifier)) {
          return false;
        }
        serviceMFPAICollectionIdentifiers.push(serviceMFPAIIdentifier);
        return true;
      });
      return [...serviceMFPAISToAdd, ...serviceMFPAICollection];
    }
    return serviceMFPAICollection;
  }
}
