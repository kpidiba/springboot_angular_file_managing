import { Component, OnDestroy, OnInit, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ImageDto } from 'src/core/interfaces/ImageDto';
import { Router } from '@angular/router';
import { SweetAlert2LoaderService, SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Subscription, takeUntil } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { MatButtonModule } from '@angular/material/button';
import Swal from 'sweetalert2';
import { DestroyService } from 'src/shared/services/destroy/destroy.service';
import { FileuploadService } from 'src/core/services/fileupload/fileupload.service';

@Component({
  selector: 'app-upload-list',
  standalone: true,
  providers: [SweetAlert2LoaderService],
  imports: [CommonModule, MatPaginatorModule, MatTooltipModule, MatButtonModule, MatSortModule, MatIconModule, MatInputModule, SweetAlert2Module, MatFormFieldModule, MatTableModule],
  templateUrl: './upload-list.component.html',
  styleUrls: ['./upload-list.component.css']
})
export class UploadListComponent implements OnInit, OnDestroy {
  subscription1$!: Subscription | undefined;
  subscription2$!: Subscription | undefined;
  imagePreview!: string;
  public images!: ImageDto[];
  @ViewChild(MatPaginator) paginator !: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public dataSource!: MatTableDataSource<ImageDto>;
  displayedColumns: string[] = ['id', 'nom', 'image', 'action'];
  private service = inject(FileuploadService);
  private router = inject(Router);
  private toastr = inject(ToastrService);
  private destroyService = inject(DestroyService);

  ngOnInit(): void {
    this.getAllImages();
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  showAlert(id: number) {
    Swal.fire({
      title: "Voulez vous supprimer l'image?",
      text: "Vous ne pourrez plus revenir en arriere",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Oui, Supprimer le!",
    }).then((result) => {
      if (result.isConfirmed) {
        this.delete(id).pipe(takeUntil(this.destroyService.onDestroy$)).subscribe({
          next: () => {
            this.getAllImages();
            this.toastr.success('IMAGE SUPPRIME', "SUCCESS");
          }, error: () => {
            this.toastr.error("SUPPRESSION FAILED", "ERROR");
          }
        });;
      }
    });
  }

  getAllImages() {
    this.subscription1$ = this.service.getAllImages().subscribe({
      next: (datas) => {
        this.images = datas;
        this.dataSource = new MatTableDataSource(this.images);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }, error: () => {
        this.toastr.error("UPLOAD FAILED", "ERROR");
      }
    });
  }

  open(file:string){
    const arrayBuffer = Uint8Array.from(atob(file), c => c.charCodeAt(0)).buffer;
    const blob = new Blob([arrayBuffer]);
    const fileURL = URL.createObjectURL(blob);
      window.open(fileURL, '_blank');
    // const fileURL = URL.createObjectURL(file);
  }

  getImageSrc(file: Uint8Array): string {
    if (file) {
      return `data:image/png;base64,${file}`;
    }
    return "assets/default.png";
  }

  delete(id: number) {
    return this.service.deleteImage(id);
  }

  download(name: string) {
    this.subscription2$ = this.service.downloadImage(name);
  }

  ngOnDestroy(): void {
    this.subscription1$?.unsubscribe();
    this.subscription2$?.unsubscribe();
  }

}
