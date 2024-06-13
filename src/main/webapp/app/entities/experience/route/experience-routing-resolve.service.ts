import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExperience } from '../experience.model';
import { ExperienceService } from '../service/experience.service';

const experienceResolve = (route: ActivatedRouteSnapshot): Observable<null | IExperience> => {
  const id = route.params['id'];
  if (id) {
    return inject(ExperienceService)
      .find(id)
      .pipe(
        mergeMap((experience: HttpResponse<IExperience>) => {
          if (experience.body) {
            return of(experience.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default experienceResolve;
