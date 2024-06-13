import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';

const priseEnChargeResolve = (route: ActivatedRouteSnapshot): Observable<null | IPriseEnCharge> => {
  const id = route.params['id'];
  if (id) {
    return inject(PriseEnChargeService)
      .find(id)
      .pipe(
        mergeMap((priseEnCharge: HttpResponse<IPriseEnCharge>) => {
          if (priseEnCharge.body) {
            return of(priseEnCharge.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default priseEnChargeResolve;
