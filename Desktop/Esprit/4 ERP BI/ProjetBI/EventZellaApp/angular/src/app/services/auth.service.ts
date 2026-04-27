import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

private API = 'http://127.0.0.1:8000/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string) {
    return this.http.post<any>(`${this.API}/login`, { email, password }).pipe(
      tap(res => {
        localStorage.setItem('token', res.access_token);
        localStorage.setItem('user', JSON.stringify(res.user));
        // On stocke le token Power BI reçu du backend
        if (res.powerbi_token) {
          localStorage.setItem('pbi_token', res.powerbi_token);
        }
      })
    );
  }

  logout() {
    localStorage.clear();
    sessionStorage.clear();
    
    // On ouvre la fenêtre Microsoft de déconnexion
    const logoutWin = window.open(
      'https://login.microsoftonline.com/common/oauth2/v2.0/logout', 
      'MicrosoftLogout', 
      'width=800,height=700,top=50,left=50'
    );

    // Une fois que l'utilisateur a potentiellement fini, on redirige et on RECHARGE
    this.router.navigate(['/login']).then(() => {
      // Le reload force le navigateur à vider les cookies de session en mémoire
      // C'est comme si vous fermiez et rouvriez l'onglet.
      setTimeout(() => {
        window.location.reload();
      }, 500);
    });
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUser(): any {
    const u = localStorage.getItem('user');
    return u ? JSON.parse(u) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getRole(): string {
    return this.getUser()?.role || '';
  }
}
