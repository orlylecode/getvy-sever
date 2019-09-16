import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICycle } from 'app/shared/model/cycle.model';

type EntityResponseType = HttpResponse<ICycle>;
type EntityArrayResponseType = HttpResponse<ICycle[]>;

@Injectable({ providedIn: 'root' })
export class CycleService {
  public resourceUrl = SERVER_API_URL + 'api/cycles';

  constructor(protected http: HttpClient) {}

  create(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .post<ICycle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .put<ICycle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICycle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICycle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cycle: ICycle): ICycle {
    const copy: ICycle = Object.assign({}, cycle, {
      dateDebut: cycle.dateDebut != null && cycle.dateDebut.isValid() ? cycle.dateDebut.toJSON() : null,
      dateFin: cycle.dateFin != null && cycle.dateFin.isValid() ? cycle.dateFin.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
      res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cycle: ICycle) => {
        cycle.dateDebut = cycle.dateDebut != null ? moment(cycle.dateDebut) : null;
        cycle.dateFin = cycle.dateFin != null ? moment(cycle.dateFin) : null;
      });
    }
    return res;
  }
}
