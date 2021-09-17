import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HeaderComponent} from "./header/header.component";
import {DropdownDirective} from "./shared/directive/dropdown.directive";
import { MainPageComponent } from './pages/main-page/main-page.component';
import { CategoryItemComponent } from './pages/main-page/category-item/category-item.component';
import {HttpClientModule} from "@angular/common/http";
import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { OfferPageComponent } from './pages/offer-page/offer-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DropdownDirective,
    MainPageComponent,
    CategoryItemComponent,
    CategoryPageComponent,
    OfferPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
