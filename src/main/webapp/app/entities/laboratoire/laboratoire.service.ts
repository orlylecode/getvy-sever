import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';

type EntityResponseType = HttpResponse<ILaboratoire>;
type EntityArrayResponseType = HttpResponse<ILaboratoire[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoireService {
  public resourceUrl = SERVER_API_URL + 'api/laboratoires';

  constructor(protected http: HttpClient) {}

  create(laboratoire: ILaboratoire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratoire);
    return this.http
      .post<ILaboratoire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(laboratoire: ILaboratoire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratoire);
    return this.http
      .put<ILaboratoire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILaboratoire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILaboratoire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(laboratoire: ILaboratoire): ILaboratoire {
    const copy: ILaboratoire = Object.assign({}, laboratoire, {
      heureOuverture:
        laboratoire.heureOuverture != null && laboratoire.heureOuverture.isValid() ? laboratoire.heureOuverture.toJSON() : null,
      heureFermeture:
        laboratoire.heureFermeture != null && laboratoire.heureFermeture.isValid() ? laboratoire.heureFermeture.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureOuverture = res.body.heureOuverture != null ? moment(res.body.heureOuverture) : null;
      res.body.heureFermeture = res.body.heureFermeture != null ? moment(res.body.heureFermeture) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((laboratoire: ILaboratoire) => {
        laboratoire.heureOuverture = laboratoire.heureOuverture != null ? moment(laboratoire.heureOuverture) : null;
        laboratoire.heureFermeture = laboratoire.heureFermeture != null ? moment(laboratoire.heureFermeture) : null;
      });
    }
    return res;
  }
}
