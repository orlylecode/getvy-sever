import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';
import { CycleComponent } from './cycle.component';
import { CycleDetailComponent } from './cycle-detail.component';
import { CycleUpdateComponent } from './cycle-update.component';
import { CycleDeletePopupComponent } from './cycle-delete-dialog.component';
import { ICycle } from 'app/shared/model/cycle.model';

@Injectable({ providedIn: 'root' })
export class CycleResolve implements Resolve<ICycle> {
  constructor(private service: CycleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICycle> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cycle>) => response.ok),
        map((cycle: HttpResponse<Cycle>) => cycle.body)
      );
    }
    return of(new Cycle());
  }
}

export const cycleRoute: Routes = [
  {
    path: '',
    component: CycleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CycleDetailComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CycleUpdateComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CycleUpdateComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cyclePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CycleDeletePopupComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
