import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcBufferComponent } from './calc-buffer.component';

describe('CalcBufferComponent', () => {
  let component: CalcBufferComponent;
  let fixture: ComponentFixture<CalcBufferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalcBufferComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalcBufferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
