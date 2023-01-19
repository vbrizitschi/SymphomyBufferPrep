import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadMinBufferComponent } from './load-min-buffer.component';

describe('LoadMinBufferComponent', () => {
  let component: LoadMinBufferComponent;
  let fixture: ComponentFixture<LoadMinBufferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadMinBufferComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoadMinBufferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
