import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../shared/model/category/category";
import {Observable} from "rxjs";
import {Offer} from "../shared/model/offer";
import {Localization} from "../shared/model/localization/localization";

@Injectable({providedIn: "root"})
export class LocalizationService implements OnInit {

  constructor(private http: HttpClient) { }

  private readonly server = "http://localhost:8080";

  ngOnInit(): void {
  }

  downloadMainLocalizations(): Observable<{ localizations:Localization[] }> {
    console.log('calling main localizations');
    return this.http.get<{ localizations:Localization[] }>(this.server + "/api/localizations", {responseType: "json"});
  }

  // downloadSubcategories(categoryName: string): Observable<Category>{
  //   return this.http.get<Category>(this.server + "/api/categories/" + categoryName);
  // }
  //
  // downloadOffersForCategory(categoryName: string): Observable<{category:string, offers:Offer[]}> {
  //   return this.http.get<{category:string, offers:Offer[]}>(this.server + "/api/categories/" + categoryName + "/offers");
  // }

}
