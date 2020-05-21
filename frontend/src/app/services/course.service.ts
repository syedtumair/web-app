
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Course } from '../models/Course';
import {AppComponent} from '../app.component'

@Injectable()
export class CourseService {

    private readonly API_URL = 'http://localhost:8765/faculty/course/';
    private readonly REPORT_URL = 'http://localhost:8765/report/studentsForCoursePdf/';

    constructor(private httpClient: HttpClient) {


    }
    public getAllCourses(page: number, size: number, filter: string): Observable<Course[]> {

        let headers = this.getHeaders();
        console.log(this.API_URL + filter + '?page=' + page + '&size=' + size)
        return this.httpClient.get<Course[]>(this.API_URL + filter + '?page=' + page + '&size=' + size , {headers : headers});

    }

    public getPdf(courseId) {

        let headers = new HttpHeaders();
        headers = headers.set('Accept', 'application/pdf');
        headers = headers.append('authToken' , AppComponent.myParam);
        console.log("New Headers are ", headers);
        return this.httpClient.get(this.REPORT_URL + courseId, { headers: headers, responseType: 'blob' });

    }

    public addCourse(course: Course): void {

        let headers = this.getHeaders();
        this.httpClient.post(this.API_URL, course ,  {headers: headers}).subscribe();
    }

    public updateCourse(course: Course): void {
        let headers = this.getHeaders();
        this.httpClient.put(this.API_URL, course, {headers: headers}).subscribe();

    }

    public deleteCourse(id: number): void {
        let headers = this.getHeaders();
        this.httpClient.delete(this.API_URL + id , {headers: headers}).subscribe();
    }

    private  getHeaders() : HttpHeaders{
        let httpHeader = new HttpHeaders();
        httpHeader = httpHeader.set('authToken' , AppComponent.myParam);
        return httpHeader;
    }
}