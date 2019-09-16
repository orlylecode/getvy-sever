import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IJourTravail, JourTravail } from 'app/shared/model/jour-travail.model';
import { JourTravailService } from './jour-travail.service';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from 'app/entities/laboratoire/laboratoire.service';

@Component({
  selector: 'jhi-jour-travail-update',
  templateUrl: './jour-travail-update.component.html'
})
export class JourTravailUpdateComponent implements OnInit {
  isSaving: boolean;

  laboratoires: ILaboratoire[];

  editForm = this.fb.group({
    id: [],
    jour: [null, [Validators.required]],
    laboratoire: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected jourTravailService: JourTravailService,
    protected laboratoireService: LaboratoireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jourTravail }) => {
      this.updateForm(jourTravail);
    });
    this.laboratoireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILaboratoire[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILaboratoire[]>) => response.body)
      )
      .subscribe((res: ILaboratoire[]) => (this.laboratoires = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(jourTravail: IJourTravail) {
    this.editForm.patchValue({
      id: jourTravail.id,
      jour: jourTravail.jour != null ? jourTravail.jour.format(DATE_TIME_FORMAT) : null,
      laboratoire: jourTravail.laboratoire
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jourTravail = this.createFromForm();
    if (jourTravail.id !== undefined) {
      this.subscribeToSaveResponse(this.jourTravailService.update(jourTravail));
    } else {
      this.subscribeToSaveResponse(this.jourTravailService.create(jourTravail));
    }
  }

  private createFromForm(): IJourTravail {
    return {
      ...new JourTravail(),
      id: this.editForm.get(['id']).value,
      jour: this.editForm.get(['jour']).value != null ? moment(this.editForm.get(['jour']).value, DATE_TIME_FORMAT) : undefined,
      laboratoire: this.editForm.get(['laboratoire']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourTravail>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackLaboratoireById(index: number, item: ILaboratoire) {
    return item.id;
  }
}
