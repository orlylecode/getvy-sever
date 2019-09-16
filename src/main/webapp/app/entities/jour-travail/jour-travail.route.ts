import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JourTravail } from 'app/shared/model/jour-travail.model';
import { JourTravailService } from './jour-travail.service';
import { JourTravailComponent } from './jour-travail.component';
import { JourTravailDetailComponent } from './jour-travail-detail.component';
import { JourTravailUpdateComponent } from './jour-travail-update.component';
import { JourTravailDeletePopupComponent } from './jour-travail-delete-dialog.component';
import { IJourTravail } from 'app/shared/model/jour-travail.model';

@Injectable({ providedIn: 'root' })
export class JourTravailResolve implements Resolve<IJourTravail> {
  constructor(private service: JourTravailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJourTravail> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<JourTravail>) => response.ok),
        map((jourTravail: HttpResponse<JourTravail>) => jourTravail.body)
      );
    }
    return of(new JourTravail());
  }
}

export const jourTravailRoute: Routes = [
  {
    path: '',
    component: JourTravailComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourTravail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JourTravailDetailComponent,
    resolve: {
      jourTravail: JourTravailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourTravail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JourTravailUpdateComponent,
    resolve: {
      jourTravail: JourTravailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourTravail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JourTravailUpdateComponent,
    resolve: {
      jourTravail: JourTravailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourTravail.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const jourTravailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: JourTravailDeletePopupComponent,
    resolve: {
      jourTravail: JourTravailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.jourTravail.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
