import {Subcategory} from "./subcategory";

export class Category {

  constructor(public mainCategoryName: string, public searchableName: string, public subcategories: Subcategory[]) {
    this.mainCategoryName = mainCategoryName;
    this.searchableName = searchableName;
    this.subcategories = subcategories;
  }

}
