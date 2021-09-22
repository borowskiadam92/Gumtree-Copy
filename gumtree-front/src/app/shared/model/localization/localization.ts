import {LocalizationItem} from "./localization-item";

export class Localization {

  constructor(public current: LocalizationItem, public childrens: LocalizationItem[], public parents: LocalizationItem[] ) {
    this.current = current;
    this.childrens = childrens;
    this.parents = parents;
  }
}
