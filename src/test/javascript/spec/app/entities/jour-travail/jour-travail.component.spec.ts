import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GetvySeverTestModule } from '../../../test.module';
import { JourTravailComponent } from 'app/entities/jour-travail/jour-travail.component';
import { JourTravailService } from 'app/entities/jour-travail/jour-travail.service';
import { JourTravail } from 'app/shared/model/jour-travail.model';

describe('Component Tests', () => {
  describe('JourTravail Management Component', () => {
    let comp: JourTravailComponent;
    let fixture: ComponentFixture<JourTravailComponent>;
    let service: JourTravailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetvySeverTestModule],
        declarations: [JourTravailComponent],
        providers: []
      })
        .overrideTemplate(JourTravailComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourTravailComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourTravailService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JourTravail(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jourTravails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
