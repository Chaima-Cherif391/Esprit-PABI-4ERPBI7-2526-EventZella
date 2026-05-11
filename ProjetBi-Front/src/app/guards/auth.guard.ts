import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }

    const requiredRole = route.data['role'];
    const requiredRoles = route.data['roles'] as string[];

    // Si un rôle simple est requis
    if (requiredRole && this.auth.getRole() !== requiredRole) {
      this.router.navigate(['/login']);
      return false;
    }

    // Si une liste de rôles est requise (il faut en avoir au moins un)
    if (requiredRoles && requiredRoles.length > 0) {
      const userRole = this.auth.getRole();
      const hasPermission = requiredRoles.some(role => role.toUpperCase() === userRole?.toUpperCase());

      if (!hasPermission) {
        this.router.navigate(['/login']);
        return false;
      }
    }

    return true;
  }
}