import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FormationComponent } from './list/formation.component';
import { FormationDetailComponent } from './detail/formation-detail.component';
import { FormationUpdateComponent } from './update/formation-update.component';
import FormationResolve from './route/formation-routing-resolve.service';

const formationRoute: Routes = [
  {
    path: '',
    component: FormationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormationDetailComponent,
    resolve: {
      formation: FormationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormationUpdateComponent,
    resolve: {
      formation: FormationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormationUpdateComponent,
    resolve: {
      formation: FormationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default formationRoute;
