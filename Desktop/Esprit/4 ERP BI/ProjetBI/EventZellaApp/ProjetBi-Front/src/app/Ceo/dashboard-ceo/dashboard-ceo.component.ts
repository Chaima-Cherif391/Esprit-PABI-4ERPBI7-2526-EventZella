import { Component ,  OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-dashboard-ceo',
  templateUrl: './dashboard-ceo.component.html',
  styleUrl: './dashboard-ceo.component.css'
})
export class DashboardCeoComponent  implements OnInit {
 activeRoute: string = '/dashboard';

  navItems = [
    {
      label: 'Vue d\'ensemble',
      route: '/dashboard',
      badge: null,
      icon: 'overview'
    },
    {
      label: 'Événements',
      route: '/events',
      badge: 12,
      icon: 'events'
    },
    {
      label: 'Participants',
      route: '/participants',
      badge: null,
      icon: 'participants'
    },
    {
      label: 'Finances',
      route: '/finances',
      badge: null,
      icon: 'finances'
    },
    {
      label: 'Dashboard',
      route: '/content-ceo',
      badge: null,
      icon: 'analytics'
    }
  ];

  managementItems = [
    { label: 'Rapports',    route: '/reports',   badge: null },
    { label: 'Marketing',   route: '/marketing', badge: null },
    { label: 'Sponsors',    route: '/sponsors',  badge: 3   },
    { label: 'Paramètres',  route: '/settings',  badge: null }
  ];

  fullName = '';
  constructor(private router: Router , private auth: AuthService) {
    // Synchronise activeRoute avec la vraie URL courante
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.activeRoute = event.urlAfterRedirects;
      });
  }

 ngOnInit() {
    this.fullName = this.auth.getUser()?.full_name || 'CEO';
  }


  navigateTo(route: string): void {
    this.router.navigate([route]);
    this.activeRoute = route;
  }

  isActive(route: string): boolean {
    return this.activeRoute === route;
  }
}
