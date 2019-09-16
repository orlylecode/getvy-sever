import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';
import { AccountService } from 'app/core/auth/account.service';
import { JourDeGardeService } from './jour-de-garde.service';

@Component({
  selector: 'jhi-jour-de-garde',
  templateUrl: './jour-de-garde.component.html'
})
export class JourDeGardeComponent implements OnInit, OnDestroy {
  jourDeGardes: IJourDeGarde[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected jourDeGardeService: JourDeGardeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.jourDeGardeService
      .query()
      .pipe(
        filter((res: HttpResponse<IJourDeGarde[]>) => res.ok),
        map((res: HttpResponse<IJourDeGarde[]>) => res.body)
      )
      .subscribe(
        (res: IJourDeGarde[]) => {
          this.jourDeGardes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInJourDeGardes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IJourDeGarde) {
    return item.id;
  }

  registerChangeInJourDeGardes() {
    this.eventSubscriber = this.eventManager.subscribe('jourDeGardeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
