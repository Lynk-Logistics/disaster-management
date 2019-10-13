import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { IAreaAction } from 'src/app/models/area.model';
import { AreaService } from 'src/app/services/area.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-donors',
  templateUrl: './donors.component.html',
  styleUrls: ['./donors.component.scss']
})
export class DonorsComponent implements OnInit, OnDestroy {
  subscriptions$ = new Subject();
  dataSource: IAreaAction[] = [];
  displayedColumns: string[] = ['Name', 'Qty', 'Item(s)', 'Contact'];
  constructor(
    private areaService: AreaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const areaId = parseInt(window.location.href.split('/')[4], 10);
    this.areaService
      .getDonations(areaId)
      .pipe(takeUntil(this.subscriptions$))
      .subscribe((data: IAreaAction[]) => (this.dataSource = data));

    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  ngOnDestroy() {
    this.subscriptions$.next();
    this.subscriptions$.complete();
  }
}
