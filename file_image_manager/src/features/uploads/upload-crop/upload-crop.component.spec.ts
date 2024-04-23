import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadCropComponent } from './upload-crop.component';

describe('UploadCropComponent', () => {
  let component: UploadCropComponent;
  let fixture: ComponentFixture<UploadCropComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UploadCropComponent]
    });
    fixture = TestBed.createComponent(UploadCropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
