import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { SponsorService } from '../sponsor.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-sponsor-registration',
  templateUrl: './sponsor-registration.component.html',
  styleUrls: ['./sponsor-registration.component.scss']
})
export class SponsorRegistrationComponent implements OnInit {

  sponsorForm: FormGroup;
  geolocationPosition: Position;

  constructor(private fb: FormBuilder, private spService: SponsorService, private router: Router, private common: CommonService) { }

  ngOnInit() {
    this.getLocation();
    this.sponsorForm = this.fb.group({
      name: ['', Validators.required],
      mobileNo: ['', Validators.required],
      address: ['', Validators.required],
      location_lat: [''],
      location_long: [''],
      offering: this.fb.array([]),
      description: ['', Validators.required],
      status: ['open']
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

  OnSaveSponsor() {
    this.sponsorForm.value.location_lat = this.geolocationPosition.coords.latitude;
    this.sponsorForm.value.location_long = this.geolocationPosition.coords.longitude;
    this.spService.saveSponsor(this.sponsorForm.value).subscribe((response: any) => {
      if (response.status === 1001) {
        this.router.navigateByUrl(this.common.role);
      }
    });
  }

  onCheckChange(event) {
    const formArray: FormArray = this.sponsorForm.get('offering') as FormArray;

    /* Selected */
    if (event.target.checked) {
      // Add a new control in the arrayForm
      formArray.push(new FormControl(event.target.value));
    } else {
      // find the unselected element
      let i = 0;
      formArray.controls.forEach((ctrl: FormControl) => {
        if (ctrl.value === event.target.value) {
          // Remove the unselected element from the arrayForm
          formArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

}
