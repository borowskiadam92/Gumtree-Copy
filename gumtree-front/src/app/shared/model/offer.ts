export class Offer {

  constructor(public id: number, public title: string, public price: number,
              public publishDate: Date, public content: string) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.publishDate = publishDate;
    this.content = content;
  }
//     private long creatorId;
//     private long categoryId;
}
