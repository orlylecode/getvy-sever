import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { PharmacieUpdateComponent } from 'app/entities/pharmacie/pharmacie-update.component';
import { PharmacieService } from 'app/entities/pharmacie/pharmacie.service';
import { Pharmacie } from 'app/shared/model/pharmacie.model';

describe('Component Tests', () => {
  describe('Pharmacie Management Update Component', () => {
    let comp: PharmacieUpdateComponent;
    let fixture: ComponentFixture<PharmacieUpdateComponent>;
    let service: PharmacieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [PharmacieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PharmacieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pharmacie(123);
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
        const entity = new Pharmacie();
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
