import {Component, Input, OnInit} from '@angular/core';
import {Offer} from "../../../shared/model/offer";

@Component({
  selector: 'app-offer-item',
  templateUrl: './offer-item.component.html'
})
export class OfferItemComponent implements OnInit {

  @Input() offer: Offer;
  @Input() categoryName: string;

  constructor() {
  }

  ngOnInit(): void {
  }

}
