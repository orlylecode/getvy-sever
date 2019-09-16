import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { JourTravailUpdateComponent } from 'app/entities/jour-travail/jour-travail-update.component';
import { JourTravailService } from 'app/entities/jour-travail/jour-travail.service';
import { JourTravail } from 'app/shared/model/jour-travail.model';

describe('Component Tests', () => {
  describe('JourTravail Management Update Component', () => {
    let comp: JourTravailUpdateComponent;
    let fixture: ComponentFixture<JourTravailUpdateComponent>;
    let service: JourTravailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourTravailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JourTravailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourTravailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourTravailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JourTravail(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new JourTravail();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
