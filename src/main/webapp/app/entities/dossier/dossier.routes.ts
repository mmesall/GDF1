import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DossierComponent } from './list/dossier.component';
import { DossierDetailComponent } from './detail/dossier-detail.component';
import { DossierUpdateComponent } from './update/dossier-update.component';
import DossierResolve from './route/dossier-routing-resolve.service';

const dossierRoute: Routes = [
  {
    path: '',
    component: DossierComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DossierDetailComponent,
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DossierUpdateComponent,
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DossierUpdateComponent,
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dossierRoute;
