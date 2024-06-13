import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProfessionnelComponent } from './list/professionnel.component';
import { ProfessionnelDetailComponent } from './detail/professionnel-detail.component';
import { ProfessionnelUpdateComponent } from './update/professionnel-update.component';
import ProfessionnelResolve from './route/professionnel-routing-resolve.service';

const professionnelRoute: Routes = [
  {
    path: '',
    component: ProfessionnelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfessionnelDetailComponent,
    resolve: {
      professionnel: ProfessionnelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfessionnelUpdateComponent,
    resolve: {
      professionnel: ProfessionnelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfessionnelUpdateComponent,
    resolve: {
      professionnel: ProfessionnelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default professionnelRoute;
