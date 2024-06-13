import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICandidatureP } from '../candidature-p.model';
import { CandidaturePService } from '../service/candidature-p.service';

const candidaturePResolve = (route: ActivatedRouteSnapshot): Observable<null | ICandidatureP> => {
  const id = route.params['id'];
  if (id) {
    return inject(CandidaturePService)
      .find(id)
      .pipe(
        mergeMap((candidatureP: HttpResponse<ICandidatureP>) => {
          if (candidatureP.body) {
            return of(candidatureP.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default candidaturePResolve;
