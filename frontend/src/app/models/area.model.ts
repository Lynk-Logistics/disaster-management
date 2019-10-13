export interface IAreaAction {
  actionId: number;
  actionType: string;
  description: string;
  itemName: string;
  itemQuantity: number;
  fulfilled: boolean;
  latitude?: any;
  longitude?: any;
  postedByUser: IPostedByUser;
  postedByOrganisation?: any;
  location: ILocation;
  file?: any;
}

export interface IPostedByUser {
  userId: number;
  name: string;
  mobile: string;
  email: string;
}

export interface ILocation {
  locationId: number;
  locationName: string;
  latitude?: any;
  longitude?: any;
}

export interface IStorage {
  storageId: number;
  centreId: number;
  itemName: string;
  itemQuantity: number;
}

// Storage List
export interface Location {
  locationId: number;
  locationName: string;
  latitude?: any;
  longitude?: any;
}

export interface CreatedBy {
  userId: number;
  name: string;
  mobile: string;
  email: string;
}

export interface IStorageList {
  centreId: number;
  centreName: string;
  location: Location;
  latitude?: any;
  longitude?: any;
  createdBy: CreatedBy;
  createdOn: string;
}
