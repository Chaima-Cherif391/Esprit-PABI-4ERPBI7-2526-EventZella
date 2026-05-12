import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

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
    console.log("Login attempt for:", this.email);
    this.error = '';
    this.loading = true;

    this.auth.login(this.email, this.password).subscribe({
      next: (res: any) => {
        const role = res.user.role.toUpperCase();
        if (role === 'CEO') this.router.navigate(['/dashboard-ceo']);
        else if (role === 'MARKETING') this.router.navigate(['/dashboard-marketing']);
        else if (role === 'ADMIN') this.router.navigate(['/dashboard-admin']);
        else {
          this.error = 'Rôle non reconnu';
          Swal.fire({
            title: 'Access Denied',
            text: 'Your account role is not recognized by the system.',
            icon: 'warning',
            background: '#091623',
            color: '#fff',
            confirmButtonColor: '#16c0de'
          });
        }
        this.loading = false;
      },
      error: (err: any) => {
        this.error = err.error?.detail || 'Email ou mot de passe incorrect';
        Swal.fire({
          title: 'Login Failed',
          text: this.error,
          icon: 'error',
          background: '#091623',
          color: '#fff',
          confirmButtonColor: '#ff4757'
        });
        this.loading = false;
      }
    });
  }
}
