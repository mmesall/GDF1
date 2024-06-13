import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfessionnel } from '../professionnel.model';
import { ProfessionnelService } from '../service/professionnel.service';

const professionnelResolve = (route: ActivatedRouteSnapshot): Observable<null | IProfessionnel> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProfessionnelService)
      .find(id)
      .pipe(
        mergeMap((professionnel: HttpResponse<IProfessionnel>) => {
          if (professionnel.body) {
            return of(professionnel.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default professionnelResolve;
