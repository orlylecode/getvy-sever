import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { JourDeGardeUpdateComponent } from 'app/entities/jour-de-garde/jour-de-garde-update.component';
import { JourDeGardeService } from 'app/entities/jour-de-garde/jour-de-garde.service';
import { JourDeGarde } from 'app/shared/model/jour-de-garde.model';

describe('Component Tests', () => {
  describe('JourDeGarde Management Update Component', () => {
    let comp: JourDeGardeUpdateComponent;
    let fixture: ComponentFixture<JourDeGardeUpdateComponent>;
    let service: JourDeGardeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourDeGardeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JourDeGardeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourDeGardeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourDeGardeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JourDeGarde(123);
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
        const entity = new JourDeGarde();
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
