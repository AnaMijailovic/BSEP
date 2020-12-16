import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-navbar-operator',
  templateUrl: './navbar-operator.component.html',
  styleUrls: ['./navbar-operator.component.scss']
})
export class NavbarOperatorComponent implements OnInit {

  constructor(private router: Router, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  logOut(): void {
    localStorage.removeItem('user');
    this.toastr.success('Succesful logout!');
    // location.reload();
    this.router.navigate(['']);
  }

}
