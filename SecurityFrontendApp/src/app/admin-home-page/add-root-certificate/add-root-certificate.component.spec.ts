import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRootCertificateComponent } from './add-root-certificate.component';

describe('AddRootCertificateComponent', () => {
  let component: AddRootCertificateComponent;
  let fixture: ComponentFixture<AddRootCertificateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddRootCertificateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRootCertificateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
