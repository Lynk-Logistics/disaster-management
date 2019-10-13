import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SponsorService {

  API_URL: string;

  constructor(private http: HttpClient) {
    this.API_URL = environment.API_URL;
  }

  saveSponsor(sponsorInfo) {
    return this.http.post(this.API_URL + 'volunteer/add', sponsorInfo);
  }
}
