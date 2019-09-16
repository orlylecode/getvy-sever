import { Moment } from 'moment';
import { IExamen } from 'app/shared/model/examen.model';
import { IJourTravail } from 'app/shared/model/jour-travail.model';

export interface ILaboratoire {
  id?: number;
  nom?: string;
  latitude?: number;
  longitude?: number;
  lieu?: string;
  adresseRue?: string;
  codePostal?: string;
  numeroTel?: string;
  email?: string;
  heureOuverture?: Moment;
  heureFermeture?: Moment;
  ville?: string;
  region?: string;
  examen?: IExamen[];
  jourNonOuvrables?: IJourTravail[];
}

export class Laboratoire implements ILaboratoire {
  constructor(
    public id?: number,
    public nom?: string,
    public latitude?: number,
    public longitude?: number,
    public lieu?: string,
    public adresseRue?: string,
    public codePostal?: string,
    public numeroTel?: string,
    public email?: string,
    public heureOuverture?: Moment,
    public heureFermeture?: Moment,
    public ville?: string,
    public region?: string,
    public examen?: IExamen[],
    public jourNonOuvrables?: IJourTravail[]
  ) {}
}
