import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../shared/model/category/category";
import {Observable} from "rxjs";
import {Offer} from "../shared/model/offer";

@Injectable({providedIn: "root"})
export class CategoryService implements OnInit {

  constructor(private http: HttpClient) { }

  private readonly server = "http://localhost:8080";

  ngOnInit(): void {
  }

  downloadMainCategories(): Observable<{ categories:Category[] }> {
    console.log('calling main categories');
    return this.http.get<{ categories:Category[] }>(this.server + "/api/categories", {responseType: "json"});
  }

  downloadSubcategories(categoryName: string): Observable<Category>{
    return this.http.get<Category>(this.server + "/api/categories/" + categoryName);
  }

  downloadOffersForCategory(categoryName: string): Observable<{category:string, offers:Offer[]}> {
    return this.http.get<{category:string, offers:Offer[]}>(this.server + "/api/categories/" + categoryName + "/offers");
  }

}
