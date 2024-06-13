import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DiplomeComponent } from './list/diplome.component';
import { DiplomeDetailComponent } from './detail/diplome-detail.component';
import { DiplomeUpdateComponent } from './update/diplome-update.component';
import DiplomeResolve from './route/diplome-routing-resolve.service';

const diplomeRoute: Routes = [
  {
    path: '',
    component: DiplomeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiplomeDetailComponent,
    resolve: {
      diplome: DiplomeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiplomeUpdateComponent,
    resolve: {
      diplome: DiplomeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiplomeUpdateComponent,
    resolve: {
      diplome: DiplomeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default diplomeRoute;
