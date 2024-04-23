import { AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

export function imageValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const file = control.value as File;

        if (!file) {
            // No file is selected, so no validation error
            return null;
        }

        const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

        if (allowedTypes.includes(file.type)) {
            // File is an image, so no validation error
            return null;
        } else {
            // File is not an allowed image type, return validation error
            return { 'invalidFileType': true };
        }
    }
}