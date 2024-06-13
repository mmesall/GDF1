import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormation } from '../formation.model';
import { FormationService } from '../service/formation.service';

const formationResolve = (route: ActivatedRouteSnapshot): Observable<null | IFormation> => {
  const id = route.params['id'];
  if (id) {
    return inject(FormationService)
      .find(id)
      .pipe(
        mergeMap((formation: HttpResponse<IFormation>) => {
          if (formation.body) {
            return of(formation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default formationResolve;
