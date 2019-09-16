import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICycle, Cycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';

@Component({
  selector: 'jhi-cycle-update',
  templateUrl: './cycle-update.component.html'
})
export class CycleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    libelle: []
  });

  constructor(protected cycleService: CycleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cycle }) => {
      this.updateForm(cycle);
    });
  }

  updateForm(cycle: ICycle) {
    this.editForm.patchValue({
      id: cycle.id,
      dateDebut: cycle.dateDebut != null ? cycle.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: cycle.dateFin != null ? cycle.dateFin.format(DATE_TIME_FORMAT) : null,
      libelle: cycle.libelle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cycle = this.createFromForm();
    if (cycle.id !== undefined) {
      this.subscribeToSaveResponse(this.cycleService.update(cycle));
    } else {
      this.subscribeToSaveResponse(this.cycleService.create(cycle));
    }
  }

  private createFromForm(): ICycle {
    return {
      ...new Cycle(),
      id: this.editForm.get(['id']).value,
      dateDebut:
        this.editForm.get(['dateDebut']).value != null ? moment(this.editForm.get(['dateDebut']).value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin']).value != null ? moment(this.editForm.get(['dateFin']).value, DATE_TIME_FORMAT) : undefined,
      libelle: this.editForm.get(['libelle']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycle>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
