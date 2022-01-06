import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';
import { filtro } from './filtro.model';

export class mensaje {
  constructor(
    public msj: string | null
  ) {}
}


@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  calcularEdad(credentials: Login): Observable<any> {
    return this.authServerProvider.calcular(credentials)
  }
  
    filtro(credentials: filtro): Observable<any> {
    return this.authServerProvider.filtro(credentials);
  }

  logout(): void {
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }
}
