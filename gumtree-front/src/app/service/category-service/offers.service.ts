import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../../shared/model/category/category";
import {Observable} from "rxjs";
import {Offer} from "../../shared/model/offer";

@Injectable({providedIn: "root"})
export class OffersService implements OnInit {

  constructor(private http: HttpClient) { }

  private readonly server = "http://localhost:8080";

  ngOnInit(): void {
  }

  downloadOffer(offerId: string): Observable<Offer> {
    return this.http.get<Offer>(this.server + "/api/offers/" + offerId);
  }

}
