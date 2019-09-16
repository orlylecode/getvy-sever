import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJourTravail } from 'app/shared/model/jour-travail.model';
import { JourTravailService } from './jour-travail.service';

@Component({
  selector: 'jhi-jour-travail-delete-dialog',
  templateUrl: './jour-travail-delete-dialog.component.html'
})
export class JourTravailDeleteDialogComponent {
  jourTravail: IJourTravail;

  constructor(
    protected jourTravailService: JourTravailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jourTravailService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'jourTravailListModification',
        content: 'Deleted an jourTravail'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-jour-travail-delete-popup',
  template: ''
})
export class JourTravailDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jourTravail }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(JourTravailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.jourTravail = jourTravail;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/jour-travail', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/jour-travail', { outlets: { popup: null } }]);
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
