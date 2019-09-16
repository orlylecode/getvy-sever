import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { ProduitComponent } from './produit.component';
import { ProduitDetailComponent } from './produit-detail.component';
import { ProduitUpdateComponent } from './produit-update.component';
import { ProduitDeletePopupComponent, ProduitDeleteDialogComponent } from './produit-delete-dialog.component';
import { produitRoute, produitPopupRoute } from './produit.route';

const ENTITY_STATES = [...produitRoute, ...produitPopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProduitComponent,
    ProduitDetailComponent,
    ProduitUpdateComponent,
    ProduitDeleteDialogComponent,
    ProduitDeletePopupComponent
  ],
  entryComponents: [ProduitComponent, ProduitUpdateComponent, ProduitDeleteDialogComponent, ProduitDeletePopupComponent]
})
export class GetvySeverProduitModule {}
