import { Component } from '@angular/core';
import { fadeAnimation } from './animations';
import { ActivatedRoute, Params } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [fadeAnimation]
})
export class AppComponent {
  title = 'frontend';
  params: Params;


  constructor(private route: ActivatedRoute){}


  openSwagger() {
    window.open('http://localhost:8765/swagger-ui.html');
  }

  openEureka() {
    window.open('http://localhost:8763/');
  }
  
  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      this.params = params;
      console.log('App params', params);
          });

}
}