import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CandidatureEComponent } from './list/candidature-e.component';
import { CandidatureEDetailComponent } from './detail/candidature-e-detail.component';
import { CandidatureEUpdateComponent } from './update/candidature-e-update.component';
import CandidatureEResolve from './route/candidature-e-routing-resolve.service';

const candidatureERoute: Routes = [
  {
    path: '',
    component: CandidatureEComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidatureEDetailComponent,
    resolve: {
      candidatureE: CandidatureEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidatureEUpdateComponent,
    resolve: {
      candidatureE: CandidatureEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidatureEUpdateComponent,
    resolve: {
      candidatureE: CandidatureEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default candidatureERoute;
