import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJourTravail } from 'app/shared/model/jour-travail.model';

type EntityResponseType = HttpResponse<IJourTravail>;
type EntityArrayResponseType = HttpResponse<IJourTravail[]>;

@Injectable({ providedIn: 'root' })
export class JourTravailService {
  public resourceUrl = SERVER_API_URL + 'api/jour-travails';

  constructor(protected http: HttpClient) {}

  create(jourTravail: IJourTravail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourTravail);
    return this.http
      .post<IJourTravail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jourTravail: IJourTravail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourTravail);
    return this.http
      .put<IJourTravail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJourTravail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJourTravail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jourTravail: IJourTravail): IJourTravail {
    const copy: IJourTravail = Object.assign({}, jourTravail, {
      jour: jourTravail.jour != null && jourTravail.jour.isValid() ? jourTravail.jour.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jour = res.body.jour != null ? moment(res.body.jour) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jourTravail: IJourTravail) => {
        jourTravail.jour = jourTravail.jour != null ? moment(jourTravail.jour) : null;
      });
    }
    return res;
  }
}
