import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacie } from 'app/shared/model/pharmacie.model';
import { PharmacieService } from './pharmacie.service';

@Component({
  selector: 'jhi-pharmacie-delete-dialog',
  templateUrl: './pharmacie-delete-dialog.component.html'
})
export class PharmacieDeleteDialogComponent {
  pharmacie: IPharmacie;

  constructor(protected pharmacieService: PharmacieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pharmacieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pharmacieListModification',
        content: 'Deleted an pharmacie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pharmacie-delete-popup',
  template: ''
})
export class PharmacieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PharmacieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pharmacie = pharmacie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pharmacie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pharmacie', { outlets: { popup: null } }]);
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
