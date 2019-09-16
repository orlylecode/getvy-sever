import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pharmacie } from 'app/shared/model/pharmacie.model';
import { PharmacieService } from './pharmacie.service';
import { PharmacieComponent } from './pharmacie.component';
import { PharmacieDetailComponent } from './pharmacie-detail.component';
import { PharmacieUpdateComponent } from './pharmacie-update.component';
import { PharmacieDeletePopupComponent } from './pharmacie-delete-dialog.component';
import { IPharmacie } from 'app/shared/model/pharmacie.model';

@Injectable({ providedIn: 'root' })
export class PharmacieResolve implements Resolve<IPharmacie> {
  constructor(private service: PharmacieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPharmacie> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pharmacie>) => response.ok),
        map((pharmacie: HttpResponse<Pharmacie>) => pharmacie.body)
      );
    }
    return of(new Pharmacie());
  }
}

export const pharmacieRoute: Routes = [
  {
    path: '',
    component: PharmacieComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'getvySeverApp.pharmacie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PharmacieDetailComponent,
    resolve: {
      pharmacie: PharmacieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.pharmacie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PharmacieUpdateComponent,
    resolve: {
      pharmacie: PharmacieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.pharmacie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PharmacieUpdateComponent,
    resolve: {
      pharmacie: PharmacieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.pharmacie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pharmaciePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PharmacieDeletePopupComponent,
    resolve: {
      pharmacie: PharmacieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getvySeverApp.pharmacie.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
