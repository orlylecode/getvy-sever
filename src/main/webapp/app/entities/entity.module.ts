import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pharmacie',
        loadChildren: () => import('./pharmacie/pharmacie.module').then(m => m.GetvySeverPharmacieModule)
      },
      {
        path: 'groupe',
        loadChildren: () => import('./groupe/groupe.module').then(m => m.GetvySeverGroupeModule)
      },
      {
        path: 'laboratoire',
        loadChildren: () => import('./laboratoire/laboratoire.module').then(m => m.GetvySeverLaboratoireModule)
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.GetvySeverProduitModule)
      },
      {
        path: 'examen',
        loadChildren: () => import('./examen/examen.module').then(m => m.GetvySeverExamenModule)
      },
      {
        path: 'jour-de-garde',
        loadChildren: () => import('./jour-de-garde/jour-de-garde.module').then(m => m.GetvySeverJourDeGardeModule)
      },
      {
        path: 'jour-travail',
        loadChildren: () => import('./jour-travail/jour-travail.module').then(m => m.GetvySeverJourTravailModule)
      },
      {
        path: 'cycle',
        loadChildren: () => import('./cycle/cycle.module').then(m => m.GetvySeverCycleModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: []
})
export class GetvySeverEntityModule {}
