import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CandidaturePComponent } from './list/candidature-p.component';
import { CandidaturePDetailComponent } from './detail/candidature-p-detail.component';
import { CandidaturePUpdateComponent } from './update/candidature-p-update.component';
import CandidaturePResolve from './route/candidature-p-routing-resolve.service';

const candidaturePRoute: Routes = [
  {
    path: '',
    component: CandidaturePComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidaturePDetailComponent,
    resolve: {
      candidatureP: CandidaturePResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidaturePUpdateComponent,
    resolve: {
      candidatureP: CandidaturePResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidaturePUpdateComponent,
    resolve: {
      candidatureP: CandidaturePResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default candidaturePRoute;
