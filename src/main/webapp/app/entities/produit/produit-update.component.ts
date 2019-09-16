import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { IPharmacie } from 'app/shared/model/pharmacie.model';
import { PharmacieService } from 'app/entities/pharmacie/pharmacie.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html'
})
export class ProduitUpdateComponent implements OnInit {
  isSaving: boolean;

  pharmacies: IPharmacie[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    prix: [],
    disponibilite: [],
    pharmacie: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected produitService: ProduitService,
    protected pharmacieService: PharmacieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);
    });
    this.pharmacieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPharmacie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPharmacie[]>) => response.body)
      )
      .subscribe((res: IPharmacie[]) => (this.pharmacies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(produit: IProduit) {
    this.editForm.patchValue({
      id: produit.id,
      nom: produit.nom,
      description: produit.description,
      prix: produit.prix,
      disponibilite: produit.disponibilite,
      pharmacie: produit.pharmacie
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      prix: this.editForm.get(['prix']).value,
      disponibilite: this.editForm.get(['disponibilite']).value,
      pharmacie: this.editForm.get(['pharmacie']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>) {
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

  trackPharmacieById(index: number, item: IPharmacie) {
    return item.id;
  }
}
