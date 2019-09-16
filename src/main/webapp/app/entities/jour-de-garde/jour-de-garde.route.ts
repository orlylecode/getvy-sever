import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JourDeGarde } from 'app/shared/model/jour-de-garde.model';
import { JourDeGardeService } from './jour-de-garde.service';
import { JourDeGardeComponent } from './jour-de-garde.component';
import { JourDeGardeDetailComponent } from './jour-de-garde-detail.component';
import { JourDeGardeUpdateComponent } from './jour-de-garde-update.component';
import { JourDeGardeDeletePopupComponent } from './jour-de-garde-delete-dialog.component';
import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';

@Injectable({ providedIn: 'root' })
export class JourDeGardeResolve implements Resolve<IJourDeGarde> {
  constructor(private service: JourDeGardeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJourDeGarde> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<JourDeGarde>) => response.ok),
        map((jourDeGarde: HttpResponse<JourDeGarde>) => jourDeGarde.body)
      );
    }
    return of(new JourDeGarde());
  }
}

export const jourDeGardeRoute: Routes = [
  {
    path: '',
    component: JourDeGardeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourDeGarde.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JourDeGardeDetailComponent,
    resolve: {
      jourDeGarde: JourDeGardeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourDeGarde.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JourDeGardeUpdateComponent,
    resolve: {
      jourDeGarde: JourDeGardeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourDeGarde.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JourDeGardeUpdateComponent,
    resolve: {
      jourDeGarde: JourDeGardeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourDeGarde.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const jourDeGardePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: JourDeGardeDeletePopupComponent,
    resolve: {
      jourDeGarde: JourDeGardeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourDeGarde.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
