import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ExperienceComponent } from './list/experience.component';
import { ExperienceDetailComponent } from './detail/experience-detail.component';
import { ExperienceUpdateComponent } from './update/experience-update.component';
import ExperienceResolve from './route/experience-routing-resolve.service';

const experienceRoute: Routes = [
  {
    path: '',
    component: ExperienceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExperienceDetailComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default experienceRoute;
