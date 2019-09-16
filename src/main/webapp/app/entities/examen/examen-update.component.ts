import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExamen, Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from 'app/entities/laboratoire/laboratoire.service';

@Component({
  selector: 'jhi-examen-update',
  templateUrl: './examen-update.component.html'
})
export class ExamenUpdateComponent implements OnInit {
  isSaving: boolean;

  laboratoires: ILaboratoire[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    prix: [],
    disponibilite: [],
    laboratoire: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected examenService: ExamenService,
    protected laboratoireService: LaboratoireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ examen }) => {
      this.updateForm(examen);
    });
    this.laboratoireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILaboratoire[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILaboratoire[]>) => response.body)
      )
      .subscribe((res: ILaboratoire[]) => (this.laboratoires = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(examen: IExamen) {
    this.editForm.patchValue({
      id: examen.id,
      nom: examen.nom,
      description: examen.description,
      prix: examen.prix,
      disponibilite: examen.disponibilite,
      laboratoire: examen.laboratoire
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const examen = this.createFromForm();
    if (examen.id !== undefined) {
      this.subscribeToSaveResponse(this.examenService.update(examen));
    } else {
      this.subscribeToSaveResponse(this.examenService.create(examen));
    }
  }

  private createFromForm(): IExamen {
    return {
      ...new Examen(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      prix: this.editForm.get(['prix']).value,
      disponibilite: this.editForm.get(['disponibilite']).value,
      laboratoire: this.editForm.get(['laboratoire']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>) {
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
