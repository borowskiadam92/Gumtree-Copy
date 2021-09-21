export class Offer {

  constructor(public id: number, public title: string, public price: number,
              public publishDate: Date, public content: string, public localization: string,
              public creatorId: string, public categoryId: string) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.publishDate = publishDate;
    this.content = content;
    this.localization = localization;
    this.creatorId = creatorId;
    this.categoryId = categoryId;
  }
}
