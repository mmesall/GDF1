import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FormationContinueComponent } from './list/formation-continue.component';
import { FormationContinueDetailComponent } from './detail/formation-continue-detail.component';
import { FormationContinueUpdateComponent } from './update/formation-continue-update.component';
import FormationContinueResolve from './route/formation-continue-routing-resolve.service';

const formationContinueRoute: Routes = [
  {
    path: '',
    component: FormationContinueComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormationContinueDetailComponent,
    resolve: {
      formationContinue: FormationContinueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormationContinueUpdateComponent,
    resolve: {
      formationContinue: FormationContinueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormationContinueUpdateComponent,
    resolve: {
      formationContinue: FormationContinueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default formationContinueRoute;
