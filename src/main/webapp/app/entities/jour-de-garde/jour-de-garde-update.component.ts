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
import { IJourDeGarde, JourDeGarde } from 'app/shared/model/jour-de-garde.model';
import { JourDeGardeService } from './jour-de-garde.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';

@Component({
  selector: 'jhi-jour-de-garde-update',
  templateUrl: './jour-de-garde-update.component.html'
})
export class JourDeGardeUpdateComponent implements OnInit {
  isSaving: boolean;

  cycles: ICycle[];

  editForm = this.fb.group({
    id: [],
    jour: [null, [Validators.required]],
    cycle: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected jourDeGardeService: JourDeGardeService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jourDeGarde }) => {
      this.updateForm(jourDeGarde);
    });
    this.cycleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICycle[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICycle[]>) => response.body)
      )
      .subscribe((res: ICycle[]) => (this.cycles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(jourDeGarde: IJourDeGarde) {
    this.editForm.patchValue({
      id: jourDeGarde.id,
      jour: jourDeGarde.jour != null ? jourDeGarde.jour.format(DATE_TIME_FORMAT) : null,
      cycle: jourDeGarde.cycle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jourDeGarde = this.createFromForm();
    if (jourDeGarde.id !== undefined) {
      this.subscribeToSaveResponse(this.jourDeGardeService.update(jourDeGarde));
    } else {
      this.subscribeToSaveResponse(this.jourDeGardeService.create(jourDeGarde));
    }
  }

  private createFromForm(): IJourDeGarde {
    return {
      ...new JourDeGarde(),
      id: this.editForm.get(['id']).value,
      jour: this.editForm.get(['jour']).value != null ? moment(this.editForm.get(['jour']).value, DATE_TIME_FORMAT) : undefined,
      cycle: this.editForm.get(['cycle']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourDeGarde>>) {
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

  trackCycleById(index: number, item: ICycle) {
    return item.id;
  }
}
