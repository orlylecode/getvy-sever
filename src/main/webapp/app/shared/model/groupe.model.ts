import { IPharmacie } from 'app/shared/model/pharmacie.model';
import { ICycle } from 'app/shared/model/cycle.model';

export interface IGroupe {
  id?: number;
  nom?: string;
  libelle?: string;
  pharmacies?: IPharmacie[];
  cycle?: ICycle;
}

export class Groupe implements IGroupe {
  constructor(public id?: number, public nom?: string, public libelle?: string, public pharmacies?: IPharmacie[], public cycle?: ICycle) {}
}
