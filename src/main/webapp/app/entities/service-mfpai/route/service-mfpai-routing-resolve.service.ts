import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServiceMFPAI } from '../service-mfpai.model';
import { ServiceMFPAIService } from '../service/service-mfpai.service';

const serviceMFPAIResolve = (route: ActivatedRouteSnapshot): Observable<null | IServiceMFPAI> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServiceMFPAIService)
      .find(id)
      .pipe(
        mergeMap((serviceMFPAI: HttpResponse<IServiceMFPAI>) => {
          if (serviceMFPAI.body) {
            return of(serviceMFPAI.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default serviceMFPAIResolve;
