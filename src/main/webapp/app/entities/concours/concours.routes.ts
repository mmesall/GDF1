import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ConcoursComponent } from './list/concours.component';
import { ConcoursDetailComponent } from './detail/concours-detail.component';
import { ConcoursUpdateComponent } from './update/concours-update.component';
import ConcoursResolve from './route/concours-routing-resolve.service';

const concoursRoute: Routes = [
  {
    path: '',
    component: ConcoursComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConcoursDetailComponent,
    resolve: {
      concours: ConcoursResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConcoursUpdateComponent,
    resolve: {
      concours: ConcoursResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConcoursUpdateComponent,
    resolve: {
      concours: ConcoursResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default concoursRoute;
