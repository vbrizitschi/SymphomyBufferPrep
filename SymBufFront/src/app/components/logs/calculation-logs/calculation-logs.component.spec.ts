import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalculationLogsComponent } from './calculation-logs.component';

describe('CalculationLogsComponent', () => {
  let component: CalculationLogsComponent;
  let fixture: ComponentFixture<CalculationLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalculationLogsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalculationLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
