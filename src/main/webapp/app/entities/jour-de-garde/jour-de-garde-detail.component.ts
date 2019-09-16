import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';

@Component({
  selector: 'jhi-jour-de-garde-detail',
  templateUrl: './jour-de-garde-detail.component.html'
})
export class JourDeGardeDetailComponent implements OnInit {
  jourDeGarde: IJourDeGarde;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jourDeGarde }) => {
      this.jourDeGarde = jourDeGarde;
    });
  }

  previousState() {
    window.history.back();
  }
}
