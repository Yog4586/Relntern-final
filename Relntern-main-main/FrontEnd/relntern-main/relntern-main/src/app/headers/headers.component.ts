import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { InternService } from '../intern.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogBodyComponent } from '../dialog-body/dialog-body.component';
import { UpdateDialogBodyComponent } from '../update-dialog-body/update-dialog-body.component';
import { InternprofileComponent } from '../internprofile/internprofile.component';

@Component({
  selector: 'app-headers',
  templateUrl: './headers.component.html',
  styleUrls: ['./headers.component.css'],
})
export class HeadersComponent implements OnInit {
  internDetails: any;
  isAdmin: boolean = false;
  isMentor: boolean = false;
  isintern: boolean = false;
  roledesc: any;
  isDropdownVisible: boolean = false;

  constructor(
    private router: Router,
    private internService: InternService,
    private matDialog: MatDialog
  ) {}

  isCurrentRoute(route: string): boolean {
    return this.router.url == route;
  }

  ngOnInit(): void {
    this.getActiveInterns();
    this.roledesc = localStorage.getItem('role');
    this.validaterole(this.roledesc);
  }

  validaterole(roledesc: any) {
    if (roledesc == 'admin') {
      this.isAdmin = true;
    } else if (roledesc == 'mentor') {
      this.isMentor = true;
    } else {
      this.isintern = true;
    }
  }

  getActiveInterns(): void {
    this.internService.getActiveInterns().subscribe(
      (resp) => {
        console.log(resp);
        this.internDetails = resp;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  logout() {
    localStorage.removeItem('role');
    this.router.navigate([``]);
  }

  toggleDropdown() {
    this.isDropdownVisible = !this.isDropdownVisible;
  }

  // Close the dropdown when clicking outside
  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const dropdown = document.querySelector('.dropdown');
    const userIcon = document.querySelector('.fa-user-circle');

    // Close the dropdown if the click is outside of the dropdown and the icon
    if (this.isDropdownVisible && !dropdown?.contains(target) && target !== userIcon) {
      this.isDropdownVisible = false;
    }
  }
  goToPage(page: string): void {
    // If admin, show the 'list' route (Active Interns), and for mentors, show 'internmentorlist'
    if (this.isAdmin && page === 'list') {
      this.router.navigate(['/list']); // Navigate to Active Interns for Admin
    } else if (this.isMentor && page === 'list') {
      this.router.navigate(['/internmentorlist']); // Navigate to Intern Mentor list for Mentor
    } else {
      this.router.navigate([page]); // Other routes remain the same
    }
  }
  

  navigateByRole(): void {
    // Navigate to dashboard if the user is an admin, otherwise to mentordashboard if they are a mentor
    if (this.isAdmin) {
      this.goToPage('dashboard');
    } else if (this.isMentor) {
      this.goToPage('mentordashboard');
    }
  }

  navigateBasedOnRole(): void {
    if (this.isAdmin) {
      this.router.navigate(['/list']); // Admin should see Active Interns list
    } else if (this.isMentor) {
      this.router.navigate(['/internmentorlist']); // Mentor should see their Interns list
    }
  }

  
}
