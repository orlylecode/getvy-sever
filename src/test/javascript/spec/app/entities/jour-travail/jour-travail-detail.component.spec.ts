import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { JourTravailDetailComponent } from 'app/entities/jour-travail/jour-travail-detail.component';
import { JourTravail } from 'app/shared/model/jour-travail.model';

describe('Component Tests', () => {
  describe('JourTravail Management Detail Component', () => {
    let comp: JourTravailDetailComponent;
    let fixture: ComponentFixture<JourTravailDetailComponent>;
    const route = ({ data: of({ jourTravail: new JourTravail(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourTravailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JourTravailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourTravailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jourTravail).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
