import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../service/category.service";
import {Category} from "../../shared/model/category/category";
import {Localization} from "../../shared/model/localization/localization";
import {LocalizationService} from "../../service/localization.service";
import {LocalizationItem} from "../../shared/model/localization/localization-item";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  categories: Category[];
  localizations: Localization[];

  constructor(private categoryService: CategoryService, private localizationService: LocalizationService) {
  }

  ngOnInit(): void {
    console.log('on init mainpage');
    this.categoryService.downloadMainCategories().subscribe(response => {
      console.log(response);
      this.categories = response.categories;
    });

    this.localizationService.downloadMainLocalizations().subscribe(response => {
      console.log(response);
      this.localizations = response.localizations;
    });

  }

}
