import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {ActivatedRoute, Params} from "@angular/router";
import {Offer} from "../../../shared/model/offer";
import {OffersService} from "../../../service/offers.service";

@Component({
  selector: 'app-edit-offer-page',
  templateUrl: './edit-offer-page.component.html'
})
export class EditOfferPageComponent implements OnInit {

  editOfferFormGroup: FormGroup;
  editMode: boolean = false;
  offer: Offer;
  offerId: string;

  constructor(private datePipe: DatePipe, private route: ActivatedRoute, private offersService: OffersService) {
  }

  ngOnInit(): void {
    this.editOfferFormGroup = new FormGroup({
      'offerData': new FormGroup({
        'title': new FormControl('', Validators.required),
        'price': new FormControl(''),
        'publishDate': new FormControl(this.datePipe.transform(new Date(), 'yyyy-MM-dd')),
        'content': new FormControl('')
      })
    });

    this.route.params.subscribe((param: Params) => {
      let offerId = param['offerId'];
      this.offerId = offerId;
      if(this.offerId){
        this.editMode = true;
      }
      this.offersService.getOfferById(offerId).subscribe(offer => {
          console.log(offer);
          this.offer = offer;

          delete this.offer.id;  //usuwamy, bo forma nie posiada takich pol
          delete this.offer.creatorId;
          delete this.offer.categoryId;
          delete this.offer.localization;

          this.editOfferFormGroup.setValue({'offerData': this.offer});
        }
      )
    });

    console.log(this.editOfferFormGroup);
    console.log(this.editOfferFormGroup.valid);
  }

  onSubmit(): void {
    //pobieramy wartosci z formularza i wysylamy na backend, jak backend
    //odpowie 200 to zerujemy formularz albo przekierowujemy na strone glowna
    let formContent = this.editOfferFormGroup.getRawValue().offerData;
    if(this.editMode){
      this.offersService.updateOffer(this.offerId, formContent).subscribe(success => {
        console.log(success)
      }, fail => {
        console.log(fail)
      });
    } else {
      this.offersService.createOffer(formContent).subscribe(success => {
        console.log(success)
      }, fail => {
        console.log(fail)
      });
    }

    //czesciowa aktualizacja
    // this.editOfferFormGroup.patchValue(
    //   {
    //     'offerData': {
    //       'title': 'To jest nowy tytuł'
    //     }
    //   }
    // );

  }

}
