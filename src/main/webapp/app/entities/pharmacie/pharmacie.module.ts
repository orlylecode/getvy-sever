import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { PharmacieComponent } from './pharmacie.component';
import { PharmacieDetailComponent } from './pharmacie-detail.component';
import { PharmacieUpdateComponent } from './pharmacie-update.component';
import { PharmacieDeletePopupComponent, PharmacieDeleteDialogComponent } from './pharmacie-delete-dialog.component';
import { pharmacieRoute, pharmaciePopupRoute } from './pharmacie.route';

const ENTITY_STATES = [...pharmacieRoute, ...pharmaciePopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PharmacieComponent,
    PharmacieDetailComponent,
    PharmacieUpdateComponent,
    PharmacieDeleteDialogComponent,
    PharmacieDeletePopupComponent
  ],
  entryComponents: [PharmacieComponent, PharmacieUpdateComponent, PharmacieDeleteDialogComponent, PharmacieDeletePopupComponent]
})
export class GetvySeverPharmacieModule {}
