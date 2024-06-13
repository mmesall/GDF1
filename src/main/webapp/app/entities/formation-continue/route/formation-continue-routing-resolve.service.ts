import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormationContinue } from '../formation-continue.model';
import { FormationContinueService } from '../service/formation-continue.service';

const formationContinueResolve = (route: ActivatedRouteSnapshot): Observable<null | IFormationContinue> => {
  const id = route.params['id'];
  if (id) {
    return inject(FormationContinueService)
      .find(id)
      .pipe(
        mergeMap((formationContinue: HttpResponse<IFormationContinue>) => {
          if (formationContinue.body) {
            return of(formationContinue.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default formationContinueResolve;
