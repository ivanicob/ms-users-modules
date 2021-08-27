import { TestBed } from '@angular/core/testing';

import { JwtClientService } from './jwt-client.service';

describe('JwtClientService', () => {
  let service: JwtClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
