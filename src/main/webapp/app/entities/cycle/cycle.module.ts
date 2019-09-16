import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { CycleComponent } from './cycle.component';
import { CycleDetailComponent } from './cycle-detail.component';
import { CycleUpdateComponent } from './cycle-update.component';
import { CycleDeletePopupComponent, CycleDeleteDialogComponent } from './cycle-delete-dialog.component';
import { cycleRoute, cyclePopupRoute } from './cycle.route';

const ENTITY_STATES = [...cycleRoute, ...cyclePopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CycleComponent, CycleDetailComponent, CycleUpdateComponent, CycleDeleteDialogComponent, CycleDeletePopupComponent],
  entryComponents: [CycleComponent, CycleUpdateComponent, CycleDeleteDialogComponent, CycleDeletePopupComponent]
})
export class GetvySeverCycleModule {}
