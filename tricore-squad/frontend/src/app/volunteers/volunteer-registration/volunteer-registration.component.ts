import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { VolunteerService } from '../volunteer.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-volunteer-registration',
  templateUrl: './volunteer-registration.component.html',
  styleUrls: ['./volunteer-registration.component.scss']
})
export class VolunteerRegistrationComponent implements OnInit {

  volunteerForm: FormGroup;
  geolocationPosition: Position;

  constructor(private fb: FormBuilder, private voService: VolunteerService, private router: Router, private common: CommonService) { }

  ngOnInit() {
    this.getLocation();
    this.volunteerForm = this.fb.group({
      name: ['', Validators.required],
      mobileNo: ['', Validators.required],
      address: ['', Validators.required],
      aadhaar: [''],
      location_lat: [''],
      location_long: ['']
    });
  }

  getLocation() {
    if (window.navigator && window.navigator.geolocation) {
      window.navigator.geolocation.getCurrentPosition(
          position => {
              this.geolocationPosition = position,
                  console.log(position);
          },
          error => {
              switch (error.code) {
                  case 1:
                      console.log('Permission Denied');
                      break;
                  case 2:
                      console.log('Position Unavailable');
                      break;
                  case 3:
                      console.log('Timeout');
                      break;
              }
          }
      );
    }
  }

  OnSaveVolunteer() {
    this.volunteerForm.value.location_lat = this.geolocationPosition.coords.latitude;
    this.volunteerForm.value.location_long = this.geolocationPosition.coords.longitude;
    this.voService.saveVictim(this.volunteerForm.value).subscribe((response: any) => {
      if (response.status === 1001) {
        this.router.navigateByUrl(this.common.role);
      }
    });
  }

}
