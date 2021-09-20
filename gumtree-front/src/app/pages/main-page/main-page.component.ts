import { Component, OnInit } from '@angular/core';
import {CategoryService} from "../../service/category.service";
import {Category} from "../../shared/model/category/category";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  categories: Category[];

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    console.log('on init mainpage');
    this.categoryService.downloadMainCategories().subscribe(response => {
      console.log(response);
      this.categories = response.categories;
    });
  }

}
