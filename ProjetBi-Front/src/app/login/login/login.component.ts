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
        this.error = err.error?.detail || 'Invalid email or password';
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

  onForgotPassword() {
    Swal.fire({
      title: 'Reset Password',
      text: 'Enter your email address to receive instructions.',
      input: 'email',
      inputPlaceholder: 'email@example.com',
      showCancelButton: true,
      confirmButtonText: 'Send Instructions',
      confirmButtonColor: '#16c0de',
      background: '#091623',
      color: '#fff',
      backdrop: `rgba(0,0,0,0.8) blur(8px)`,
      customClass: {
        popup: 'glass-popup'
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        this.auth.forgotPassword(result.value).subscribe({
          next: (res: any) => {
            Swal.fire({
              title: 'Success!',
              text: res.message,
              icon: 'success',
              confirmButtonColor: '#16c0de',
              background: '#091623',
              color: '#fff'
            });
          },
          error: (err: any) => {
            Swal.fire({
              title: 'Error',
              text: err.error?.detail || 'Something went wrong.',
              icon: 'error',
              confirmButtonColor: '#ff4757',
              background: '#091623',
              color: '#fff'
            });
          }
        });
      }
    });
  }
}
