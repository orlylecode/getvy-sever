import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPharmacie } from 'app/shared/model/pharmacie.model';

type EntityResponseType = HttpResponse<IPharmacie>;
type EntityArrayResponseType = HttpResponse<IPharmacie[]>;

@Injectable({ providedIn: 'root' })
export class PharmacieService {
  public resourceUrl = SERVER_API_URL + 'api/pharmacies';

  constructor(protected http: HttpClient) {}

  create(pharmacie: IPharmacie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacie);
    return this.http
      .post<IPharmacie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pharmacie: IPharmacie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacie);
    return this.http
      .put<IPharmacie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPharmacie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPharmacie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pharmacie: IPharmacie): IPharmacie {
    const copy: IPharmacie = Object.assign({}, pharmacie, {
      heureOuverture: pharmacie.heureOuverture != null && pharmacie.heureOuverture.isValid() ? pharmacie.heureOuverture.toJSON() : null,
      heureFermeture: pharmacie.heureFermeture != null && pharmacie.heureFermeture.isValid() ? pharmacie.heureFermeture.toJSON() : null
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
      res.body.forEach((pharmacie: IPharmacie) => {
        pharmacie.heureOuverture = pharmacie.heureOuverture != null ? moment(pharmacie.heureOuverture) : null;
        pharmacie.heureFermeture = pharmacie.heureFermeture != null ? moment(pharmacie.heureFermeture) : null;
      });
    }
    return res;
  }
}
