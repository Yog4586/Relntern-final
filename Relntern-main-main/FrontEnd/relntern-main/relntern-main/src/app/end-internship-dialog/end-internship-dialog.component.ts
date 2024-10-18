import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InternService } from '../intern.service'; // Adjust the path as necessary

interface FileData {
  file: File; // Use the actual File object
  name: string;
  url: string;
}

interface FileMapping {
  files: FileData[];
  errorMessage: string;
}

@Component({
  selector: 'app-end-internship-dialog',
  templateUrl: './end-internship-dialog.component.html',
  styleUrls: ['./end-internship-dialog.component.css']
})
export class EndInternshipDialogComponent {
  isLoading: boolean = false; // Initialize loading state
  isSuccess: boolean = false; // Initialize success state

  fileMapping: {
    pdf: FileMapping;
    presentation: FileMapping;
    zip: FileMapping;
    txt: FileMapping;
  } = {
    pdf: { files: [], errorMessage: '' },
    presentation: { files: [], errorMessage: '' },
    zip: { files: [], errorMessage: '' },
    txt: { files: [], errorMessage: '' }
  };

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private internService: InternService // Inject the service
  ) {}

  onFileSelect(input: HTMLInputElement): void {
    input.click();
  }

  onFileChange(event: Event, type: 'pdf' | 'presentation' | 'zip' | 'txt'): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      const files = Array.from(input.files);

      files.forEach(file => {
        if (this.validateFile(file, type)) {
          const fileUrl = URL.createObjectURL(file);
          const fileData: FileData = { file, name: file.name, url: fileUrl };

          this.fileMapping[type].files.push(fileData);
        }
      });

      input.value = ''; // Clear the input value to allow re-selection of the same file
    }
  }

  validateFile(file: File, type: 'pdf' | 'presentation' | 'zip' | 'txt'): boolean {
    const validTypes: { [key: string]: string[] } = {
      pdf: ['application/pdf'],
      presentation: ['application/vnd.ms-powerpoint', 'application/vnd.openxmlformats-officedocument.presentationml.presentation'],
      zip: ['application/zip', 'application/x-zip-compressed'],
      txt: ['text/plain']
    };

    if (validTypes[type].includes(file.type)) {
      this.fileMapping[type].errorMessage = '';
      return true;
    } else {
      this.fileMapping[type].errorMessage = 'File type not correct';
      return false;
    }
  }

  removeFile(file: FileData, type: 'pdf' | 'presentation' | 'zip' | 'txt'): void {
    const filesArray = this.fileMapping[type].files;
    const index = filesArray.indexOf(file);
    if (index > -1) {
      filesArray.splice(index, 1);
      URL.revokeObjectURL(file.url); // Clean up memory
    }
  }

  // Check if all file types have at least one file
  isFormValid(): boolean {
    // Only PDF and Presentation files are mandatory
    return (
      this.fileMapping['pdf'].files.length > 0 &&
      this.fileMapping['presentation'].files.length > 0
    );
  }

  onSubmit(): void { 
    this.isLoading = true; // Set loading state to true
    console.log(this.data);
  debugger
    // Create an object with the intern details
    const emailDetails = {
        id: this.data.id,
        fullname: this.data.fullname,
        email: this.data.email,
        startDate: this.data.startDate,
        endDate: this.data.endDate,
        domainId: this.data.domainId,
        projectname: this.data.projectname,
        mentor: this.data.mentor,
        association: this.data.association
    };
  
    // Create FormData to include files and intern details
    const formData = new FormData();
    formData.append('id', emailDetails.id.toString());
    formData.append('fullname', emailDetails.fullname);
    formData.append('association', emailDetails.association);
    formData.append('endDate', emailDetails.endDate);
  
    // Append each file to FormData
    const allFiles: FileData[] = [
        ...this.fileMapping.pdf.files,
        ...this.fileMapping.presentation.files,
        ...this.fileMapping.zip.files,
        ...this.fileMapping.txt.files
    ];
  
    // Append all files to FormData with the key 'uploads'
    allFiles.forEach((fileData: FileData) => {
        console.log("Appending file to formData:", fileData.name);
        formData.append('uploads', fileData.file);
    });
  
    // Send the email details to the mentor
    this.internService.sendToMentor(emailDetails).subscribe(
        response => {
            console.log('Email sent successfully');
        },
        error => {
            console.error('Error sending email', error);
        }
    );
  
    // Send intern details along with uploaded files to the backend
    this.internService.uploadFile(formData).subscribe(
        response => {
            console.log('Intern details with files sent successfully');
            this.isLoading = false; // Reset loading state
            this.isSuccess = true; // Set success state
            // Optionally close the dialog or handle success logic
        },
        error => {
            console.error('Error sending intern details with files', error);
            this.isLoading = false; // Reset loading state
        }
    );
}
}
