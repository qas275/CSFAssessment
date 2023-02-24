import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';


@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent implements OnInit {
	
  constructor(private activatedroute: ActivatedRoute, private svc: RestaurantService, private router: Router ){

  }

	// TODO Task 3
	// For View 2

  restaurants:string[] = []
  cuisine :string=""

  ngOnInit(): void {
    console.log("START RESTAURANTS")
    let cuisine:string = this.activatedroute.snapshot.params['cuisine'];
    this.cuisine = cuisine;
    this.svc.getRestaurantsByCuisine(cuisine).then( v=>{
      console.log(v);
      this.restaurants = v;
  });
  }
}
