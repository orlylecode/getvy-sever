import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPharmacie } from 'app/shared/model/pharmacie.model';

@Component({
  selector: 'jhi-pharmacie-detail',
  templateUrl: './pharmacie-detail.component.html'
})
export class PharmacieDetailComponent implements OnInit {
  pharmacie: IPharmacie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacie }) => {
      this.pharmacie = pharmacie;
    });
  }

  previousState() {
    window.history.back();
  }
}
