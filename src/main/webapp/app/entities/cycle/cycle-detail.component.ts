import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICycle } from 'app/shared/model/cycle.model';

@Component({
  selector: 'jhi-cycle-detail',
  templateUrl: './cycle-detail.component.html'
})
export class CycleDetailComponent implements OnInit {
  cycle: ICycle;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cycle }) => {
      this.cycle = cycle;
    });
  }

  previousState() {
    window.history.back();
  }
}
