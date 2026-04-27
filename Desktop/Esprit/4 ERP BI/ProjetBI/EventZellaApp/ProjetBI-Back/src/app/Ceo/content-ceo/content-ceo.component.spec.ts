import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentCeoComponent } from './content-ceo.component';

describe('ContentCeoComponent', () => {
  let component: ContentCeoComponent;
  let fixture: ComponentFixture<ContentCeoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ContentCeoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContentCeoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
