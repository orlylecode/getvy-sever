import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';
import { JourDeGardeService } from './jour-de-garde.service';

@Component({
  selector: 'jhi-jour-de-garde-delete-dialog',
  templateUrl: './jour-de-garde-delete-dialog.component.html'
})
export class JourDeGardeDeleteDialogComponent {
  jourDeGarde: IJourDeGarde;

  constructor(
    protected jourDeGardeService: JourDeGardeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jourDeGardeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'jourDeGardeListModification',
        content: 'Deleted an jourDeGarde'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-jour-de-garde-delete-popup',
  template: ''
})
export class JourDeGardeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jourDeGarde }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(JourDeGardeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.jourDeGarde = jourDeGarde;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/jour-de-garde', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/jour-de-garde', { outlets: { popup: null } }]);
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
