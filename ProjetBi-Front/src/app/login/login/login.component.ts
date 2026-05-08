import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) { }

  onLogin() {
    this.error = '';
    this.loading = true;

    this.auth.login(this.email, this.password).subscribe({
      next: (res) => {
        const role = res.user.role.toUpperCase();
        if (role === 'CEO') this.router.navigate(['/dashboard-ceo']);
        else if (role === 'MARKETING') this.router.navigate(['/dashboard-marketing']);
        else if (role === 'ADMIN') this.router.navigate(['/dashboard-admin']);
        else this.error = 'Rôle non reconnu';
        this.loading = false;

      },
      error: (err) => {
        this.error = err.error?.detail || 'Email ou mot de passe incorrect';
        this.loading = false;
      }
    });
  }
}
