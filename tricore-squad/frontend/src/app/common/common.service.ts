import { Injectable } from '@angular/core';
import { VictimService } from '../victim/victim.service';
import { VolunteerService } from '../volunteers/volunteer.service';
import { SponsorService } from '../sponsors/sponsor.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  API_URL: string;
  role: string;
  requests: any;

  constructor(
    private http: HttpClient
  ) {
    this.API_URL = environment.API_URL;
  }

  checkMobileExist({mobileNo}) {
    return this.http.get(this.API_URL + this.role + '/check-exists?mobileNo=' + mobileNo);
  }


}
