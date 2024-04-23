import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { imageValidator } from 'src/shared/validators/image.validator';
import { Subscription } from 'rxjs';
import { FileuploadService } from 'src/core/services/fileupload/fileupload.service';
@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [
    CommonModule, MatCardModule, FormsModule,
    ReactiveFormsModule, MatFormFieldModule, MatButtonModule, MatInputModule
  ],
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit,OnDestroy {
  subscription1$!: Subscription | undefined;
  selectedFileName!: string;
  registerFile!: FormGroup;
  imagePreview!: string;
  private fb = inject(FormBuilder);
  private service = inject(FileuploadService);
  

  ngOnInit(): void {
    this.registerFile = this.fb.group({
      file: [null, [Validators.required, imageValidator()]],
    });
  }

  get fileControl(): FormControl {
    return this.registerFile.get("file") as FormControl;
  }

  onFileSelected(e: Event) {
    if (e.target instanceof HTMLInputElement) {
      const fileInput = e.target as HTMLInputElement;
      if (fileInput.files && fileInput.files.length > 0) {
        this.selectedFileName = fileInput.files[0].name;
        // Image preview logic
        const reader = new FileReader();
        reader.onload = () => {
          this.imagePreview = reader.result as string;
        };
        reader.readAsDataURL(fileInput.files[0]);
        this.fileControl.setValue(fileInput.files[0]);
      } else {
        this.selectedFileName = "No file selected";
      }
    }
  }

  upload() {
    this.subscription1$ = this.service.uploadFile(this.registerFile);
    this.registerFile.setValue({
      file: null
    });
    this.imagePreview = '';
    this.selectedFileName = '';
  }

  ngOnDestroy(): void {
    this.subscription1$?.unsubscribe();
  }
}
