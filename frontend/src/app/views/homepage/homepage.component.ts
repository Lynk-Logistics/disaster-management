import { FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AreaService } from 'src/app/services/area.service';
import { startWith, map } from 'rxjs/operators';
import { Router } from '@angular/router';

export interface Location {
  latitude: number;
  locationId: number;
  locationName: string;
  longitude: number;
}

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  areaForm: FormGroup;
  options: any;
  filteredOptions: Observable<Location[]>;
  constructor(private areaService: AreaService, private router: Router) {
    console.log('homepage');
  }

  ngOnInit() {
    this.areaForm = new FormGroup({
      area: new FormControl('')
    });
    this.areaService.getAllArea().subscribe(data => {
      this.options = data;
      this.filteredOptions = this.areaForm.get('area').valueChanges.pipe(
        startWith(''),
        map(value => (typeof value === 'string' ? value : value.locationName)),
        map(locationName => (locationName ? this._filter(locationName) : this.options.slice()))
      );
    });
  }

  isContinueDisabled() {
    return !this.areaForm.valid;
  }
  continue() {
    var areaData = this.areaForm.get('area').value;
    this.router.navigate(['area/', areaData.locationId]);
  }

  displayFn(location?: Location): string | undefined {
    return location ? location.locationName : undefined;
  }

  private _filter(name: string): Location[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(
      option => option.locationName.toLowerCase().indexOf(filterValue) === 0
    );
  }
}
