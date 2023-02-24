import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant, Comment } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit{
	

  constructor(private svc: RestaurantService, private activatedRoute: ActivatedRoute, private fb:FormBuilder, private router:Router){

  }
	// TODO Task 4 and Task 5
	// For View 3

  restaurant!:Restaurant;
  form!:FormGroup
  commentObj!:Comment
  imgurl = "";

  ngOnInit(): void {
    console.log("START RESTAURANTS")
    this.form = this.createForm()
    let cuisine:string = this.activatedRoute.snapshot.params['cuisine'];
    let idx = this.activatedRoute.snapshot.params['idx'];
    this.svc.getRestaurant(cuisine, idx).then( v=>{
      console.log(v);
      this.restaurant = v;
      this.imgurl = "qas275.sgp1.digitaloceanspaces.com/"+this.restaurant.restaurantId+".jpeg"
  });
  }

  createForm(){
    return this.fb.group({
      name:this.fb.control('',[Validators.required, Validators.minLength(4)]),
      rating:this.fb.control(1),
      text: this.fb.control('', Validators.required)
    })
  }

  comment(){
    this.commentObj.restaurantId = this.restaurant.restaurantId
    this.commentObj.name = this.form.controls['name'].value
    this.commentObj.rating = this.form.controls['rating'].value
    this.commentObj.text = this.form.controls['comment'].value
    this.svc.postComment(this.commentObj).then(v=>{
      console.log(v);
      this.router.navigate(['/cuisines'])
    })
  }

}
