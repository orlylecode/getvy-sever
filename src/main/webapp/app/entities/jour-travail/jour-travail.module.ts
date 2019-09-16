import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { JourTravailComponent } from './jour-travail.component';
import { JourTravailDetailComponent } from './jour-travail-detail.component';
import { JourTravailUpdateComponent } from './jour-travail-update.component';
import { JourTravailDeletePopupComponent, JourTravailDeleteDialogComponent } from './jour-travail-delete-dialog.component';
import { jourTravailRoute, jourTravailPopupRoute } from './jour-travail.route';

const ENTITY_STATES = [...jourTravailRoute, ...jourTravailPopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    JourTravailComponent,
    JourTravailDetailComponent,
    JourTravailUpdateComponent,
    JourTravailDeleteDialogComponent,
    JourTravailDeletePopupComponent
  ],
  entryComponents: [JourTravailComponent, JourTravailUpdateComponent, JourTravailDeleteDialogComponent, JourTravailDeletePopupComponent]
})
export class GetvySeverJourTravailModule {}
