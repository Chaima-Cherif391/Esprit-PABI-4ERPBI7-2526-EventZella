import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardCeoComponent } from './dashboard-ceo.component';

describe('DashboardCeoComponent', () => {
  let component: DashboardCeoComponent;
  let fixture: ComponentFixture<DashboardCeoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardCeoComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DashboardCeoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});