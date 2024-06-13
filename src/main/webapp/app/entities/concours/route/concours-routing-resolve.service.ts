import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConcours } from '../concours.model';
import { ConcoursService } from '../service/concours.service';

const concoursResolve = (route: ActivatedRouteSnapshot): Observable<null | IConcours> => {
  const id = route.params['id'];
  if (id) {
    return inject(ConcoursService)
      .find(id)
      .pipe(
        mergeMap((concours: HttpResponse<IConcours>) => {
          if (concours.body) {
            return of(concours.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default concoursResolve;
