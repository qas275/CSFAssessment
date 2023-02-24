// Do not change these interfaces

//following changes were made to the model to suit the requirements of mongo results
// export interface Restaurant {
// 	restaurantId: string
// 	namd: string --> name
// 	cusisine: string --> cuisine
// 	address: string
// 	coordinates: number[]
// }

export interface Restaurant {
	restaurantId: string
	name: string
	cuisine: string
	address: string
	coordinates: number[]
}

export interface Comment {
	name: string
	rating: number
	restaurantId: string
	text: string
}
