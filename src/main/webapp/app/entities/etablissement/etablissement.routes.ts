import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EtablissementComponent } from './list/etablissement.component';
import { EtablissementDetailComponent } from './detail/etablissement-detail.component';
import { EtablissementUpdateComponent } from './update/etablissement-update.component';
import EtablissementResolve from './route/etablissement-routing-resolve.service';

const etablissementRoute: Routes = [
  {
    path: '',
    component: EtablissementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtablissementDetailComponent,
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtablissementUpdateComponent,
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtablissementUpdateComponent,
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default etablissementRoute;
