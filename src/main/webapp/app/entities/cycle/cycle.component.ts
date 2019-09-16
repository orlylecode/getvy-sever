import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICycle } from 'app/shared/model/cycle.model';
import { AccountService } from 'app/core/auth/account.service';
import { CycleService } from './cycle.service';

@Component({
  selector: 'jhi-cycle',
  templateUrl: './cycle.component.html'
})
export class CycleComponent implements OnInit, OnDestroy {
  cycles: ICycle[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected cycleService: CycleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.cycleService
      .query()
      .pipe(
        filter((res: HttpResponse<ICycle[]>) => res.ok),
        map((res: HttpResponse<ICycle[]>) => res.body)
      )
      .subscribe(
        (res: ICycle[]) => {
          this.cycles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCycles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICycle) {
    return item.id;
  }

  registerChangeInCycles() {
    this.eventSubscriber = this.eventManager.subscribe('cycleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
