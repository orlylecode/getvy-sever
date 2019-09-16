import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJourTravail } from 'app/shared/model/jour-travail.model';
import { AccountService } from 'app/core/auth/account.service';
import { JourTravailService } from './jour-travail.service';

@Component({
  selector: 'jhi-jour-travail',
  templateUrl: './jour-travail.component.html'
})
export class JourTravailComponent implements OnInit, OnDestroy {
  jourTravails: IJourTravail[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected jourTravailService: JourTravailService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.jourTravailService
      .query()
      .pipe(
        filter((res: HttpResponse<IJourTravail[]>) => res.ok),
        map((res: HttpResponse<IJourTravail[]>) => res.body)
      )
      .subscribe(
        (res: IJourTravail[]) => {
          this.jourTravails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInJourTravails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IJourTravail) {
    return item.id;
  }

  registerChangeInJourTravails() {
    this.eventSubscriber = this.eventManager.subscribe('jourTravailListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
