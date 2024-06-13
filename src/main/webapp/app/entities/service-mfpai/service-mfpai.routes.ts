import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServiceMFPAIComponent } from './list/service-mfpai.component';
import { ServiceMFPAIDetailComponent } from './detail/service-mfpai-detail.component';
import { ServiceMFPAIUpdateComponent } from './update/service-mfpai-update.component';
import ServiceMFPAIResolve from './route/service-mfpai-routing-resolve.service';

const serviceMFPAIRoute: Routes = [
  {
    path: '',
    component: ServiceMFPAIComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceMFPAIDetailComponent,
    resolve: {
      serviceMFPAI: ServiceMFPAIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceMFPAIUpdateComponent,
    resolve: {
      serviceMFPAI: ServiceMFPAIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceMFPAIUpdateComponent,
    resolve: {
      serviceMFPAI: ServiceMFPAIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default serviceMFPAIRoute;
