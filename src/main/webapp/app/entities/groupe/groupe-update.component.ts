import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGroupe, Groupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';

@Component({
  selector: 'jhi-groupe-update',
  templateUrl: './groupe-update.component.html'
})
export class GroupeUpdateComponent implements OnInit {
  isSaving: boolean;

  cycles: ICycle[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    libelle: [],
    cycle: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected groupeService: GroupeService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ groupe }) => {
      this.updateForm(groupe);
    });
    this.cycleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICycle[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICycle[]>) => response.body)
      )
      .subscribe((res: ICycle[]) => (this.cycles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(groupe: IGroupe) {
    this.editForm.patchValue({
      id: groupe.id,
      nom: groupe.nom,
      libelle: groupe.libelle,
      cycle: groupe.cycle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const groupe = this.createFromForm();
    if (groupe.id !== undefined) {
      this.subscribeToSaveResponse(this.groupeService.update(groupe));
    } else {
      this.subscribeToSaveResponse(this.groupeService.create(groupe));
    }
  }

  private createFromForm(): IGroupe {
    return {
      ...new Groupe(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      libelle: this.editForm.get(['libelle']).value,
      cycle: this.editForm.get(['cycle']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>) {
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
