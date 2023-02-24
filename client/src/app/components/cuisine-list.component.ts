import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit{

  constructor(private svc:RestaurantService, private router: Router){

  }

  cuisines:string[] =[]
	// TODO Task 2
	// For View 1
  ngOnInit(): void {
      this.svc.getCuisineList().then( v=>{
        console.log(v);
        this.cuisines = v;
    });
  }


}
