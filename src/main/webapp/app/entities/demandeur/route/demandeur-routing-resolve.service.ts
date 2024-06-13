import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';

const demandeurResolve = (route: ActivatedRouteSnapshot): Observable<null | IDemandeur> => {
  const id = route.params['id'];
  if (id) {
    return inject(DemandeurService)
      .find(id)
      .pipe(
        mergeMap((demandeur: HttpResponse<IDemandeur>) => {
          if (demandeur.body) {
            return of(demandeur.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default demandeurResolve;
