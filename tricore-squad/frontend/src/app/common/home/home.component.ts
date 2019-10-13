import { Component, OnInit } from '@angular/core';
import { CommonService } from '../common.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private common: CommonService, private router: Router) { }

  ngOnInit() {
  }

  goToMobile(role) {
    this.common.role = role;
    this.router.navigateByUrl('enter-mobile');
  }

}
