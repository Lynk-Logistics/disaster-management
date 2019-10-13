import { AreaService } from './../../../services/area.service';
import { Component, OnInit } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  FormGroup,
  FormBuilder,
  Validators
} from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export interface HelpGroup {
  value: string;
  viewValue: string;
}

export interface Location {
  latitude: number;
  locationId: number;
  locationName: string;
  longitude: number;
}

@Component({
  selector: 'app-action-form',
  templateUrl: './action-form.component.html',
  styleUrls: ['./action-form.component.scss']
})
export class ActionFormComponent implements OnInit {
  actionForm: FormGroup;
  matcher: MyErrorStateMatcher;
  needHelpOptions = ['Rescue Me', 'I need resources', 'I need doctor'];
  options: any;
  filteredOptions: Observable<string[]>;
  type: string;
  constructor(
    private fb: FormBuilder,
    private areaService: AreaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    route.queryParams.subscribe(params => {
      this.type = params.type;
    });
    this.areaService.getAllArea().subscribe(data => {
      this.options = data;
      this.filteredOptions = this.actionForm.get('area').valueChanges.pipe(
        startWith(''),
        map(value => (typeof value === 'string' ? value : value.locationName)),
        map(locationName => (locationName ? this._filter(locationName) : this.options.slice()))
      );
    });
  }

  ngOnInit() {
    this.actionForm = new FormGroup({
      itemName: new FormControl(''),
      itemQuantity: new FormControl(''),
      serviceOffered: new FormControl(''),
      needHelp: new FormControl(''),
      description: new FormControl(''),
      area: new FormControl('')
    });
    this.actionForm = this.fb.group({
      itemName: ['', Validators.required],
      itemQuantity: ['', Validators.required],
      serviceOffered: [''],
      needHelp: ['', Validators.required],
      description: ['', Validators.required],
      area: ['', Validators.required]
    });

    this.matcher = new MyErrorStateMatcher();
  }

  displayFn(location?: Location): string | undefined {
    return location ? location.locationName : undefined;
  }

  isSubmitDisabled() {
    if (this.type === 'WANT_TO_HELP') {
      if (
        this.actionForm.get('area').value.locationId &&
        this.actionForm.get('serviceOffered').value
      ) {
        return true;
      }
    } else if (this.type === 'RESOURCES_AVAILABLE') {
      if (
        this.actionForm.get('area').value.locationId &&
        this.actionForm.get('itemName').value &&
        this.actionForm.get('itemQuantity').value
      ) {
        return true;
      }
    } else if (this.type === 'NEED_HELP') {
      if (this.actionForm.get('area').value.locationId && this.actionForm.get('needHelp').value) {
        return true;
      }
      return false;
    }
  }

  submitAction() {
    var params = JSON;
    if (this.type === 'WANT_TO_HELP') {
      params['locationId'] = this.actionForm.get('area').value.locationId;
      params['itemName'] = this.actionForm.get('serviceOffered').value;
      params['actionType'] = 'WANT_TO_HELP';
    } else if (this.type === 'RESOURCES_AVAILABLE') {
      params['locationId'] = this.actionForm.get('area').value.locationId;
      params['itemName'] = this.actionForm.get('itemName').value;
      params['itemQuantity'] = this.actionForm.get('itemQuantity').value;
      params['actionType'] = 'RESOURCES_AVAILABLE';
    } else if (this.type === 'NEED_HELP') {
      params['locationId'] = this.actionForm.get('area').value.locationId;
      var help = this.actionForm.get('needHelp').value;
      console.log(help);
      params['itemName'] = help.toString();
      params['description'] = this.actionForm.get('description').value;
      params['actionType'] = 'NEED_HELP';
    }
    this.areaService.submitAction(params).subscribe(data => {
      this.router.navigate(['../../../'], { relativeTo: this.route, queryParams: null });
    });
  }

  private _filter(name: string): Location[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(
      option => option.locationName.toLowerCase().indexOf(filterValue) === 0
    );
  }
}
