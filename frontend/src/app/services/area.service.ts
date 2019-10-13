import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { IStorage, IStorageList } from '../models/area.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AreaService {
  baseUrl = environment.ipAddress;

  constructor(private http: HttpClient) {}

  getDonations(areaId: number) {
    return this.http.get(
      `${this.baseUrl}/action/?locationId=${areaId}&actionType=RESOURCES_AVAILABLE&noOfDays=2&pageNo=0&noOfRecords=3`
    );
  }

  getVolunteers(areaId) {
    return this.http.get(
      `${this.baseUrl}/action/?locationId=${areaId}&actionType=WANT_TO_HELP&noOfDays=2&pageNo=0&noOfRecords=3`
    );
  }

  getRequests(areaId) {
    return this.http.get(
      `${this.baseUrl}/action/?locationId=${areaId}&actionType=NEED_HELP &noOfDays=2&pageNo=0&noOfRecords=3`
    );
  }

  getAllArea() {
    return this.http.get(`${this.baseUrl}/location/`);
  }

  submitAction(params: JSON) {
    return this.http.post(`${this.baseUrl}/action/`, params);
  }

  getAllStorages(locationId: number): Observable<IStorageList[]> {
    return this.http.get<IStorageList[]>(`${this.baseUrl}/storage/location/${locationId}`);
  }

  getStorageData(storageId: number): Observable<IStorage[]> {
    return this.http.get<IStorage[]>(`${this.baseUrl}/storage/item/show/${storageId}`);
  }
  saveStorage(params: JSON) {
    return this.http.post(`${this.baseUrl}/storage/item/save/`, params);
  }
}
