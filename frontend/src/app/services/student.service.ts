
import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable } from 'rxjs';
import { Student } from '../models/student';

@Injectable()
export class StudentService {

    private readonly API_URL = 'http://localhost:8765/faculty/student/';

    constructor(private httpClient: HttpClient) {

    }
    public getStudentsForCourse(page: number, size: number, courseId: number): Observable<Student[]> {
        let oAuthToken: string = `RO.v3.emptytoken`;
        console.log ("Auth Token is : " + oAuthToken);
        let headers = new HttpHeaders()
        headers = headers.set("token",oAuthToken);
        console.log(this.API_URL + 'studentsForCourse/' + courseId + '?page=' + page + '&size=' + size)
        return this.httpClient.get<Student[]>(this.API_URL + 'studentsForCourse/' + courseId + '?page=' + page + '&size=' + size , {headers:headers});
    }

    public getAllStudents(page: number, size: number, filter: string): Observable<Student[]> {
        let oAuthToken:string = `RO.v3.emptytoken`;
        console.log("Auth Token is : " + oAuthToken);
        let headers = new HttpHeaders()
        headers = headers.set("token",oAuthToken);
        console.log(this.API_URL + filter + '?page=' + page + '&size=' + size + ' \n with Headers' + headers);
        return this.httpClient.get<Student[]>(this.API_URL + filter + '?page=' + page + '&size=' + size, {headers:headers});
    }

    public addStudent(student: Student): void {
        let headers = new HttpHeaders()
        let oAuthToken:string = `RO.v3.emptytoken`;
        headers = headers.set("token",oAuthToken);
        this.httpClient.post(this.API_URL, student , {headers:headers}).subscribe();
    }

    public updateStudent(student: Student): void {
        let headers = new HttpHeaders()
        let oAuthToken:string = `RO.v3.emptytoken`;
        headers = headers.set("token",oAuthToken);
        this.httpClient.put(this.API_URL, student,{headers:headers}).subscribe();
    }

    public deleteStudent(id: number): void {
        let headers = new HttpHeaders()
        let oAuthToken:string = `RO.v3.emptytoken`;
        headers = headers.set("token",oAuthToken);
        this.httpClient.delete(this.API_URL + id , {headers:headers}).subscribe();
    }
}