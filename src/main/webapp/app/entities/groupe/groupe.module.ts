import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GetvySeverSharedModule } from 'app/shared/shared.module';
import { GroupeComponent } from './groupe.component';
import { GroupeDetailComponent } from './groupe-detail.component';
import { GroupeUpdateComponent } from './groupe-update.component';
import { GroupeDeletePopupComponent, GroupeDeleteDialogComponent } from './groupe-delete-dialog.component';
import { groupeRoute, groupePopupRoute } from './groupe.route';

const ENTITY_STATES = [...groupeRoute, ...groupePopupRoute];

@NgModule({
  imports: [GetvySeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GroupeComponent, GroupeDetailComponent, GroupeUpdateComponent, GroupeDeleteDialogComponent, GroupeDeletePopupComponent],
  entryComponents: [GroupeComponent, GroupeUpdateComponent, GroupeDeleteDialogComponent, GroupeDeletePopupComponent]
})
export class GetvySeverGroupeModule {}
