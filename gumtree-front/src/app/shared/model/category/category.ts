import {Subcategory} from "./subcategory";

export class Category {

  constructor(public categoryName: string, public searchableName: string, public subcategories: Subcategory[], public parents: Subcategory[]) {
    this.categoryName = categoryName;
    this.searchableName = searchableName;
    this.subcategories = subcategories;
    this.parents = parents;
  }

}
