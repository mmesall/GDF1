import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EleveComponent } from './list/eleve.component';
import { EleveDetailComponent } from './detail/eleve-detail.component';
import { EleveUpdateComponent } from './update/eleve-update.component';
import EleveResolve from './route/eleve-routing-resolve.service';

const eleveRoute: Routes = [
  {
    path: '',
    component: EleveComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EleveDetailComponent,
    resolve: {
      eleve: EleveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EleveUpdateComponent,
    resolve: {
      eleve: EleveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EleveUpdateComponent,
    resolve: {
      eleve: EleveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default eleveRoute;
