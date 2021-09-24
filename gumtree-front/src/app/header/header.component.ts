import {Component, OnInit} from '@angular/core'
import {CategoryService} from "../service/category.service";
import {Category} from "../shared/model/category/category";
import {Localization} from "../shared/model/localization/localization";
import {LocalizationItem} from "../shared/model/localization/localization-item";
import {LocalizationService} from "../service/localization.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  collapsed = true;

  categories: Category[];
  localizations: LocalizationItem[];

  defaultCategory: Category = {
    categoryName: 'Wszystkie Kategorie',
    searchableName: 'wszystkie-kategorie',
    parents: null,
    subcategories: null
  };

  defaultLocalization: LocalizationItem = {
    name: 'Polska',
    searchableName: 'polska'
  }

  selectedQuery: string;
  selectedCategory: Category = this.defaultCategory;
  selectedLocalization: LocalizationItem = this.defaultLocalization;

  constructor(private categoryService: CategoryService, private localizationService: LocalizationService) {
  }

  ngOnInit(): void {
    this.categoryService.downloadMainCategories().subscribe(res => {
      this.categories = res.categories;
    });
    this.localizationService.downloadMainLocalizations().subscribe(res => {
      this.localizations = res.localizations[0].childrens;
      console.log(this.localizations);
    });
  }

  onLocationChoose(localization: LocalizationItem){
    this.selectedLocalization = localization;
  }

  onCategoryChoose(category: string){
    console.log(category);
  }


  onSubmit() {
    console.log(this.selectedCategory);
    console.log(this.selectedLocalization);
    console.log(this.selectedQuery);
  }
}
