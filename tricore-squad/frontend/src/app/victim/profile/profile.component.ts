import { Component, OnInit } from '@angular/core';
import { VictimService } from '../victim.service';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  requests: any;

  constructor(private viService: VictimService, private common: CommonService) { }

  ngOnInit() {
    this.getMyReq();
  }

  getMyReq() {
    this.requests = typeof( this.common.requests) === 'object' ? [this.common.requests] : this.common.requests;
  }

}
