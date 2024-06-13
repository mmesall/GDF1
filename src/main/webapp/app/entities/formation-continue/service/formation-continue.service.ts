import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormationContinue, NewFormationContinue } from '../formation-continue.model';

export type PartialUpdateFormationContinue = Partial<IFormationContinue> & Pick<IFormationContinue, 'id'>;

export type EntityResponseType = HttpResponse<IFormationContinue>;
export type EntityArrayResponseType = HttpResponse<IFormationContinue[]>;

@Injectable({ providedIn: 'root' })
export class FormationContinueService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/formation-continues');

  create(formationContinue: NewFormationContinue): Observable<EntityResponseType> {
    return this.http.post<IFormationContinue>(this.resourceUrl, formationContinue, { observe: 'response' });
  }

  update(formationContinue: IFormationContinue): Observable<EntityResponseType> {
    return this.http.put<IFormationContinue>(
      `${this.resourceUrl}/${this.getFormationContinueIdentifier(formationContinue)}`,
      formationContinue,
      { observe: 'response' },
    );
  }

  partialUpdate(formationContinue: PartialUpdateFormationContinue): Observable<EntityResponseType> {
    return this.http.patch<IFormationContinue>(
      `${this.resourceUrl}/${this.getFormationContinueIdentifier(formationContinue)}`,
      formationContinue,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormationContinue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormationContinue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFormationContinueIdentifier(formationContinue: Pick<IFormationContinue, 'id'>): number {
    return formationContinue.id;
  }

  compareFormationContinue(o1: Pick<IFormationContinue, 'id'> | null, o2: Pick<IFormationContinue, 'id'> | null): boolean {
    return o1 && o2 ? this.getFormationContinueIdentifier(o1) === this.getFormationContinueIdentifier(o2) : o1 === o2;
  }

  addFormationContinueToCollectionIfMissing<Type extends Pick<IFormationContinue, 'id'>>(
    formationContinueCollection: Type[],
    ...formationContinuesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const formationContinues: Type[] = formationContinuesToCheck.filter(isPresent);
    if (formationContinues.length > 0) {
      const formationContinueCollectionIdentifiers = formationContinueCollection.map(formationContinueItem =>
        this.getFormationContinueIdentifier(formationContinueItem),
      );
      const formationContinuesToAdd = formationContinues.filter(formationContinueItem => {
        const formationContinueIdentifier = this.getFormationContinueIdentifier(formationContinueItem);
        if (formationContinueCollectionIdentifiers.includes(formationContinueIdentifier)) {
          return false;
        }
        formationContinueCollectionIdentifiers.push(formationContinueIdentifier);
        return true;
      });
      return [...formationContinuesToAdd, ...formationContinueCollection];
    }
    return formationContinueCollection;
  }
}
