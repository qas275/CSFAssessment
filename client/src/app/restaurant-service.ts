import { Restaurant, Comment } from './models'
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class RestaurantService {

	constructor(private http:HttpClient){

	}

	// TODO Task 2 
	// Use the following method to get a list of cuisines
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public getCuisineList() {
		// Implememntation in here
		return lastValueFrom(this.http.get<string[]>('/api/cuisines'))
	}

	// // TODO Task 3 
	// // Use the following method to get a list of restaurants by cuisine
	// // You can add any parameters (if any) and the return type 
	// // DO NOT CHNAGE THE METHOD'S NAME
	public getRestaurantsByCuisine(cuisine:string) {
		// Implememntation in here
		const url = '/api/'+cuisine+'/restaurants';
		return lastValueFrom(this.http.get<string[]>(url))
	}
	
	// // TODO Task 4
	// // Use this method to find a specific restaurant
	// // You can add any parameters (if any) 
	// // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public getRestaurant(cuisine:string, index:number): Promise<Restaurant> {
		// Implememntation in here
		const url = '/api/'+cuisine+'/restaurants/'+index;
		return lastValueFrom(this.http.get<Restaurant>(url))
	}

	// // TODO Task 5
	// // Use this method to submit a comment
	// // DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
	public postComment(comment: Comment): Promise<any> {
		// Implememntation in here
		return lastValueFrom(this.http.post<any>("/api/comments", comment))
	}
}
