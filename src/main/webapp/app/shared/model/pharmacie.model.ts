import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IPharmacie {
  id?: number;
  nom?: string;
  lieu?: string;
  adresseRue?: string;
  codePostal?: string;
  numeroTel?: string;
  email?: string;
  ville?: string;
  region?: string;
  heureOuverture?: Moment;
  heureFermeture?: Moment;
  latitude?: number;
  longitude?: number;
  produits?: IProduit[];
  groupe?: IGroupe;
}

export class Pharmacie implements IPharmacie {
  constructor(
    public id?: number,
    public nom?: string,
    public lieu?: string,
    public adresseRue?: string,
    public codePostal?: string,
    public numeroTel?: string,
    public email?: string,
    public ville?: string,
    public region?: string,
    public heureOuverture?: Moment,
    public heureFermeture?: Moment,
    public latitude?: number,
    public longitude?: number,
    public produits?: IProduit[],
    public groupe?: IGroupe
  ) {}
}
