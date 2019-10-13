import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CommonService } from '../common.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-mobile-number',
  templateUrl: './mobile-number.component.html',
  styleUrls: ['./mobile-number.component.scss']
})
export class MobileNumberComponent implements OnInit {

  mobileForm: FormGroup;

  constructor(private fb: FormBuilder, private common: CommonService, private router: Router, private actRoute: ActivatedRoute) { }

  ngOnInit() {
    this.actRoute.queryParams.subscribe(params => {
      this.common.role = params['role'];
    });
    
    this.mobileForm = this.fb.group({
      mobileNo: ['', Validators.required]
    });
  }

  mobileSubmit() {
    console.log(this.mobileForm.value);
    this.common.checkMobileExist(this.mobileForm.value).subscribe((res: any) => {
      console.log(res);
      if (res.status === 1003) {
        console.log('Exists');
        this.common.requests = res.data;
        this.router.navigate([this.common.role]);
      } else if (res.status === 1004) {
        console.log('No Exists');
        this.router.navigate([this.common.role + '/new']);
      }
    });
  }

}
