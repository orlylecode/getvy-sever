import { Moment } from 'moment';
import { IGroupe } from 'app/shared/model/groupe.model';
import { IJourDeGarde } from 'app/shared/model/jour-de-garde.model';

export interface ICycle {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  libelle?: string;
  groupes?: IGroupe[];
  jourDeGardes?: IJourDeGarde[];
}

export class Cycle implements ICycle {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public libelle?: string,
    public groupes?: IGroupe[],
    public jourDeGardes?: IJourDeGarde[]
  ) {}
}
