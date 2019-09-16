import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourTravail } from 'app/shared/model/jour-travail.model';

@Component({
  selector: 'jhi-jour-travail-detail',
  templateUrl: './jour-travail-detail.component.html'
})
export class JourTravailDetailComponent implements OnInit {
  jourTravail: IJourTravail;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jourTravail }) => {
      this.jourTravail = jourTravail;
    });
  }

  previousState() {
    window.history.back();
  }
}
