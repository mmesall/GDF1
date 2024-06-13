import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BailleurComponent } from './list/bailleur.component';
import { BailleurDetailComponent } from './detail/bailleur-detail.component';
import { BailleurUpdateComponent } from './update/bailleur-update.component';
import BailleurResolve from './route/bailleur-routing-resolve.service';

const bailleurRoute: Routes = [
  {
    path: '',
    component: BailleurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BailleurDetailComponent,
    resolve: {
      bailleur: BailleurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BailleurUpdateComponent,
    resolve: {
      bailleur: BailleurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BailleurUpdateComponent,
    resolve: {
      bailleur: BailleurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bailleurRoute;
