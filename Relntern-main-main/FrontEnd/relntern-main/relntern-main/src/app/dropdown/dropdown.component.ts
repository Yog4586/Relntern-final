import { Component, HostListener, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css'],
})
export class DropdownComponent {
  @Input() roledesc: string = ''; // Role description input
  isDropdownVisible: boolean = false;

  constructor(private router: Router) {}

  toggleDropdown() {
    this.isDropdownVisible = !this.isDropdownVisible;
  }

  logout() {
    localStorage.removeItem('role');
    this.router.navigate(['']); // Redirect to login or home page
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
}
