import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { ApplicationConfigService } from '../config/application-config.service';
import { Login } from 'app/login/login.model';
import { filtro } from 'app/login/filtro.model';

type JwtToken = {
  id_token: string;
};

type listado = string;

type mensaje = string;

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(
    private http: HttpClient,
    private $localStorage: LocalStorageService,
    private $sessionStorage: SessionStorageService,
    private applicationConfigService: ApplicationConfigService
  ) {}

  getToken(): string {
    const tokenInLocalStorage: string | null = this.$localStorage.retrieve('authenticationToken');
    const tokenInSessionStorage: string | null = this.$sessionStorage.retrieve('authenticationToken');
    return tokenInLocalStorage ?? tokenInSessionStorage ?? '';
  }

  calcular(credentials: Login): Observable<any> {
    return this.http
      .get<any>(this.applicationConfigService.getEndpointFor('api/calcularEdad'+'?name='+credentials.username+'&fechaNacimiento='+credentials.nacimiento));
  }
    filtro(credentials: filtro): Observable<any> {
    return this.http
      .get<listado>(this.applicationConfigService.getEndpointFor('api/buscarFiltro' + "?filtro="+credentials.filtro1));
  }

  logout(): Observable<void> {
    return new Observable(observer => {
      this.$localStorage.clear('authenticationToken');
      this.$sessionStorage.clear('authenticationToken');
      observer.complete();
    });
  }

  private authenticateSuccess(response: JwtToken, rememberMe: boolean): void {
    const jwt = response.id_token;
    if (rememberMe) {
      this.$localStorage.store('authenticationToken', jwt);
      this.$sessionStorage.clear('authenticationToken');
    } else {
      this.$sessionStorage.store('authenticationToken', jwt);
      this.$localStorage.clear('authenticationToken');
    }
  }
}
