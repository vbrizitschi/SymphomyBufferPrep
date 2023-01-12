import { TestBed } from '@angular/core/testing';

import { SymphonyDataService } from './symphony-data.service';

describe('SymphonyDataService', () => {
  let service: SymphonyDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SymphonyDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
