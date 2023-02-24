import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';

const appRoutes: Routes = [
  {path:"cuisines", component:CuisineListComponent},
  {path:"", component:CuisineListComponent},
  {path:"restaurants/:cuisine", component:RestaurantCuisineComponent},
  {path:"restaurants/:cuisine/:idx", component:RestaurantDetailsComponent},
  {path:"**", redirectTo:'/', pathMatch:'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    CuisineListComponent,
    RestaurantCuisineComponent,
    RestaurantDetailsComponent
  ],
  imports: [
    HttpClientModule, ReactiveFormsModule, BrowserModule, RouterModule.forRoot(appRoutes,{useHash:true})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
