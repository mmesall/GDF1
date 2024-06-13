import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';

const bailleurResolve = (route: ActivatedRouteSnapshot): Observable<null | IBailleur> => {
  const id = route.params['id'];
  if (id) {
    return inject(BailleurService)
      .find(id)
      .pipe(
        mergeMap((bailleur: HttpResponse<IBailleur>) => {
          if (bailleur.body) {
            return of(bailleur.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bailleurResolve;
