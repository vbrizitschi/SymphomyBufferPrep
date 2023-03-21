import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcBufferDialogComponent } from './calc-buffer-dialog.component';

describe('CalcBufferDialogComponent', () => {
  let component: CalcBufferDialogComponent;
  let fixture: ComponentFixture<CalcBufferDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalcBufferDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalcBufferDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
