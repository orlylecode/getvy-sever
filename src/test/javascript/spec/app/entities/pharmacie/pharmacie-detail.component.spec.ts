import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { PharmacieDetailComponent } from 'app/entities/pharmacie/pharmacie-detail.component';
import { Pharmacie } from 'app/shared/model/pharmacie.model';

describe('Component Tests', () => {
  describe('Pharmacie Management Detail Component', () => {
    let comp: PharmacieDetailComponent;
    let fixture: ComponentFixture<PharmacieDetailComponent>;
    const route = ({ data: of({ pharmacie: new Pharmacie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [PharmacieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PharmacieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pharmacie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
