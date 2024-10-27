import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const authToken = btoa(`admin:123456`);

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    // Authorization: 'Bearer your-token',
    Authorization: `Basic ${authToken}`,
  }),
};

@Injectable({
  providedIn: 'root',
})
export class HttpConnection {
  constructor(private http: HttpClient) {}

  getData(requestUrl: string): Observable<any> {
    return this.http
      .get(requestUrl, httpOptions)
      .pipe(catchError(this.handleError));
  }

  postData(requestUrl: string, data: any): Observable<any> {
    return this.http
      .post(requestUrl, data, httpOptions)
      .pipe(catchError(this.handleError));
  }
  
   putData(requestUrl: string, data: any): Observable<any> {
    return this.http
      .put(requestUrl, data, httpOptions)
      .pipe(catchError(this.handleError));
  }
  
   

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(
      () => new Error('Something went wrong; please try again later.')
    );
  }

  //   sendRequest(requestUrl: string): Promise<any> {
  //     let apiPath = requestUrl;
  //     let apiPromise: Promise<any> = this.http.get(apiPath).toPromise();

  //     return apiPromise
  //       .then((response) => {
  //         return response;
  //       })
  //       .catch(this.handlePromiseError);
  //   }

  //   private handlePromiseError(error: any): Promise<any> {
  //     console.error('An error occurred', error);
  //     return Promise.reject(error.message || error);
  //   }
}
