import { AreaService } from './../../../../services/area.service';
import { Component, OnInit } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  FormGroup,
  Validators,
  FormBuilder
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

// export interface Food {
//   value: string;
//   viewValue: string;
// }

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-storage-form',
  templateUrl: './storage-form.component.html',
  styleUrls: ['./storage-form.component.scss']
})
export class StorageFormComponent implements OnInit {
  storageCenterList: any;
  storageForm: FormGroup;
  matcher: MyErrorStateMatcher;
  constructor(
    private fb: FormBuilder,
    private areaService: AreaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.areaService.getAllStorages(43).subscribe(data => {
      this.storageCenterList = data;
    });
  }

  ngOnInit() {
    this.storageForm = new FormGroup({
      storageCenter: new FormControl(''),
      itemName: new FormControl(''),
      itemQuantity: new FormControl('')
    });
    this.storageForm = this.fb.group({
      storageCenter: ['', Validators.required],
      itemName: ['', Validators.required],
      itemQuantity: ['', Validators.required]
    });

    this.matcher = new MyErrorStateMatcher();
  }

  isSubmitDisabled() {
    return !this.storageForm.valid;
  }

  submitAction() {
    let param = JSON;
    param['centreId'] = this.storageForm.get('storageCenter').value;
    param['itemName'] = this.storageForm.get('itemName').value;
    param['itemQuantity'] = this.storageForm.get('itemQuantity').value;

    this.areaService.saveStorage(param).subscribe(data => {
      this.router.navigate(['../../../'], { relativeTo: this.route });
    });
  }
}
