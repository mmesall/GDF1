import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DemandeurComponent } from './list/demandeur.component';
import { DemandeurDetailComponent } from './detail/demandeur-detail.component';
import { DemandeurUpdateComponent } from './update/demandeur-update.component';
import DemandeurResolve from './route/demandeur-routing-resolve.service';

const demandeurRoute: Routes = [
  {
    path: '',
    component: DemandeurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeurDetailComponent,
    resolve: {
      demandeur: DemandeurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeurUpdateComponent,
    resolve: {
      demandeur: DemandeurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeurUpdateComponent,
    resolve: {
      demandeur: DemandeurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default demandeurRoute;
