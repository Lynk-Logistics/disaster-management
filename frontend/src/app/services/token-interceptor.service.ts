import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {
  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.userService.getApi();
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `${token}`
      }
    });

    return next.handle(clonedReq).pipe(
      // if api call returns any error, show details in console and redirect to appropriate page
      tap(
        (event: HttpEvent<any>) => {},
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            ////////// ERROR 404 - REDIRECT //////////
            if (err.status === 404) {
              this.router.navigate(['/not-found']);
            }

            ////////// ERROR 403 - SNACKBAR //////////
            if (err.status === 403) {
              console.log('error');
              this.snackbar.open(
                `You do not have required permissions to perform the action. Please contact admin.`
              );
            }

            ////////// ERROR 401 - REDIRECT //////////
            if (err.status === 401) {
              // this.snackbar.open(`Oops! You are not authorised. Please login to continue.`);
              this.router.navigate(['/login']);
            }
          }
        }
      )
    );
  }
}
