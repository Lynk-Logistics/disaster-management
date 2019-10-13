import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-volunteers-list',
  templateUrl: './volunteers-list.component.html',
  styleUrls: ['./volunteers-list.component.scss']
})
export class VolunteersListComponent implements OnInit {

  searchForm: FormGroup;

  constructor(private fb: FormBuilder, private common: CommonService) { }

  ngOnInit() {
    this.searchForm = this.fb.group({
      query: ['', Validators.required]
    });
  }

  onSearch() {
    console.log(this.searchForm.value);
  }

}
