import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GetvySeverTestModule } from '../../../test.module';
import { PharmacieDeleteDialogComponent } from 'app/entities/pharmacie/pharmacie-delete-dialog.component';
import { PharmacieService } from 'app/entities/pharmacie/pharmacie.service';

describe('Component Tests', () => {
  describe('Pharmacie Management Delete Component', () => {
    let comp: PharmacieDeleteDialogComponent;
    let fixture: ComponentFixture<PharmacieDeleteDialogComponent>;
    let service: PharmacieService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [PharmacieDeleteDialogComponent]
      })
        .overrideTemplate(PharmacieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacieService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
