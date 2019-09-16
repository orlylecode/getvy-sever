import { ILaboratoire } from 'app/shared/model/laboratoire.model';

export interface IExamen {
  id?: number;
  nom?: string;
  description?: string;
  prix?: number;
  disponibilite?: boolean;
  laboratoire?: ILaboratoire;
}

export class Examen implements IExamen {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public prix?: number,
    public disponibilite?: boolean,
    public laboratoire?: ILaboratoire
  ) {
    this.disponibilite = this.disponibilite || false;
  }
}
