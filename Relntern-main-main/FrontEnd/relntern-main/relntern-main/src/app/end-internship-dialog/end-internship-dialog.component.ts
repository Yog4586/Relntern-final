import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InternService } from '../intern.service';// Adjust the path as necessary

@Component({
  selector: 'app-end-internship-dialog',
  templateUrl: './end-internship-dialog.component.html',
  styleUrls: ['./end-internship-dialog.component.css']
})
export class EndInternshipDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private internService: InternService // Inject the service
  ) {}

  onSubmit(): void { 
    console.log(this.data);
    const emailDetails = {
      fullname: this.data.fullname,
      email: this.data.email,
      startDate: this.data.startDate,
      endDate: this.data.endDate,
      domainId: this.data.domainId,
      projectname: this.data.projectname,
      mentor: this.data.mentor
    };

    this.internService.sendToMentor(emailDetails).subscribe(
      response => {
        console.log('Email sent successfully');
        // Handle success, e.g., close the dialog
      },
      error => {
        console.error('Error sending email', error);
        // Handle error
      }
    );
  }
}
