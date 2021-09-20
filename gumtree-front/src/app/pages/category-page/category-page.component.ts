import { Component, OnInit } from '@angular/core';
import {Offer} from "../../shared/model/offer";
import {CategoryService} from "../../service/category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Category} from "../../shared/model/category/category";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html'
})
export class CategoryPageComponent implements OnInit {

  category: Category;
  offers: Offer[];

  constructor(private categoryService: CategoryService, private router: Router,
              private activatedRoute: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      let categoryName = params['category'];
      this.categoryService.downloadSubcategories(categoryName).subscribe(response => this.category = response);
      this.categoryService.downloadOffersForCategory(categoryName).subscribe(response => this.offers = response.offers);
    });
    this.authService.getUsers();
  }

}
