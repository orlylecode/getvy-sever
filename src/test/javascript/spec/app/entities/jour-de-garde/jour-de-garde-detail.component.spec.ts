import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GetvySeverTestModule } from '../../../test.module';
import { JourDeGardeDetailComponent } from 'app/entities/jour-de-garde/jour-de-garde-detail.component';
import { JourDeGarde } from 'app/shared/model/jour-de-garde.model';

describe('Component Tests', () => {
  describe('JourDeGarde Management Detail Component', () => {
    let comp: JourDeGardeDetailComponent;
    let fixture: ComponentFixture<JourDeGardeDetailComponent>;
    const route = ({ data: of({ jourDeGarde: new JourDeGarde(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourDeGardeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JourDeGardeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourDeGardeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jourDeGarde).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
