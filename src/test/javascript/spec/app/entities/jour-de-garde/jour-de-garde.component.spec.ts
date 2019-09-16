import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GetvySeverTestModule } from '../../../test.module';
import { JourDeGardeComponent } from 'app/entities/jour-de-garde/jour-de-garde.component';
import { JourDeGardeService } from 'app/entities/jour-de-garde/jour-de-garde.service';
import { JourDeGarde } from 'app/shared/model/jour-de-garde.model';

describe('Component Tests', () => {
  describe('JourDeGarde Management Component', () => {
    let comp: JourDeGardeComponent;
    let fixture: ComponentFixture<JourDeGardeComponent>;
    let service: JourDeGardeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourDeGardeComponent],
        providers: []
      })
        .overrideTemplate(JourDeGardeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourDeGardeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourDeGardeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JourDeGarde(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jourDeGardes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
