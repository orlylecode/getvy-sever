import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GetvySeverTestModule } from '../../../test.module';
import { JourDeGardeDeleteDialogComponent } from 'app/entities/jour-de-garde/jour-de-garde-delete-dialog.component';
import { JourDeGardeService } from 'app/entities/jour-de-garde/jour-de-garde.service';

describe('Component Tests', () => {
  describe('JourDeGarde Management Delete Component', () => {
    let comp: JourDeGardeDeleteDialogComponent;
    let fixture: ComponentFixture<JourDeGardeDeleteDialogComponent>;
    let service: JourDeGardeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourDeGardeDeleteDialogComponent]
      })
        .overrideTemplate(JourDeGardeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourDeGardeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourDeGardeService);
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
