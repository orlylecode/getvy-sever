import { IPharmacie } from 'app/shared/model/pharmacie.model';

export interface IProduit {
  id?: number;
  nom?: string;
  description?: string;
  prix?: number;
  disponibilite?: boolean;
  pharmacie?: IPharmacie;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public prix?: number,
    public disponibilite?: boolean,
    public pharmacie?: IPharmacie
  ) {
    this.disponibilite = this.disponibilite || false;
  }
}
