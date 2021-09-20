import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {CategoryPageComponent} from "./pages/category-page/category-page.component";
import {OfferPageComponent} from "./pages/offer-page/offer-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import {AuthPageComponent} from "./auth/auth-page/auth-page.component";

const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {path: 'login', component: AuthPageComponent},
  {path: 'register-user', component: RegisterPageComponent},
  {path: 'wszystkie-ogloszenia', component: CategoryPageComponent},
  {path: ':category', component: CategoryPageComponent},
  {path: ':category/:localization', component: CategoryPageComponent},
  {path: ':category/:localization/:title/:id', component: OfferPageComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}