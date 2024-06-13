import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDiplome } from '../diplome.model';
import { DiplomeService } from '../service/diplome.service';

const diplomeResolve = (route: ActivatedRouteSnapshot): Observable<null | IDiplome> => {
  const id = route.params['id'];
  if (id) {
    return inject(DiplomeService)
      .find(id)
      .pipe(
        mergeMap((diplome: HttpResponse<IDiplome>) => {
          if (diplome.body) {
            return of(diplome.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default diplomeResolve;
