import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from '../../interfaces/ApiResponse';
import { environment } from 'src/environments/environment.development';
import { FormGroup } from '@angular/forms';
import { ImageDto } from '../../interfaces/ImageDto';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class FileuploadService {
  baseUrl = environment.apiUrl + "images";
  constructor(private http: HttpClient, private toastr: ToastrService) {

  }

  getAllImages() {
    return this.http.get<ImageDto[]>(`${this.baseUrl}`);
  }

  downloadImage(name: string) {
    return this.http.get(`${this.baseUrl}` + "/download/" + name, { responseType: 'blob' }).subscribe(
      {
        next: (message) => {
          const link = document.createElement('a');
          link.href = window.URL.createObjectURL(message);
          link.download = name;
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          this.toastr.success("DOWNLOAD " + name, "SUCCESS")
        }, error: () => {
          this.toastr.error("DOWNLOAD FAILED", "ERROR")
        }
      }
    );
  }

  uploadFile(register: FormGroup) {
    const formData = new FormData();
    formData.append('file', register.get('file')?.value);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.http.post<ApiResponse>(`${this.baseUrl}/upload`, formData, { headers }).subscribe(
      {
        next: (message) => {
          this.toastr.success(message.message, "SUCCESS")
        }, error: (error) => {
          this.toastr.error("UPLOAD FAILED","ERROR");
        }
      }
    );
  }

  deleteImage(id:number){
    return this.http.delete(`${this.baseUrl}` + "/delete/"+id);
  }

}
