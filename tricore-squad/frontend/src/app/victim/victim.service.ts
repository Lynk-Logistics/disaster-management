import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VictimService {

  API_URL: string;

  constructor(private http: HttpClient) {
    this.API_URL = environment.API_URL;
  }

  saveVictim(victimInfo) {
    return this.http.post(this.API_URL + 'victim/add', victimInfo);
  }
}
