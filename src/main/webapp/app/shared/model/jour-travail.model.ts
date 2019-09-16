import { Moment } from 'moment';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';

export interface IJourTravail {
  id?: number;
  jour?: Moment;
  laboratoire?: ILaboratoire;
}

export class JourTravail implements IJourTravail {
  constructor(public id?: number, public jour?: Moment, public laboratoire?: ILaboratoire) {}
}
