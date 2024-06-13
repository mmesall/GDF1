import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICandidatureE } from '../candidature-e.model';
import { CandidatureEService } from '../service/candidature-e.service';

const candidatureEResolve = (route: ActivatedRouteSnapshot): Observable<null | ICandidatureE> => {
  const id = route.params['id'];
  if (id) {
    return inject(CandidatureEService)
      .find(id)
      .pipe(
        mergeMap((candidatureE: HttpResponse<ICandidatureE>) => {
          if (candidatureE.body) {
            return of(candidatureE.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default candidatureEResolve;
