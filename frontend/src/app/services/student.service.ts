
import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable } from 'rxjs';
import { Student } from '../models/student';
import { ActivatedRoute, Params } from '@angular/router';
import {AppComponent} from '../app.component'



@Injectable()
export class StudentService {

    private readonly API_URL = 'http://localhost:8765/faculty/student/';


    constructor(private httpClient: HttpClient , private route: ActivatedRoute) {

    }
    public getStudentsForCourse(page: number, size: number, courseId: number): Observable<Student[]> {
        let headers = this.getHeaders();
        console.log(this.API_URL + 'studentsForCourse/' + courseId + '?page=' + page + '&size=' + size)
        return this.httpClient.get<Student[]>(this.API_URL + 'studentsForCourse/' + courseId + '?page=' + page + '&size=' + size, {headers : headers});
    }

    public getAllStudents(page: number, size: number, filter: string): Observable<Student[]> {

        console.log("query parameter inside students is ", AppComponent.myParam)

        let headers = this.getHeaders();
        console.log("headers we are sending are ", headers)

        console.log(this.API_URL + filter + '?page=' + page + '&size=' + size)
        return this.httpClient.get<Student[]>(this.API_URL + filter + '?page=' + page + '&size=' + size , { headers: headers});
    }

    public addStudent(student: Student): void {
        let headers = this.getHeaders();
        this.httpClient.post(this.API_URL, student , {headers : headers}).subscribe();
    }

    public updateStudent(student: Student): void {
        let headers = this.getHeaders();
        this.httpClient.put(this.API_URL, student , {headers : headers}).subscribe();
    }

    public deleteStudent(id: number): void {
        let headers = this.getHeaders();
        this.httpClient.delete(this.API_URL + id , {headers : headers}).subscribe();
    }

    private  getHeaders() : HttpHeaders{
        let httpHeader = new HttpHeaders();
        httpHeader = httpHeader.set('authToken' , AppComponent.myParam);
        return httpHeader;
    }

}
