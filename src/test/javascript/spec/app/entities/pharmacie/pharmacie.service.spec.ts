import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PharmacieService } from 'app/entities/pharmacie/pharmacie.service';
import { IPharmacie, Pharmacie } from 'app/shared/model/pharmacie.model';

describe('Service Tests', () => {
  describe('Pharmacie Service', () => {
    let injector: TestBed;
    let service: PharmacieService;
    let httpMock: HttpTestingController;
    let elemDefault: IPharmacie;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PharmacieService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pharmacie(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureOuverture: currentDate.format(DATE_TIME_FORMAT),
            heureFermeture: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Pharmacie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureOuverture: currentDate.format(DATE_TIME_FORMAT),
            heureFermeture: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            heureOuverture: currentDate,
            heureFermeture: currentDate
          },
          returnedFromService
        );
        service
          .create(new Pharmacie(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Pharmacie', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            lieu: 'BBBBBB',
            adresseRue: 'BBBBBB',
            codePostal: 'BBBBBB',
            numeroTel: 'BBBBBB',
            email: 'BBBBBB',
            ville: 'BBBBBB',
            region: 'BBBBBB',
            heureOuverture: currentDate.format(DATE_TIME_FORMAT),
            heureFermeture: currentDate.format(DATE_TIME_FORMAT),
            latitude: 1,
            longitude: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureOuverture: currentDate,
            heureFermeture: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Pharmacie', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            lieu: 'BBBBBB',
            adresseRue: 'BBBBBB',
            codePostal: 'BBBBBB',
            numeroTel: 'BBBBBB',
            email: 'BBBBBB',
            ville: 'BBBBBB',
            region: 'BBBBBB',
            heureOuverture: currentDate.format(DATE_TIME_FORMAT),
            heureFermeture: currentDate.format(DATE_TIME_FORMAT),
            latitude: 1,
            longitude: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            heureOuverture: currentDate,
            heureFermeture: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pharmacie', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
