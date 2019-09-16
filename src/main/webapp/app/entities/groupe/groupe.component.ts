import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroupe } from 'app/shared/model/groupe.model';
import { AccountService } from 'app/core/auth/account.service';
import { GroupeService } from './groupe.service';

@Component({
  selector: 'jhi-groupe',
  templateUrl: './groupe.component.html'
})
export class GroupeComponent implements OnInit, OnDestroy {
  groupes: IGroupe[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected groupeService: GroupeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.groupeService
      .query()
      .pipe(
        filter((res: HttpResponse<IGroupe[]>) => res.ok),
        map((res: HttpResponse<IGroupe[]>) => res.body)
      )
      .subscribe(
        (res: IGroupe[]) => {
          this.groupes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGroupes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGroupe) {
    return item.id;
  }

  registerChangeInGroupes() {
    this.eventSubscriber = this.eventManager.subscribe('groupeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
