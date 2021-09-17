import { Component, OnInit } from '@angular/core';
import {Subcategory} from "../../shared/model/category/subcategory";
import {Offer} from "../../shared/model/offer";
import {CategoryService} from "../../service/category-service/category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Category} from "../../shared/model/category/category";

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.scss']
})
export class CategoryPageComponent implements OnInit {

  category: Category;
  offers: Offer[];

  constructor(private categoryService: CategoryService, private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    let categoryName = this.activatedRoute.snapshot.params['category'];
    this.categoryService.downloadSubcategories(categoryName).subscribe(response => this.category = response);
    this.categoryService.downloadOffersForCategory(categoryName).subscribe(response => this.offers = response.offers);

    this.activatedRoute.params.subscribe((params: Params) => {
      console.log(params);
      let categoryName = params['category'];
      this.categoryService.downloadSubcategories(categoryName).subscribe(response => this.category = response);
      this.categoryService.downloadOffersForCategory(categoryName).subscribe(response => this.offers = response.offers);
    })
  }

}
