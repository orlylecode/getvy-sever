import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';

type EntityResponseType = HttpResponse<IJourDeGarde>;
type EntityArrayResponseType = HttpResponse<IJourDeGarde[]>;

@Injectable({ providedIn: 'root' })
export class JourDeGardeService {
  public resourceUrl = SERVER_API_URL + 'api/jour-de-gardes';

  constructor(protected http: HttpClient) {}

  create(jourDeGarde: IJourDeGarde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourDeGarde);
    return this.http
      .post<IJourDeGarde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jourDeGarde: IJourDeGarde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourDeGarde);
    return this.http
      .put<IJourDeGarde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJourDeGarde>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJourDeGarde[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jourDeGarde: IJourDeGarde): IJourDeGarde {
    const copy: IJourDeGarde = Object.assign({}, jourDeGarde, {
      jour: jourDeGarde.jour != null && jourDeGarde.jour.isValid() ? jourDeGarde.jour.toJSON() : null
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
      res.body.forEach((jourDeGarde: IJourDeGarde) => {
        jourDeGarde.jour = jourDeGarde.jour != null ? moment(jourDeGarde.jour) : null;
      });
    }
    return res;
  }
}
