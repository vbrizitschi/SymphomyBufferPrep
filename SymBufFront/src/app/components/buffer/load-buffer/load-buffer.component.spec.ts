import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadBufferComponent } from './load-buffer.component';

describe('LoadBufferComponent', () => {
  let component: LoadBufferComponent;
  let fixture: ComponentFixture<LoadBufferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadBufferComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoadBufferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
