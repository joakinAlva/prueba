import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';

import { DEBUG_INFO_ENABLED } from 'app/app.constants';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username?: ElementRef;
  resultado: any = "Joaquin";

  authenticationError = false;

  loginForm = this.fb.group({
    username: [null, [Validators.required]],
    nacimiento: [null, [Validators.required]],
    filtro: [null, [Validators.required]],
    rememberMe: [false],
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private authServerProvider: AuthServerProvider ,
    private router: Router,
    private fb: FormBuilder
  ) {
}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    if (this.username) {
      this.username.nativeElement.focus();
    }
  }

  calcularEdad(): void {
	
    this.authServerProvider
      .calcular({
        username: this.loginForm.get('username')!.value,
        nacimiento: this.loginForm.get('nacimiento')!.value,
      })
      .subscribe(
        response => {
		alert(response.respuesta);
		this.resultado = response.respuesta}
      );
  }
  
   filtro(): void {
	
    this.authServerProvider
      .filtro({
        filtro1: this.loginForm.get('filtro')!.value,
      })
      .subscribe(
		response => {
       // response => this.resultado = response
       alert(response.listaProductos);}
      );
  }
}
