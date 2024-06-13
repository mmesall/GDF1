import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormationInitiale } from '../formation-initiale.model';
import { FormationInitialeService } from '../service/formation-initiale.service';

const formationInitialeResolve = (route: ActivatedRouteSnapshot): Observable<null | IFormationInitiale> => {
  const id = route.params['id'];
  if (id) {
    return inject(FormationInitialeService)
      .find(id)
      .pipe(
        mergeMap((formationInitiale: HttpResponse<IFormationInitiale>) => {
          if (formationInitiale.body) {
            return of(formationInitiale.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default formationInitialeResolve;
