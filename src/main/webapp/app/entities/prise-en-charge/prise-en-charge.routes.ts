import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PriseEnChargeComponent } from './list/prise-en-charge.component';
import { PriseEnChargeDetailComponent } from './detail/prise-en-charge-detail.component';
import { PriseEnChargeUpdateComponent } from './update/prise-en-charge-update.component';
import PriseEnChargeResolve from './route/prise-en-charge-routing-resolve.service';

const priseEnChargeRoute: Routes = [
  {
    path: '',
    component: PriseEnChargeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriseEnChargeDetailComponent,
    resolve: {
      priseEnCharge: PriseEnChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriseEnChargeUpdateComponent,
    resolve: {
      priseEnCharge: PriseEnChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriseEnChargeUpdateComponent,
    resolve: {
      priseEnCharge: PriseEnChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default priseEnChargeRoute;
