import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgent, NewAgent } from '../agent.model';

export type PartialUpdateAgent = Partial<IAgent> & Pick<IAgent, 'id'>;

type RestOf<T extends IAgent | NewAgent> = Omit<T, 'dateNaiss'> & {
  dateNaiss?: string | null;
};

export type RestAgent = RestOf<IAgent>;

export type NewRestAgent = RestOf<NewAgent>;

export type PartialUpdateRestAgent = RestOf<PartialUpdateAgent>;

export type EntityResponseType = HttpResponse<IAgent>;
export type EntityArrayResponseType = HttpResponse<IAgent[]>;

@Injectable({ providedIn: 'root' })
export class AgentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agents');

  create(agent: NewAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http.post<RestAgent>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(agent: IAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http
      .put<RestAgent>(`${this.resourceUrl}/${this.getAgentIdentifier(agent)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(agent: PartialUpdateAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http
      .patch<RestAgent>(`${this.resourceUrl}/${this.getAgentIdentifier(agent)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAgent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgentIdentifier(agent: Pick<IAgent, 'id'>): number {
    return agent.id;
  }

  compareAgent(o1: Pick<IAgent, 'id'> | null, o2: Pick<IAgent, 'id'> | null): boolean {
    return o1 && o2 ? this.getAgentIdentifier(o1) === this.getAgentIdentifier(o2) : o1 === o2;
  }

  addAgentToCollectionIfMissing<Type extends Pick<IAgent, 'id'>>(
    agentCollection: Type[],
    ...agentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agents: Type[] = agentsToCheck.filter(isPresent);
    if (agents.length > 0) {
      const agentCollectionIdentifiers = agentCollection.map(agentItem => this.getAgentIdentifier(agentItem));
      const agentsToAdd = agents.filter(agentItem => {
        const agentIdentifier = this.getAgentIdentifier(agentItem);
        if (agentCollectionIdentifiers.includes(agentIdentifier)) {
          return false;
        }
        agentCollectionIdentifiers.push(agentIdentifier);
        return true;
      });
      return [...agentsToAdd, ...agentCollection];
    }
    return agentCollection;
  }

  protected convertDateFromClient<T extends IAgent | NewAgent | PartialUpdateAgent>(agent: T): RestOf<T> {
    return {
      ...agent,
      dateNaiss: agent.dateNaiss?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAgent: RestAgent): IAgent {
    return {
      ...restAgent,
      dateNaiss: restAgent.dateNaiss ? dayjs(restAgent.dateNaiss) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAgent>): HttpResponse<IAgent> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAgent[]>): HttpResponse<IAgent[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
