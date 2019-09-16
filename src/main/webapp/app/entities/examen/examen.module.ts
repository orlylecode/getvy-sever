import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { ExamenComponent } from './examen.component';
import { ExamenDetailComponent } from './examen-detail.component';
import { ExamenUpdateComponent } from './examen-update.component';
import { ExamenDeletePopupComponent, ExamenDeleteDialogComponent } from './examen-delete-dialog.component';
import { examenRoute, examenPopupRoute } from './examen.route';

const ENTITY_STATES = [...examenRoute, ...examenPopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ExamenComponent, ExamenDetailComponent, ExamenUpdateComponent, ExamenDeleteDialogComponent, ExamenDeletePopupComponent],
  entryComponents: [ExamenComponent, ExamenUpdateComponent, ExamenDeleteDialogComponent, ExamenDeletePopupComponent]
})
export class GetvySeverExamenModule {}
