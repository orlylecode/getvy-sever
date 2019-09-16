import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { LaboratoireComponent } from './laboratoire.component';
import { LaboratoireDetailComponent } from './laboratoire-detail.component';
import { LaboratoireUpdateComponent } from './laboratoire-update.component';
import { LaboratoireDeletePopupComponent, LaboratoireDeleteDialogComponent } from './laboratoire-delete-dialog.component';
import { laboratoireRoute, laboratoirePopupRoute } from './laboratoire.route';

const ENTITY_STATES = [...laboratoireRoute, ...laboratoirePopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LaboratoireComponent,
    LaboratoireDetailComponent,
    LaboratoireUpdateComponent,
    LaboratoireDeleteDialogComponent,
    LaboratoireDeletePopupComponent
  ],
  entryComponents: [LaboratoireComponent, LaboratoireUpdateComponent, LaboratoireDeleteDialogComponent, LaboratoireDeletePopupComponent]
})
export class GetvySeverLaboratoireModule {}
