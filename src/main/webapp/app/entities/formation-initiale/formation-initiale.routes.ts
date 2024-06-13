import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FormationInitialeComponent } from './list/formation-initiale.component';
import { FormationInitialeDetailComponent } from './detail/formation-initiale-detail.component';
import { FormationInitialeUpdateComponent } from './update/formation-initiale-update.component';
import FormationInitialeResolve from './route/formation-initiale-routing-resolve.service';

const formationInitialeRoute: Routes = [
  {
    path: '',
    component: FormationInitialeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormationInitialeDetailComponent,
    resolve: {
      formationInitiale: FormationInitialeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormationInitialeUpdateComponent,
    resolve: {
      formationInitiale: FormationInitialeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormationInitialeUpdateComponent,
    resolve: {
      formationInitiale: FormationInitialeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default formationInitialeRoute;
