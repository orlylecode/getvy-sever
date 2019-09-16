import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamen } from 'app/shared/model/examen.model';

type EntityResponseType = HttpResponse<IExamen>;
type EntityArrayResponseType = HttpResponse<IExamen[]>;

@Injectable({ providedIn: 'root' })
export class ExamenService {
  public resourceUrl = SERVER_API_URL + 'api/examen';

  constructor(protected http: HttpClient) {}

  create(examen: IExamen): Observable<EntityResponseType> {
    return this.http.post<IExamen>(this.resourceUrl, examen, { observe: 'response' });
  }

  update(examen: IExamen): Observable<EntityResponseType> {
    return this.http.put<IExamen>(this.resourceUrl, examen, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
