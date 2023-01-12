import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StocklocationsComponent } from './stocklocations.component';

describe('StocklocationsComponent', () => {
  let component: StocklocationsComponent;
  let fixture: ComponentFixture<StocklocationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StocklocationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StocklocationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
