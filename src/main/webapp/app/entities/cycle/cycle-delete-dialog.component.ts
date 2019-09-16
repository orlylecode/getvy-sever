import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';

@Component({
  selector: 'jhi-cycle-delete-dialog',
  templateUrl: './cycle-delete-dialog.component.html'
})
export class CycleDeleteDialogComponent {
  cycle: ICycle;

  constructor(protected cycleService: CycleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cycleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cycleListModification',
        content: 'Deleted an cycle'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cycle-delete-popup',
  template: ''
})
export class CycleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cycle }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CycleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cycle = cycle;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cycle', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cycle', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
