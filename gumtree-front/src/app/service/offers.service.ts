import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Offer} from "../shared/model/offer";

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private httpClient: HttpClient) { }

  getOfferById(offerId: string): Observable<Offer> {
    return this.httpClient.get<Offer>('http://localhost:8080/api/offers/' + offerId);
  }

  updateOffer(offerId:string, offerBody: Offer){
    return this.httpClient.put('http://localhost:8080/api/offers/' + offerId, offerBody);
  }

  createOffer(offerBody: Offer){
    return this.httpClient.post('http://localhost:8080/api/offers/', offerBody);
  }
}
