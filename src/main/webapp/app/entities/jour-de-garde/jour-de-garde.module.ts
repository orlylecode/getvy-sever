import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { JourDeGardeComponent } from './jour-de-garde.component';
import { JourDeGardeDetailComponent } from './jour-de-garde-detail.component';
import { JourDeGardeUpdateComponent } from './jour-de-garde-update.component';
import { JourDeGardeDeletePopupComponent, JourDeGardeDeleteDialogComponent } from './jour-de-garde-delete-dialog.component';
import { jourDeGardeRoute, jourDeGardePopupRoute } from './jour-de-garde.route';

const ENTITY_STATES = [...jourDeGardeRoute, ...jourDeGardePopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    JourDeGardeComponent,
    JourDeGardeDetailComponent,
    JourDeGardeUpdateComponent,
    JourDeGardeDeleteDialogComponent,
    JourDeGardeDeletePopupComponent
  ],
  entryComponents: [JourDeGardeComponent, JourDeGardeUpdateComponent, JourDeGardeDeleteDialogComponent, JourDeGardeDeletePopupComponent]
})
export class GetvySeverJourDeGardeModule {}
