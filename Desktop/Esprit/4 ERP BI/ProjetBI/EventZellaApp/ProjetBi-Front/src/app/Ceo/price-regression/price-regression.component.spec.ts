import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceRegressionComponent } from './price-regression.component';

describe('PriceRegressionComponent', () => {
  let component: PriceRegressionComponent;
  let fixture: ComponentFixture<PriceRegressionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PriceRegressionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriceRegressionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
