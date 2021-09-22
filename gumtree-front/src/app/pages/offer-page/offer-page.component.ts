import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Offer} from "../../shared/model/offer";
import {OffersService} from "../../service/offers.service";

@Component({
  selector: 'app-offer-page',
  templateUrl: './offer-page.component.html',
  styleUrls: ['./offer-page.component.scss']
})
export class OfferPageComponent implements OnInit {

  categoryName: string;
  localization: string;
  title: string;
  offerId: number;

  offer: Offer;

  constructor(private offersService: OffersService, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }


  ngOnInit(): void {
    this.categoryName = this.activatedRoute.snapshot.params['category'];
    this.localization = this.activatedRoute.snapshot.params['localization.ts'];
    this.title = this.activatedRoute.snapshot.params['title'];
    this.offerId = this.activatedRoute.snapshot.params['id'];

    this.offersService.getOfferById(String(this.offerId)).subscribe(response => {
      this.offer = response;
    });
  }


}
