import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {CategoryPageComponent} from "./pages/category-page/category-page.component";
import {OfferPageComponent} from "./pages/offer-page/offer-page.component";

const routes: Routes = [
  { path: '', redirectTo: '/main-page', pathMatch: 'full' },
  { path: 'main-page', component: MainPageComponent },
  { path: ':category', component: CategoryPageComponent },
  { path: ':category/:localization/:title/:id', component: OfferPageComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
