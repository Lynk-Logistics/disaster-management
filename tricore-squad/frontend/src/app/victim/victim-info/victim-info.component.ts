import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { VictimService } from '../victim.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-victim-info',
  templateUrl: './victim-info.component.html',
  styleUrls: ['./victim-info.component.scss']
})
export class VictimInfoComponent implements OnInit {

  victimForm: FormGroup;
  geolocationPosition: any;

  constructor(private fb: FormBuilder, private viService: VictimService, private router: Router, private common: CommonService) { }

  ngOnInit() {
    this.getLocation();
    this.victimForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      mobileNo: ['', Validators.required],
      needs: this.fb.array([]),
      headCount: ['', Validators.required],
      description: ['', Validators.required],
      status: ['open'],
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

  OnSaveVictim() {
    console.log(this.victimForm);
    this.victimForm.value.location_lat = this.geolocationPosition.coords.latitude;
    this.victimForm.value.location_long = this.geolocationPosition.coords.longitude;
    this.viService.saveVictim(this.victimForm.value).subscribe((response: any) => {
      console.log(response);
      this.common.requests = response.data;
      if (response.status === 1001) {
        console.log(this.common.role);
        this.router.navigateByUrl(this.common.role);
      }
    });
  }

  onCheckChange(event) {
    const formArray: FormArray = this.victimForm.get('needs') as FormArray;

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
