import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPriseEnCharge, NewPriseEnCharge } from '../prise-en-charge.model';

export type PartialUpdatePriseEnCharge = Partial<IPriseEnCharge> & Pick<IPriseEnCharge, 'id'>;

export type EntityResponseType = HttpResponse<IPriseEnCharge>;
export type EntityArrayResponseType = HttpResponse<IPriseEnCharge[]>;

@Injectable({ providedIn: 'root' })
export class PriseEnChargeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prise-en-charges');

  create(priseEnCharge: NewPriseEnCharge): Observable<EntityResponseType> {
    return this.http.post<IPriseEnCharge>(this.resourceUrl, priseEnCharge, { observe: 'response' });
  }

  update(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
    return this.http.put<IPriseEnCharge>(`${this.resourceUrl}/${this.getPriseEnChargeIdentifier(priseEnCharge)}`, priseEnCharge, {
      observe: 'response',
    });
  }

  partialUpdate(priseEnCharge: PartialUpdatePriseEnCharge): Observable<EntityResponseType> {
    return this.http.patch<IPriseEnCharge>(`${this.resourceUrl}/${this.getPriseEnChargeIdentifier(priseEnCharge)}`, priseEnCharge, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPriseEnCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriseEnCharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPriseEnChargeIdentifier(priseEnCharge: Pick<IPriseEnCharge, 'id'>): number {
    return priseEnCharge.id;
  }

  comparePriseEnCharge(o1: Pick<IPriseEnCharge, 'id'> | null, o2: Pick<IPriseEnCharge, 'id'> | null): boolean {
    return o1 && o2 ? this.getPriseEnChargeIdentifier(o1) === this.getPriseEnChargeIdentifier(o2) : o1 === o2;
  }

  addPriseEnChargeToCollectionIfMissing<Type extends Pick<IPriseEnCharge, 'id'>>(
    priseEnChargeCollection: Type[],
    ...priseEnChargesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const priseEnCharges: Type[] = priseEnChargesToCheck.filter(isPresent);
    if (priseEnCharges.length > 0) {
      const priseEnChargeCollectionIdentifiers = priseEnChargeCollection.map(priseEnChargeItem =>
        this.getPriseEnChargeIdentifier(priseEnChargeItem),
      );
      const priseEnChargesToAdd = priseEnCharges.filter(priseEnChargeItem => {
        const priseEnChargeIdentifier = this.getPriseEnChargeIdentifier(priseEnChargeItem);
        if (priseEnChargeCollectionIdentifiers.includes(priseEnChargeIdentifier)) {
          return false;
        }
        priseEnChargeCollectionIdentifiers.push(priseEnChargeIdentifier);
        return true;
      });
      return [...priseEnChargesToAdd, ...priseEnChargeCollection];
    }
    return priseEnChargeCollection;
  }
}
