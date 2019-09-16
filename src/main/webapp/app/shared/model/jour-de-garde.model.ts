import { Moment } from 'moment';
import { ICycle } from 'app/shared/model/cycle.model';

export interface IJourDeGarde {
  id?: number;
  jour?: Moment;
  cycle?: ICycle;
}

export class JourDeGarde implements IJourDeGarde {
  constructor(public id?: number, public jour?: Moment, public cycle?: ICycle) {}
}
