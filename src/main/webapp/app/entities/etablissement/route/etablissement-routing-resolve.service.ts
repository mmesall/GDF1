import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtablissement } from '../etablissement.model';
import { EtablissementService } from '../service/etablissement.service';

const etablissementResolve = (route: ActivatedRouteSnapshot): Observable<null | IEtablissement> => {
  const id = route.params['id'];
  if (id) {
    return inject(EtablissementService)
      .find(id)
      .pipe(
        mergeMap((etablissement: HttpResponse<IEtablissement>) => {
          if (etablissement.body) {
            return of(etablissement.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default etablissementResolve;
