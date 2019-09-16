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
import { IPharmacie, Pharmacie } from 'app/shared/model/pharmacie.model';
import { PharmacieService } from './pharmacie.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';

@Component({
  selector: 'jhi-pharmacie-update',
  templateUrl: './pharmacie-update.component.html'
})
export class PharmacieUpdateComponent implements OnInit {
  isSaving: boolean;

  groupes: IGroupe[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    lieu: [],
    adresseRue: [],
    codePostal: [],
    numeroTel: [],
    email: [],
    ville: [],
    region: [],
    heureOuverture: [],
    heureFermeture: [],
    latitude: [],
    longitude: [],
    groupe: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pharmacieService: PharmacieService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pharmacie }) => {
      this.updateForm(pharmacie);
    });
    this.groupeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGroupe[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGroupe[]>) => response.body)
      )
      .subscribe((res: IGroupe[]) => (this.groupes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pharmacie: IPharmacie) {
    this.editForm.patchValue({
      id: pharmacie.id,
      nom: pharmacie.nom,
      lieu: pharmacie.lieu,
      adresseRue: pharmacie.adresseRue,
      codePostal: pharmacie.codePostal,
      numeroTel: pharmacie.numeroTel,
      email: pharmacie.email,
      ville: pharmacie.ville,
      region: pharmacie.region,
      heureOuverture: pharmacie.heureOuverture != null ? pharmacie.heureOuverture.format(DATE_TIME_FORMAT) : null,
      heureFermeture: pharmacie.heureFermeture != null ? pharmacie.heureFermeture.format(DATE_TIME_FORMAT) : null,
      latitude: pharmacie.latitude,
      longitude: pharmacie.longitude,
      groupe: pharmacie.groupe
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pharmacie = this.createFromForm();
    if (pharmacie.id !== undefined) {
      this.subscribeToSaveResponse(this.pharmacieService.update(pharmacie));
    } else {
      this.subscribeToSaveResponse(this.pharmacieService.create(pharmacie));
    }
  }

  private createFromForm(): IPharmacie {
    return {
      ...new Pharmacie(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      lieu: this.editForm.get(['lieu']).value,
      adresseRue: this.editForm.get(['adresseRue']).value,
      codePostal: this.editForm.get(['codePostal']).value,
      numeroTel: this.editForm.get(['numeroTel']).value,
      email: this.editForm.get(['email']).value,
      ville: this.editForm.get(['ville']).value,
      region: this.editForm.get(['region']).value,
      heureOuverture:
        this.editForm.get(['heureOuverture']).value != null
          ? moment(this.editForm.get(['heureOuverture']).value, DATE_TIME_FORMAT)
          : undefined,
      heureFermeture:
        this.editForm.get(['heureFermeture']).value != null
          ? moment(this.editForm.get(['heureFermeture']).value, DATE_TIME_FORMAT)
          : undefined,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value,
      groupe: this.editForm.get(['groupe']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPharmacie>>) {
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

  trackGroupeById(index: number, item: IGroupe) {
    return item.id;
  }
}
