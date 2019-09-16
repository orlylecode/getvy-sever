import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILaboratoire, Laboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from './laboratoire.service';

@Component({
  selector: 'jhi-laboratoire-update',
  templateUrl: './laboratoire-update.component.html'
})
export class LaboratoireUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    latitude: [],
    longitude: [],
    lieu: [],
    adresseRue: [],
    codePostal: [],
    numeroTel: [],
    email: [],
    heureOuverture: [],
    heureFermeture: [],
    ville: [],
    region: []
  });

  constructor(protected laboratoireService: LaboratoireService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ laboratoire }) => {
      this.updateForm(laboratoire);
    });
  }

  updateForm(laboratoire: ILaboratoire) {
    this.editForm.patchValue({
      id: laboratoire.id,
      nom: laboratoire.nom,
      latitude: laboratoire.latitude,
      longitude: laboratoire.longitude,
      lieu: laboratoire.lieu,
      adresseRue: laboratoire.adresseRue,
      codePostal: laboratoire.codePostal,
      numeroTel: laboratoire.numeroTel,
      email: laboratoire.email,
      heureOuverture: laboratoire.heureOuverture != null ? laboratoire.heureOuverture.format(DATE_TIME_FORMAT) : null,
      heureFermeture: laboratoire.heureFermeture != null ? laboratoire.heureFermeture.format(DATE_TIME_FORMAT) : null,
      ville: laboratoire.ville,
      region: laboratoire.region
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const laboratoire = this.createFromForm();
    if (laboratoire.id !== undefined) {
      this.subscribeToSaveResponse(this.laboratoireService.update(laboratoire));
    } else {
      this.subscribeToSaveResponse(this.laboratoireService.create(laboratoire));
    }
  }

  private createFromForm(): ILaboratoire {
    return {
      ...new Laboratoire(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value,
      lieu: this.editForm.get(['lieu']).value,
      adresseRue: this.editForm.get(['adresseRue']).value,
      codePostal: this.editForm.get(['codePostal']).value,
      numeroTel: this.editForm.get(['numeroTel']).value,
      email: this.editForm.get(['email']).value,
      heureOuverture:
        this.editForm.get(['heureOuverture']).value != null
          ? moment(this.editForm.get(['heureOuverture']).value, DATE_TIME_FORMAT)
          : undefined,
      heureFermeture:
        this.editForm.get(['heureFermeture']).value != null
          ? moment(this.editForm.get(['heureFermeture']).value, DATE_TIME_FORMAT)
          : undefined,
      ville: this.editForm.get(['ville']).value,
      region: this.editForm.get(['region']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratoire>>) {
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
